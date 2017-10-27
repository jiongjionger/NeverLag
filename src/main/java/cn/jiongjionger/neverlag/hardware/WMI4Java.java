package cn.jiongjionger.neverlag.hardware;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WMI4Java {
	private static final String NEWLINE_REGEX = "\\r?\\n";
	private static final String SPACE_REGEX = "\\s+";
	private static final String GENERIC_ERROR_MSG = "Error calling WMI4Java";

	public static WMI4Java get() {
		return new WMI4Java();
	}

	private String namespace = "*";
	private String computerName = ".";

	private boolean forceVBEngine = false;
	List<String> properties = null;

	List<String> filters = null;

	private WMI4Java() {
	}

	public WMI4Java computerName(String computerName) {
		this.computerName = computerName;
		return this;
	}

	public WMI4Java filters(List<String> filters) {
		this.filters = filters;
		return this;
	}

	public String getRawWMIObjectOutput(String wmiClass) throws WMIException {
		try {
			if (this.properties != null || this.filters != null) {
				return getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace,
								this.computerName);
			} else {
				return getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
			}
		} catch (WMIException ex) {
			Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, GENERIC_ERROR_MSG, ex);
			throw new WMIException(ex);
		}
	}

	public String getRawWMIObjectOutput(WMIClass wmiClass) {
		return getRawWMIObjectOutput(wmiClass.getName());
	}

	public Map<String, String> getWMIObject(String wmiClass) throws WMIException {
		Map<String, String> foundWMIClassProperties = new HashMap<>();
		try {
			String rawData;
			if (this.properties != null || this.filters != null) {
				rawData = getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace,
						this.computerName);
			} else {
				rawData = getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
			}

			String[] dataStringLines = rawData.split(NEWLINE_REGEX);

			for (final String line : dataStringLines) {
				if (!line.isEmpty()) {
					String[] entry = line.split(":");
					if (entry != null && entry.length == 2) {
						foundWMIClassProperties.put(entry[0].trim(), entry[1].trim());
					}
				}
			}
			return foundWMIClassProperties;
		} catch (WMIException ex) {
			Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, GENERIC_ERROR_MSG, ex);
			throw new WMIException(ex);
		}
	}

	public Map<String, String> getWMIObject(WMIClass wmiClass) {
		return getWMIObject(wmiClass.getName());
	}

	public List<Map<String, String>> getWMIObjectList(String wmiClass) throws WMIException {
		List<Map<String, String>> foundWMIClassProperties = new ArrayList<>();
		try {
			String rawData;
			if (this.properties != null || this.filters != null) {
				rawData = getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace,
						this.computerName);
			} else {
				rawData = getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
			}

			String[] dataStringObjects = rawData.split(NEWLINE_REGEX + NEWLINE_REGEX);
			for (String dataStringObject : dataStringObjects) {
				String[] dataStringLines = dataStringObject.split(NEWLINE_REGEX);
				Map<String, String> objectProperties = new HashMap<>();
				for (final String line : dataStringLines) {
					if (!line.isEmpty()) {
						String[] entry = line.split(":");
						if (entry.length == 2) {
							objectProperties.put(entry[0].trim(), entry[1].trim());
						}
					}
				}
				foundWMIClassProperties.add(objectProperties);
			}
			return foundWMIClassProperties;
		} catch (WMIException ex) {
			Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, GENERIC_ERROR_MSG, ex);
			throw new WMIException(ex);

		}
	}

	public List<Map<String, String>> getWMIObjectList(WMIClass wmiClass) {
		return getWMIObjectList(wmiClass.getName());
	}

	private WMIStub getWMIStub() {
		if (this.forceVBEngine) {
			return new WMIVBScript();
		} else {
			return new WMIPowerShell();
		}
	}

	public List<String> listClasses() throws WMIException {
		Set<String> wmiClasses = new LinkedHashSet<>();
		String rawData;
		try {
			rawData = getWMIStub().listClasses(this.namespace, this.computerName);

			String[] dataStringLines = rawData.split(NEWLINE_REGEX);
			for (String line : dataStringLines) {
				if (!line.isEmpty() && !line.startsWith("_")) {
					String[] infos = line.split(SPACE_REGEX);
					wmiClasses.addAll(Arrays.asList(infos));
				}
			}

			return new ArrayList<>(wmiClasses);
		} catch (Exception ex) {
			Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, GENERIC_ERROR_MSG, ex);
			throw new WMIException(ex);
		}
	}

	public List<String> listProperties(String wmiClass) throws WMIException {
		List<String> foundPropertiesList = new ArrayList<>();
		try {
			String rawData = getWMIStub().listProperties(wmiClass, this.namespace, this.computerName);

			String[] dataStringLines = rawData.split(NEWLINE_REGEX);

			for (final String line : dataStringLines) {
				if (!line.isEmpty()) {
					foundPropertiesList.add(line.trim());
				}
			}

			List<String> notAllowed = Arrays.asList("Equals", "GetHashCode", "GetType", "ToString");
			foundPropertiesList.removeAll(notAllowed);

			return foundPropertiesList;
		} catch (Exception ex) {
			Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, GENERIC_ERROR_MSG, ex);
			throw new WMIException(ex);
		}
	}

	public WMI4Java namespace(String namespace) {
		this.namespace = namespace;
		return this;
	}

	public WMI4Java PowerShellEngine() {
		this.forceVBEngine = false;
		return this;
	}

	public WMI4Java properties(List<String> properties) {
		this.properties = properties;
		return this;
	}

	public WMI4Java VBSEngine() {
		this.forceVBEngine = true;
		return this;
	}
}
