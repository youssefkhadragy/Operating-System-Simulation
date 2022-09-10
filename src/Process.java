import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Process {
	private ArrayList<Variable> variables = new ArrayList<Variable>();
	private String fileSource;
	private String name;
	private String input;
	private String readFile;
	private int pcMem = 0;
	private Stack<String> programLines = new Stack<String>();
	private Stack<String> temp = new Stack<String>();
	private int instSize = 0;
	private PCB pcb;
	private ArrayList<String> processInfo = new ArrayList<String>();

	public Process(String fileSource) {
		processInfo.add("#");
		processInfo.add("#");
		processInfo.add("#");

		this.fileSource = fileSource;
		this.name = fileSource.replace(".txt", "");
		File file = new File(fileSource);
		Scanner scan;
		try {
			scan = new Scanner(file);
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] split = line.split(" ");
				if (split.length == 4) {// readfile a
					temp.push(split[2] + " " + split[3]);
					temp.push(line);
					continue;
				} else if (split.length == 3) {
					// input
					if (split[2].equals("input")) {
						temp.push(split[2]);
						temp.push(line);
						continue;
					}
				}
				temp.push(line);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		while (!temp.isEmpty()) {

			programLines.push(temp.pop());
		}

		// Instructions*
		Stack<String> temp=new Stack<String>() ;
		while (!programLines.isEmpty()) {
			String lineX = programLines.pop();
			String[] line = lineX.split(" ");
			if (line[0].equals("readfile") && line.length == 2) {
				temp.push(lineX);
				continue;
			}

			if (line[0].equals("input") && line.length == 1) {
				temp.push(lineX);
				continue;
			}
			
			instSize++;
			temp.push(lineX);
			processInfo.add(lineX);
		}
		
		while (!temp.isEmpty()) {

			programLines.push(temp.pop());
		}

		// get variables
//		for(int i=0;i<p.getVariables().size();i++)
//			processInfo.add(p.getVariables().get(i).getName()+"= "+p.getVariables().get(i).getStringData());


		// PCB
		pcb = new PCB();
		processInfo.add("Process ID: "+pcb.getProcessID() + "");
		processInfo.add("Process State: "+pcb.getProcessState());
		processInfo.add("Program Counter"+pcb.getProgramCounter() + "");
		processInfo.add("Lower Bound: "+pcb.getMemoryBoundaryLower() + "");
		processInfo.add("Upper Bound: "+(pcb.getMemoryBoundaryUpper()-1) + "");

	}
	public void refresh() {
		processInfo.set(instSize+3, "Process ID: "+pcb.getProcessID()+ "");
		processInfo.set(instSize+4, "Process State: "+pcb.getProcessState()+ "");
		processInfo.set(instSize+5,"Program Counter: "+pcb.getProgramCounter() + "");
		processInfo.set(instSize+6, "Lower Bound: "+pcb.getMemoryBoundaryLower() + "");
		processInfo.set(instSize+7, "Upper Bound: "+(pcb.getMemoryBoundaryUpper()-1)+ "");
		
	}
	public ArrayList<String> getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(ArrayList<String> processInfo) {
		this.processInfo = processInfo;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<Variable> arrayList) {
		this.variables = arrayList;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public static void main(String[] args) {
		File file = new File("p1.txt");
		Scanner scan;
		try {
			scan = new Scanner(file);
			scan.nextLine();
			System.out.println(scan.hasNext());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public PCB getPcb() {
		return pcb;
	}

	public void setPcb(PCB pcb) {
		this.pcb = pcb;
	}

	public String getReadFile() {
		return readFile;
	}

	public void setReadFile(String readFile) {
		this.readFile = readFile;
	}

	public Stack<String> getProgramLines() {
		return programLines;
	}

	public void setProgramLines(Stack<String> programLines) {
		this.programLines = programLines;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getPcMem() {
		return pcMem;
	}

	public void setPcMem(int pcMem) {
		this.pcMem = pcMem;
	}

}
