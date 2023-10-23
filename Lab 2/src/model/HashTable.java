package model;

public class HashTable {
	private String[] table;
	private int numeroElementos;
	
	public HashTable(int size) {
		this.numeroElementos = 0;
		//We use prime numbers to minimaze the number of colisions.
		if(!isPrime(size)) {
			size = nextPrime(size);
		}
		this.table = new String[size];
	}
	
	public String[] getSymTable() {
		return table;
	}
	
	public int getNumeroElementos() {
		return numeroElementos;
	}
	
	public int getSize() {
		return table.length;
	}
	
	public int insert(String id) {
		//If a null element is trying to be introduced we return -2.
		if(id == null) {
			return -2;
		}
		//If the element is already in the table we return -1.
		if(find(id) != -1) {return -1;}
		//In other case we insert the element.
		int counter=0;
		int position = funcHash(id);
		
		while(counter < getSize() && table[position] != null) {
			position=searchPos(id, counter);
			counter++;
		}
		table[position] = id;
		numeroElementos++;
		return 0;
	}
	
	public int find(String id) {
		int pos=0;
		int counter=0;
		while(counter<=getSize()) {
			pos = searchPos(id, counter);
			if(table[pos]!=null && table[pos].equals(id)) {
				return pos;
			}
			counter++;
		}
		return -1;
	}
	
	private int searchPos(String identifier, int counter) {
		return (funcHash(identifier)+counter)%getSize();
	}
	
	private int funcHash(String identifier) {
		int pos = identifier.hashCode()%getSize();
		if(pos<0) {
			return pos + getSize();
		}
		return pos;
	}
	
	public boolean isPrime(int number) {
		if(number <= 1) {return false;}
		if(number == 2) {return true;}
		for(int i = 2; i < number/2 + 1; i++) {
			if(number%i==0) {return false;}
		}
		return true;
	}
	
	public int nextPrime(int number) {
		if(isPrime(number)) {number++;}
		while(!isPrime(number)) {number++;}
		return number;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < getSize(); i++) {
			if(table[i] == null) {
				str.append("-");
			} else {
				str.append(table[i].toString());
			}
			//If it is the last element we use a blank spance, not a comma.
			if(i != getSize() -1) {str.append(",");} else {str.append(" ");}	
		}
		
		str.append("[Size: " + getSize() + " Num.Elems " + getNumeroElementos() + "]");
		return str.toString();
	}
}
