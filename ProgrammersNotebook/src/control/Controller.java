package control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import database.*;
import dataStructure.*;
import exceptions.CannotAddToDbException;
import exceptions.NoTitleException;
import exceptions.PENException;
import gui.IUserInterface;

/**
 * 
 * @author Thanaporn can run (sort of) MockUI0 (anjali) and MockUI1 and MockUI2
 *         (Chrissy) retrieve function is not included in the controller (yet)
 *         MockUI0 has a prototype of retrieve function see MockUI0 for more
 *         info
 */
public class Controller implements IController {
	IDatabase db;
	IUserInterface ui;

	public Controller(IDatabase db, IUserInterface ui) {
		this.db = db;
		this.ui = ui;
	}

	@Override
	/**
	 * return the added Example after it's added
	 */
	public BasicExample addBasicExample(BufferEntry buf) throws PENException {
		if ((buf.getTitle() == null)||buf.getTitle().equals("")) {
			throw (new NoTitleException("Example must have a title."));
		}
		if ((buf.getCode() == null)||buf.getCode().equals("")) {
			throw (new NoTitleException("Example must have a code."));
		}

		BasicExample e = new BasicExample();
		e.setTitle(buf.getTitle());
		e.setCode(buf.getCode());
		e.setAuthors(buf.getAuthors());
		e.setLanguage(buf.getLanguage());
		e.setSource(buf.getSource());
		e.setDescription(buf.getDescription());
		this.addToDB(e);
		return e;
	}

	public void addToDB(IEntry e) {
		db.store(e);
	}

	public List<IEntry> getAllinDB() {
		return db.getAll();
	}

	public List<String> getTitleList() {
		List<IExample> listE = db.getAllExample();
		List<String> listH = new ArrayList<String>();
		for (int i = 0; i < listE.size(); i++) {
			listH.add(listE.get(i).getTitle());
		}
		return listH;
	}

	public void close() {
		db.close();

	}

	/**
	 * @author Awiovanna, kirkgrimsley, dmulcahy, pren, tpatikorn, jchines
	 *         addCategory takes in a buffer object (entered from the GUI) and
	 *         creates a new category, which is pushed to the database. The
	 *         category is initially empty, meaning it has no code entries
	 *         associated with it.
	 * @param buf
	 * @throws CannotAddToDbException
	 */
	public void addCategory(BufferEntry buf) throws PENException {
		if ((buf.getTitle() == null)||buf.getTitle().equals("")) {
			throw (new NoTitleException("Category must have a title."));
		} else if (db.isNameRepeat(buf.getTitle())) {
			throw (new CannotAddToDbException("The title \"" + buf.getTitle()
					+ "\" for category is taken."));
		} else {
			Category newC = new Category(buf.getTitle(), buf.getDescription());
			db.store(newC);
		}
	}
	
	@Override
	public List<IExample> getAllExampleinDB() {
		return db.getAllExample();
	}
	
	@Override
	public List<ICategory> getAllCategoryinDB() {
		return this.db.getAllCategory();
	}

}
