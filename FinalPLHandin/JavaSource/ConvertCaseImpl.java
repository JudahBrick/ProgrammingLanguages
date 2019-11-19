
public class ConvertCaseImpl implements ConvertCase {
	@Override
	public String process(String documentInString) {
		return documentInString.toLowerCase();
	}
}
