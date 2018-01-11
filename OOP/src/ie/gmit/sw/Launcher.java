package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.*;

/**
 * 
 * This class starts the worker and consumer threads.
 * Imports the java util package.
 * The file names are passed from the menu and other variable needed to start 
 * document parser threads are hard coded.
 * These variables could be inputed by user using getters from the menu class
 *
 */


public class Launcher 
{
	/**
	 * @param Variables
	 *Variables used to launch the doc parser threads. These variables are needed as they are part of the document
	 *parser class.
	 *They are declared as constants.
	 *
	 */
			private static final int ShingleSize = 4;
			private static final int k = 200;
			private static final int PoolSize = 50;
			private static final int qSize = 50;
	
		
		public static void launch(String f1, String f2) throws Exception 
		{
			
			BlockingQueue<Shingle> q = new LinkedBlockingQueue<>(qSize);// declares blocking queue
			
			/**
			 * @param Threads
			 * When launched 2 worker threads grab the filename, shingle size, blocking queue size, hash amount 
			 * and are assigned a doc Id at the end (eg 1/2).
			 * A third consumer thread is then started to begin the check for similarity.
			 */
			Thread t1 = new Thread(new DocumentParser(q, f1, ShingleSize, k,1), "T1");// worker
			Thread t2 = new Thread(new DocumentParser(q ,f2, ShingleSize, k,2), "T2");// threads
			
			Thread t3 = new Thread(new Consumer(q,k,PoolSize), "T3");// consumer thread
			
			t1.start();
			t2.start();
			t3.start();
			
			t1.join();
			t2.join();
			t3.join();
		
		}// Launch

  }// Launcher