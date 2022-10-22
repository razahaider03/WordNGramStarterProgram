package WordNGramStarterProgram;

import java.util.ArrayList;
import java.util.Random;

public class MarkovWordTwo implements IMarkovModel{
    private String[] myText;
    private Random myRandom;

    public MarkovWordTwo() {
        myRandom = new Random();
    }


    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
    }

    private int indexOf(String[] words, String target1,String target2, int start) {
        for (int i = start; i < words.length; i++) {
            if (words[i].equals(target1) && words[i+1].equals(target2)) {
                return i+1;
            }
        }
        return -1;
    }



    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-2);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1).append(" ").append(key2);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key1,key2);
            if (follows.size() == 0) {
                break;
            }
//			System.out.println(key);
//			System.out.println(follows);
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }

        return sb.toString().trim();
    }

    private ArrayList<String> getFollows(String key1,String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        int start = 0;
        for (int i = 0; i < myText.length-1; i++) {
            int index = indexOf(myText, key1,key2, start);
            if (index == -1 || index >= myText.length-1) {
                break;
            }
            String next = myText[index + 1];
            follows.add(next);
            start= index;
        }

        return follows;
    }
}
