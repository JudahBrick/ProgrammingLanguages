
public class FilterLinesImpl implements FilterLines {

	String searchString;
	public FilterLinesImpl(String searchString){
		this.searchString = searchString;
	}

	@Override
	public String process(String documentInString) {
		StringBuilder rtn = new StringBuilder();
		String[] lines = documentInString.split("\\v");
		for(int i = 0; i < lines.length; i++){
			if(lines[i].contains(searchString)){
				rtn.append(lines[i]);
			}
		}
//		StringBuilder line = new StringBuilder();
//		char[] chars = documentInString.toCharArray();
//		for(int i = 0; i < chars.length; i++){
//			char current = chars[i];
//			//TODO fix this so were not worried about index out of bouds issues
//			if(current == '\n' || (current == '\r' && chars[i + 1] == '\n')){
//				line.append(' ');
//				String lineAsString = line.toString();
//				if(lineAsString.contains(documentInString)){
//					rtn.append(line);
//				}
//				line = new StringBuilder();
//			}
//			else {
//				line.append(current);
//			}
//		}
		return rtn.toString();
	}
}
