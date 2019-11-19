import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentProcessorBuilder {

//	- grep only uses step 1
//	- wc uses steps 2 through 5
//	t- grep | wc uses all 5 steps
	private FilterLines filterLines;		//Step 1
	private ConvertCase convertCase;		//Step 2
	private FindWords findWords;			//Step 3
	private FilterNonAlpha filterNonAlpha;	//Step 4
	private CountWords countWords;			//Step 5




	public DocumentProcessorBuilder setFilterLines(FilterLines filterLines) {
		this.filterLines = filterLines;
		return this;
	}

	public DocumentProcessorBuilder setConvertCase(ConvertCase convertCase) {
		this.convertCase = convertCase;
		return this;
	}

	public DocumentProcessorBuilder setFindWords(FindWords findWords) {
		this.findWords = findWords;
		return this;
	}

	public DocumentProcessorBuilder setFilterNonAlpha(FilterNonAlpha filterNonAlpha) {
		this.filterNonAlpha = filterNonAlpha;
		return this;
	}

	public DocumentProcessorBuilder setCountWords(CountWords countWords) {
		this.countWords = countWords;
		return this;
	}

	public DocumentProcessor build(){
		return new DocumentProcessor(filterLines, convertCase, findWords, filterNonAlpha, countWords);
	}

}
