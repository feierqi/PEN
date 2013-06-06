package exceptions;

/**
 * Used to tell user interface that the code field received from UI is empty
 */
public class NoCodeException extends PENException {

	public NoCodeException(String message) {
		super(message);
	}

}
