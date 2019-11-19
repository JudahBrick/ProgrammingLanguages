
import java.util.ArrayList;
import java.util.List;

public class FilterNonAlphaImpl implements FilterNonAlpha {

	@Override
	public List<String> process(List<String> listOfWords) {
		List<String> rtn = new ArrayList<>();
		for (String word: listOfWords) {
			String current;
			current =  word.replaceAll("\\s|\\W", "");
			if(current.length() > 0){
				rtn.add(current);
			}
		}
		return rtn;
	}

//	public String processWord(String documentInString) {
//		StringBuilder rtn = new StringBuilder();
//		char[] chars = documentInString.toCharArray();
//		for(int i = 0; i < chars.length; i++){
//			char current = chars[i];
//			//TODO make this filter goo for the other unix space letters lik \r \n...
//			if(current == '\w'){//current >= 65 && current <= 90				//Uppercase letters
//					//|| current >= 97 && current <= 122 		//lower case letters
//					// || current == 32						// SPACE
//					//|| current >= 48 && current <= 57 ){	//numbers
//				rtn.append(current);
//			}
//		}
//		return rtn.toString();
//	}

}
