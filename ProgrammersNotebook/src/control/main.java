package control;

import gui.DesktopUI;
import gui.IUserInterface;

import java.io.IOException;

import database.Db4oDatabase;
import database.IDatabase;

public class main 
{
	/**
	 * The main function for the desktop application
	 */
	public static void main(String args[]) throws IOException
	{
		IDatabase db = new Db4oDatabase("PEN.yap");
		IUserInterface  ui = null;
		Controller controller = new Controller(db,ui);
		ui =  new DesktopUI(controller);
		controller.ui = ui;
		ui.init();
		ui.show();
		
		// Database closed in windowClosing listener in UI
	}
}
