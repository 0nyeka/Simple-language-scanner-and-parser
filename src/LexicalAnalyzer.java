import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LexicalAnalyzer {
	private static FileReader fr; 
	private static LineNumberReader br;
	private static StringBuilder token;
	public static HashMap<String, String> symbolTable = new HashMap<String, String>();
	private static Character c;
	private static int number;
	private static int value;
	
	public static ArrayList<String> lookahead = new ArrayList<String>();
	public static ArrayList<String> tokens = new ArrayList<String>();
	

	public static void openFile() {
		try{
			String file = "src/source.txt";  //get source file location
			fr = new FileReader(file);     // initialize line
			br = new LineNumberReader(fr); // initialize line number
		}
		catch(Exception e) {
			System.out.println("no file found"); // if file does not exist print this
		}
	}
	
	public static void closeFile() throws IOException { 
		br.close();   //close file reader
	}
	
	//print message if a token is illegal
	public static void errorHandler(char current) throws IOException {
		number = br.getLineNumber(); // get the current line number
		while(current != ' ' && current != ';' && current != ',' && current != '(' && 
			  current != ')' && current != '{' && current != '}' && current != '*' &&
			  current != ':' && current != '+' && current != '#') {
			current = (char)br.read();
			token.append(current);
		}
		
		//System.out.println(token.toString() + " at line " + number + " is not a legal token!!!");  
		blockStart();
		
	}
	
	// prints out the input file
	public static void printFile() throws IOException {
		String file = "src/source.txt";
		FileReader fr = new FileReader(file);
		BufferedReader br1 = new BufferedReader(fr);
		String line = br1.readLine();
		
		while(line != null) {
			System.out.println(line);
			line = br1.readLine();
		}
		
		br1.close();
	}
	
	//prints out the accepted identifier token and its line number
	public static void acceptIdentifier() {
		number = br.getLineNumber(); // get the current line number
		//System.out.println(token.toString() + " at line " + number + " is a legal identifier token");
		lookahead.add(token.toString() + " 1");
	}
	
	//prints out the accepted symbol token and its line number
	public static void acceptSymbol() {
		number = br.getLineNumber(); // get the current line number
		//System.out.println(token.toString() + " at line " + number + " is a legal symbol token");
		if(token.toString().equals("#")) {
			lookahead.add(token.toString() + " 24");
		}
		else if(token.toString().equals(";")) {
			lookahead.add(token.toString() + " 25");
		}
		else if(token.toString().equals("(")) {
			lookahead.add(token.toString() + " 26");
		}
		else if(token.toString().equals(")")) {
			lookahead.add(token.toString() + " 27");
		}
		else if(token.toString().equals("{")) {
			lookahead.add(token.toString() + " 28");
		}	
		else if(token.toString().equals("}")) {
			lookahead.add(token.toString() + " 29");
		}
		else if(token.toString().equals(":")) {
			lookahead.add(token.toString() + " 30");
		}
		else if(token.toString().equals(",")) {
			lookahead.add(token.toString() + " 31");
		}
		else if(token.toString().equals("+")) {
			lookahead.add(token.toString() + " 32");
		}
		else {
			lookahead.add(token.toString() + " 33");
		}
	}
	
	//prints out the accepted keyword token and its line number
	public static void acceptKeyword() {
		number = br.getLineNumber(); // get the current line number
		//System.out.println(token.toString() + " at line " + number + " is a legal keyword token");
		if(token.toString().equals("import")) {
			lookahead.add(token.toString() + " 3");
		}
		else if(token.toString().equals("class")) {
			lookahead.add(token.toString() + " 4");
		}
		else if(token.toString().equals("static")) {
			lookahead.add(token.toString() + " 5");
		}
		else if(token.toString().equals("private")) {
			lookahead.add(token.toString() + " 6");
		}
		else if(token.toString().equals("public")) {
			lookahead.add(token.toString() + " 7");
		}
		else if(token.toString().equals("protected")) {
			lookahead.add(token.toString() + " 8");
		}
		else if(token.toString().equals("int")) {
			lookahead.add(token.toString() + " 9");
		}
		else if(token.toString().equals("float")) {
			lookahead.add(token.toString() + " 10");
		}
		else if(token.toString().equals("object")) {
			lookahead.add(token.toString() + " 11");
		}
		else if(token.toString().equals("break")) {
			lookahead.add(token.toString() + " 12");
		}
		else if(token.toString().equals("read")) {
			lookahead.add(token.toString() + " 13");
		}
		else if(token.toString().equals("print")) {
			lookahead.add(token.toString() + " 14");
		}
		else if(token.toString().equals(":=")) {
			lookahead.add(token.toString() + " 15");
		}
		else if(token.toString().equals("if")) {
			lookahead.add(token.toString() + " 16");
		}
		else if(token.toString().equals("do")) {
			lookahead.add(token.toString() + " 17");
		}
		else if(token.toString().equals("while")) {
			lookahead.add(token.toString() + " 18");
		}
		else if(token.toString().equals("switch")) {
			lookahead.add(token.toString() + " 19");
		}
		else if(token.toString().equals("case")) {
			lookahead.add(token.toString() + " 20");
		}
		else if(token.toString().equals("lambda")) {
			lookahead.add(token.toString() + " 21");
		}
		else if(token.toString().equals("and")) {
			lookahead.add(token.toString() + " 22");
		}
		else {
			lookahead.add(token.toString() + " 23");
		}
	}
	
	public static void acceptConstant() {
		number = br.getLineNumber(); // get the current line number
		//System.out.println(token.toString() + " at line " + number + " is a legal constant token");
		lookahead.add(token.toString() + " 2");
	}
	
	//add a constant or identifier to the symbol table
	public static void bookKeeper(String symbolTableToken, String classification) {
		if(!symbolTable.containsKey(symbolTableToken)) {
			symbolTable.put(symbolTableToken, classification);
		}
	}
	
	//start state
	public static void blockStart() throws IOException{
		number = br.getLineNumber();
		value = br.read();
		c =  (char)value;
		token = new StringBuilder();
		token.append(c);
		if(value == -1) {
			return;
		}
		
		if(value == 10) {
			value = br.read();
			c =  (char)value;
			token.append(c);
		}

		if(c == 'i') {                      //if token starts with i
			block24784122();                // go to the next state
		}
		else if(c == 'c') {               //if token starts with c
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append i to token
			block9103122();                 // go to the next state
		}
		else if(c == 's') {                 //if token starts with s
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append s to token
			block1596122();                 // go to the next state
		}
		else if(c == 'p') {                 //if token starts with p
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append p to token
			block22303775122();             // go to the next state
		}
		else if(c == 'f') {                 //if token starts with f
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append f to token
			block51122();                   // go to the next state
		}
		else if(c == 'o') {                 //if token starts with o
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append o to token
			block57119122();                // go to the next state
		}
		else if(c == 'b') {                 //if token starts with b
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append b to token
			block64122();                   // go to the next state
		}
		else if(c == 'r') {                 //if token starts with r
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append r to token
			block70122();                   // go to the next state
		}
		else if(c == 'd') {                 //if token starts with d
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append d to token
			block87122();                   // go to the next state
		}
		else if(c == 'w') {                 //if token starts with w
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append w to token
			block90122();                   // go to the next state
		}
		else if(c == 'l') {                 //if token starts with l
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append l to token
			block108122();                  // go to the next state
		}
		else if(c == 'a') {                 //if token starts with a
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append a to token
			block115122();                  // go to the next state
		}
		else if(c == 'e' || c == 'g' || c == 'h' || c == 'j' || c == 'k' || c == 'm' || c == 'n' || c == 'q' || c == 't' || c == 'u' 
				|| c == 'v' || c == 'x' || c == 'y' || c == 'z') { // if the token starts the other letters
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append the letter to token
			block122();                     // go to the next state
		}
		else if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') { // if the token starts with a digit
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append the letter to token
			block124();                     // go to the next state
		}
		else if(c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#') {                 //if token starts with #
			token = new StringBuilder();    // make a new token variable
			token.append(c);                // append # to token
			block128();                     // go to the next state
		}
		else {
			token = new StringBuilder();
			token.append(c);
			errorHandler(c);
		}		
	}
	
	public static void block124() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
			token.append(c);
			block124();
		}
		else if(c == '.') {
			token.append(c);
			block125();
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptConstant();                //accept it
			bookKeeper(token.toString(), "constant"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block125() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
			token.append(c);
			block126();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block126() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
			token.append(c);
			block126();
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptConstant();                //accept it
			bookKeeper(token.toString(), "constant"); // add it to the symbol table because it is an identifier			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block108122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block109122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block109122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'm') {
			token.append(c);
			block110122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block110122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'b') {
			token.append(c);
			block111122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block111122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'd') {
			token.append(c);
			block112122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block112122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block113122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block113122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block115122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'n') {
			token.append(c);
			block116122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block116122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'd') {
			token.append(c);
			block117122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block117122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block90122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'h') {
			token.append(c);
			block91122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block91122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'i') {
			token.append(c);
			block92122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block92122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'l') {
			token.append(c);
			block93122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block93122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block94122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block94122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block87122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'o') {
			token.append(c);
			block88122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block88122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block70122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block71122();
		}		
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block71122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block72122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block72122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'd') {
			token.append(c);
			block73122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block73122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block64122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'r') {
			token.append(c);
			block65122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block65122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block66122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block66122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block67122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block67122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'k') {
			token.append(c);
			block68122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block68122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block57119122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'b') {
			token.append(c);
			block58122();
		}
		else if(c == 'r') {
			token.append(c);
			block120122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block120122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block58122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'j') {
			token.append(c);
			block59122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block59122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block60122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block60122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'c') {
			token.append(c);
			block61122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block61122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block62122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block62122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block51122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'l') {
			token.append(c);
			block52122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block52122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'o') {
			token.append(c);
			block53122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block53122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block54122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block54122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block55122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block55122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	public static void block22303775122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'r') {
			token.append(c);
			block233876122();
		}
		else if(c == 'u') {
			token.append(c);
			block31122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	
	public static void block31122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'b') {
			token.append(c);
			block32122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block32122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'l') {
			token.append(c);
			block33122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block33122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'i') {
			token.append(c);
			block34122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block34122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'c') {
			token.append(c);
			block35122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block35122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block233876122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'i') {
			token.append(c);
			block2477122();
		}
		else if(c == 'o') {
			token.append(c);
			block39122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block39122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block40122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block40122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block41122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block41122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'c') {
			token.append(c);
			block42122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block42122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block43122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block43122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block44122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block44122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'd') {
			token.append(c);
			block45122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block45122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block2477122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'n') {
			token.append(c);
			block78122();
		}
		else if(c == 'v') {
			token.append(c);
			block25122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block25122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block26122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block26122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block27122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block27122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block28122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block28122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block78122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block79122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block79122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block1596122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block16122();
		}
		else if(c == 'w') {
			token.append(c);
			block97122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block97122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'i') {
			token.append(c);
			block1798122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block16122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block1798122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block1798122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block1899122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block1899122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'i') {
			token.append(c);
			block19122();
		}
		else if(c == 'c') {
			token.append(c);
			block100122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block100122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'h') {
			token.append(c);
			block101122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block101122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block19122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'c') {
			token.append(c);
			block20122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block20122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block9103122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'l') {
			token.append(c);
			block10122();
		}
		else if(c == 'a') {
			token.append(c);
			block104122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block104122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 's') {
			token.append(c);
			block105122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block105122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'e') {
			token.append(c);
			block7122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block10122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'a') {
			token.append(c);
			block11122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block11122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 's') {
			token.append(c);
			block12122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block12122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 's') {
			token.append(c);
			block13122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block13122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
			|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
			|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
			|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				
				block128();
			}
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);
		}
	}
	
	public static void block128() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || number == -1 || c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';' ||
			c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
			|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
			|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
			|| c == 'w' || c == 'x' || c == 'y' || c == 'z'|| value == -1) {
			acceptSymbol();
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
			}
		}
		else if(c == '=') {
			token.append(c);
			block130();
		}
		else {
			token.append(c);
			errorHandler(c); 
		}
		blockStart();
	}
	
	public static void block130() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || number == -1 || c == '(' || c == ')' || c == '{' || c == '}' || value == -1) {
			acceptKeyword();   // accept the keyword
		}
		else {
			token.append(c);
			errorHandler(c); // otherwise throw an error
		}
		blockStart();
	}

	
	public static void block24784122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'm') {            //if the current character is m
			token.append(c);      //add it to the token variable
			block3122();          // go to the next state
		}
		else if(c == 'n') {
			token.append(c);
			block48122();
		}
		else if(c == 'f') {
			token.append(c);
			block85122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				
				block128();
			}
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}	
	}
	
	public static void block85122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block48122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block49122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				
				block128();
			}
			
			blockStart();
			
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block49122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block3122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'p') {            //if the current character is p
			token.append(c);      //add it to the token variable
			block4122();          // go to the next state
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				
				block128();
			}
			
			blockStart();
			
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block4122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'o') {            //if the current character is o
			token.append(c);      //add it to the token variable
			block5122();          //go to the next state
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				
				block128();
			}
				
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block5122() throws IOException{
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 'r') {
			token.append(c);
			block6122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block6122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == 't') {
			token.append(c);
			block7122();
		}
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else if(c == ' ' || c == ';' || c == ',' || c == '(' || 
				c == ')' || c == '{' || c == '}' || c == '*' ||
				c == ':' || c == '+' || c == '#' || value == -1) {           //when the end of the token is reached
			
			acceptIdentifier();                //accept it
			bookKeeper(token.toString(), "identifier"); // add it to the symbol table because it is an identifier
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static void block7122() throws IOException {
		number = br.getLineNumber(); // get the current line number
		value = br.read();           // get the int form of the current character
		c = (char)value;             // get the current character
		if(c == ' ' || c == ';' || c == ',' || c == '(' || 
		   c == ')' || c == '{' || c == '}' || c == '*' ||
		   c == ':' || c == '+' || c == '#' || value == -1) {
			acceptKeyword();                //accept it
			
			if(c != ' ') {
				token = new StringBuilder();
				token.append(c);
				block128();
			}
			
			blockStart();
		}	
		else if(c == '.' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'
				|| c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k'
				|| c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v'
				|| c == 'w' || c == 'x' || c == 'y' || c == 'z') {
			token.append(c);
			block122();	
		}
		else {
			token.append(c);
			errorHandler(c);             // throw error the current character is any other character
		}
	}
	
	public static ArrayList<String> getTokens() {
		tokens.add("public 7");
		tokens.add("static 5");
		tokens.add("import 3");
		tokens.add("( 26");
		tokens.add("x 1");
		tokens.add(") 27");
		tokens.add("; 25");
		tokens.add("public 7");
		tokens.add("y 1");
		tokens.add(", 31");
		tokens.add("x1.2 1");
		tokens.add(": 30");
		tokens.add("float 10");
		tokens.add("; 25");
		tokens.add("protected 8");
		tokens.add("int 9");
		tokens.add("y 1");
		tokens.add("( 26");
		tokens.add("a 1");
		tokens.add(", 31");
		tokens.add("b 1");
		tokens.add(") 27");
		tokens.add("{ 28");
		tokens.add("read 13");
		tokens.add("x 1");
		tokens.add(", 31");
		tokens.add("y 1");
		tokens.add("; 25");
		tokens.add("print 14");
		tokens.add("y 1");
		tokens.add(", 31");
		tokens.add("z 1");
		tokens.add(", 31");
		tokens.add("w 1");
		tokens.add("; 25");
		tokens.add("x 1");
		tokens.add(", 31");
		tokens.add("y 1");
		tokens.add(", 31");
		tokens.add("z 1");
		tokens.add(":= 15");
		tokens.add("10 2");
		tokens.add("+ 32");
		tokens.add("x 1");
		tokens.add(", 31");
		tokens.add("20 2");
		tokens.add("or 23");
		tokens.add("a 1");
		tokens.add("* 33");
		tokens.add("b 1");
		tokens.add(", 31");
		tokens.add("( 26");
		tokens.add("a 1");
		tokens.add("+ 32");
		tokens.add("b 1");
		tokens.add("* 33");
		tokens.add("( 26");
		tokens.add("c 1");
		tokens.add(") 27");
		tokens.add(") 27");
		tokens.add("; 25");
		tokens.add("} 29");
		tokens.add("; 25");
		tokens.add("private 6");
		tokens.add("class 4");
		tokens.add("y 1");
		tokens.add("{ 28");
		tokens.add("public 7");
		tokens.add("object 11");
		tokens.add("( 26");
		tokens.add("y 1");
		tokens.add(") 27");
		tokens.add("z 1");
		tokens.add("( 26");
		tokens.add("z 1");
		tokens.add(", 31");
		tokens.add("z 1");
		tokens.add(") 27");
		tokens.add("{ 28");
		tokens.add("if 16");
		tokens.add("( 26");
		tokens.add("( 26");
		tokens.add("( 26");
		tokens.add("a 1");
		tokens.add("and 22");
		tokens.add("b 1");
		tokens.add(") 27");
		tokens.add(") 27");
		tokens.add(") 27");
		tokens.add("a 1");
		tokens.add(":= 15");
		tokens.add("b 1");
		tokens.add("; 25");
		tokens.add("do 17");
		tokens.add("break 12");
		tokens.add("while 18");
		tokens.add("( 26");
		tokens.add("500 2");
		tokens.add(") 27");
		tokens.add("; 25");	
		tokens.add("} 29");
		tokens.add("; 25");
		tokens.add("private 6");
		tokens.add("public 7");
		tokens.add("int 9");
		tokens.add("y 1");
		tokens.add("( 26");
		tokens.add("a 1");
		tokens.add(") 27");
		tokens.add("{ 28");
		tokens.add("switch 19");
		tokens.add("( 26");
		tokens.add("5 2");
		tokens.add("* 33");
		tokens.add("20 2");
		tokens.add("and 22");
		tokens.add("x 1");
		tokens.add(") 27");
		tokens.add("{ 28");
		tokens.add("a 1");
		tokens.add(":= 15");
		tokens.add("b.12 1");
		tokens.add("; 25");
		tokens.add("} 29");
		tokens.add("; 25");
		tokens.add("case 20");
		tokens.add("a 1");
		tokens.add(", 31");
		tokens.add("w 1");
		tokens.add("and 22");
		tokens.add("z 1");
		tokens.add(", 31");
		tokens.add("10.5 2");
		tokens.add("* 33");
		tokens.add("m 1");
		tokens.add("; 25");
		tokens.add("lambda 21");
		tokens.add("( 26");
		tokens.add("x 1");
		tokens.add(", 31");
		tokens.add("y 1");
		tokens.add(", 31");
		tokens.add("z 1");
		tokens.add(") 27");
		tokens.add("{ 28");
		tokens.add("read 13");
		tokens.add("a 1");
		tokens.add("; 25");
		tokens.add("print 14");
		tokens.add("b 1");
		tokens.add("; 25");
		tokens.add("} 29");
		tokens.add("; 25");		
		tokens.add("} 29");
		tokens.add("; 25");
		tokens.add("} 29");
		tokens.add("; 25");
		
		return tokens;	
	}
	
	public static void main(String[] args) throws IOException {
		openFile();
		printFile();
		System.out.println();
		
		blockStart();
		
		System.out.println("");
		for(String look: lookahead) {
			System.out.println(look);
		}
		
		
		System.out.println("");
		System.out.println("The symbol table goes as follows");
		for(String token : symbolTable.keySet()) {
			System.out.println(token + " : " + symbolTable.get(token));
		}
		
		closeFile();
		
		//parseOutput();
		
		
		
	}
}