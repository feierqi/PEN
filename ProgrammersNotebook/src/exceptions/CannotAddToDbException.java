package exceptions;

/**
 * Thrown by anything except user interface classes to tell user interface that
 * the object cannot be added to database for some reasons
 * If more specific exception can be used (NoIdAvailableException, NoTitleException, etc),
 * those exceptions are recommended.
 */
public class CannotAddToDbException extends PENException
{
	public CannotAddToDbException(String message) {
		super(message);
	}
}
