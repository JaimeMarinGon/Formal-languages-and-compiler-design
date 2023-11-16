import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.FiniteAutomata;

public class Main {

	public static void main(String[] args) throws IOException {
		FiniteAutomata FA = new FiniteAutomata("files/FA.in");

		while(true) {
			printMenu();
			BufferedReader optReader = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println("Enter command: ");
	        String command = optReader.readLine();
	        if(command.equals("0")) {
	        	System.exit(0);
	        }else if(command.equals("1")){
	        	System.out.println("States");
	        	System.out.println(FA.getStatesList() + "\n");
	        }else if(command.equals("2")){
	        	System.out.println("Alphabet");
	        	System.out.println(FA.getAlphabet() + "\n");
		    }else if(command.equals("3")){
		    	System.out.println("Transitions");
	        	System.out.println(FA.getTransitionsList() + "\n");
		    }else if(command.equals("4")){
		    	System.out.println("Final states");
	        	System.out.println(FA.getFinalStates() + "\n");
		    }else if(command.equals("5")){
		    	System.out.println("Initial state");
	        	System.out.println(FA.getInitialState() + "\n");
		    }else if(command.equals("6")){
		    	if(FA.isDFA()) {
		    		System.out.println("It is a DFA \n");
		    	}else {
		    		System.out.println("It is a DFA \n");
		    	}
		    }else if(command.equals("7")){
		    	BufferedReader seqReader = new BufferedReader(new InputStreamReader(System.in));
		    	System.out.println("Sequence: ");
		    	String sequence = seqReader.readLine();
		    	if(FA.isDFA()) {
		    		if(FA.isSequenceAccepted(sequence)) {
			    		System.out.println("Sequence accepted");
			    	}else {
			    		System.out.println("Sequence not accepted");
			    	}
		    	}else {
		    		System.out.println("The finite automata is not deterministic");
		    	}		    	
		    }else {
		    	System.err.println("Unknown option");
	        }
		}
	}
	
	private static void printMenu() {
		System.out.println("1 - Show states");
        System.out.println("2 - Show alphabet");
        System.out.println("3 - Show transitions");
        System.out.println("4 - Show final states");
        System.out.println("5 - Show initial state");
        System.out.println("6 - Is DFA?");
        System.out.println("7 - Verify a sequence");
        System.out.println("0 - Exit");
	}

}
