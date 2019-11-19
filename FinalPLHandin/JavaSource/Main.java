
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    //	- grep only uses step 1
//	- wc uses steps 2 through 5
//	t- grep | wc uses all 5 steps
//    private FilterLines filterLines;		//Step 1
//    private ConvertCase convertCase;		//Step 2
//    private FindWords findWords;			//Step 3
//    private FilterNonAlpha filterNonAlpha;	//Step 4
//    private CountWords countWords;			//Step 5


//        System.out.println(testStr);
//        //testStr = test.convertToLowerCase(testStr);
//        //String aftertakeOutUslessLines = test.takeOutUslessLines(testStr, "this");
//        System.out.println("RESULTS OF GREP");
//        System.out.println(aftertakeOutUslessLines);
    }

//
//    public static void parseArgs(String[] args) {
//
//    }
//
//
//    //1. Filter-Lines: filter out lines that don’t meet grep search criteria    -  String -> String
//    public String takeOutUslessLines(String everything, String serachString) {
//        StringBuilder rtn = new StringBuilder();
//        StringBuilder line = new StringBuilder();
//        char[] chars = everything.toCharArray();
//        for(int i = 0; i < chars.length; i++){
//            char current = chars[i];
//            //TODO fix this so were not worried about index out of bouds issues
//            if(current == '\n' || (current == '\r' && chars[i + 1] == '\n')){
//                line.append(' ');
//                String lineAsString = line.toString();
//                if(lineAsString.contains(serachString)){
//                    rtn.append(line);
//                }
//                line = new StringBuilder();
//            }
//            else {
//                line.append(current);
//            }
//
//        }
//        return rtn.toString();
//        //return "";
//    }
//
//
////2. Non-Alphabetic-Filter: strip out all non-alphabetic characters         -  String -> String
//    public String stipOutNonAlphaChars(String everything){
//        StringBuilder rtn = new StringBuilder();
//        char[] chars = everything.toCharArray();
//        for(int i = 0; i < chars.length; i++){
//            char current = chars[i];
//            if(current >= 65 && current <= 90  || current >= 97 && current <= 122 || current == 32){
//                rtn.append(current);
//            }
//        }
//        return rtn.toString();
//    }
//
//
//    //3. Convert-Case: put everything in lowercase                              -  String -> String
//    public String convertToLowerCase(String everything) {
//        return everything.toLowerCase();
//    }
//
//
//
////4. Find-Words: split the text into individual words                       -  String -> list of all words
//    public List<String> splitIntoList(String words){
//        String[] allWords = words.split("\s");
//        return Arrays.asList(allWords);
//    }
//
//
////5. Count-Words: produce a set of unique words and the number of times they each appear    - list of all words -> set of unique words and their counts
//    public Map<String, Integer> countWords(List<String> words){
//        Map<String, Integer> rtn = new HashMap<>();
//        for(String word: words){
//            if(rtn.containsKey(word)){
//                int numOfTimes = rtn.get(word);
//                rtn.put(word, numOfTimes + 1);
//            }
//            else{
//                rtn.put(word, 1);
//            }
//        }
//        return rtn;
//    }
//}
