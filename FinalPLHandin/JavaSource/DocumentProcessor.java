
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentProcessor {
	//	- grep only uses step 1
//	- wc uses steps 2 through 5
//	t- grep | wc uses all 5 steps
	private FilterLines filterLines;		//Step 1
	private ConvertCase convertCase;		//Step 2
	private FindWords findWords;			//Step 3
	private FilterNonAlpha filterNonAlpha;	//Step 4
	private CountWords countWords;			//Step 5


	public DocumentProcessor(FilterLines filterLines, ConvertCase convertCase,
							 FindWords findWords, FilterNonAlpha filterNonAlpha, CountWords countWords){
		this.filterLines = filterLines;
		this.convertCase = convertCase;
		this.findWords = findWords;
		this.filterNonAlpha = filterNonAlpha;
		this.countWords = countWords;

	}

	public Map<String, Integer> process(String fileContents){
		Map<String, Integer> rtn = new HashMap<>();

		if(filterLines != null  && convertCase == null && findWords == null
				&& filterNonAlpha == null && countWords == null){
			System.out.println(filterLines.process(fileContents));
			return rtn;
		}
		String goodLines = fileContents;
		if(filterLines != null){
			goodLines = filterLines.process(fileContents);
		}
		//if(filterLines == null  && convertCase != null && findWords != null
		//		&& filterNonAlpha!= null && countWords != null){
			String goodCase = convertCase.process(goodLines);
			List<String> splitToWords = findWords.process(goodCase);
			List<String> removeNonAlpha = filterNonAlpha.process(splitToWords);
			return countWords.process(removeNonAlpha);
		//}
		//return rtn;
	}

	public void setFilterLines(FilterLines filterLines) {
		this.filterLines = filterLines;
	}

	public void setConvertCase(ConvertCase convertCase) {
		this.convertCase = convertCase;
	}

	public void setFindWords(FindWords findWords) {
		this.findWords = findWords;
	}

	public void setFilterNonAlpha(FilterNonAlpha filterNonAlpha) {
		this.filterNonAlpha = filterNonAlpha;
	}

	public void setCountWords(CountWords countWords) {
		this.countWords = countWords;
	}
}
