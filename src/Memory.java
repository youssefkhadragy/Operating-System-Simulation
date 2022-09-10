import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Memory {
	static String[] memory = new String[40];
	public static String[] getMemory() {
		return memory;
	}

	public static void setMemory(String[] memory) {
		Memory.memory = memory;
	}

	public static ArrayList<Process> getProcessesInMemory() {
		return processesInMemory;
	}

	public static void setProcessesInMemory(ArrayList<Process> processesInMemory) {
		Memory.processesInMemory = processesInMemory;
	}

	private static ArrayList<Process> processesInMemory = new ArrayList<Process>();
	private static ArrayList<String> processesDisk = new ArrayList<String>();
	private static boolean[] emptySpaces = { true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true };
	// false,false,false,false,false,false,false,false,false,false
	// true, true, true, true, true,true, true, true, true, true
	// true, true, true, true, true, true, true, true, true, true

	public Memory() {
		// memory = new String[40];
	}

	public static void er(String filePath, String programName) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filePath));
		StringBuffer sb = new StringBuffer();
		while (sc.next() != null) {
			if (sc.nextLine().equals(programName)) {
				while (sc.next() != "$") {

				}
				sc.nextLine();
			}

		}

	}

	public static String fileToString(String filePath) throws Exception {
		String input = null;
		Scanner sc = new Scanner(new File(filePath));
		StringBuffer sb = new StringBuffer();
		while (!sc.next().equals("$")) {
			input = sc.nextLine();
			sb.append(input);
		}
		return sb.toString();
	}

	public void memoryInsert(Process t) throws Exception {
		int falseCounter = 0;
		int trueCounter = 0;
		int startPointer = 0;
		int endPointer = 0;
		int j = 0;
		boolean isInserted = false;
		for(int i = 0;i<processesInMemory.size();i++) {
			if(processesInMemory.get(i).getName().equals(t.getName()))
				return;
		}
		unloadDisk(t);

		for (int i = 0; i < emptySpaces.length; i++) {
			for (; j < emptySpaces.length; j++) {
				if (emptySpaces[j] == false) {
					i++;
					continue;
				} else {
					startPointer = j;
					break;
				}
			}
			for (; j < emptySpaces.length; j++) {
				if (emptySpaces[j] == true)
					trueCounter++;

				else {
					i = j - 1;
					endPointer = j - 1;
					break;
				}
			}
			if (j == 40) {
				i = j - 1;
				endPointer = j - 1;
			}
//			System.out.println(trueCounter);
//            System.out.println(trueCounter>=t.getProcessInfo().size());
			if (trueCounter >= t.getProcessInfo().size()) {
				int x = 0;
				t.getPcb().setMemoryBoundaryUpper(startPointer + t.getProcessInfo().size());
				t.getPcb().setMemoryBoundaryLower(startPointer);
				t.refresh();
				processesInMemory.add(t);
				isInserted = true;
				for (int k = startPointer; k < startPointer + t.getProcessInfo().size(); k++) {
//					System.out.println(t.getProcessInfo().get(x));
					memory[k] = t.getProcessInfo().get(x);
					emptySpaces[k] = false;
					x++;
				}
				// ?????
				trueCounter = 0;
			}
			else {
				trueCounter = 0;
				i = endPointer;
			}
		}
		if (!isInserted) {
			swap(t);
			return;
		}
	}

	public void swap(Process t) throws Exception {
		Process p = null;
		for (int i = 0; i < processesInMemory.size(); i++) {
			p = processesInMemory.get(i);
			if (p.getProcessInfo().size() >= t.getProcessInfo().size()) {
//				System.out.println(p.getName());
				processesInMemory.remove(p);

				// Scheduler.addToReady(p, Operating_System.generalReady);
				for (int j = p.getPcb().getMemoryBoundaryLower(); j <= p.getPcb().getMemoryBoundaryUpper() - 1; j++) {
					emptySpaces[j] = true;
					memory[j] = null;
				}
				// System.out.println("x");
				memoryInsert(t);
				processesDisk.add(p.getName());
				processesInMemory.add(t);
				break;
			} else {

				int l = p.getPcb().getMemoryBoundaryLower();
				int u = p.getPcb().getMemoryBoundaryUpper();
				int pSize = u - l + 1;
				int tSize = t.getProcessInfo().size();
				int j = 0;
				int counter = 0;
				while (j < tSize - pSize) {
					if (emptySpaces[u + j + 1] == true) {
						counter++;
					}
					j++;
				}
				if (counter == j) {
					for (int k = 0; k < pSize; k++) {
						emptySpaces[l + k] = true;
						memory[l + k] = null;
						processesInMemory.remove(p);
					}
					memoryInsert(t);
				} else {
					continue;
				}
			}

		}
		System.out.println();
		System.out.println(p.getName()+" Got Swapped Out of The Memory with "+t.getName());
		writeToDisk(p);

	}
	public void deleteFromMemory(Process p) {
		for (int i = 0; i < processesInMemory.size(); i++) {
				p = processesInMemory.get(i);
				processesInMemory.remove(p);
				// Scheduler.addToReady(p, Operating_System.generalReady);
				for (int j = p.getPcb().getMemoryBoundaryLower(); j <= p.getPcb().getMemoryBoundaryUpper() - 1; j++) {
					emptySpaces[j] = true;
					memory[j] = null;
				}
				break;
				// System.out.println("x");
	}
	}

	public static void writeToDisk(Process p) {
		String str = p.getName() + "";
		processesDisk.add(p.getName());
		try {
			FileWriter file = new FileWriter("Disk.txt", true);
			BufferedWriter out = new BufferedWriter(file);
			File reader = new File("Disk.txt");
			Scanner scan = new Scanner(reader);
			if (scan.hasNext())
				out.write("\n");
			out.write(str + "\n");
			for (int c = 0; c < p.getProcessInfo().size(); c++) {
				str = p.getProcessInfo().get(c) + "\n";
				out.write(str);
			}
			out.write("$");
			out.close();
			scan.close();

		} catch (IOException e) {
		}
	}
	public static void unloadDisk(Process t) {
		try {
			for (int c = 0; c < processesDisk.size(); c++) {
				if (t.getName().equals(processesDisk.get(c))) {
					processesDisk.remove(c);
					File inputFile = new File("Disk.txt");
					File tempFile = new File("myTempFile.txt");


					BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

					String currentLine;
					boolean removeLine = false;
					while ((currentLine = reader.readLine()) != null) {
						String trimmedLine = currentLine.trim();
						if (trimmedLine.equals(t.getName())) {
							removeLine = true;
						}
						// System.out.println(removeLine);
						// trim newline when comparing with lineToRemove
						if (trimmedLine.equals("$")) {
							removeLine = false;
							continue;
						}
						if (removeLine)
							continue;
						writer.write(currentLine + System.getProperty("line.separator"));
					}
					File file = new File("Disk.txt");
					PrintWriter writer1 = new PrintWriter(file);
					writer1.print("");
					BufferedWriter diskWriter = new BufferedWriter(new FileWriter(inputFile));
					Scanner scan = new Scanner(tempFile);
					while(scan.hasNext()) {
						diskWriter.write(scan.nextLine() + System.getProperty("line.separator"));
					}
					writer.close();
					reader.close();
					boolean delete = inputFile.delete();
					boolean successful = tempFile.renameTo(inputFile);
//				System.out.println(successful);
//				System.out.println(delete);
					break;

				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {


		Process p1 = new Process("Program_1.txt");
		Process p2 = new Process("Program_2.txt");
		Process p3 = new Process("Program_3.txt");
		Memory m = new Memory();
//		for(int i = 0;i<p1.getProcessInfo().size();i++) {
//			System.out.println(p1.getProcessInfo().get(i));
//		}
//
//		for(int i=0; i<40;i++) { 
//			System.out.println(i+" "+ memory[i] + "   " + emptySpaces[i]);
//			}
//	  
//		m.memoryInsert(p2);
//		m.memoryInsert(p3);
//		m.deleteFromMemory(p2);
//		memoryInsert(p1);

//		memoryInsert(p1);
//		memoryInsert(p2);
//		memoryInsert(p3);
//		for(int i=0; i<40;i++) { 
//			System.out.println(i+" "+ memory[i] + "   " + emptySpaces[i]);
//			}

		// memoryInsert(p3);
		// System.out.println(p2.getName());

//		
		if (memory.length != 0) {
			for (int i = 0; i < 40; i++) {
				System.out.println(i + " " + memory[i] + "   " + emptySpaces[i]);
			}
		}

	}
}