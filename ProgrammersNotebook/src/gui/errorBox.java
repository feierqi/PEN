package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class errorBox extends JFrame {

	private JPanel contentPane;
	private JLabel descField;

	/**
	 * Create the frame.
	 */
	public errorBox(String desc) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 182, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		descField = new JLabel();
		descField.setBounds(5, 5, 305, 20);
		contentPane.add(descField);
		this.descField.setText(desc);
		
		JButton btnClose = new JButton("close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(43, 36, 89, 23);
		contentPane.add(btnClose);
	}
}
