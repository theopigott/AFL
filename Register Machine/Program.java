import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Program {
	ArrayList<State> states;
	int upper;

	public Program(String filename) {
		states = new ArrayList<State>();
		upper = 0;
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] args = line.split(",");
				states.add(new State(args));
				int index = Integer.parseInt(args[0]);
				if (index > upper) upper = index;
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int run(int[] input) {
		int[] registers = new int[Math.max(upper, input.length)];
		
		// Fill register with input
		for (int i = 0; i < input.length; i++) {
			registers[i] = input[i];
		}
		
		// Begin execution at state 1 (0 in Java indexing)
		// Stop when reach state 0 (-1 in Java indexing)
		int currentState = 0;
//		int count = 0;
		while (currentState != -1) {
			// The -1 is to convert to Java 0-based indexing
			currentState = states.get(currentState).act(registers) - 1;
			if (currentState >= states.size()) {
				System.out.println("Program fallout: attempt to access undefined state " + (currentState + 1));
				return -1;
			}
//			count++;
		}
//		System.out.println("Count: " + count);
		return registers[0];
	}

	public static void main(String[] args) {
		/*
		 * Program arguments:
		 * args[0] String: filename of program containing sequence of instructions on each line
		 * e.g. 1,+,2 adds 1 to register 1 and goes to state 2
		 * or 2,-,6,7 subtracts 1 from register 2 and goes to state 6 (or state 7 if register 2 contained 0)
		 * 
		 * args[1] String: filename of input for program, each line contains input for a separate run
		 * On each line input should be separated by a space
		 * e.g. 2 3 on a line sets the 1st register to 2 and the 2nd to 3
		 */
		if (args.length < 2) {
			System.out.println("Please provide program and input file names as arguments.");
			return;
		}
		
		// Create program from given instructions
		System.out.println("Program file: " + args[0]);
		Program prog = new Program(args[0]);
		
		System.out.println("Input file: " + args[1]);
		String inputFilename = args[1];
		try {
			FileReader fileReader = new FileReader(new File(inputFilename));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			// Run the program with each line of input
			while ((line = bufferedReader.readLine()) != null) {
				System.out.print("Input: ");
				System.out.println(line);
				
				// Extract input from file
				String[] inputStrings = line.split(" ");
				int[] input = new int[inputStrings.length];
				for (int i = 0; i < inputStrings.length; i++) {
					input[i] = Integer.parseInt(inputStrings[i]);
				}
				
				int output = prog.run(input);
				
				// Display the result
				System.out.print("Output: ");
				System.out.println(output);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
