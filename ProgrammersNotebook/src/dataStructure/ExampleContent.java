package dataStructure;

/**
 * @author Team3
 * 
 * The ExampleContent class contains the code
 * of the examples and allows the user to access
 * and edit code.
 */
public class ExampleContent implements IContent
{
	/**
	 * Code in the example.
	 */
	private String code;
	
	/**
	 * Getter function to get code from the example
	 * @return String
	 */
	public String getCode()
	{
		return this.code;
	}
	
	/**
	 * Set up the code to the example
	 * @param code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
	
	/**
	 * Default constructor for the ExampleContent class
	 */
	public ExampleContent(String code)
	{
		this.code = code;
	}
}
