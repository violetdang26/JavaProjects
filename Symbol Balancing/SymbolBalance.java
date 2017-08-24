
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class SymbolBalance<T> {

	public static String readFile(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line;
		String fileContents = "";
		 while ((line = br.readLine()) != null) {
			 fileContents = fileContents + line;
		 }
		 br.close();
		 return fileContents;
	}

	public static void balanceMethod(String s) {
		
		MyStack<Character> symbolstack = new MyStack<Character>();
		
		for (int i = 0; i < s.length();i++) {
			Character symbol = s.charAt(i);
			
			if ((symbol == '(' || symbol== '{' || symbol == '[' ) && isComOrLit(symbolstack)==false){
				symbolstack.push(symbol);
				//System.out.println(symbolstack.peek());
			}
			
			if (symbol == '"' && isComment(symbolstack)==false) {
				if (symbolstack.peek() != '"') {
					symbolstack.push(symbol);
					//System.out.println(symbolstack.peek());
				} 
				else {
					symbolstack.pop();
				}
			}
			
			if (symbol == '/' && s.charAt(i+1) == '*' && isComOrLit(symbolstack)==false) {
					symbolstack.push('+'); 
					//System.out.println(symbolstack.peek());
			}
			
			if (symbol == '*' && s.charAt(i+1) == '/' && (symbolstack.isEmpty()==true || symbolstack.pop() != '+' )) {
				System.out.println("The error is in symbol /*");
				System.exit(0);
			}
	
			if (symbol == ')' && isComOrLit(symbolstack)==false && (symbolstack.isEmpty()==true || symbolstack.pop() != '('  )) {
				System.out.println("The error is in symbol " + symbol);
				System.exit(0);
				}
			
			if (symbol == '}' && isComOrLit(symbolstack)==false && ( symbolstack.isEmpty()==true || symbolstack.pop() != '{')) {
				System.out.println("The error is in symbol " + symbol);
				System.exit(0);
				}
			
			if (symbol == ']' && isComOrLit(symbolstack)==false && (symbolstack.isEmpty()==true || symbolstack.pop() != '[' )) {
				System.out.println("The error is in symbol " + symbol);
				System.exit(0);
			}
			

			//else if ((symbol == ')' || symbol== '}' || symbol == ']' || symbol == '"' || (symbol == '*' && s.charAt(i+1) == '/') ) && symbolstack.isEmpty()==true) {
			//	System.out.println("daThe error is in symbol " + symbol);
			//}
		}
			
		if (symbolstack.size() != 0) {
			System.out.println("The error is in symbol " + symbolstack.peek());
		}

	}
	public static boolean isComment(MyStack<Character> stack) {
		while (stack.isEmpty() == false) {
			if (stack.peek() == '+') {
				return true;
				}
			return false;
			}
		return false;
		}
	
	public static boolean isLiteral(MyStack<Character> stack) {
		while (stack.isEmpty() == false) {
			if (stack.peek() =='"') {
				return true;
				}
			return false;
			}
		return false;
		}
	
	public static boolean isComOrLit(MyStack<Character> stack) {
		//is comment or literal
		if (isLiteral(stack) == false && isComment(stack)==false) {
			return false;
		}
		else {return true;}
	}
	
	

	
	public static void main (String[] args) throws IOException {
		//String file = "/* this is the [] main ''method */ public static void main(String[] args)}";
		String file = SymbolBalance.readFile(args[0]);
		balanceMethod(file);
	}
	
	
}
