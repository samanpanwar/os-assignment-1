import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class WordCountRunnable implements Runnable {

        private String word;
	private JTextArea textArea;
	private int count = 0;

	public WordCountRunnable(String word, JTextArea textArea) {
		this.word = word;
		this.textArea = textArea;
	}

	@Override
	public void run() {
		File f = new File("input.txt");
		try {
			Scanner scanner = new Scanner(f);
			while (scanner.hasNext())
			{
				String str = scanner.next();
				if (str.equals(word))
					count++;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					textArea.append(word + " appears: " + count + " times\n");
					//System.out.println(SwingUtilities.isEventDispatchThread());
				}
			});
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
