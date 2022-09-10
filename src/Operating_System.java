import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Operating_System {
	public Operating_System() {

	}
	public static Queue<Process> getGeneralBlocked() {
		return generalBlocked;
	}
	public static Queue<Process> getGeneralReady() {
		return generalReady;
	}
	static Queue<Process> generalBlocked = new LinkedList<Process>();
	static Queue<Process> generalReady = new LinkedList<Process>();
	Mutex accessFile = new Mutex(null);
	Mutex userInput = new Mutex(null);
	Mutex userOutput = new Mutex(null);

	public void assign(Process p, String[] instruction) {
		if (instruction.length == 4) { // assign b readfile a
			//String data = SystemCalls.readDataFromMemory(p, instruction[3]);
			SystemCalls.writeDataToMemory(p, instruction[1], p.getReadFile());
		} else if (instruction[2].equals("input")) {
			SystemCalls.writeDataToMemory(p, instruction[1], p.getInput());
			for(int i = 0;i<3;i++) {
				ArrayList<String> temp = p.getProcessInfo();
				if(p.getProcessInfo().get(i).equals("#")) {
					temp.remove(i);
					temp.add(i,instruction[1] +" = "+ p.getInput());
					p.setProcessInfo(temp);
					Main.getMemory().getMemory()[i+p.getPcb().getMemoryBoundaryLower()]=instruction[1] +" = "+ p.getInput();
					break;
				}
			}
		} else {
			String data = SystemCalls.readDataFromMemory(p, instruction[2]);
			SystemCalls.writeDataToMemory(p, instruction[1], data);
		}
	}
	public void writeFile(Process p,String[] instruction) {
		String temp = "";
		String temp1 = "";
		for (Variable v : p.getVariables()) {
			if (v.getName().equals(instruction[1])) {
				temp = v.getStringData();
			}
			if (v.getName().equals(instruction[2])) {
				temp1 = v.getStringData();
			}
		}
		SystemCalls.writeFileToDisk(temp + ".txt", temp1);
	}
	public boolean semWait(Mutex m, Process p) {
		boolean flag = false;
		if (m.getValue() == mutexValue.one) {
			m.setOwnerID(p.getName());
			m.setValue(mutexValue.zero);
			if (generalBlocked.contains(p)) {
				generalBlocked.remove(p);
			}
		} else if (m.getOwnerID().equals(p.getName())) {
			return flag;
		} else if (!m.getQueue().contains(p)) {
			p.getPcb().setProcessState("Blocked");
			Main.getMemory().getMemory()[p.getPcb().getMemoryBoundaryUpper()-4]=p.getPcb().getProcessState();
			generalReady.remove(p);
			generalBlocked.add(p);
			m.getQueue().add(p);
		}
		if (m.getQueue().contains(p)) {
			flag = true;
		}

		return flag;
	}

	public void semSignal(Mutex m, Process p) {
		if (m.getOwnerID().equals(p.getName())) {
			if (!m.getQueue().isEmpty()) {
				Process temp = m.getQueue().remove();
				p.getPcb().setProcessState("Ready");
				Main.getMemory().getMemory()[p.getPcb().getMemoryBoundaryUpper()-4]=p.getPcb().getProcessState();
				generalReady.add(temp);
				generalBlocked.remove(temp);
				m.setOwnerID(temp.getName());
				return;
			}
			m.setValue(mutexValue.one);
		}
	}
	public void input(Process p) {
		p.setInput(SystemCalls.input());
	}
	public void printData(Process p, String[] instruction) {
		SystemCalls.printData(p, instruction[1]);
		
	}
	public void printFromTo(Process p, String[] instruction) {
		int num1 = -1;
		int num2 = -1;
		num1 = Integer.parseInt(SystemCalls.readDataFromMemory(p, instruction[1]));
		num2 = Integer.parseInt(SystemCalls.readDataFromMemory(p, instruction[2]));
		if (num1 > num2) {
			for (int i = num1; i >= num2; i--) {
				SystemCalls.printData(i + "");
			}
		}
		if (num1 < num2) {
			for (int i = num1; i <= num2; i++) {
				SystemCalls.printData(i + "");
			}
		}
	}
	public void readFile(Process p, String[] instruction) {
		String fileName = SystemCalls.readDataFromMemory(p,instruction[1]);
		p.setReadFile(SystemCalls.readFileFromDisk(fileName));
	}
}
