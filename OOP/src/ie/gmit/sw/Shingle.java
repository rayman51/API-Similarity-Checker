package ie.gmit.sw;

/**
 *
 *This class creates a shingle object to be used for the similarity check
 */

public class Shingle 
{
	
	private int docId;
	private int hashcode;
	/**
	 * @param docId
	 * id number given to each file read in
	 * @param hashcode
	 * hash code generated for each shingle created
	 */
	public Shingle(int docId, int hashcode) 
	{
		super();
		this.docId = docId;
		this.hashcode = hashcode;
	}

	public int getDocId() 
	{
		return docId;
	}
	
	public void setDocId(int docId) 
	{
		this.docId = docId;
	}
	
	public int getHashcode()
	{
		return hashcode;
	}
	
	public void setHashcode(int hashcode) 
	{
		this.hashcode = hashcode;
	}
	
	
}// Shingle

