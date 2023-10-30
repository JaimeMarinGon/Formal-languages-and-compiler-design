package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LexicalScanner {
	
	private static int ST_CAPACITY = 97;
	
	private ArrayList<String> tokens;
	private ArrayList<String> separators;
	private ArrayList<String> specialRelationals;
	private ArrayList<String> regularRelationals;
	
	private int currentLine;
	
	private HashTable ST;
	private HashMap<String,Integer> PIF;
	
	private boolean isStringLexicallyCorrect;
	private boolean isCharLexicallyCorrect;
	
	private String stringConstant;
	private String charConstant;
	
	private String file;
	
	public LexicalScanner(String file) {
		this.file = file;
		
		this.isStringLexicallyCorrect = true;
		this.isCharLexicallyCorrect = true;
		this.currentLine = 0;
		this.stringConstant = "";
		this.charConstant = "";
		
		this.ST = new HashTable(ST_CAPACITY);
		this.PIF = new HashMap<String,Integer>();
		
		this.tokens = new ArrayList<String>();
		this.separators = new ArrayList<String>();
		this.specialRelationals = new ArrayList<String>();
		this.regularRelationals = new ArrayList<String>();
		
		this.specialRelationals.add("<=");
		this.specialRelationals.add(">=");
		this.specialRelationals.add("==");
		this.regularRelationals.add(">");
		this.regularRelationals.add("<");
		
		this.readSeparatorsOperators(); this.readTokens();
	}
	
	private void readTokens() {
	       try {
	          File file = new File("src/files/token.in");
	          Scanner scanner = new Scanner(file);
	          while (scanner.hasNextLine()) {
	             tokens.add(scanner.nextLine());
	          }
	          scanner.close();
	       } catch (FileNotFoundException e) {
	          System.err.println(e.getMessage());
	       }
	}

	private void readSeparatorsOperators() {
	       try {
	          File file = new File("src/files/token.in");
	          Scanner scanner = new Scanner(file);
	          for (int i = 0; i < 24; i++){
	             separators.add(scanner.nextLine());
	          }
	          scanner.close();
	       } catch (FileNotFoundException e) {
	    	   System.err.println(e.getMessage());
	       }
	}
	
	private boolean isConstant(String token) {
        return token.matches("\\-?[1-9]+[0-9]*|0")
                || token.matches("\"[a-zA-Z0-9 _]+\"");
    }

    private boolean isIdentifier(String token){
        return token.matches("(^[a-zA-Z][a-zA-Z0-9 _]*)");
    }

    private boolean isStringConstant(String token) {
        if (token.length() >= 2 && token.charAt(0) == '"' && token.charAt(token.length() - 2) == '"') {
            String withoutQuote = token.substring(1, token.length() - 2);
            return withoutQuote.length() > 1;
        } else {
            return false;
        }
    }

    private Boolean isCharConstant(String token) {
        if (token.length()>=2 &&	String.valueOf(token.charAt(0)).equals("'") && String.valueOf(token.charAt(token.length() - 2)).equals("'")) {
            String withoutQuote = token.substring(1, token.length() - 2);
            return withoutQuote.length() <= 1;
        } else {
            return false;
        }
    }

    private Boolean isReservedOperatorSeparator(String myToken) {
        for (String token : this.tokens) {
            if (myToken.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public void classifyTokens() throws FileNotFoundException {
    	PrintWriter pw = new PrintWriter("src/output/pif.out");
        pw.printf("%-20s %s\n", "Token", "ST_Pos");
        
        
        Integer lastLine = 0;
        for (String key: this.PIF.keySet()) {
            if (isReservedOperatorSeparator(key)) {
                pw.printf("%-20s %d\n", key, -1);
            }else if (isIdentifier(key)) {
            	ST.insert(key);
                int position = ST.find(key);
                pw.printf("%-20s %d\n", "IDENTIFIER", position);
            }else if(isConstant(key)|| isStringConstant(key) || isCharConstant(key)) {
            	
            	ST.insert(key);
                int position = ST.find(key);
                pw.printf("%-20s %d\n", "CONSTANT", position);
            }else {
                System.out.println("LEXICAL ERROR " + key + " AT LINE " + (this.PIF.get(key)));
            }
            lastLine = this.PIF.get(key);
        }
        if (!isStringLexicallyCorrect) {
            System.out.println("LEXICAL ERROR: DOUBLE QUOTES NOT CLOSED AT LINE " + lastLine);
        }
        if (!isCharLexicallyCorrect) {
            System.out.println("LEXICAL ERROR: SINGLE QUOTES NOT CLOSED AT LINE " + lastLine);
        }
        pw.close();
    }
    
    public void writeToSymbolTable() throws FileNotFoundException {
    	PrintWriter pw = new PrintWriter("src/output/st.out");
    	pw.printf("%-20s %s\n", "Symbol Table as:", "Hash Table");
        pw.printf("%-20s %s\n", "Symbol", "ST Position");
        String[] symTable = ST.getSymTable();

        for(int i = 0; i < ST_CAPACITY; i++) {
            if (symTable[i] != null) {
                pw.printf("%-20s %s\n", symTable[i], i);
            }
        }
        pw.close();
    }
    
    public void scan() {
    	File myObj = new File(this.file);
    	try {
			Scanner myReader = new Scanner(myObj);
			while(myReader.hasNextLine()) {
				Scanner data = new Scanner(myReader.nextLine());
				currentLine++;
				while(data.hasNext()) {
					String word = data.next();
					boolean hasSeparator = false;
					for(String separator : separators) {
						if(word.contains(separator)) {
							hasSeparator = true;
							this.splitWordWithSeparator(word,separator,currentLine);
							break;
						}
					}
					if (!hasSeparator && !isStringLexicallyCorrect) {
	                    stringConstant += word + " ";
	                }
	                if (!hasSeparator && !isCharLexicallyCorrect) {
	                    charConstant += word + " ";
	                }
	                if (!hasSeparator && isStringLexicallyCorrect && isCharLexicallyCorrect) {
	                     this.PIF.put(word, currentLine);
	                }
					
					
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    private void splitWordWithSeparator(String word, String separator, Integer line) {
    	String[] splitedWord;
    	
    	boolean specialCase = false;
    	boolean containsRelational = false;
    	char doubleQuotes = '"';
    	String stringDoubleQuotes = String.valueOf(doubleQuotes);
    	
    	// Si contiene doble comillas
    	if(word.contains(stringDoubleQuotes) && !isStringLexicallyCorrect) {
    		specialCase=true;
    		this.isStringLexicallyCorrect = true;
    		// Si el string acaba en ;
    		if(word.charAt(word.length()-1) == ';') {
    			String newWord = word.substring(0,word.length()-1);
    			stringConstant += newWord + " ";
    			this.PIF.put(stringConstant, currentLine);
    			this.PIF.put(";", currentLine);
    			stringConstant = "";
    			return;
    		}else {
    			stringConstant += word + " ";
                this.PIF.put(stringConstant, currentLine);
                stringConstant = "";
                return;
    		}
    	}
    	
    	// Si la palabra está dentro en un String que aun no es correcto
    	if(!isStringLexicallyCorrect) {
    		specialCase = true;
    		// Añadimos la palabra a la cadena String
    		stringConstant += word + " ";
    	}
    	
    	// El string empieza por doble comillas
    	if(separator.charAt(0) == '"' && isStringLexicallyCorrect) {
    		specialCase = true;
    		// Entramos en un String no correcto
    		isStringLexicallyCorrect = false;
    		stringConstant += word + " ";
    		return;
    	}
    	
    	//Si contiene comillas simples
    	if (word.contains("'") && !isCharLexicallyCorrect) {
    		specialCase = true;
    		this.isCharLexicallyCorrect = true;
    		// Si el string acaba en ;
    		if(word.charAt(word.length()-1) == ';') {
    			String newWord = word.substring(0, word.length() - 1);
                charConstant += newWord + " ";
                
                this.PIF.put(charConstant, currentLine);
                this.PIF.put(";", currentLine);

                charConstant = "";
                return;
    		}else {
    			charConstant += word + " ";
    			this.PIF.put(charConstant, currentLine);
                charConstant = "";
                return;
    		}
    	}
    	
    	if(!isCharLexicallyCorrect) {
    		specialCase=true;
    		charConstant += word + " ";
    		return;
    	}
    	
    	if (separator.equals("'") && isCharLexicallyCorrect) {
            specialCase = true;
            isCharLexicallyCorrect = false;
            charConstant += word + " ";
            return;
        }
    	
    	if(separator.equals("(")){
    		specialCase = true;
    		String[] Lhs;
    		String[] Rhs;
    		
    		for(String specialSeparator : this.specialRelationals) {
    			if(word.contains(specialSeparator)) {
    				containsRelational = true;
    				splitedWord = word.split(specialSeparator);
    				Rhs = splitedWord[0].split("\\(");
    				Lhs = splitedWord[1].split("\\)");
                    
                    this.PIF.put("(", currentLine);
                    this.PIF.put(Rhs[1], currentLine);
                    this.PIF.put(specialSeparator, currentLine);
                    this.PIF.put(Lhs[0], currentLine);
                    this.PIF.put(")", currentLine);
    			}
    		}
    		for (String regularSeparator : this.regularRelationals) {
                if (word.contains(regularSeparator) && !containsRelational) {
                    containsRelational = true;
                    splitedWord = word.split(regularSeparator);
                    Rhs = splitedWord[0].split("\\(");
    				Lhs = splitedWord[1].split("\\)");
    				this.PIF.put("(", currentLine);
                    this.PIF.put(Rhs[1], currentLine);
                    this.PIF.put(regularSeparator, currentLine);
                    this.PIF.put(Lhs[0], currentLine);
                    this.PIF.put(")", currentLine);
                }
            }
    	}
    	if (separator.equals(")")) {
            specialCase = true;
            splitedWord = word.split("\\)");
            
            this.PIF.put(splitedWord[0], currentLine);
            this.PIF.put(separator, currentLine);
        }
    	
    	if (separator.equals("[")) {
            specialCase = true;
            splitedWord = word.split("\\[");

            this.PIF.put(splitedWord[0], currentLine);
            this.PIF.put(separator, currentLine);
            
            String[] LHS = splitedWord[1].split("\\]");
            if (LHS.length == 1) {
            	
            	this.PIF.put(LHS[0], currentLine);
            	this.PIF.put("]", currentLine);

            } else if (LHS.length == 2) {
            	
            	this.PIF.put(LHS[0], currentLine);
            	this.PIF.put("]", currentLine);
            	this.PIF.put(LHS[1], currentLine);
            }
        }
    	
    	if (separator.equals("+")) {
    		this.PIF.put(separator, currentLine);
            specialCase = true;
        }

        if (separator.equals(".")) {
        	splitedWord = word.split("\\.");
        	this.PIF.put(splitedWord[0], currentLine);
            this.PIF.put(separator, currentLine);
            specialCase = true;
        }

        if (!specialCase) {
        	splitedWord = word.split(separator);
            if (splitedWord.length == 0) {
            	this.PIF.put(separator, currentLine);
            }

            if (splitedWord.length == 1) {
            	this.PIF.put(splitedWord[0], currentLine);
                this.PIF.put(separator, currentLine);
            }

            if (splitedWord.length == 2) {
                if (!splitedWord[0].equals("")) {
                	this.PIF.put(splitedWord[0], currentLine);
                }
                this.PIF.put(separator, currentLine);
                this.PIF.put(splitedWord[1], currentLine);
            }
        }
    }
}

	

