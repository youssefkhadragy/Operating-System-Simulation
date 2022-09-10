import java.util.Queue;

public class Scheduler {
	Queue<Process> readyQueue;

	public Scheduler() {
	}

	public Process chooseNext() {
		for (Process p : Operating_System.generalReady) {
			if (p.getProgramLines().isEmpty()) {
				Operating_System.generalReady.remove(p);
				Main.memory.deleteFromMemory(p);
				System.out.println("\n" + p.getName() + " finished Execution");
			}
		}
		if (Operating_System.generalReady.isEmpty()) {
			return null;
		} else {
			
			Process temp = Operating_System.generalReady.remove();
			return temp;
		}
	}

	public static void addToReady(Process p, Queue<Process> generalReady) {
		generalReady.add(p);
	}

}
