import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;


public class SpellChecker {
	static HashSet<String> DictTable;
	
	
	public static HashSet<String> readDict(String filename) throws IOException {
		//read dictionary and hash it 
		DictTable = new HashSet<String>();
		
		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line;
		 while ((line = br.readLine()) != null) {
			 line=line.toLowerCase();
			 DictTable.add(line);
		 }
		 
		 br.close();
		 return DictTable;
	}
	
	public static ArrayList<String> suggestWords(String m_word) { //m_word = misspelled word		
		ArrayList<String> suggestions=new ArrayList<String>();
		
		int slen = m_word.length();
		StringBuilder str;
		//add character a-z to word
		for (int i = 0; i <= slen; i++) {
			
			for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
				str =  new StringBuilder(m_word);
				
			    String new_word = str.insert(i, alphabet).toString();
			    if (spellCheck(new_word)) {
			    	suggestions.add(new_word);
			    }
			}
		
		}
		
		//remove one character
		
		for (int i = 0; i < slen; i++) {
			str =  new StringBuilder(m_word);
			str.deleteCharAt(i);
			String new_word = str.toString();

			if (spellCheck(new_word)) {
		    	suggestions.add(new_word);
		    }
			
		}
		//swap adjacent characters
		for (int i = 0; i < slen -1 ;i++) {
			str =  new StringBuilder(m_word);
			char temp = m_word.charAt(i);
			str.deleteCharAt(i);
			str.insert(i+1, temp);
			
			String new_word = str.toString();
			if (spellCheck(new_word)) {
		    	suggestions.add(new_word);
		    }
		}
		
		return suggestions;
		
		
		
	}
	
	public static boolean spellCheck(String word) {
		return DictTable.contains(word);
		}
	
	
	public static void main (String[] args) throws IOException {
		
		DictTable = readDict(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(args[1]));
		//DictTable = readDict("/Users/hoadang/Desktop/Columbia/Spring 2017/Data Structure/HW4/src/words.txt");
		//BufferedReader br = new BufferedReader(new FileReader("/Users/hoadang/Desktop/Columbia/Spring 2017/Data Structure/HW4/src/filetospellcheck.txt"));
		
		
		
		String line;
		int lineNumber = 0;
		//go thru each line
		while ((line = br.readLine()) != null) {
			lineNumber++;
			String[] words = line.split(" ");
			
			for (int i = 0; i < words.length; i++) {
				//go thru each word in line
				
				 String word = words[i];
				 
				 
				 word= word.toLowerCase().replaceAll("[^A-z0-9]+$", "").replaceAll("^[^A-z0-9]+", "");
				 if (word.length()!=0) { 
				 if (DictTable.contains(word)) {
					 System.out.println("On line "+ lineNumber+", the word '"+word + "': is in the dictionary");
				 }
				 
				 else {
				 System.out.println("On line "+ lineNumber+", the word: '"+word + "' is NOT in the dictionary");
				 

				 System.out.println("Suggestions: ");
				 if(suggestWords(word).isEmpty()) {
					 System.out.println("Could not find a suggestion");
				 }
				 ListIterator<String> listIterator  = suggestWords(word).listIterator();
                 while (listIterator.hasNext()) {
                     System.out.println(listIterator.next());
                 }
				 }

			 
			}
			}	
			
		}
		br.close();
	}
}

