import java.util.LinkedList;
import java.util.Queue;

public class Mutex {
	
	private mutexValue value; 
	private String ownerID;
	private Queue<Process> queue;
  
	
	public Mutex(String ownerID) 
	{
		this.ownerID=ownerID;
		this.value=mutexValue.one;
		this.queue=new LinkedList<Process>();
	}
  

	public Queue<Process> getQueue() {
		return queue;
	}
	public void setQueue(Queue<Process> queue) {
		this.queue = queue;
	}
	public mutexValue getValue() {
		return value;
	}
	public void setValue(mutexValue value) {
		this.value = value;
	}
	public String getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	  
}
