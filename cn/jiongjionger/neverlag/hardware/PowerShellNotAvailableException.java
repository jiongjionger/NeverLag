package cn.jiongjionger.neverlag.hardware;

import java.io.IOException;

public class PowerShellNotAvailableException extends IOException {
	private static final long serialVersionUID = 8387251378765251753L;

	PowerShellNotAvailableException() {
	}

	PowerShellNotAvailableException(String message) {
		super(message);
	}

	PowerShellNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	PowerShellNotAvailableException(Throwable cause) {
		super(cause);
	}
}
