package cn.jiongjionger.neverlag.hardware;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PowerShell {

	private Process p;
	private PrintWriter commandWriter;

	private boolean closed = false;
	private ExecutorService threadpool;

	private int maxThreads = 3;
	private int waitPause = 10;
	private long maxWait = 10000;
	private boolean remoteMode = false;

	private boolean scriptMode = false;
	public static final String END_SCRIPT_STRING = "--END-JPOWERSHELL-SCRIPT--";

	private PowerShell() {
	}

	private PowerShell initalize() throws PowerShellNotAvailableException {
		String codePage = PowerShellCodepage.getIdentifierByCodePageName(Charset.defaultCharset().name());
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp", codePage, ">>", "null", "&", "powershell.exe", "-NoExit", "-Command", "-");
		try {
			p = pb.start();
		} catch (IOException ex) {
			throw new PowerShellNotAvailableException("Cannot execute PowerShell.exe. Please make sure that it is installed in your system", ex);
		}
		commandWriter = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(p.getOutputStream())), true);
		this.threadpool = Executors.newFixedThreadPool(this.maxThreads);
		return this;
	}

	public static PowerShell openSession() throws PowerShellNotAvailableException {
		PowerShell powerShell = new PowerShell();
		return powerShell.initalize();
	}

	public PowerShellResponse executeCommand(String command) {
		Callable<String> commandProcessor = new PowerShellCommandProcessor("standard", p.getInputStream(), this.maxWait, this.waitPause, this.scriptMode);
		Callable<String> commandProcessorError = new PowerShellCommandProcessor("error", p.getErrorStream(), this.maxWait, this.waitPause, false);
		String commandOutput = "";
		boolean isError = false;
		boolean timeout = false;
		Future<String> result = threadpool.submit(commandProcessor);
		Future<String> resultError = threadpool.submit(commandProcessorError);
		if (this.remoteMode) {
			command = completeRemoteCommand(command);
		}
		commandWriter.println(command);
		try {
			while (!result.isDone() && !resultError.isDone()) {
				Thread.sleep(this.waitPause);
			}
			if (result.isDone()) {
				if (((PowerShellCommandProcessor) commandProcessor).isTimeout()) {
					timeout = true;
				} else {
					commandOutput = result.get();
				}
			} else {
				isError = true;
				commandOutput = resultError.get();
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when processing PowerShell command", ex);
		} catch (ExecutionException ex) {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when processing PowerShell command", ex);
		} finally {
			((PowerShellCommandProcessor) commandProcessor).close();
			((PowerShellCommandProcessor) commandProcessorError).close();
		}

		return new PowerShellResponse(isError, commandOutput, timeout);
	}

	public static PowerShellResponse executeSingleCommand(String command) {
		PowerShell session = null;
		PowerShellResponse response = null;
		try {
			session = PowerShell.openSession();
			response = session.executeCommand(command);
		} catch (PowerShellNotAvailableException ex) {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "PowerShell not available", ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	private File createWriteTempFile(BufferedReader srcReader) {
		BufferedWriter tmpWriter = null;
		File tmpFile = null;
		try {

			tmpFile = File.createTempFile("psscript_" + new Date().getTime(), ".ps1");
			if (tmpFile == null || !tmpFile.exists()) {
				return null;
			}
			tmpWriter = new BufferedWriter(new FileWriter(tmpFile));
			String line;
			while (srcReader != null && (line = srcReader.readLine()) != null) {
				tmpWriter.write(line);
				tmpWriter.newLine();
			}
			tmpWriter.write("Write-Host \"" + END_SCRIPT_STRING + "\"");
		} catch (IOException ioex) {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error while writing temporary PowerShell script", ioex);
		} finally {
			try {
				if (srcReader != null) {
					srcReader.close();
				}
				if (tmpWriter != null) {
					tmpWriter.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when processing temporary PowerShell script", ex);
			}
		}
		return tmpFile;
	}

	public PowerShellResponse executeScript(String scriptPath) {
		return executeScript(scriptPath, "");
	}

	public PowerShellResponse executeScript(String scriptPath, String params) {
		BufferedReader srcReader = null;
		File scriptToExecute = new File(scriptPath);
		if (!scriptToExecute.exists()) {
			return new PowerShellResponse(true, "Wrong script path: " + scriptToExecute, false);
		}
		try {
			srcReader = new BufferedReader(new FileReader(scriptToExecute));
		} catch (FileNotFoundException fnfex) {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when processing PowerShell script: file not found", fnfex);
		}
		return executeScript(srcReader, params);
	}

	public PowerShellResponse executeScript(BufferedReader srcReader) {
		return executeScript(srcReader, "");
	}

	public PowerShellResponse executeScript(BufferedReader srcReader, String params) {
		if (srcReader != null) {
			File tmpFile = createWriteTempFile(srcReader);
			if (tmpFile != null) {
				this.scriptMode = true;
				return executeCommand(tmpFile.getAbsolutePath() + " " + params);
			} else {
				return new PowerShellResponse(true, "Cannot create temp script file!", false);
			}
		} else {
			Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Script buffered reader is null!");
			return new PowerShellResponse(true, "Script buffered reader is null!", false);
		}

	}

	public void close() {
		if (!this.closed) {
			try {
				Future<String> closeTask = threadpool.submit(new Callable<String>() {
					public String call() throws Exception {
						commandWriter.println("exit");
						p.waitFor();
						return "OK";
					}
				});
				waitUntilClose(closeTask);
			} catch (InterruptedException ex) {
				Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when when closing PowerShell", ex);
			} finally {
				try {
					p.getInputStream().close();
					p.getErrorStream().close();
				} catch (IOException ex) {
					Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when when closing streams", ex);
				}
				commandWriter.close();
				if (this.threadpool != null) {
					try {
						this.threadpool.shutdownNow();
						this.threadpool.awaitTermination(5, TimeUnit.SECONDS);
					} catch (InterruptedException ex) {
						Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when when shutting thread pool", ex);
					}
				}
				this.closed = true;
			}
		}
	}

	private void waitUntilClose(Future<String> task) throws InterruptedException {
		int closingTime = 0;
		while (!task.isDone()) {
			if (closingTime > maxWait) {
				Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error when closing PowerShell: TIMEOUT!");
				break;
			}
			Thread.sleep(this.waitPause);
			closingTime += this.waitPause;
		}
	}

	private String completeRemoteCommand(String command) {
		return command + ";Write-Host \"\"";
	}
}
