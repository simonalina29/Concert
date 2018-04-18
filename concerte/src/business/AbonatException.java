package business;

@SuppressWarnings("serial")
public class AbonatException extends Exception {

	public AbonatException() {
		super();
	}

	public AbonatException(Exception ex) {
		super(ex);
	}

	public AbonatException(String msg) {
		super(msg);
	}
}