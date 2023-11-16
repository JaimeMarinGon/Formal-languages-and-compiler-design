package model;

import java.util.List;

public class Transition {
	private String initialState;
	private String value;
	private List<String> finalState;
	
	public Transition() {
    }

    public void setStartState(String startState) {
        this.initialState = startState;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEndState(List<String> endState) {
        this.finalState = endState;
    }

    public String getStartState() {
        return initialState;
    }

    public String getValue() {
        return value;
    }

    public List<String> getEndState() {
        return finalState;
    }

    @Override
    public String toString() {
        return "δ(" + initialState + "," + value + ") = " + finalState;
    }
	
}
