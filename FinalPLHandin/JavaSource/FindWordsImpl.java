

import java.util.Arrays;
import java.util.List;

public class FindWordsImpl implements FindWords {
	@Override
	public List<String> process(String filteredDoc) {
		//TODO get a correct regex to split off of. that includes other white spaces
		List<String> words = Arrays.asList(filteredDoc.split("\\s"));
		return words;
	}
}
