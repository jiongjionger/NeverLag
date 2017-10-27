package cn.jiongjionger.neverlag.hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PowerShellCommandProcessor implements Callable<String> {

	private static final String CRLF = "\r\n";
	private final BufferedReader reader;
	private final String name;
	private boolean closed = false;
	private boolean timeout = false;
	private boolean scriptMode = false;
	private final long maxWait;
	private final int waitPause;

	public PowerShellCommandProcessor(String name, InputStream inputStream, long maxWait, int waitPause, boolean scriptMode) {
		this.reader = new BufferedReader(new InputStreamReader(
				inputStream));
		this.name = name;
		this.maxWait = maxWait;
		this.waitPause = waitPause;
		this.scriptMode = scriptMode;
	}

	@Override
	public String call() throws IOException, InterruptedException {
		StringBuilder powerShellOutput = new StringBuilder();
		try {
			if (startReading()) {
				readData(powerShellOutput);
			}
		} catch (IOException ioe) {
			Logger.getLogger(PowerShellCommandProcessor.class.getName()).log(Level.SEVERE, "Unexpected error reading PowerShell output", ioe);
			return ioe.getMessage();
		} catch (Exception e) {
			Logger.getLogger(PowerShellCommandProcessor.class.getName()).log(Level.SEVERE, "Unexpected error reading PowerShell output", e);
		}
		return powerShellOutput.toString();
	}

	public void close() {
		this.closed = true;
	}

	private boolean continueReading() throws IOException, InterruptedException {
		Thread.sleep(this.waitPause);
		return this.reader.ready();
	}

	public String getName() {
		return this.name;
	}

	public boolean isTimeout() {
		return this.timeout;
	}

	private void readData(StringBuilder powerShellOutput) throws IOException {
		String line;
		while (null != (line = this.reader.readLine())) {
			if (this.scriptMode) {
				if (line.equals(PowerShell.END_SCRIPT_STRING)) {
					break;
				}
			}
			powerShellOutput.append(line).append(CRLF);
			if (!this.scriptMode) {
				try {
					if (!continueReading() || this.closed) {
						break;
					}
				} catch (InterruptedException ex) {
					Logger.getLogger(PowerShellCommandProcessor.class.getName()).log(Level.SEVERE, "Error executing command and reading result", ex);
				}
			}
		}
	}

	private boolean startReading() throws IOException, InterruptedException {
		int timeWaiting = 0;
		while (!this.reader.ready()) {
			Thread.sleep(this.waitPause);
			timeWaiting += this.waitPause;
			if ((timeWaiting > this.maxWait) || this.closed) {
				this.timeout = timeWaiting > this.maxWait;
				return false;
			}
		}
		return true;
	}
}