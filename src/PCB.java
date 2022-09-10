
public class PCB {
	private static int counter = 1;
	private int processID;
	private String processState;
	private int ProgramCounter;
	private int memoryBoundaryLower;
	private int memoryBoundaryUpper;

	public PCB() {
		this.processID = counter;
		this.processState = processState;
		this.memoryBoundaryLower = memoryBoundaryLower;
		this.memoryBoundaryUpper = memoryBoundaryUpper;
		this.ProgramCounter = ProgramCounter;
		counter++;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		PCB.counter = counter;
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public int getProgramCounter() {
		return ProgramCounter;
	}

	public void setProgramCounter(int programCounter) {
		ProgramCounter = programCounter;
	}

	public int getMemoryBoundaryLower() {
		return memoryBoundaryLower;
	}

	public void setMemoryBoundaryLower(int memoryBoundaryLower) {
		this.memoryBoundaryLower = memoryBoundaryLower;
	}

	public int getMemoryBoundaryUpper() {
		return memoryBoundaryUpper;
	}

	public void setMemoryBoundaryUpper(int memoryBoundaryUpper) {
		this.memoryBoundaryUpper = memoryBoundaryUpper;
	}

}
