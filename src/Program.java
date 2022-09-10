public class Program {

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	private String fileSource;
	private int arrivalTime;
	
	public Program(String fileSource,int arrivalTime) 
	{
		this.arrivalTime= arrivalTime;
		this.fileSource= fileSource;

	}
}
