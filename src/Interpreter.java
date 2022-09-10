
public class Interpreter {
	String input;
	int min, max;
	Operating_System os = new Operating_System();

	public Interpreter() {
	}

	public void parse(Process p) {
		String executingLine = p.getProgramLines().pop();
		String[] instruction = executingLine.split(" ");
		System.out.println("Current Instruction: " + executingLine);
		// String data = "";
		switch (instruction[0].toLowerCase()) {
		case "assign":
			os.assign(p, instruction);
			break;
		case "writefile":
			os.writeFile(p, instruction);
			break;

		case "input":
			os.input(p);
			break;
		case "print":
			os.printData(p, instruction);
			break;
		case "printfromto":
			os.printFromTo(p, instruction);
			break;
		case "readfile":
			os.readFile(p, instruction);
		case "semwait":
			if (instruction[1].equals("userInput")) {
				os.semWait(os.userInput, p);
			}
			if (instruction[1].equals("file")) {
				os.semWait(os.accessFile, p);
			}
			if (instruction[1].equals("userOutput")) {
				os.semWait(os.userOutput, p);
			}
			break;
		case "semsignal":
			if (instruction[1].equals("userInput")) {
				os.semSignal(os.userInput, p);
			}
			if (instruction[1].equals("file")) {
				os.semSignal(os.accessFile, p);
			}
			if (instruction[1].equals("userOutput")) {
				os.semSignal(os.userOutput, p);
			}
			break;

		}

	}
}