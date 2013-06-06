package gui;

import dataStructure.*;

public interface IUserInterface {
	void show();
	void init();
	/**
	 * gather any information possible from the user interface
	 * and put them together in a BufferEntry
	 * used as a buffer object to add another object
	 * @return a BufferEntry containing information gathered from user interface
	 * or null if the user doesn't input correctly
	 */
	BufferEntry getBufferEntry();

}
