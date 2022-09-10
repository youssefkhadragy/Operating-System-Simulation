import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SystemCalls {

	public SystemCalls() {

	}
	public static String readFileFromDisk(String fileName) {
		File file = new File(fileName+".txt");
		Scanner scan;
		String result = "";
		try {
			scan = new Scanner(file);
			while (scan.hasNext()) {
				result += (scan.nextLine() + "  " + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void writeFileToDisk(String fileName, String str) {

		try {
			FileWriter file = new FileWriter(fileName, true);
			BufferedWriter out = new BufferedWriter(file);
			File reader = new File(fileName);
			Scanner scan = new Scanner(reader);
			if (scan.hasNext())
				out.write("\n");
			out.write(str);
			out.close();
			scan.close();
		} catch (IOException e) {
		}
	}

	public static String input() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter a value: ");
		String x = sc.nextLine();
		return x;
	}

	public static String readDataFromMemory(Process p, String name) {
		for (int i = 0; i < p.getVariables().size(); i++) {
			if (p.getVariables().get(i).getName().equals(name)) {
				return p.getVariables().get(i).getStringData();
			}
		}
		return null;
	}

	public static void writeDataToMemory(Process p, String name, String data) {
		//???
		Variable v = new Variable(name, data);
		p.getVariables().add(v);
	}

	public static void printData(Process p, String name) {
		try {
			int num = Integer.parseInt(name);
			System.out.print("Output: " + num);
			System.out.println(" ");
		} catch (NumberFormatException e) {
			System.out.println("Output: " + readDataFromMemory(p, name));

		}

	}
	public static void printData(String data) {
		System.out.println("Output: " + data);
	}
	public static Variable searchVariable(Process p, String name) {
		for (int i = 0; i < p.getVariables().size(); i++) {
			if (p.getVariables().get(i).getName().equals(name)) {
				return p.getVariables().get(i);
			}
		}
		return null;
	}

}
