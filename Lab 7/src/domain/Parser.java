package domain;

import java.util.*;

public class Parser {
    private Grammar grammar;
    private Map<String, Set<String>> firstSet;
    private Map<String, Set<String>> followSet;
    private Map<Pair<String, List<String>>, Integer> productionsNumbered = new HashMap<>();
    private static Stack<List<String>> rules = new Stack<>();
    private ParserOutput parserOutput = new ParserOutput();
    private Stack<String> alfa = new Stack<>();
    private Stack<String> beta = new Stack<>();
    private Stack<String> pi = new Stack<>();

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = new HashMap<>();
        this.followSet = new HashMap<>();
    }

    public void generateSets() {
        generateFirstSet();
        generateFollowSet();
    }

    private void generateFirstSet() {
        for (String nonTerminal : grammar.getSetOfNonTerminals()) {
            firstSet.put(nonTerminal, this.firstOf(nonTerminal));
        }
    }

    private void generateFollowSet() {
        for (String nonTerminal : grammar.getSetOfNonTerminals()) {
            followSet.put(nonTerminal, this.followOf(nonTerminal, nonTerminal));
        }
    }

    private void numberingProductions() {
        int index = 1;
        for (Production production: grammar.getSetOfProductions())
            for (List<String> rule: production.getRules())
                productionsNumbered.put(new Pair<>(production.getStart(), rule), index++);
    }

    private Set<String> firstOf(String nonTerminal) {
        if (firstSet.containsKey(nonTerminal))
            return firstSet.get(nonTerminal);
        Set<String> temp = new HashSet<>();
        Set<String> terminals = grammar.getSetOfTerminals();
        for (Production production : grammar.productionForNonTerminal(nonTerminal))
            for (List<String> rule : production.getRules()) {
                String firstSymbol = rule.get(0);
                if (firstSymbol.equals("ε"))
                    temp.add("ε");
                else if (terminals.contains(firstSymbol))
                    temp.add(firstSymbol);
                else
                    temp.addAll(firstOf(firstSymbol));
            }
        return temp;
    }

    private Set<String> followOf(String nonTerminal, String initialNonTerminal) {
        if (followSet.containsKey(nonTerminal))
            return followSet.get(nonTerminal);
        Set<String> temp = new HashSet<>();
        Set<String> terminals = grammar.getSetOfTerminals();

        if (nonTerminal.equals(grammar.getStartingSymbol()))
            temp.add("$");

        for (Production production : grammar.productionContainingNonTerminal(nonTerminal)) {
            String productionStart = production.getStart();
            for (List<String> rule : production.getRules()){
                List<String> ruleConflict = new ArrayList<>();
                ruleConflict.add(nonTerminal);
                ruleConflict.addAll(rule);
                if (rule.contains(nonTerminal) && !rules.contains(ruleConflict)) {
                    rules.push(ruleConflict);
                    int indexNonTerminal = rule.indexOf(nonTerminal);
                    temp.addAll(followOperation(nonTerminal, temp, terminals, productionStart, rule, indexNonTerminal, initialNonTerminal));

                    List<String> sublist = rule.subList(indexNonTerminal + 1, rule.size());
                    if (sublist.contains(nonTerminal))
                        temp.addAll(followOperation(nonTerminal, temp, terminals, productionStart, rule, indexNonTerminal + 1 + sublist.indexOf(nonTerminal), initialNonTerminal));

                    rules.pop();
                }
            }
        }

        return temp;
    }

    private Set<String> followOperation(String nonTerminal, Set<String> temp, Set<String> terminals, String productionStart, List<String> rule, int indexNonTerminal, String initialNonTerminal) {
        if (indexNonTerminal == rule.size() - 1) {
            if (productionStart.equals(nonTerminal))
                return temp;
            if (!initialNonTerminal.equals(productionStart)){
                temp.addAll(followOf(productionStart, initialNonTerminal));
            }
        }
        else
        {
            String nextSymbol = rule.get(indexNonTerminal + 1);
            if (terminals.contains(nextSymbol))
                temp.add(nextSymbol);
            else{
                if (!initialNonTerminal.equals(nextSymbol)) {
                    Set<String> fists = new HashSet<>(firstSet.get(nextSymbol));
                    if (fists.contains("ε")) {
                        temp.addAll(followOf(nextSymbol, initialNonTerminal));
                        fists.remove("ε");
                    }
                    temp.addAll(fists);
                }
            }
        }
        return temp;
    }

    public void createParseTable() {
        numberingProductions();

        List<String> columnSymbols = new LinkedList<>(grammar.getSetOfTerminals());
        columnSymbols.add("$");

        parserOutput.put(new Pair<>("$", "$"), new Pair<>(Collections.singletonList("acc"), -1));
        for (String terminal: grammar.getSetOfTerminals()) {
            parserOutput.put(new Pair<>(terminal, terminal), new Pair<>(Collections.singletonList("pop"), -1));
        }

        productionsNumbered.forEach((key, value) -> {
            String rowSymbol = key.getKey();
            List<String> rule = key.getValue();
            Pair<List<String>, Integer> parserOutputValue = new Pair<>(rule, value);

            for (String columnSymbol : columnSymbols) {
                Pair<String, String> parserOutputKey = new Pair<>(rowSymbol, columnSymbol);

                if (rule.get(0).equals(columnSymbol) && !columnSymbol.equals("ε")) {
                    parserOutput.put(parserOutputKey, parserOutputValue);
                } else if (grammar.getSetOfNonTerminals().contains(rule.get(0))
                        && firstSet.get(rule.get(0)).contains(columnSymbol)) {
                    if (!parserOutput.containsKey(parserOutputKey)) {
                        parserOutput.put(parserOutputKey, parserOutputValue);
                    }
                }
                else {
                    if (rule.get(0).equals("ε")) {
                        for (String b : followSet.get(rowSymbol)) {
                            parserOutput.put(new Pair<>(rowSymbol, b), parserOutputValue);
                        }
                    } else {
                        Set<String> firsts = new HashSet<>();
                        for (String symbol : rule)
                            if (grammar.getSetOfNonTerminals().contains(symbol))
                                firsts.addAll(firstSet.get(symbol));
                        if (firsts.contains("ε")) {
                            for (String b : firstSet.get(rowSymbol)) {
                                if (b.equals("ε"))
                                    b = "$";
                                parserOutputKey = new Pair<>(rowSymbol, b);
                                if (!parserOutput.containsKey(parserOutputKey)) {
                                    parserOutput.put(parserOutputKey, parserOutputValue);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public boolean parseSequence(List<String> sequence) {
        initializeStacks(sequence);

        boolean go = true;
        boolean result = true;

        while (go) {
            String betaHead = beta.peek();
            String alfaHead = alfa.peek();

            if (betaHead.equals("$") && alfaHead.equals("$")) {
                return true;
            }

            Pair<String, String> heads = new Pair<>(betaHead, alfaHead);
            Pair<List<String>, Integer> parseTableEntry = parserOutput.get(heads);

            if (parseTableEntry == null) {
                heads = new Pair<>(betaHead, "ε");
                parseTableEntry = parserOutput.get(heads);
                if (parseTableEntry != null) {
                    beta.pop();
                    continue;
                }

            }

            if (parseTableEntry == null) {
                go = false;
                result = false;
            } else {
                List<String> production = parseTableEntry.getKey();
                Integer productionPos = parseTableEntry.getValue();

                if (productionPos == -1 && production.get(0).equals("acc")) {
                    go = false;
                } else if (productionPos == -1 && production.get(0).equals("pop")) {
                    beta.pop();
                    alfa.pop();
                } else {
                    beta.pop();
                    if (!production.get(0).equals("ε")) {
                        pushAsChars(production, beta);
                    }
                    pi.push(productionPos.toString());
                }
            }
        }

        return result;
    }

    private void initializeStacks(List<String> w) {
        alfa.clear();
        alfa.push("$");
        pushAsChars(w, alfa);

        beta.clear();
        beta.push("$");
        beta.push(grammar.getStartingSymbol());

        pi.clear();
        pi.push("ε");
    }

    private void pushAsChars(List<String> sequence, Stack<String> stack) {
        for (int i = sequence.size() - 1; i >= 0; i--) {
            stack.push(sequence.get(i));
        }
    }

    public Map<String, Set<String>> getFirstSet() {
        return firstSet;
    }

    public Map<String, Set<String>> getFollowSet() {
        return followSet;
    }

    public ParserOutput getParserOutput() {
        return parserOutput;
    }
}
