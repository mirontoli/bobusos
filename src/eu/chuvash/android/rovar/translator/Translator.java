package eu.chuvash.android.rovar.translator;

public class Translator {
	private char[] vocals = { 'a', 'e', 'i', 'o', 'u', 'y', 'ä', 'ö', 'å' };
	private char[] punctuationSymbols = { ' ', '.', ',', ';', '-', '_', '@', '"', '\'', '/', '?', '!', '*' };

	public String translate2Rovar(String input) {
		StringBuilder output = new StringBuilder("");
		char[] inputChars = input.toLowerCase().toCharArray();
		for (char ch : inputChars) {
			output.append(ch);
			if ( ! (isAPunctuationSymbol(ch) || isVocal(ch) || isDigit(ch) ) ) {
				output.append('o');
				output.append(ch);				
			}
		}
		return output.toString();
	}
	public String translateFromRovar(String input) {
		StringBuilder output = new StringBuilder("");
		char[] inputChars = input.toCharArray();
		for (int i = 0; i < inputChars.length; i++) {
			if( ((i+2) < inputChars.length) && (inputChars[i+1] == 'o') && (inputChars[i] == inputChars[i+2])) {				
				i = i + 2;
			}
			output.append(inputChars[i]);
		}
		return output.toString();
	}
	public boolean isVocal(char character) {
		boolean isVocal = false;
		
		int index = 0;
		while (!isVocal && index < vocals.length) {
			if (character == vocals[index]) {
				isVocal = true;
			}
			index++;
		}

		return isVocal;
	}
	public boolean isAPunctuationSymbol(char character) {
		boolean isAPunctuationSymbol = false;
		
		int index = 0;
		while (!isAPunctuationSymbol && index < punctuationSymbols.length) {
			if (character == punctuationSymbols[index] ) {
				isAPunctuationSymbol = true;
			}
			index++;
		}
		
		return isAPunctuationSymbol;
	}
	public boolean isDigit(char character) {	
		return Character.isDigit(character);
	}
}