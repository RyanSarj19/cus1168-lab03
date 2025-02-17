package academy.javapro;

import java.util.*;
import java.util.regex.*;

public class Lexer {
    // Patterns to recognize different token types
    private static final Pattern[] PATTERNS = {
            Pattern.compile("\\s+"),                                     // Whitespace
            Pattern.compile("\\b(if|else|for|while|int|float|String)\\b"), // Keywords
            Pattern.compile("\\b\\d+(\\.\\d+)?\\b"),                    // Numeric literals
            Pattern.compile("==|<=|>=|!=|&&|\\|\\||[+\\-*/=<>!]"),       // Operators
            Pattern.compile("[;,.(){}\\[\\]]"),                         // Punctuation
            Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")             // Identifiers
    };

    // Corresponding types
    private static final String[] TYPES = {
            "WHITESPACE",
            "KEYWORD",
            "LITERAL",
            "OPERATOR",
            "PUNCTUATION",
            "IDENTIFIER"
    };

    private String input;
    private List<String[]> tokens;
    private int position;

    /**
     * Constructor stores input string, initializes the token list, 
     * and sets position to start
     */
    public Lexer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

    /**
     * Reads input from the current position, attempts to match 
     * against each pattern. If matched, adds token 
     * and moves up the position. Otherwise, throws error for invalid input.
     */
    public void tokenize() {
        while (position < input.length()) {
            String remaining = input.substring(position);
            boolean foundMatch = false;

            for (int i = 0; i < PATTERNS.length; i++) {
                Matcher matcher = PATTERNS[i].matcher(remaining);
                if (matcher.lookingAt()) {
                    String matchedText = matcher.group();

                    // Skip whitespace token
                    if (i != 0) {
                        tokens.add(new String[]{TYPES[i], matchedText});
                    }

                    position += matchedText.length();
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                throw new RuntimeException("Invalid token at position " 
                    + position + ": '" + remaining.charAt(0) + "'");
            }
        }
    }

    
      // Returns list of tokens, each represented as a two element array
     
    public List<String[]> getTokens() {
        return tokens;
    }

    // test in main
    public static void main(String[] args) {
        String code = "int x = 10; if (x > 5) { x = x + 1; }";
        Lexer lexer = new Lexer(code);
        lexer.tokenize();
        for (String[] token : lexer.getTokens()) {
            System.out.println(token[0] + ": " + token[1]);
        }
    }
}

