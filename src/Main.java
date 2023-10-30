import java.io.IOException;


import model.LexicalScanner;

public class Main {

	public static void main(String[] args) throws IOException {
		LexicalScanner scanner = new LexicalScanner("src\\input\\p1err.txt");
        scanner.scan();
        scanner.classifyTokens();
        scanner.writeToSymbolTable();
	}

}