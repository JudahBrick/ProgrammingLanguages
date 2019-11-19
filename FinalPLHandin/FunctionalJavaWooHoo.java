
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalJavaWooHoo {

	public static void main(String[] args) {
		String filePath;
		String fullDoc;
		Map<String, Integer> results;
		int argNum = args.length;
		switch (argNum) {
			case 2://wc FP
				filePath = args[1];
				fullDoc = getDocText(filePath);
				results = wc(fullDoc);
				break;
			case 3://grep word FP
				filePath = args[2];
				fullDoc = getDocText(filePath);
				results = grep(fullDoc, args[1]);
				break;
			case 5://grep word FP "|" wc
				filePath = args[2];
				fullDoc = getDocText(filePath);
				results = grepAndWc(fullDoc, args[1]);
				break;
			default:
				System.out.println("Wrong number of arguments");
				return;
		}
		System.out.println(results.entrySet());
	}

	public static String getDocText(String filePath){
			File file = new File(filePath);
			Scanner scan = null;
			try {
				scan = new Scanner(file);
			}
			catch (Exception e){
				e.printStackTrace();
				return "brocken scanner";
			}
			StringBuilder fileContents = new StringBuilder();
			//scan.
			while (scan.hasNextLine()) {
				fileContents.append(scan.nextLine() + "\n");
			}
			String fileString = fileContents.toString();
			return fileString;
		}

	public static Map<String, Integer> grepAndWc(String fullDoc, String searchString){
		return Arrays.stream(fullDoc.split("\\v"))
				.filter( line -> {
					return line.contains(searchString);
				}).map( lineBadCase -> {
					return lineBadCase.toLowerCase();
				}).collect(Collectors.toList()).stream().flatMap( lineWithGoodCase -> {
					return  Arrays.asList(lineWithGoodCase.split("\\s")).stream();
				}).map( word -> {
					return word.replaceAll("\\s|\\W", "");
				}).filter( word -> {
					return !word.isEmpty();
				}).collect(Collector.of(
						() -> new HashMap<String, Integer>(),
						(map, word) -> {
							if(map.containsKey(word)){
								int num = map.get(word);
								map.put((String)word, num + 1);
							}
							else{
								map.put((String) word, 1);
							}
						},
						(map1, map2) -> map1));
	}

	public static Map<String, Integer> wc(String fullDoc){
		return Arrays.asList(fullDoc.toLowerCase().split("\\s")).stream()
		.map( word -> {
			return word.replaceAll("\\s|\\W", "");
		}).filter( word -> {
			return !word.isEmpty();
		}).collect(Collector.of(
				() -> new HashMap<String, Integer>(),
				(map, word) -> {
			if(map.containsKey(word)){
				int num = map.get(word);
				map.put((String)word, num + 1);
			}
			else{
				map.put((String) word, 1);
			}
		},
				(map1, map2) -> map1));

		}

	public static Map<String, Integer> grep(String fullDoc, String searchString){
		Map<String, Integer> rtn = new HashMap<>();
		Optional<String> results = Arrays.stream(fullDoc.split("\\v"))
				.filter(line -> {
					return line.contains(searchString);
				}).reduce( (line, doc) -> {
					return doc + " " + line;
		});
		rtn.put(results.get(), 1);
		return rtn;




//		return (Map<String, Integer>) new HashMap<>().put(Arrays.stream(fullDoc.split("\\v"))
//				.filter( line -> {
//					return line.contains(searchString);
//				}).toString(), 1);
	}
}
