package ie.gmit.sw;

import java.util.*;
/**
 *
 * Menu class to run the program.
 * Imports the java util package.
 * This class contains the menu that the user is presented with when the program
 * starts. The user is asked to enter 1 or 2 as an option.
 * When they pick 1, they are asked to enter 2 file names and the program checked how similar the files are.
 * All variables are declared at the top and getters and setter along with constructor. 
 *
 */

public class Menu
{
	/**
	 * @param Variables
	 *These are the variables used to start the program
	 *It also includes the scanner which is used to input data on the console.
	 *
	 */
		static Scanner scanner = new Scanner (System.in);
		private String f1;
		private String f2;
		
		/**
		 * @param f1 
		 * name of first file entered 
		 * @param f2 
		 * name of second file entered
		 */
		public String getF1()
		{
			return f1;
		}
		
		public void setF1(String f1)
		{
			this.f1 = f1;
		}
		
		public String getF2() 
		{
			return f2;
		}
		
		public void setF2(String f2) 
		{
			this.f2 = f2;
		}
		
		
	public void show() 
	{
		// TODO Auto-generated method stub
		System.out.println("FILE SIMILARITY CHECKER");
		System.out.println("=======================");
		
		
			System.out.println("please enter file name 1");
			f1 = scanner.next();
			System.out.println("please enter file name 2");
			f2 = scanner.next();
					try 
					{
						Launcher.launch(f1,f2);
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}// show
	
}// Menu

