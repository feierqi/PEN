package javagui.views;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.UIManager;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import java.awt.Font;
import javax.swing.JInternalFrame;
import java.beans.PropertyVetoException;
import javax.swing.JLayeredPane;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField ttxt;
	private JTextField textField;
	private JTextField ltxt;
	private JTextField atxt;
	private JTextField stxt;
	private JTextField tgtxt;
	String t1, t2, t3, a1, a2, a3, l1, l2, l3, s1, s2, s3, tg1, tg2, tg3, c1, c2, c3;
	int counter;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/javagui/resources/Notebook-icon.png")));
		setTitle("Programmers Examples Notebook (PEN) 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
				
		final DefaultListModel listModel = new DefaultListModel();

		final JList list = new JList(listModel);
		
		JLabel lblExamples = new JLabel(" Examples:");
		lblExamples.setBounds(15, 16, 137, 16);
		lblExamples.setIcon(new ImageIcon(Main.class.getResource("/javagui/resources/icon-book.png")));
		listModel.addElement("Add New Example...");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 35, 139, 234);
		
		textField = new JTextField();
		textField.setBounds(15, 272, 113, 20);
		textField.setToolTipText("Search (enter keyword)");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(127, 270, 27, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/javagui/resources/search-icon.png")));
		scrollPane.setViewportView(list);
		contentPane.setLayout(null);
		contentPane.add(lblExamples);
		contentPane.add(scrollPane);
		contentPane.add(textField);
		contentPane.add(btnNewButton);
		
		final JInternalFrame internalFrame = new JInternalFrame("Add New Example");
		internalFrame.setBounds(480, 0, 284, 290);
		contentPane.add(internalFrame);
		internalFrame.setClosable(true);
		internalFrame.setFrameIcon(new ImageIcon(Main.class.getResource("/javagui/resources/add-icon.png")));
		internalFrame.setVisible(true);
		internalFrame.getContentPane().setLayout(null);
		internalFrame.moveToFront();
		final JEditorPane ctxt = new JEditorPane();
				
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 145, 221, 78);
		internalFrame.getContentPane().add(scrollPane_1);
		
		
		scrollPane_1.setViewportView(ctxt);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(50, 11, 47, 18);
		internalFrame.getContentPane().add(lblTitle);
		
		ttxt = new JTextField();
		ttxt.setBounds(84, 10, 162, 20);
		internalFrame.getContentPane().add(ttxt);
		ttxt.setColumns(10);
		
		JLabel lblCode = new JLabel("Code:");
		lblCode.setBounds(25, 130, 54, 15);
		internalFrame.getContentPane().add(lblCode);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(117, 228, 65, 23);
		internalFrame.getContentPane().add(btnSubmit);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(185, 228, 61, 23);
		internalFrame.getContentPane().add(btnReset);
		
		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setBounds(22, 33, 54, 18);
		internalFrame.getContentPane().add(lblLanguage);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(37, 56, 46, 18);
		internalFrame.getContentPane().add(lblAuthor);
		
		JLabel lblSource = new JLabel("Source:");
		lblSource.setBounds(37, 80, 46, 14);
		internalFrame.getContentPane().add(lblSource);
		
		JLabel lblTags = new JLabel("Tags:");
		lblTags.setBounds(47, 102, 54, 18);
		internalFrame.getContentPane().add(lblTags);
		
		ltxt = new JTextField();
		ltxt.setBounds(84, 33, 162, 20);
		internalFrame.getContentPane().add(ltxt);
		ltxt.setColumns(10);
		
		atxt = new JTextField();
		atxt.setBounds(84, 56, 162, 20);
		internalFrame.getContentPane().add(atxt);
		atxt.setColumns(10);
		
		stxt = new JTextField();
		stxt.setBounds(84, 79, 162, 20);
		internalFrame.getContentPane().add(stxt);
		stxt.setColumns(10);
		
		tgtxt = new JTextField();
		tgtxt.setBounds(84, 102, 162, 20);
		internalFrame.getContentPane().add(tgtxt);
		tgtxt.setColumns(10);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(173, 23, 289, 257);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);
		
		final JLabel lblTitle_1 = new JLabel("Title:");
		lblTitle_1.setBounds(3, 0, 90, 16);
		layeredPane.add(lblTitle_1);
		lblTitle_1.setIcon(new ImageIcon(Main.class.getResource("/javagui/resources/page_code.png")));
		lblTitle_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel lblProperties = new JLabel("Properties:");
		lblProperties.setBounds(149, 0, 82, 16);
		layeredPane.add(lblProperties);
		lblProperties.setIcon(new ImageIcon(Main.class.getResource("/javagui/resources/document-properties.png")));
		lblProperties.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 18, 142, 229);
		layeredPane.add(scrollPane_2);
		
		final JTextPane textArea = new JTextPane();
		textArea.setBackground(UIManager.getColor("FormattedTextField.disabledBackground"));
		textArea.setText("\r\n\r\n\r\n\r\n\r\n\r\n   <No Example Selected>");
		scrollPane_2.setViewportView(textArea);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(150, 18, 137, 229);
		layeredPane.add(scrollPane_3);
		
		final JTextPane textArea_1 = new JTextPane();
		textArea_1.setBackground(UIManager.getColor("FormattedTextField.disabledBackground"));
		scrollPane_3.setViewportView(textArea_1);
		
		JButton btnEdit = new JButton("Edit...");
		btnEdit.setBounds(393, 271, 69, 23);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(322, 271, 69, 23);
		contentPane.add(btnDelete);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ttxt.setText("");
				ltxt.setText("");
				stxt.setText("");
				atxt.setText("");
				tgtxt.setText("");
				ctxt.setText("");
			}
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String title = ttxt.getText();
				counter++;
				if (title.length() != 0 && ctxt.getText().length() != 0) {
				listModel.add(0, ttxt.getText());
				if (counter==1) {
					t1=ttxt.getText();
					l1=ltxt.getText();
					a1=atxt.getText();
					c1=ctxt.getText();
					s1=stxt.getText();
					tg1=tgtxt.getText();
				}
				if (counter==2) {
					t2=ttxt.getText();
					l2=ltxt.getText();
					a2=atxt.getText();
					c2=ctxt.getText();
					s2=stxt.getText();
					tg2=tgtxt.getText();
				}
				if (counter==3) {
					t3=ttxt.getText();
					l3=ltxt.getText();
					a3=atxt.getText();
					c3=ctxt.getText();
					s3=stxt.getText();
					tg3=tgtxt.getText();
				}
				ttxt.setText("");
				ltxt.setText("");
				stxt.setText("");
				atxt.setText("");
				tgtxt.setText("");
				ctxt.setText("");
				try {
					internalFrame.setClosed(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setBounds(100, 100, 490, 341);
				}
			}
		});
		
		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        if (mouseEvent.getClickCount() == 2) {
		          int index = list.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		            //Object o = list.getModel().getElementAt(index);
		            //System.out.println("Double-clicked on: " + o.toString());
		            if(index==((list.getModel().getSize())-1)){
		            	internalFrame.setVisible(true);
			            contentPane.add(internalFrame);
			            ttxt.requestFocus();
			            setBounds(100, 100, 782, 341);
		            }
		            else if(index==((list.getModel().getSize())-2)) {
		            	lblTitle_1.setText("Title: "+t1);
		            	textArea.setText("Code:\n"+c1);
		            	textArea_1.setText("Language: "+l1+"\n\nAuthor: "+a1+"\n\nSource: "+s1+"\n\nTags: "+tg1);
		            	internalFrame.setVisible(false);
		            	try {
		            		internalFrame.setClosed(true);
							setBounds(100, 100, 490, 341);
						} catch (PropertyVetoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            else if(index==((list.getModel().getSize())-3)) {
		            	lblTitle_1.setText("Title: "+t2);
		            	textArea.setText("Code:\n"+c2);
		            	textArea_1.setText("Language: "+l2+"\n\nAuthor: "+a2+"\n\nSource: "+s2+"\n\nTags: "+tg2);
		            	internalFrame.setVisible(false);
		            	try {
							internalFrame.setClosed(true);
							setBounds(100, 100, 490, 341);
						} catch (PropertyVetoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            else if(index==((list.getModel().getSize())-4)) {
		            	lblTitle_1.setText("Title: "+t3);
		            	textArea.setText("Code:\n"+c3);
		            	textArea_1.setText("Language: "+l3+"\n\nAuthor: "+a3+"\n\nSource: "+s3+"\n\nTags: "+tg3);
		            	internalFrame.setVisible(false);
		            	try {
							internalFrame.setClosed(true);
							setBounds(100, 100, 490, 341);
						} catch (PropertyVetoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		          }
		        }
		      }
		    };
		
		 list.addMouseListener(mouseListener);
	}
}

