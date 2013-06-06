package dataStructure;

/**
 * @author Thanaporn
 * IEntry should be an entry
 * covering anything from examples/codes/tests to folders
 * 
 */
public interface IEntry 
{	
	/**
	 * @return The owner of an entry.
	 */
	IPerson getOwner();
	
	Long getId();
	
	Long getOwnerId();
	
	/**
	 * Assigns an owner for an entry. It will do nothing if the entry already has an owner
	 * @param owner The person who will own the entry
	 * @return 0 if success, 1 if entry already has an owner
	 */
	int assignOwner(IPerson owner);
	
	/**
	 * Assigns an id for an entry. It will do nothing if the entry already has an id
	 * @param id id to be assigned
	 * @return 0 if success, 1 if entry already has an id
	 */
	int assignId(Long id);
}
