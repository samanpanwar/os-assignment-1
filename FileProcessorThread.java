import java.util.Hashtable;
import java.util.List;


public class FileProcessorThread implements Runnable{
	private int startLine;
	private int endLine;
	private Hashtable<String, Integer> wordCountTable;
	private List<String> readLines;
	public int getStartLine() {
		return startLine;
	}
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}
	public int getEndLine() {
		return endLine;
	}
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
	public Hashtable<String, Integer> getWordCountTable() {
		return wordCountTable;
	}
	public void setWordCountTable(Hashtable<String, Integer> wordCountTable) {
		this.wordCountTable = wordCountTable;
	}
	
	public List<String> getReadLines() {
		return readLines;
	}
	public void setReadLines(List<String> readLines) {
		this.readLines = readLines;
	}
	
	@Override
	public void run() {
		//Process the file.
		if(readLines!=null){
			for(int i=startLine;i<endLine;i++){
				String line = readLines.get(i);
				String[] wordsArray = line.toLowerCase().split(" ");
				for(String word: wordsArray){
					String actualWord = word.replaceAll("[\\W]", "");
					//System.out.println(actualWord);
					Integer count = wordCountTable.get(actualWord);
					if(count==null){
						wordCountTable.put(actualWord, 1);
					}else{
						wordCountTable.put(actualWord, count+1);
					}
				}
			}
		}
	}
}
