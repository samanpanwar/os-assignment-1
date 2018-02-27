import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class PThreadAssignment {

	private Hashtable<String, Integer> wordCountTable = new Hashtable<String, Integer>();

	private int findNoOfCores(){
		return Runtime.getRuntime().availableProcessors();
	}

	public void readFile(String filePath){
		List<String> linesList = null;
		try {
			linesList = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset());
			int totalLines = linesList.size();
			int totalThreadsToCreate = findNoOfCores();
			int linesEachThreadCanProcess = totalLines%2==1?(totalLines+1)/totalThreadsToCreate : totalLines/totalThreadsToCreate;
			System.out.println("Total Lines: "+ totalLines);
			System.out.println("Total Processors: "+ totalThreadsToCreate);
			System.out.println("Total Lines Each thread can handle: "+ linesEachThreadCanProcess);
			int skipLines = 0;
			int startLine = 0;
			int endLine = 0;
			ExecutorService threadExecutor = Executors.newFixedThreadPool(totalThreadsToCreate);
			for(int i=0;i<totalThreadsToCreate;i++){
				if(i==0){
					startLine = 0;
					skipLines += linesEachThreadCanProcess;
				}else{
					skipLines += linesEachThreadCanProcess; 
					startLine = skipLines-1;
				}
				if(skipLines>=totalLines){
					endLine = totalLines-1;
				}else if((i+1)==totalThreadsToCreate && skipLines<=totalLines){
					endLine = totalLines-1;
				}else{
					endLine = skipLines;
				}
				FileProcessorThread thread = new FileProcessorThread();
				thread.setStartLine(startLine);
				thread.setEndLine(endLine);
				thread.setWordCountTable(wordCountTable);
				thread.setReadLines(linesList);
				threadExecutor.execute(thread);
			}
			try {
				threadExecutor.shutdown();
				if (!threadExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
					threadExecutor.shutdownNow();
				}else{
					Set<String> keys = wordCountTable.keySet();
					for(String word: keys){
						System.out.println("------------------------------------------------------------------------------------------------------");
						if(!word.equals("")){
							System.out.println("With the Word : "+ word);
							for(String key: keys){
								int count = wordCountTable.get(key);
								if(!key.equals("")){
									System.out.println( key+" occurs "+ count +" times");
								}
							}
						}
					}
				}
			} catch (InterruptedException e) {
				threadExecutor.shutdownNow();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PThreadAssignment assignment = new PThreadAssignment();
		assignment.readFile("input.txt");
	}

}
