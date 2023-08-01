import java.io.IOException;
import java.util.ArrayList;

public class SyntaxAnalyzer {
	
	private static ArrayList<String> stack = new ArrayList<>();
	private static ArrayList<String> lookahead = LexicalAnalyzer.getTokens();
	private static int step;
	
	public static void parser() {
		
		step = 1;
		System.out.println("LL(1) Parse Output:");
		System.out.println("Steps          StackTop          Lookahead          Action");
		System.out.println("0              0        //0         -          Push <pike> // 34");

		
		stack.add("0");
		stack.add(0, "<pike>");
		
		while(stack.get(0) != "0") {
			int spaceIndex = 0;
			String stackSymbol = "";
			String inputSymbol = "";
			String inputType = "";
			if(lookahead.isEmpty()) {
				break;
			}
			if(!lookahead.isEmpty() && !stack.isEmpty()) {
				spaceIndex = lookahead.get(0).indexOf(' ');
				stackSymbol = stack.get(0);    //get stack top
				inputSymbol = lookahead.get(0).substring(0, spaceIndex); //get next input symbol
			    inputType = lookahead.get(0).substring(spaceIndex + 1);
			}
			
			if(!stackSymbol.isEmpty() && stackSymbol.charAt(0) != '<' ) {  //if stackSymbol is a terminal
				if(stackSymbol.equals(inputSymbol)) {
					stack.remove(0);          //pop stack
					lookahead.remove(0);      //remove inputSymbol
					System.out.println(step + "              " + stackSymbol + "      //" + inputType + "          -          Pop " + inputType);
				}
				if(stackSymbol.equals("[id]")) {
					if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						lookahead.remove(0);      //remove inputSymbol
						System.out.println(step + "              " + stackSymbol + "      //" + inputType + "          -          Pop " + inputType);
					}
				}
				if(stackSymbol.equals("[const]")) {
					if(inputType.equals("2")) {
						stack.remove(0);          //pop stack
						lookahead.remove(0);      //remove inputSymbol
						System.out.println(step + "              " + stackSymbol + "      //" + inputType + "          -          Pop " + inputType);
					}
				}
			}
			else {                               //if stack symbol is a non-terminal
				if(stackSymbol.equals("<pike>")) {
					if(inputSymbol.equals("static") || inputSymbol.equals("private") || inputSymbol.equals("public") || inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-defs>");  //push the RHS
						stack.add(0, ";");
						stack.add(0, "<def>");
						System.out.println(step + "              " + stackSymbol + "   //" + 34 + "   " + inputSymbol + "  //" + inputType + "      Pop 34, Push 35, 25, 36  //R1");
					}
				}
				else if(stackSymbol.equals("<more-defs>")) {
					if(inputSymbol.equals("static") || inputSymbol.equals("private") || inputSymbol.equals("public") || inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-defs>"); //push the RHS
						stack.add(0, ";");
						stack.add(0, "<def>");
						System.out.println(step + "              " + stackSymbol + "   //" + 35 + "   " + inputSymbol + "  //" + inputType + "      Pop 35, Push 35, 25, 36  //R2");
					}
					else if(inputSymbol.equals("ε") || inputSymbol.equals("}")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 35 + "   " + inputSymbol + "  //" + inputType + "      Pop 35  //R3");
					}
				}
				else if(stackSymbol.equals("<def>")) {
					if(inputSymbol.equals("static") || inputSymbol.equals("private") || inputSymbol.equals("public") || inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<def-body>"); //push the RHS
						stack.add(0, "<modifs>");
						System.out.println(step + "              " + stackSymbol + "   //" + 36 + "   " + inputSymbol + "  //" + inputType + "      Pop 36, Push 37, 42  //R4");
					}
					
				}
				else if(stackSymbol.equals("<def-body>")) {
					if(inputSymbol.equals("import")){
						stack.remove(0);          //pop stack
						stack.add(0, "<import>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 37 + "   " + inputSymbol + "  //" + inputType + "      Pop 37, Push 38  //R5");
					}
					else if(inputSymbol.equals("int") || inputSymbol.equals("float") || inputSymbol.equals("object")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<function>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 37 + "   " + inputSymbol + "  //" + inputType + "      Pop 37, Push 39  //R6");
					}
					else if(inputSymbol.equals("class")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<class>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 37 + "   " + inputSymbol + "  //" + inputType + "      Pop 37, Push 40  //R7");
					}
					else if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<var-decl>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 37 + "   " + inputSymbol + "  //" + inputType + "      Pop 37, Push 41  //R8");
					}
				}
				else if(stackSymbol.equals("<import>")) {
					if(inputSymbol.equals("import")){
						stack.remove(0);          //pop stack
						stack.add(0, ")"); //push the RHS
						stack.add(0, "[id]");
						stack.add(0, "(");
						stack.add(0, "import");
						System.out.println(step + "              " + stackSymbol + "   //" + 38 + "   " + inputSymbol + "  //" + inputType + "      Pop 38, Push 27, 1, 26, 3  //R9");
					}
				}
				else if(stackSymbol.equals("<function>")) {
					if(inputSymbol.equals("int") || inputSymbol.equals("float") || inputSymbol.equals("object")){
						stack.remove(0);          //pop stack
						stack.add(0, "<block>"); //push the RHS
						stack.add(0, ")"); 
						stack.add(0, "<id-list>");
						stack.add(0, "(");
						stack.add(0, "[id]");
						stack.add(0, "<type>");
						System.out.println(step + "              " + stackSymbol + "   //" + 39 + "   " + inputSymbol + "  //" + inputType + "      Pop 39, Push 48, 27, 46, 26, 1, 45  //R10");
					}
				}
				else if(stackSymbol.equals("<class>")) {
					if(inputSymbol.equals("class")){
						stack.remove(0);          //pop stack
						stack.add(0, "}"); //push the RHS
						stack.add(0, "<pike>"); 
						stack.add(0, "{");
						stack.add(0, "[id]");
						stack.add(0, "class");
						System.out.println(step + "              " + stackSymbol + "   //" + 40 + "   " + inputSymbol + "  //" + inputType + "      Pop 40, Push 48, 29, 34, 28, 1, 44  //R11");
					}
				}
				else if(stackSymbol.equals("<var-decl>")) {
					if(inputType.equals("1")){
						stack.remove(0);          //pop stack
						stack.add(0, "<type>"); //push the RHS
						stack.add(0, ":"); 
						stack.add(0, "<id-list>");
						System.out.println(step + "              " + stackSymbol + "   //" + 41 + "   " + inputSymbol + "  //" + inputType + "      Pop 41, Push 45, 30, 46  //R12");
					}
				
				}
				else if(stackSymbol.equals("<modifs>")) {
					if(inputSymbol.equals("static") || inputSymbol.equals("private") || inputSymbol.equals("public") || inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-modifs>");  //push the RHS
						stack.add(0, "<modif>");
						System.out.println(step + "              " + stackSymbol + "   //" + 42 + "   " + inputSymbol + "  //" + inputType + "      Pop 42, Push 43, 44  //R13");
					}
				}
				else if(stackSymbol.equals("<more-modifs>")) {
					if(inputSymbol.equals("static") || inputSymbol.equals("private") || inputSymbol.equals("public") || inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-modifs>");  //push the RHS
						stack.add(0, "<modif>");
						System.out.println(step + "              " + stackSymbol + "   //" + 43 + "   " + inputSymbol + "  //" + inputType + "      Pop 43, Push 43, 44  //R14");
					}
					else if(inputSymbol.equals("import") || inputSymbol.equals("int") || inputSymbol.equals("float") || inputSymbol.equals("object") || inputSymbol.equals("class") || inputType.equals("1")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 43 + "   " + inputSymbol + "  //" + inputType + "      Pop 43  //R15");
					}
				}
				else if(stackSymbol.equals("<modif>")) {
					if(inputSymbol.equals("static")) {
						stack.remove(0);          //pop stack
						stack.add(0, "static");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 44 + "   " + inputSymbol + "  //" + inputType + "      Pop 44, Push 5  //R16");
					}
					else if(inputSymbol.equals("private")) {
						stack.remove(0);          //pop stack
						stack.add(0, "private");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 44 + "   " + inputSymbol + "  //" + inputType + "      Pop 44, Push 6  //R17");
					}
					else if(inputSymbol.equals("public")) {
						stack.remove(0);          //pop stack
						stack.add(0, "public");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 44 + "   " + inputSymbol + "  //" + inputType + "      Pop 44, Push 7  //R18");
					}
					else if(inputSymbol.equals("protected")) {
						stack.remove(0);          //pop stack
						stack.add(0, "protected");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 44 + "   " + inputSymbol + "  //" + inputType + "      Pop 44, Push 8  //R19");
					}
				}
				else if(stackSymbol.equals("<type>")) {
					if(inputSymbol.equals("int")) {
						stack.remove(0);          //pop stack
						stack.add(0, "int");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 45 + "   " + inputSymbol + "  //" + inputType + "      Pop 45, Push 9  //R20");
					}
					else if(inputSymbol.equals("float")) {
						stack.remove(0);          //pop stack
						stack.add(0, "float");  //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 45 + "   " + inputSymbol + "  //" + inputType + "      Pop 45, Push 10  //R21");
					}
					else if(inputSymbol.equals("object")) {
						stack.remove(0);          //pop stack
						stack.add(0, ")");  //push the RHS
						stack.add(0, "[id]");
						stack.add(0, "(");
						stack.add(0, "object");
						System.out.println(step + "              " + stackSymbol + "   //" + 45 + "   " + inputSymbol + "  //" + inputType + "      Pop 45, Push 27, 1, 26, 11  //R22");
					}
					
				}
				else if(stackSymbol.equals("<id-list>")) {
					if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-ids>");  //push the RHS
						stack.add(0, "[id]");
						System.out.println(step + "              " + stackSymbol + "   //" + 46 + "   " + inputSymbol + "  //" + inputType + "      Pop 46, Push 47, 1  //R23");
					}
				}
				else if(stackSymbol.equals("<more-ids>")) {
					if(inputSymbol.equals(",")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-ids>"); //push the RHS
						stack.add(0, "[id]");
						stack.add(0, ",");
						System.out.println(step + "              " + stackSymbol + "   //" + 47 + "   " + inputSymbol + "  //" + inputType + "      Pop 47, Push 47, 1, 31  //R24");
					}
					else if(inputSymbol.equals(")") || inputSymbol.equals(":") || inputSymbol.equals(";") || inputSymbol.equals(":=")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 47 + "   " + inputSymbol + "  //" + inputType + "      Pop 47  //R25");
					}
					
				}
				else if(stackSymbol.equals("<block>")) {
					if(inputSymbol.equals("{")) {
						stack.remove(0);          //pop stack
						stack.add(0, "}"); //push the RHS
						stack.add(0, "<stmts>");
						stack.add(0, "{");
						System.out.println(step + "              " + stackSymbol + "   //" + 48 + "   " + inputSymbol + "  //" + inputType + "      Pop 48, Push 29, 51, 28  //R26");
					}
				}
				
				else if(stackSymbol.equals("<stmts>")) {
					if(inputSymbol.equals("read") || inputSymbol.equals("print") || inputType.equals("1") || inputSymbol.equals("if") || inputSymbol.equals("do") || inputSymbol.equals("switch") || inputSymbol.equals("case") || inputSymbol.equals("break") || inputSymbol.equals("lambda")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-stmts>"); //push the RHS
						stack.add(0, ";");
						stack.add(0, "<stmt>");
						System.out.println(step + "              " + stackSymbol + "   //" + 49 + "   " + inputSymbol + "  //" + inputType + "      Pop 49, Push 50, 25, 51  //R27");
					}
				}
				else if(stackSymbol.equals("<more-stmts>")) {
					if(inputSymbol.equals("read") || inputSymbol.equals("print") || inputType.equals("1") || inputSymbol.equals("if") || inputSymbol.equals("do") || inputSymbol.equals("switch") || inputSymbol.equals("case") || inputSymbol.equals("break") || inputSymbol.equals("lambda")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-stmts>"); //push the RHS
						stack.add(0, ";");
						stack.add(0, "<stmt>");
						System.out.println(step + "              " + stackSymbol + "   //" + 50 + "   " + inputSymbol + "  //" + inputType + "      Pop 50, Push 50, 25, 51  //R28");
					}
					else if(inputSymbol.equals("}")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 50 + "   " + inputSymbol + "  //" + inputType + "      Pop 50  //R29");
					}
				}
				else if(stackSymbol.equals("<stmt>")) {
					if(inputSymbol.equals("read")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<input>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 52  //R30");
					}
					else if(inputSymbol.equals("print")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<output>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 53  //R31");
					}
					else if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<asmt>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 54  //R32");
					}
					else if(inputSymbol.equals("if")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<cond>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 57  //R33");
					}
					else if(inputSymbol.equals("do")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<loop>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 58  //R34");
					}
					else if(inputSymbol.equals("switch")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<switch>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 59  //R35");
					}
					else if(inputSymbol.equals("case")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<case>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 60  //R36");
					}
					else if(inputSymbol.equals("break")) {
						stack.remove(0);          //pop stack
						stack.add(0, "break"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 12  //R37");
					}
					else if(inputSymbol.equals("lambda")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<lambda>"); //push the RHS
						System.out.println(step + "              " + stackSymbol + "   //" + 51 + "   " + inputSymbol + "  //" + inputType + "      Pop 51, Push 61  //R38");
					}
					
				}
				else if(stackSymbol.equals("<input>")) {
					if(inputSymbol.equals("read")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<id-list>"); //push the RHS
						stack.add(0, "read");
						System.out.println(step + "              " + stackSymbol + "   //" + 52 + "   " + inputSymbol + "  //" + inputType + "      Pop 52, Push 46, 13  //R39");
					}
				}
				else if(stackSymbol.equals("<output>")) {
					if(inputSymbol.equals("print")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<id-list>"); //push the RHS
						stack.add(0, "print");
						System.out.println(step + "              " + stackSymbol + "   //" + 53 + "   " + inputSymbol + "  //" + inputType + "      Pop 53, Push 46, 14  //R40");
					}
				}
				else if(stackSymbol.equals("<asmt>")) {
					if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr-list>"); //push the RHS
						stack.add(0, ":=");
						stack.add(0, "<id-list>");
						System.out.println(step + "              " + stackSymbol + "   //" + 54 + "   " + inputSymbol + "  //" + inputType + "      Pop 54, Push 55, 15, 46  //R41");
					}
				}
				else if(stackSymbol.equals("<expr-list>")) {
					if(inputType.equals("1") || inputType.equals("2") || inputSymbol.equals("(") || inputSymbol.equals("+") || inputSymbol.equals("*")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-exprs>"); //push the RHS
						stack.add(0, "<expr>");
						System.out.println(step + "              " + stackSymbol + "   //" + 55 + "   " + inputSymbol + "  //" + inputType + "      Pop 55, Push 56, 62  //R42");
					}
				}
				else if(stackSymbol.equals("<more-exprs>")) {
					if(inputSymbol.equals(",")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<more-exprs>"); //push the RHS
						stack.add(0, "<expr>");
						stack.add(0, ",");
						System.out.println(step + "              " + stackSymbol + "   //" + 56 + "   " + inputSymbol + "  //" + inputType + "      Pop 56, Push 56, 62, 31  //R43");
					}
					else if(inputSymbol.equals(";") || inputSymbol.equals("while")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 56 + "   " + inputSymbol + "  //" + inputType + "      Pop 56  //R44");
					}
				}
				else if(stackSymbol.equals("<cond>")) {
					if(inputSymbol.equals("if")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<stmt>"); //push the RHS
						stack.add(0, "<expr>");
						stack.add(0, "if");
						System.out.println(step + "              " + stackSymbol + "   //" + 57 + "   " + inputSymbol + "  //" + inputType + "      Pop 57, Push 51, 62, 16  //R45");
					}
				}
				else if(stackSymbol.equals("<loop>")) {
					if(inputSymbol.equals("do")) {
						stack.remove(0);          //pop stack
						stack.add(0, ")"); //push the RHS
						stack.add(0, "<expr>");
						stack.add(0, "(");
						stack.add(0, "while");
						stack.add(0, "<stmt>");
						stack.add(0, "do");
						System.out.println(step + "              " + stackSymbol + "   //" + 58 + "   " + inputSymbol + "  //" + inputType + "      Pop 58, Push 27, 62, 26, 16, 51, 17  //R46");
					}
				}
				else if(stackSymbol.equals("<switch>")) {
					if(inputSymbol.equals("switch")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<block>"); //push the RHS
						stack.add(0, ")");
						stack.add(0, "<expr>");
						stack.add(0, "(");
						stack.add(0, "switch");
						System.out.println(step + "              " + stackSymbol + "   //" + 59 + "   " + inputSymbol + "  //" + inputType + "      Pop 59, Push 48, 27, 62, 26, 19  //R47");
					}
				}
				else if(stackSymbol.equals("<case>")) {
					if(inputSymbol.equals("case")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr-list>"); //push the RHS
						stack.add(0, "case");
						System.out.println(step + "              " + stackSymbol + "   //" + 60 + "   " + inputSymbol + "  //" + inputType + "      Pop 60, Push 55, 20  //R48");
					}
				}
				else if(stackSymbol.equals("<lambda>")) {
					if(inputSymbol.equals("lambda")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<block>"); //push the RHS
						stack.add(0, ")");
						stack.add(0, "<id-list>");
						stack.add(0, "(");
						stack.add(0, "lambda");
						System.out.println(step + "              " + stackSymbol + "   //" + 61 + "   " + inputSymbol + "  //" + inputType + "      Pop 61, Push 48, 27, 46, 26, 21  //R49");
					}
					
				}
				else if(stackSymbol.equals("<expr>")) {
					if(inputType.equals("1")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr1>"); //push the RHS
						stack.add(0, "[id]");
						System.out.println(step + "              " + stackSymbol + "   //" + 62 + "   " + inputSymbol + "  //" + inputType + "      Pop 62, Push 63, 1  //R50");
					}
					else if(inputType.equals("2")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr1>"); //push the RHS
						stack.add(0, "[const]");
						System.out.println(step + "              " + stackSymbol + "   //" + 62 + "   " + inputSymbol + "  //" + inputType + "      Pop 62, Push 63, 2  //R51");
					}
					else if(inputSymbol.equals("(")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr1>"); //push the RHS
						stack.add(0, ")");
						stack.add(0, "<expr>");
						stack.add(0, "(");
						System.out.println(step + "              " + stackSymbol + "   //" + 62 + "   " + inputSymbol + "  //" + inputType + "      Pop 62, Push 63, 27, 62, 26  //R52");
					}
					
				}
				else if(stackSymbol.equals("<expr1>")) {
					if(inputSymbol.equals("+")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr>"); //push the RHS
						stack.add(0, "+");
						System.out.println(step + "              " + stackSymbol + "   //" + 63 + "   " + inputSymbol + "  //" + inputType + "      Pop 63, Push 62, 32  //R53");
					}
					else if(inputSymbol.equals("*")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr>"); //push the RHS
						stack.add(0, "*");
						System.out.println(step + "              " + stackSymbol + "   //" + 63 + "   " + inputSymbol + "  //" + inputType + "      Pop 63, Push 62, 33  //R54");
					}
					else if(inputSymbol.equals("and")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr>"); //push the RHS
						stack.add(0, "and");
						System.out.println(step + "              " + stackSymbol + "   //" + 63 + "   " + inputSymbol + "  //" + inputType + "      Pop 63, Push 62, 22  //R55");
					}
					else if(inputSymbol.equals("or")) {
						stack.remove(0);          //pop stack
						stack.add(0, "<expr>"); //push the RHS
						stack.add(0, "or");
						System.out.println(step + "              " + stackSymbol + "   //" + 63 + "   " + inputSymbol + "  //" + inputType + "      Pop 63, Push 62, 23  //R56");
					}
					else if(inputSymbol.equals(",") || inputSymbol.equals(";") || inputSymbol.equals("while") || inputSymbol.equals(")") || inputSymbol.equals("read") || inputSymbol.equals("print") || inputType.equals("1") || inputSymbol.equals("if") || inputSymbol.equals("do") || inputSymbol.equals("switch") || inputSymbol.equals("case") || inputSymbol.equals("break") || inputSymbol.equals("lambda")) {
						stack.remove(0);          //pop stack
						System.out.println(step + "              " + stackSymbol + "   //" + 63 + "   " + inputSymbol + "  //" + inputType + "      Pop 63 //R57");
					}
				}
				
			}
			++step;
			
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		LexicalAnalyzer.openFile();
		LexicalAnalyzer.printFile();
		System.out.println();
		LexicalAnalyzer.blockStart();   //scanner
		
		
		parser();
		
		
		System.out.println("");
		System.out.println("The symbol table goes as follows");
		for(String token : LexicalAnalyzer.symbolTable.keySet()) {
			System.out.println(token + " : " + LexicalAnalyzer.symbolTable.get(token));
		}
		
		
		
		LexicalAnalyzer.closeFile();;
		
	}


}
