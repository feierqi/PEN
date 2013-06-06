package dataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thanaporn
 *w
 * This object will accumulate all fields of all kind of IEntry
 * to be a buffer entry
 * this should help web app - controller interaction
 * (web app only need to send data once using this buffer)
 */
public class BufferEntry 
{

	private String categoryName;
	
	private ArrayList<String> categoryNameList;

	public String getCategoryName() {
		return categoryName;
	}
	public ArrayList<String> getCategoryNameList() {
		return categoryNameList;
	}
	
	public void setCategoryNameList(ArrayList<String> categoryNameList) {
		this.categoryNameList = categoryNameList;
	}
	
	public void addCategoryNameList(String categoryName) {
		this.categoryNameList.add(categoryName);
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	private List<IPerson> authors;
	/**
	 * Categories the example belongs to
	 */
	private ArrayList<Category> categories = new ArrayList<Category>();
	
	private String code;

	private String description;

	/**
	 * Language in which the example is written
	 */
	private String language;
	
	private IPerson owner;
	
	private ExampleProperties properties;
	/**
	 * Where is the source of the example
	 */
	private String source;
	private String title;
	
	public BufferEntry() {
		this.authors = new ArrayList<IPerson>();
		this.categories = new ArrayList<Category>();
	}
	public void addAuthor(IPerson person) {
		this.authors.add(person);
	}
	
	public void addCategory(Category category) {
		this.categories.add(category);
	}
	
	public List<IPerson> getAuthors() {
		return authors;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}	
	public String getLanguage() {
		return language;
	}
	public IPerson getOwner() {
		return owner;
	}
	public ExampleProperties getProperties() {
		return properties;
	}
	public String getSource() {
		return source;
	}
	public String getTitle() {
		return title;
	}
	public void setAuthors(List<IPerson> authors) {
		this.authors = authors;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setOwner(IPerson owner) {
		this.owner = owner;
	}
	public void setProperties(ExampleProperties properties) {
		this.properties = properties;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
