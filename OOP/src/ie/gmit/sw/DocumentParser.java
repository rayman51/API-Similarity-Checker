package ie.gmit.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * This class is used to parse in the files. It is 
 * implemented as a runnable so it can be called multiple times
 * by as many threads as you want.
 *
 */
public class DocumentParser implements Runnable {
	private BlockingQueue<Shingle>q;
	private String file;
	private int shingleSize, k;
	private Deque<String> buffer = new LinkedList<>();
	private int docId;
	
	public DocumentParser() {
		super();
		// TODO Auto-generated constructor stub
	}// Constructor
	
	/**
	 * @param q
	 * blocking queue for shingles
	 * @param file
	 * files read in
	 * @param shingleSize
	 * size for each shingle
	 * @param k
	 * amount of hash codes generated
	 * @param docId
	 * id of each file
	 */
	public DocumentParser(BlockingQueue<Shingle> q, String file, int shingleSize, int k,int docId) {
		this.q = q;
		this.file = file;
		this.shingleSize = shingleSize;
		this.k = k;
		this.docId = docId;
	}// Constructor

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/**
		 * @param Run
		 *When this method is ran, it utilizes the bufferered reader.
		 *It reads in the file and as its read it converts the words to upper case
		 *and then sends the data to another method called addWordsToBuffer.
		 *It then calls the getNextShingle method and puts the shingle in to the blocking queue.
		 */
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		    String line = null;
			while((line = br.readLine())!= null) 
			{
				String uLine = line.toUpperCase();
				String[]words = uLine.split(""); 
				addWordsToBuffer(words);
				Shingle s = getNextShingle();
				q.put(s);
				//System.out.print(words.toString());
				//System.out.print(words); for testing
			}// while
			flushBuffer();
			br.close();
		}// try 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch
	
	}// Run
	/**
	 * @param addWordsToBuffer
	 *Method to add the words to the buffer
	 */
	private void addWordsToBuffer(String [] words) 
	{
		for(String s : words) 
		{
			buffer.addLast(s);
		}// for
		
    }// addWordsToBuffer
	/**
	 * @param getNextShingle
	 *Method to get a new shingle and add to the queues the files are read in.
	 */
	private Shingle getNextShingle()
	{
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while(counter < shingleSize)
		{
			if(buffer.peek() != null) 
			{
				sb.append(buffer.poll());
				counter++;
			}// if
			
		}// while 

		if (sb.length() > 0) 
		{
			return(new Shingle(docId, sb.toString().hashCode()));
		}
		else 
		{
			return(null);
		}// if/else
  	}// getNextShingle
	/**
	 * @param flushBuffer
	 *Method to flush the buffer
	 */
	private void flushBuffer() throws InterruptedException
	{
		while(buffer.size() > 0) 
		{
			Shingle s = getNextShingle();
			if(s != null)
			{
					q.put(s);
			}// if
			else 
			{
					q.put(new Poison(docId, 0));
			}// else
			
		}// while
	}// flushBuffer
	
	
}// DocumentParser
