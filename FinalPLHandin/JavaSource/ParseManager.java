
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ParseManager {

	String[] commandLineArgs;
	DocumentProcessor doc;
	String filePath;

	public ParseManager(String[] commandLineArgs){
		this.commandLineArgs = commandLineArgs;
		this.create();
	}

	private void create() {
		int argNum = commandLineArgs.length;
		DocumentProcessorBuilder docbuilder = new DocumentProcessorBuilder();
		switch (argNum) {
			case 2://wc FP
				doc = docbuilder.setConvertCase(new ConvertCaseImpl()).setFindWords(new FindWordsImpl())
						.setFilterNonAlpha(new FilterNonAlphaImpl()).setCountWords(new CountWordsImpl()).build();
				filePath = commandLineArgs[1];
				break;
			case 3://grep word FP
				doc = docbuilder.setFilterLines(new FilterLinesImpl(commandLineArgs[1])).build();
				filePath = commandLineArgs[2];
				break;
			case 5://grep word FP "|" wc
				doc = docbuilder.setFilterLines(new FilterLinesImpl(commandLineArgs[1])).setConvertCase(new ConvertCaseImpl())
						.setFindWords(new FindWordsImpl()).setFilterNonAlpha(new FilterNonAlphaImpl())
						.setCountWords(new CountWordsImpl()).build();
				filePath = commandLineArgs[2];
				break;
			default:
				System.out.println("Wrong number of arguments");
				return;
		}
	}

	public void process(){

		File file = new File(filePath);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder fileContents = new StringBuilder();
		//scan.
		while (scan.hasNextLine()) {
			fileContents.append(scan.nextLine() + "\n");
		}
		String fileString = fileContents.toString();
		Map<String, Integer> rs =  doc.process(fileString);
		if(rs.isEmpty()){
			return;
		}
		Set<String> keys = rs.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()){
			String current = it.next();
			System.out.println(current + ": " + rs.get(current));
		}
	}

	public static void main(String[] args) {
		ParseManager parseManager = new ParseManager(args);
		parseManager.process();
	}

}
