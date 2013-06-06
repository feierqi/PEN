package dataStructure;

import java.util.List;

/**
 * @author Thanaporn
 * An instance of IHeader should contains data that identify the IEntry
 * (like name and author/owner)
 * IHeader should give IEntry uniqueness.
 */
public interface IHeader 
{
	/**
	 * The getter function to get the title of the example
	 * @return String
	 */
	String getTitle();
	
	/**
	 * Set the title of the example to the given string
	 * @param String title
	 */
	void setTitle(String t);
	
	/**
	 * The getter function to get the authors of the examples
	 * @return List<IPerson>
	 */
	List<IPerson> getAuthors();
	
	/**
	 * Set the authors of the example to the given authors
	 * @param List<IPerson> The authors for the example
	 */
	void setAuthors(List<IPerson> a);
}
