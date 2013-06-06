package gui;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

import dataStructure.BasicExample;
import dataStructure.BufferEntry;
import dataStructure.IExample;
import dataStructure.NonUser;

public class ExamplePanel extends JPanel {
	private JTextField catText;
	private JTextField sourceText;
	private JTextField langText;
	private JTextField authorText;
	private JTextField titleText;
	private JTextPane codeText;
	/**
	 * Create the panel.
	 */
	public ExamplePanel() {
		setForeground(Color.LIGHT_GRAY);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(570, 11, 220, 338);
		add(panel);

		JLabel catlbl = new JLabel("Categories:");

		catText = new JTextField();
		catText.setColumns(10);

		JLabel label_1 = new JLabel("Author:");

		JLabel langlbl = new JLabel("Language");

		JLabel sourcelbl = new JLabel("Source");

		sourceText = new JTextField();
		sourceText.setColumns(10);

		langText = new JTextField();
		langText.setColumns(10);

		authorText = new JTextField();
		authorText.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(SystemColor.inactiveCaption);

		JLabel label_5 = new JLabel("Properties");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap(81, Short.MAX_VALUE)
						.addComponent(label_5)
						.addGap(72))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(label_5)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(catlbl)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(catText, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
										.addGroup(gl_panel.createSequentialGroup()
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(label_1)
														.addComponent(langlbl)
														.addComponent(sourcelbl))
														.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
																.addGroup(gl_panel.createSequentialGroup()
																		.addGap(23)
																		.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																				.addComponent(sourceText, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
																				.addComponent(langText, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
																				.addGroup(gl_panel.createSequentialGroup()
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(authorText, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))))
																						.addContainerGap())
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1)
								.addComponent(authorText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(langlbl)
										.addComponent(langText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(sourcelbl)
												.addComponent(sourceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
														.addComponent(catlbl)
														.addComponent(catText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addContainerGap(185, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);
		codeText = new JTextPane();
		codeText.setText("Put your code here");
		codeText.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		codeText.setBackground(Color.WHITE);
		codeText.setBounds(10, 42, 550, 307);
		add(codeText);

		titleText = new JTextField();
		titleText.setText("Put your code title here");
		titleText.setBounds(77, 11, 483, 20);
		add(titleText);
		titleText.setColumns(10);

		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 17, 46, 14);
		add(lblNewLabel);

	}

	public void displayExample(IExample bx) {
		this.codeText.setText(bx.getCode());
		this.titleText.setText(bx.getTitle());
		this.sourceText.setText(bx.getSource());
		this.langText.setText(bx.getLanguage());
		this.authorText.setText(bx.getAuthors().get(0).getName());
	}

	public BufferEntry getBufferEntry() {
		BufferEntry entry = new BufferEntry();
		entry.setTitle(this.titleText.getText());
		entry.setSource(this.sourceText.getText());
		entry.setLanguage(this.langText.getText());
		entry.setCode(this.codeText.getText());
		entry.addAuthor(new NonUser(this.authorText.getText()));
		entry.setCategoryName(this.catText.getText());
		return entry;
	}
	
	/**
	 * return an arraylist of strings separated by comma in input
	 * @param input
	 * @return
	 */
	public ArrayList<String> interpretCategories(String input)
	{
		ArrayList<String> stringlist = new ArrayList<String>();
		boolean hasComma = true;
		while(hasComma)
		{
			hasComma = input.contains(",");
			if(hasComma)
			{
				String sub = input.substring(0,input.indexOf(","));
				stringlist.add(sub);
				input = input.substring(input.indexOf(",")+1);
			}
			else
			{
				if(input.length()!=0)
					stringlist.add(input);
				return stringlist;
			}
		}
		return stringlist;
	}

}
