package gui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CategoryPanel extends JPanel {
	private JTextField catTitleText;
	private JTextPane catDescText;
	private JLabel lblTitle;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label_5;

	/**
	 * Create the panel.
	 */
	public CategoryPanel() {
		setBackground(SystemColor.menu);
		setLayout(null);
		
		catTitleText = new JTextField();
		catTitleText.setText("Put your category Title here");
		catTitleText.setColumns(10);
		catTitleText.setBounds(58, 11, 483, 20);
		add(catTitleText);
		
		catDescText = new JTextPane();
		catDescText.setText("Put your category description here");
		catDescText.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		catDescText.setBackground(Color.WHITE);
		catDescText.setBounds(10, 42, 550, 307);
		add(catDescText);
		
		lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTitle.setBounds(24, 13, 30, 14);
		add(lblTitle);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(570, 11, 220, 338);
		add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(SystemColor.inactiveCaption);
		
		label_5 = new JLabel("Properties");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 218, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(81, Short.MAX_VALUE)
					.addComponent(label_5)
					.addGap(72))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 42, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_5)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 220, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 338, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(294, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

	}
	
	public String getTitle()
	{
		return this.catTitleText.getText();
	}
	
	public String getDescription()
	{
		return this.catDescText.getText();
	}
}
