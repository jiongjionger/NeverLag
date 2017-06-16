package cn.jiongjionger.neverlag.hardware;

public class WMIException extends RuntimeException {
	private static final long serialVersionUID = -5992635300188042890L;

	public WMIException(String message) {
		super(message);
	}

	public WMIException(String message, Throwable cause) {
		super(message, cause);
	}

	public WMIException(Throwable cause) {
		super(cause);
	}

}