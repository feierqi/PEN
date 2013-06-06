package exceptions;

/**
 * Superclass for all exception used in PEN project
 * This class is abstract.
 */
public abstract class PENException extends Exception{

	private String message;

	public PENException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
