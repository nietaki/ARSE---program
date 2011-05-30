package session;

public class LoginErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginErrorException(String message) {
		super(message);
	}

}
