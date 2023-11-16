package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
	private String initialState;
	private List<String> finalStates;
	private List<Transition> transitionsList;
	private List<String> alphabet;
	private List<String> statesList;
	
	public FiniteAutomata(String fileName) {
		this.finalStates=new ArrayList<>();
		this.transitionsList=new ArrayList<>();
		this.alphabet=new ArrayList<>();
		this.statesList=new ArrayList<>();
		readFile(fileName);
	}
	
	public List<String> getFinalStates(){
		return this.finalStates;
	}
	
	public List<Transition> getTransitionsList(){
		return this.transitionsList;
	}
	
	public List<String> getAlphabet(){
		return this.alphabet;
	}
	
	public List<String> getStatesList(){
		return this.statesList;
	}
	
	public String getInitialState() {
		return this.initialState;
	}
	
	private void readFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File(fileName));
			//States list
			scanner.nextLine();
			this.statesList=Arrays.asList(scanner.nextLine());
			//Alphabet
			scanner.nextLine();
			this.alphabet=Arrays.asList(scanner.nextLine());
			//Transitions
			scanner.nextLine();
			String transition;
			while(!scanner.hasNext("FINAL")) {
				transition = scanner.nextLine();
				List<String> splitedTransition = Arrays.asList(transition.split(","));
				Transition tran= new Transition();
				tran.setStartState(splitedTransition.get(0));
				tran.setValue(splitedTransition.get(1));
				List<String> endStates = new ArrayList<>();
				for(int i=2;i<splitedTransition.size();i++) {
					 endStates.add(splitedTransition.get(i));
				}
				tran.setEndState(endStates);
	            this.transitionsList.add(tran);
			}
			//Final states
			scanner.nextLine();
			this.finalStates = Arrays.asList(scanner.next().split(","));
			//Initial state
			scanner.nextLine();
			scanner.nextLine();
			this.initialState = scanner.nextLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String nextState(String startState, String value) {
		for(Transition tran: transitionsList) {
			if(tran.getStartState().equals(startState) && tran.getValue().equals(value)) {
				if(tran.getEndState().size() == 1){
					return tran.getEndState().get(0);
				}
			}
		}
		return "No state";
	}
	
	public boolean isDFA() {
		for(Transition tran : transitionsList) {
			if (tran.getEndState().size() > 1) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isSequenceAccepted(String sequence) {
		String actualState = this.initialState;
		String [] splittedSequence = sequence.split("");
			for(String chara : splittedSequence) {
				String nextState = nextState(actualState, chara);
				if(nextState.equals("No state")) {
					return false;
				}
				actualState = nextState;
			}
			if(this.finalStates.contains(actualState)) {
				return true;
			}
			return false;
	}
}
