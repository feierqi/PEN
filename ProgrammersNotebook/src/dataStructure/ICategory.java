package dataStructure;

import java.util.List;

public interface ICategory extends IEntry{
	
	/**
	 * @return Returns the title of the category. For example: "Web" or "Mobile".
	 */
	String getTitle();
	
	/**
	 * Sets the title of a category.
	 * @param title The title of the category
	 */
	void setTitle(String title);
	
	/**
	 * @return Returns the description of a category.
	 */
	String getDescription();
	
	/**
	 * Sets a description of a category.
	 * @param description The new description
	 */
	void setDescription(String description);
	
	/**
	 * Adds a new example to this category.
	 * @param example The example entry to add.
	 */
	void addCodeExample(IExample example);

	List<IExample> getExampleList();
}
