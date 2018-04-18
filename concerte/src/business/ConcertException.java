package business;

@SuppressWarnings("serial")
public class ConcertException extends Exception {

	public ConcertException() {
		super();
	}

	public ConcertException(Exception ex) {
		super(ex);
	}

	public ConcertException(String msg) {
		super(msg);
	}
}