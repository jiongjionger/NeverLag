package cn.jiongjionger.neverlag.hardware;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WMIPowerShell implements WMIStub {

	private static final String NAMESPACE_PARAM = "-Namespace ";
	private static final String COMPUTERNAME_PARAM = "-ComputerName ";
	private static final String GETWMIOBJECT_COMMAND = "Get-WMIObject ";

	private static String executeCommand(String command) throws WMIException {
		String commandResponse = null;
		PowerShell powerShell = null;
		try {
			powerShell = PowerShell.openSession();
			Map<String, String> config = new HashMap<String, String>();
			config.put("maxWait", "20000");
			PowerShellResponse psResponse = powerShell.executeCommand(command);
			if (psResponse.isError()) {
				throw new WMIException("WMI operation finished in error: " + psResponse.getCommandOutput());
			}
			commandResponse = psResponse.getCommandOutput().trim();
			powerShell.close();
		} catch (PowerShellNotAvailableException ex) {
			throw new WMIException(ex.getMessage(), ex);
		} finally {
			if (powerShell != null) {
				powerShell.close();
			}
		}
		return commandResponse;
	}

	public String listClasses(String namespace, String computerName) throws WMIException {
		String namespaceString = "";
		if (!"*".equals(namespace)) {
			namespaceString += NAMESPACE_PARAM + namespace;
		}
		return executeCommand(GETWMIOBJECT_COMMAND + namespaceString + " -List | Sort Name");
	}

	public String listProperties(String wmiClass, String namespace, String computerName) throws WMIException {
		String command = initCommand(wmiClass, namespace, computerName);
		command += " | ";
		command += "Select-Object * -excludeproperty \"_*\" | ";
		command += "Get-Member | select name | format-table -hidetableheader";
		return executeCommand(command);
	}

	public String listObject(String wmiClass, String namespace, String computerName) throws WMIException {
		String command = initCommand(wmiClass, namespace, computerName);
		command += " | ";
		command += "Select-Object * -excludeproperty \"_*\" | ";
		command += "Format-List *";
		return executeCommand(command);
	}

	public String queryObject(String wmiClass, List<String> wmiProperties, List<String> conditions, String namespace, String computerName) throws WMIException {
		String command = initCommand(wmiClass, namespace, computerName);

		List<String> usedWMIProperties;
		if (wmiProperties == null || wmiProperties.isEmpty()) {
			usedWMIProperties = Collections.singletonList("*");
		} else {
			usedWMIProperties = wmiProperties;
		}
		command += " | ";
		command += "Select-Object " + WMI4JavaUtil.join(", ", usedWMIProperties) + " -excludeproperty \"_*\" | ";
		if (conditions != null && !conditions.isEmpty()) {
			for (String condition : conditions) {
				command += "Where-Object -FilterScript {" + condition + "} | ";
			}
		}
		command += "Format-List *";
		return executeCommand(command);
	}

	private String initCommand(String wmiClass, String namespace, String computerName) {
		String command = GETWMIOBJECT_COMMAND + wmiClass + " ";
		if (!"*".equals(namespace)) {
			command += NAMESPACE_PARAM + namespace + " ";
		}
		if (!computerName.isEmpty()) {
			command += COMPUTERNAME_PARAM + computerName + " ";
		}
		return command;
	}
}