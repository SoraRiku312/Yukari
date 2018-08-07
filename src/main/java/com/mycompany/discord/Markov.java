/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Markov {

	// Hashmap
	public static HashMap<String, ArrayList<String>> markovChain = new HashMap<String, ArrayList<String>>();
	static Random rnd = new Random();
	
	
	/*
	 * Main constructor
	 */
	public static void Markov() throws IOException {
		
		// Create the first two entries (k:_start, k:_end)
		markovChain.put("_start", new ArrayList<String>());
		markovChain.put("_end", new ArrayList<String>());

		
		// Add the words to the hash table
		}
		
	
	
	/*
	 * Add words
	 */
	public static void addWords(List<String> word) {
		// put each word into an array

                String[]words=word.toArray(new String[word.size()]);
                for(int n=0;n<words.length;n++)
                {
                    System.out.println(words[n]);
                }
		// Loop through each word, check if it's already added
		// if its added, then get the suffix vector and add the word
		// if it hasn't been added then add the word to the list
		// if its the first or last word then select the _start / _end key
		
		for (int i=0; i<words.length; i++) {
						
			// Add the start and end words to their own
			if (i == 0) {
				ArrayList<String> startWords = markovChain.get("_start");
                                System.out.println(words[i]);

				startWords.add(words[i]);
                                System.out.println(words[i]);
				
				ArrayList<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
					suffix = new ArrayList<String>();
					suffix.add(words[i+1]);
					markovChain.put(words[i], suffix);
				}
				
			} else if (i == words.length-1) {
				ArrayList<String> endWords = markovChain.get("_end");
				endWords.add(words[i]);
				
			} else {	
				ArrayList<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
					suffix = new ArrayList<String>();
					suffix.add(words[i+1]);
					markovChain.put(words[i], suffix);
				} else {
					suffix.add(words[i+1]);
					markovChain.put(words[i], suffix);
				}
			}
		}		
	}
	
	
	/*
	 * Generate a markov phrase
	 */
	public static String generateSentence() {
		
		// Vector to hold the phrase
		ArrayList<String> newPhrase = new ArrayList<String>();
		
		// String for the next word
		String nextWord = "";
				
		// Select the first word
		ArrayList<String> startWords = markovChain.get("_start");
		int startWordsLen = startWords.size();
		nextWord = startWords.get(rnd.nextInt(startWordsLen));
		newPhrase.add(nextWord);
		
		// Keep looping through the words until we've reached the end
		while (nextWord.charAt(nextWord.length()-1) != '.') {
                    System.out.println(nextWord);
			ArrayList<String> wordSelection = markovChain.get(nextWord);
                        System.out.println(nextWord);

			try{
                            int wordSelectionLen = wordSelection.size();
                        System.out.println(nextWord);
			nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
			newPhrase.add(nextWord);
                        }catch(NullPointerException err)
                        {
                            return (phraseMaker(newPhrase)); 
                        }
		}
		
		return (phraseMaker(newPhrase));	
	}
        public static String phraseMaker(ArrayList<String> list)
        {
            String phrase = "";
            String[]array=new String[list.size()];
            
            array = list.toArray(array);
            for(int n=0;n<array.length;n++)
            {
                phrase+=array[n]+" ";
            }
            return phrase;
        }
}