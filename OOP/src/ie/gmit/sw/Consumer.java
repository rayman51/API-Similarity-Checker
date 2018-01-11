package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.*;

/**
 *  
 * This class starts with the third thread and creates multiple consumer threads to 
 * create hash codes for each word 
 */
public class Consumer implements  Runnable {

	private BlockingQueue<Shingle> q;
	private int k;
	private int[] minHashes;
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<>();
	private ExecutorService pool;
	private int docCount = 2;
	
	/**
	 * @param q
	 * blocking queue, size fed in is hard coded in launcher class
	 * @param k
	 * amount of hash codes needed hard coded in launcher class
	 * @param poolSize
	 * thread poll size hard coded in launcher class
	 */
	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) {
		super();
		this.q = q;
		this.k = k;
		pool = Executors.newFixedThreadPool(poolSize);
		init();
	}// constructor
	/** @param init
	 * This method pulls random hash codes from the list of each doc
	 */
	public void init() {
		Random random = new Random();
		minHashes = new int[k];
		for (int i = 0; i < minHashes.length; i++) {
			minHashes[i] = random.nextInt();
			//System.out.println(minHashes);
		}
	}// init
	/** @param run
	 * This method put the random shingles from each document in to a list
	 * and checks for the similarities between them, with these, the jaccard formula can be used
	 */
	public void run(){
		try {
			
			while (docCount > 0)
			{
				Shingle s = q.take();
				if (s instanceof Poison) 
				{
					docCount--;
				}// if
				else	
				{
					pool.execute( new Runnable()
					{
						@Override
						public void run() 
						{
							
							for (int i = 0; i < minHashes.length; i++) 
							{
									int value = s.getHashcode() ^ minHashes[i]; 
									List<Integer> list = map.get(s.getDocId());
									
									if (list == null) 
									{
										
										list = new ArrayList<Integer>(k);
										for (int j = 0; j < minHashes.length; j++) 
										{
											list.add(Integer.MAX_VALUE);
											map.put(s.getDocId(), list);
											//System.out.println(value);
										}// for
										
									}// if
									else 
									{
										if (list.get(i) > value) 
										{
											list.set(i, value);
										}// if
										
									}// else
									/** @param jaccard
									 *This code takes the similarity of each document and runs an equation to check 
									 *if the document are similar or not
									 */
									List<Integer> intersection = new ArrayList<Integer>(map.get(2));
									intersection.retainAll(map.get(1));
									
									 float jaccard =((float) intersection.size())/((k)+((float)intersection.size()));
									 System.out.println("documents are " + (jaccard*2)*100+ "% similar");// jaccard similarity coefficient
							} //for
						}// inner run
					});// execute/runnable
					
				}// else
				
			}// while
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// try/catch

	}// run
	
}// consumer

