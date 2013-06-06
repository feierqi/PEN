package dataStructure;

import java.util.ArrayList;

/**
 * Getters and setters for managing properties of an example
 */
public interface IProperties 
{
	/**
	 * Gets the source of an example
	 * @return String
	 */
	public String getSource();
	
	/**
	 * A setter function to set the source property of the example
	 * to the given source
	 * 
	 * @param source
	 */
	public void setSource(String source);
	
	/**
	 * Gets the language the example was written in
	 * @return String
	 */
	public String getLanguage();
	
	/**
	 * A setter function to set the language property of the example
	 * to the given language
	 * 
	 * @param language
	 */
	public void setLanguage(String language);

	/**
	 * Gets the list of categories the example belongs to.
	 * @return LinkedList<String>
	 */
	public ArrayList<Category> getCategories();
	
	/**
	 * A setter function to set example to belong to
	 * the given categories
	 * 
	 * @param tags
	 */
	public void setCategories(ArrayList<Category> categories);
	
	/**
	 * Update the properties so the example belongs to the given category
	 * 
	 * @param category The category to add the code example to.
	 */
	public void addCategory(Category category);
}