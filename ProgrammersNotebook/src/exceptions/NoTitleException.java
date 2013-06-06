package exceptions;

/**
 * Used to tell user interface that the title field received from UI is empty
 */
public class NoTitleException extends PENException {

	public NoTitleException(String message) {
		super(message);
	}

}
