
public class State {
	// Index of register to change
	int targetRegister;
	// +1 or -1 for whether should add or subtract
	int type;
	// Index of next state to enter
	int nextState;
	// (For subtraction state) Index of next state to enter if target register contains 0
	int defaultState;

	public State(String[] args) {
		this.targetRegister = Integer.parseInt(args[0]);
		if (args[1].equals("+")) this.type = 1;
		else this.type = -1;
		nextState = Integer.parseInt(args[2]);
		if (this.type == -1) this.defaultState = Integer.parseInt(args[3]);
	}
	
	public int act(int[] registers) {
		// The -1 is to allow for Java 0-based indexing
		int targetValue = registers[targetRegister - 1];
		if (type == -1 && targetValue == 0) return defaultState;
		registers[targetRegister - 1] = targetValue + type;
		return nextState;
	}
}
