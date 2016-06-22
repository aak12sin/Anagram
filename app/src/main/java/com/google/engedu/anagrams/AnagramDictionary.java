package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int wordLength =DEFAULT_WORD_LENGTH;
    private HashSet<String> wordSet = new HashSet<String>();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String,ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String l;
        while((l = in.readLine()) != null) {
            String alp = l.trim();
            wordSet.add(alp);
            wordList.add(alp);

            String key = alphaOrder(alp);
            if(lettersToWord.containsKey(key)){
                lettersToWord.get(key).add(alp);
            }
            else{
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(alp);
                lettersToWord.put(key,temp);
            }
            if(sizeToWords.containsKey(alp.length()))
                sizeToWords.get(alp.length()).add(alp);
            else{
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(alp);
                sizeToWords.put(alp.length(), temp);
            }


        }
    }
    public String alphaOrder(String word)
    {
        char arr[]=word.toCharArray();
        Arrays.sort(arr);
        return  String.valueOf(arr);
    }

    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word) && !word.contains(base))
            return true;

        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(int j=97;j<=121;j++)
        {
            String newWord=word+ (char)j;
            if(lettersToWord.containsKey(alphaOrder(newWord)))
            {
                ArrayList<String> temp=lettersToWord.get(alphaOrder(newWord));
                for(String n :temp)
                    result.add(n);

            }
        }

        return result;
    }

    public String pickGoodStarterWord()
    {
        boolean hasMin=false;
        String starter;
        ArrayList<String> words=sizeToWords.get(wordLength);
        int let=random.nextInt(words.size());
        do{
            if(let> (words.size()-1))
            {
                let=0;
            }
            starter=words.get(let);

            if(getAnagramsWithOneMoreLetter(starter).size()>=MIN_NUM_ANAGRAMS)
                hasMin=true;

            let++;
        }while(!hasMin);

        if(wordLength<=MAX_WORD_LENGTH)
            wordLength++;

        return starter;
    }
}
