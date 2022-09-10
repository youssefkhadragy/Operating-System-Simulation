import java.util.ArrayList;

public class Main {
	Scheduler scheduler = new Scheduler();
	Interpreter interpreter = new Interpreter();
	static Memory memory = new Memory();
	static int time = 0;
	int currSlice = 0;
	final int quantum = 1;
	Process currentExecuting;
	static ArrayList<Program> programs = new ArrayList<Program>();
	static ArrayList<Process> Processs = new ArrayList<Process>();

	public Main() {

	}

	public void executeLine(Process p) {
		interpreter.parse(p);

	}

	public static void checkArrivalTime() throws Exception {
		boolean flag = false;
		Program temp = null;
		for (Program p : programs) {
			if (time == p.getArrivalTime()) {
				Process t = new Process(p.getFileSource());
				Scheduler.addToReady(t,Operating_System.getGeneralReady());
				t.getPcb().setProcessState("Ready");
				memory.memoryInsert(t);
				flag = true;
				temp = p;
			}
		}
		if (flag) {
			programs.remove(temp);
		}
	}

	public void start() throws Exception {
		Program p1 = new Program("Program_1.txt", 0);
		Program p2 = new Program("Program_2.txt", 1);
		Program p3 = new Program("Program_3.txt", 4);
		programs.add(p1);
		programs.add(p2);
		programs.add(p3);
		boolean flag = false;
		while (Operating_System.generalReady.isEmpty()) {
			for (Program p : programs) {
				if (time == p.getArrivalTime()) {
					Process t = new Process(p.getFileSource());
					Scheduler.addToReady(t,Operating_System.getGeneralReady());
					Processs.add(t);
					programs.remove(p);
					t.getPcb().setProcessState("Ready");
					memory.memoryInsert(t);
					flag = true;
					break;
				}
			}
			if (!flag) {
				System.out.println("Current Clock Cycles: " + time);
				System.out.println("No Process is executing");
				System.out.println();
				time++;
			}
		}
		currentExecuting = scheduler.chooseNext();
		
		while (!Operating_System.generalReady.isEmpty() || !programs.isEmpty() || !Operating_System.generalBlocked.isEmpty()
				|| currentExecuting != null) {

			while ((currSlice < quantum && !Operating_System.generalBlocked.contains(currentExecuting)
					&& !currentExecuting.getProgramLines().isEmpty())) {
				System.out.println();
				System.out.println("Current Process: " + currentExecuting.getName());
				System.out.println("Current Clock Cycles: " + time);
				currentExecuting.getPcb().setProcessState("Executing");
				Main.getMemory().getMemory()[currentExecuting.getPcb().getMemoryBoundaryUpper()-4]=currentExecuting.getPcb().getProcessState();
				executeLine(currentExecuting);
				memory.memoryInsert(currentExecuting);
				System.out.println("Ready Queue: " + Operating_System.generalReady);
				System.out.println("Blocked Queue: " + Operating_System.generalBlocked);
				currentExecuting.getPcb().setProgramCounter(currentExecuting.getPcb().getProgramCounter()+1);
				currentExecuting.refresh();
				System.out.println("Memory:");
				for(int i=0; i<40;i++) { 
					System.out.println(i+" "+ memory.getMemory()[i]);
					}
				currSlice++;
				time++;
				checkArrivalTime();

	
				//System.out.println("========================================================");
			}
			if (!Operating_System.generalBlocked.contains(currentExecuting)) {
				currentExecuting.getPcb().setProcessState("Ready");
				Main.getMemory().getMemory()[currentExecuting.getPcb().getMemoryBoundaryUpper()-4]=currentExecuting.getPcb().getProcessState();
				Operating_System.generalReady.add(currentExecuting);
			}
			if (currSlice == quantum || currentExecuting.getProgramLines().isEmpty()
					|| Operating_System.generalBlocked.contains(currentExecuting)) {
				currentExecuting = scheduler.chooseNext();
				currentExecuting.getPcb().setProcessState("Executing");
				Main.getMemory().getMemory()[currentExecuting.getPcb().getMemoryBoundaryUpper()-4]=currentExecuting.getPcb().getProcessState();
				currSlice = 0;
			}
			///???remark
			while (!programs.isEmpty() && Operating_System.generalReady.isEmpty() && currentExecuting == null) {
				System.out.println();
				System.out.println("Current Clock Cycles: " + time);
				System.out.println("No Process executing");
				System.out.println();
				time++;
				checkArrivalTime();
				currentExecuting = scheduler.chooseNext();
			}

		}
		System.out.println();
		System.out.println("End Of Execution");

	}

	public static Memory getMemory() {
		return memory;
	}

	public static void setMemory(Memory memory) {
		Main.memory = memory;
	}

	public Process getCurrentExecuting() {
		return currentExecuting;
	}

	public void setCurrentExecuting(Process currentExecuting) {
		this.currentExecuting = currentExecuting;
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.start();
	}
}
