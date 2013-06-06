package dataStructure;

import java.util.ArrayList;

/**
 * @author Thanaporn
 * Properties about an example. 
 */
public class ExampleProperties implements IProperties
{
	/**
	 * Language in which the example is written
	 */
	private String language;
	
	/**
	 * Where is the source of the example
	 */
	private String source;
	
	/**
	 * Categories the example belongs to
	 */
	private ArrayList<Category> categoryList = new ArrayList<Category>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLanguage()
	{
		return this.language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSource()
	{
		return this.source;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Category> getCategories()
	{
		return this.categoryList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCategories(ArrayList<Category> categories)
	{
		this.categoryList = categories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
	}
}
