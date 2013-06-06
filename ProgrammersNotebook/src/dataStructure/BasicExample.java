package dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is what the example should look like
 */
public class BasicExample implements IExample {

	private List<IPerson> authors;
	
	/**
	 * Categories the example belongs to
	 */
	private ArrayList<ICategory> categoryList = new ArrayList<ICategory>();
	private String code;
	private String description;
	private Long id;

	/**
	 * Language in which the example is written
	 */
	private String language;
	private IPerson owner;
	
	/**
	 * Where is the source of the example
	 */
	private String source;
	/**
	 * all the tags include in the example
	 */
	private ArrayList<String> tags = new ArrayList<String>();
	private String title;
	

	public BasicExample() {
		authors = new LinkedList<IPerson>();
		owner = null;
		this.id = -1L;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCategory(ICategory category) {
		if (!this.isInCategory(category)) {
			categoryList.add(category);
			category.addCodeExample(this);
		}
	}

	/**
	 * add the given tag to the example
	 * 
	 * @param tag
	 */
	public void addTag(String tag) {
		this.tags.add(tag);
	}

	@Override
	public int assignId(Long id) {
		if (this.id != -1)
			return 1; // return 1 if already assigned
		else
			this.id = id;
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int assignOwner(IPerson owner) {
		if (this.owner != null)
			return 1; // return 1 if already assigned
		else
			this.owner = owner;
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IPerson> getAuthors() {
		return authors;
	}

	@Override
	/**
	 * Gets the list of categories the example belongs to.
	 * @return LinkedList<String>
	 */
	public List<ICategory> getCategories() {
		return categoryList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	/**
	 * Gets the language the example was written in
	 * @return String
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPerson getOwner() {
		return owner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	/**
	 * {@inheritDoc}
	 */
	public Long getOwnerId() {
		return this.owner.getId();
	}

	@Override
	/**
	 * Gets the source of an example
	 * @return String
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * a getter function to get the tags of the example
	 * 
	 * @return LinkedList<String>
	 */
	@Override
	public ArrayList<String> getTags() {
		return this.tags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * helper function check if the example (this) is already in category
	 * 
	 * @param category
	 *            the category wanted to be check
	 * @return true if the example is in category. false otherwise
	 */
	private boolean isInCategory(ICategory category) {
		return categoryList.contains(category);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAuthors(List<IPerson> authors) {
		this.authors = authors;
	}

	@Override
	/**
	 * A setter function to set the tag property of the example 
	 * to the given categories.
	 * 
	 * @param tags
	 */
	public void setCategories(ArrayList<ICategory> categories) {
		this.categoryList = categories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	/**
	 * A setter function to set the language property of the example
	 * to the given language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language.toLowerCase();
	}

	@Override
	/**
	 * A setter fucntion to set the source property of the example
	 * to the given source
	 * 
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * a setter function to set the tag property of the example to the given
	 * tags
	 * 
	 * @param tags
	 */
	@Override
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasicExample transferFromBuffer(BufferEntry e) {
		this.authors = e.getAuthors();
		this.code = e.getCode();
		this.description = e.getDescription();
		this.source = e.getSource();
		this.language = e.getLanguage();
		this.title = e.getTitle();
		return this;
	}

	@Override
	public void addTags(String tag) {
		// @TODO check existence of tag in tags
		this.tags.add(tag);

	}

}
