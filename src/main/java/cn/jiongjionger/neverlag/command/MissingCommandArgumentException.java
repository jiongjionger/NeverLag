package cn.jiongjionger.neverlag.command;

public class MissingCommandArgumentException extends RuntimeException {
	public static final MissingCommandArgumentException INSTANCE = new MissingCommandArgumentException(true);
	private boolean disableStacktrace = false;

	private MissingCommandArgumentException(boolean disableStacktrace) {
		this.disableStacktrace = disableStacktrace;
	}

	public MissingCommandArgumentException() {
	}

	public MissingCommandArgumentException(String msg) {
		super(msg);
	}

	@Override
	public Throwable fillInStackTrace() {
		if (!disableStacktrace)
			return super.fillInStackTrace();
		else
			return this;
	}
}
