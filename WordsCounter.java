import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class WordsCounter {

        private JTextField firstTextField;
	private JTextField secondTextField;
	private JTextField thirdTextField;
	private JTextArea textArea;
	private JButton startButton;
	private JButton clearButton;

	public WordsCounter() {
		JFrame frame = new JFrame("WordsCounter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(makeLabelsPanel(), BorderLayout.NORTH);
		frame.add(makeTextAreaPanel(), BorderLayout.CENTER);
		frame.add(makeButtonPanel(), BorderLayout.SOUTH);
		frame.setSize(1024, 768);
		frame.setVisible(true);
		// System.out.println(SwingUtilities.isEventDispatchThread());
	}

	private JPanel makeLabelsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		firstTextField = new JTextField(10);
		secondTextField = new JTextField(10);
		thirdTextField = new JTextField(10);
		panel.add(new JLabel("Please enter word"));
		panel.add(firstTextField);
		panel.add(new JLabel("Please enter word"));
		panel.add(secondTextField);
		panel.add(new JLabel("Please enter word"));
		panel.add(thirdTextField);
		return panel;
	}

	private JScrollPane makeTextAreaPanel() {
		JPanel panel = new JPanel();
		textArea = new JTextArea();
		panel.setLayout(new BorderLayout());
		panel.add(textArea);
		JScrollPane scrollPane = new JScrollPane(panel);
		return scrollPane;
	}

	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		startButton = new JButton("Start");
		clearButton = new JButton("Clear");
		panel.add(startButton);
		panel.add(clearButton);
		startButton.addActionListener(new StartListener());
		clearButton.addActionListener(new ClearListener());
		return panel;
	}

	public class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!firstTextField.getText().equals("")) {
				new Thread(new WordCountRunnable(firstTextField.getText(), textArea)).start();
			}

			if (!secondTextField.getText().equals("")) {
				new Thread(new WordCountRunnable(secondTextField.getText(), textArea)).start();
			}

			if (!thirdTextField.getText().equals("")) {
				new Thread(new WordCountRunnable(thirdTextField.getText(), textArea)).start();
			}
		}
	}

	public class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			firstTextField.setText("");
			secondTextField.setText("");
			thirdTextField.setText("");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WordsCounter();
			}
		});
	}
}
