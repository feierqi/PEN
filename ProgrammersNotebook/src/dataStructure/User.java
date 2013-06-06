package dataStructure;

/**
 * @author Thanaporn IUser should be used to identify user
 */
public class User implements IPerson {
	/**
	 * The name of the user
	 */
	private String name;

	private Long id;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void assignName(String name) {
		if (this.name != null) {
			// throw exception
		} else
			this.name = name;
	}
	
	public int assignId(Long id) {
		if (this.id != -1)
			return 1; // return 1 if already assigned
		else
			this.id = id;
		return 0;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
}
