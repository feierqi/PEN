package database;

import java.util.ArrayList;
import java.util.List;

import dataStructure.*;
import exceptions.NoIdAvailableException;

public interface IDatabase {
	public void store(IEntry e);
	
	public List<IEntry> getAll();
	
	//public List<IExample> getByHeader(IHeader head); // null is wildcard (this might be an issue, we can change later)
	//public List<IExample> getByHeader(String title, List<IPerson> authors); // null is wildcard (this might be an issue, we can change later)
	
	public List<IExample> getByHeader(String title, IPerson owner); // null is wildcard (this might be an issue, we can change later)
	
 	public List<IExample> getByKeyword(String key); // This could be a cool function to implement (match by keyword in body or title)
	// More "getBy" functions to come based on metadata choices
	
 	public void delete(IEntry e);
	
	public void close(); // Close the connection
	
	/**
	 * @return A list of all code examples in the database
	 */
	public List<IExample> getAllExample();
	
	/**
	 * Checks to see if a category name has already been used in the database
	 * @param name
	 * @return true if the name given is already taken by another category
	 * false otherwise 
	 */
	public boolean isNameRepeat (String name);
	
	/**
	 * Returns a list of all categories in the database
	 * @return A list of all category names as Strings
	 */
	public ArrayList<String> listCategoryNames();
	
	/**
	 * @return A list of all categories in the database
	 */
	List<ICategory> getAllCategory();

	Long getNewId();

	IEntry getByID(Long id);

	IEntry getCategoryByID(Long id);

	IEntry getExampleByID(Long id);

	Long generateEntryId() throws NoIdAvailableException;
}
