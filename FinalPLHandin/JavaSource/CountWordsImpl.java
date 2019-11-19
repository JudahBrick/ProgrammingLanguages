
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountWordsImpl implements CountWords {
	@Override
	public Map<String, Integer> process(List<String> documentInString) {
		Map<String, Integer> rtn = new HashMap<>();
		for(String word: documentInString){
			if(rtn.containsKey(word)){
				int numOfTimes = rtn.get(word);
				rtn.put(word, numOfTimes + 1);
			}
			else{
				rtn.put(word, 1);
			}
		}
		return rtn;
	}
}
