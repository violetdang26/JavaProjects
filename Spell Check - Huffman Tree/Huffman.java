import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;


/*
The program should take as a command line argument the name of a file which 
contains some text. It should then compute the frequencies of the characters 
in that text and internally build the Huffman tree.  You should then print out 
in the console window a table of characters along with the corresponding Huffman codes.

The program should then prompt the user to enter a code of 0's and ones. 
When you press enter the program should decode your input based on the Huffman 
tree that you constructed from the original input file. If there is an error in the code, 
print error, rather than the decoded message.

Finally, the program should prompt the user for a series of characters. 
When the user presses enter, those characters should be converted into 
the corresponding Huffman code based on the Huffman tree built from the original file input.
 */
public class Huffman {

	 //HashMap<Character,Integer> countFreq;
	static HuffmanNode root;
	

	public static HuffmanNode buildTree(HashMap<Character,Integer> countFreq) {
		PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
		
		for (char c : countFreq.keySet()) {
			int fr = countFreq.get(c);
			pq.offer(new HuffmanNode(c,fr));
		}
		
		while (pq.size() >1) {
			HuffmanNode n1 = pq.poll();
			HuffmanNode n2 = pq.poll();
			
			pq.offer(new HuffmanNode('*',n1,n2,n1.freq+n2.freq));
			}
		
		
		root = pq.poll();
		return root;
	}
	
	
	
	public static void decode(HuffmanNode root, String s){
		//take in string of huffman code, decode to characters
		String output = "";
		HuffmanNode new_root = root;
		if (root != null) {
			for (int i=0; i<s.length();i++) {
					if(s.charAt(i)=='0') {
						new_root = new_root.left;
					}
					else if(s.charAt(i)=='1') {
						new_root = new_root.right;
					}
					else {
						System.out.println("Invalid Huffman input (contain character not 1/0)");
						break; //break loop is input is not 0 or 1	
					}
					if (i+1!=s.length()) {
						if (new_root.isLeaf()) {
							output += new_root.element;
							new_root = root;
						}
					}
					else {
						if (new_root.isLeaf()) {
							output += new_root.element;

							System.out.println(output);
						}
						else {
							System.out.println("Invalid Huffman input");
						}}}}
		else {
		System.out.println("Invalid input, cannot find character");
		}}
	
	

	public static HashMap<String, String> makeHuffmanCode(HashMap<String,String> huffmanCodes,HuffmanNode t, String s){
		//HashMap<Character,String> huffmanCodes=new HashMap<Character,String>();
		if (t != null){
			if(!t.isLeaf()) {
				makeHuffmanCode(huffmanCodes, t.left, s + "0");
				makeHuffmanCode(huffmanCodes, t.right, s + "1");
			}
			else {
				if (t.element == '\n') {
					huffmanCodes.put("\n", s);
				}
				else if (t.element == ' ') {
					huffmanCodes.put(" ", s);
			}
				else {
					String str = Character.toString(t.element);
					huffmanCodes.put(str, s);
				}
				
				
			}
		}
		return huffmanCodes;

	}
	
	
	
	static class HuffmanNode implements Comparable<HuffmanNode>
	    {
	    	
	    	char   			element;      // The data in the node
	    	HuffmanNode  	left;         // Left child
	    	HuffmanNode  	right;        // Right child
	        int      		freq;       // frequency
	        
	            // Constructors

	        public HuffmanNode(char theElement, int fr) {
	        	element = theElement;
	        	freq = fr;
	        	left = right = null;
	        }
	        
	        public HuffmanNode( char theElement, HuffmanNode lt, HuffmanNode rt, int fr) 
	        {
	            element  = theElement;
	            left     = lt;
	            right    = rt;
	            freq   = fr;
	        }
	        
	        
	        public boolean isLeaf(){
	            return left==null && right==null;
	         }
	        
	        public int compareTo(HuffmanNode x) {
	        	return freq -(x.freq);
	        }
	    }
	 
	
	public static HashMap<Character, Integer> readText(String filename) throws IOException {
		//read text and map text frequency into a hashmap
		
		HashMap<Character,Integer> countFreq=new HashMap<Character,Integer>();

		int lineNumber = 0;
		
		File f = new File(filename);
		Scanner s = new Scanner(f);
		while(s.hasNextLine()){
			lineNumber++;
			String line = s.nextLine();
			for (int i = 0; i < line.length(); i++){
				if (countFreq.get(line.charAt(i)) != null)
					countFreq.put(line.charAt(i), countFreq.get(line.charAt(i)) + 1);
				else
					countFreq.put(line.charAt(i), 1);
			}
		}
		countFreq.put('\n', lineNumber); //add new line into the hashmap
		s.close();
		return countFreq;
	
	}
	public static void printTree(HuffmanNode root) {
		printTree(root,"");
	}

	private static void printTree(HuffmanNode t,String s) {
		if(t!=null) {
			if(!t.isLeaf()) {
				
				printTree(t.left,s+"0");
				printTree(t.right,s+"1");
				
			}
			else {
				if (t.element == '\n') {
					System.out.println(t.element +"\t"+s + " (new line)");
				}
				else if (t.element == ' ') {
					System.out.println(t.element +"\t" + s +" (space)");
			}
				else {
					System.out.println(t.element +"\t" + s);
				}
			}
		}
	}
	
	
	public static void encode(HashMap<String,String> huffmanTable, String s){
		String output = "";
		for (int i = 0; i < s.length(); i++){
			String s_char = Character.toString(s.charAt(i));
			if (huffmanTable.containsKey(s_char)) {
					output += huffmanTable.get(s_char);
				}
			else {
				System.out.println("The characters you entered does not exist in the existing Huffman Tree");
				break;
			}
			
			if (i==s.length()-1) {
			System.out.println(output);}	
		}
		
	   }
	
	
	public static void main (String[] args) throws IOException {
		//String file = "/Users/hoadang/Desktop/Columbia/Spring 2017/Data Structure/HW4/src/simple.txt";
		HashMap<Character,Integer> countFreq = readText(args[0]);
		HuffmanNode r = buildTree(countFreq);
		HashMap<String,String> huffmanCodes= new HashMap<String,String>();
		HashMap<String,String> huffmanTable= makeHuffmanCode(huffmanCodes,r,"");
		
		
		printTree(r);
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a Huffman code: ");
		String s = reader.nextLine();
		
		decode(r,s);
		System.out.println("Enter string of text for encoding");
		String newletters = reader.nextLine();
		
		encode(huffmanTable, newletters);
		reader.close();
	}
}


/*
String fileContents = "";
while ((line = br.readLine()) != null) {
	 line=line.toLowerCase();
	 fileContents = fileContents +line + "\n";
}


char[] char_array = fileContents.toCharArray();
for (int i =0;i<char_array.length;i++) {
	 char c = char_array[i];
	 if (countFreq.containsKey(c)) {
		 countFreq.put(c, countFreq.get(c)+1);
	 }
	 else {
		 countFreq.put(c, 1);
	 }
	 
}

public static void decode(HuffmanNode root, String s)
{
	String output = "";
	if (root == null) {
		System.out.println("invalid input");
	}
	
	while(!s.isEmpty()) {
	if (s.charAt(0)=='0') {
		decode(root.right,s);
		s = s.substring(1);
		output += "1";
	}
	else {
		decode(root.left,s);
		s = s.substring(1);
		output += "0";
	}
	}
	
	System.out.println(output);
}
*/


