import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DFA {
	String alphabet;
	ArrayList<State> states;
	
	public DFA(String filename) {
		states = new ArrayList<State>();
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			alphabet = bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				boolean isAccept = false;
				String[] row = line.split(" ");
				if (row[row.length-1].equals("*")) isAccept = true;
				State state = new State(alphabet.length(), isAccept);
				for (int i = 0; i < alphabet.length(); i++) {
					state.setEdge(alphabet.charAt(i), Integer.parseInt(row[i]));
				}
				states.add(state);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean test(String word) {
		State state = states.get(0);
		for (char c : word.toCharArray()) {
			state = states.get(state.nextState(alphabet.indexOf(c)));
		}
		return state.isAccept;
	}

	public static void main(String[] args) {
		/*
		 * File for program should contain
		 * 	First, the alphabet as a string of characters
		 * 	Following this, the transition table for the DFA with
		 * 		rows implicitly labelled 0, 1, 2, ...
		 * 		columns implicitly labelled by characters in the order they appear in alphabet
		 * 	and each position in the table denotes the state that the DFA should move if it gives the given letter in that state
		 * 	moreover, a star at the end of the line denotes an accept state
		 * 
		 * e.g. 
		 * 	01
		 *	1 2
		 *	3 2 *
		 *	1 3 *
		 *	3 3
		 * Gives alphabet {0, 1}. States 1 and 2 are accept states. 
		 * If the DFA reads 0 when in state 0 it goes to state 1, while if it reads 1 it goes to state 2, etc.
		 * [This example accepts strings which are alternating 0's and 1's]
		 */
		DFA D = new DFA("multiple3.txt");
		DFA E = new DFA("alternating.txt");
		
		for (int n = 0; n <= 10; n++) {
			String word = Integer.toBinaryString(n);
			System.out.println(word + " (" + n + ") : multiple3 - " + D.test(word) + ", alternating - " + E.test(word));
		}

	}
	
	public class State {
		boolean isAccept = false;
		int[] edges;
		
		public State(int n, boolean isAccept) {
			this.edges = new int[n];
			this.isAccept = isAccept;
		}
		
		public State(int n) {
			this(n, false);
		}
		
		public void setEdge(char let, int to) {
			edges[alphabet.indexOf(let)] = to;
		}
		
		public int nextState(int let) {
			return edges[let];
		}
	}

}
