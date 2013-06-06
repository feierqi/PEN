package exceptions;

/**
 * This exception is thrown when there is no unique id (<maxID) available for new object
 */
public class NoIdAvailableException extends PENException {

	final long maxID;
	
	/**
	 * @param maxID is the max value of ID database will assign to object
	 * @param message additional information about the error (apart from maxID)
	 */
	public NoIdAvailableException(long maxID, String message) {
		super(message);
		this.maxID = maxID;
	}

}
