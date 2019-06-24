package paint;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class LeftPanel {

	// ä���, ���� �β� ��ư 
	private JButton FillButton, NonFillButton;						  
	private JButton thinnessButton, normalButton, thicknessButton;		
	
	// �׸��� ���ʿ� �������� �г�
	static JPanel dataJPanel = new JPanel();

	static Toolkit tk 	= Toolkit.getDefaultToolkit(); 			// �̹����� ���� ��Ŷ ����
	static Image pen 	= tk.getImage("resource/pencil.png");  	// �� �̹���
	static Image eraser = tk.getImage("resource/eraser.png");	// ���찳 �̹���
	
	static Cursor cs_1	= tk.createCustomCursor(pen, new Point(8,8),"pen");
	static Cursor cs_2	= tk.createCustomCursor(eraser, new Point(30,30),"eraser");
	

	/**
	 * �׸��� ���� �г�
	 */
	
	public void dataJPanel() {

		// �׸��� ���ʿ� �������� �г�
		dataJPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		dataJPanel.setPreferredSize(new Dimension(150, 180));
		dataJPanel.setBackground(Color.LIGHT_GRAY);
		dataJPanel.setLayout(new GridLayout(2, 1));
		
		DataButton handler = new DataButton();
		
		/**
		 * ä��� ��ư ���
		 */
		
		JPanel subPanel0 = new JPanel(); 						
		subPanel0.setBorder(new TitledBorder("ä���"));			
		subPanel0.setLayout(null); 								 
		FillButton = new JButton(new ImageIcon("resource/varnish.png"));
		NonFillButton = new JButton(new ImageIcon("resource/varnish2.png"));

		subPanel0.add(FillButton);								
		FillButton.addActionListener(handler);					
		FillButton.setToolTipText("�� ä���");						
		FillButton.setBounds(12, 25, 55, 55);					
		FillButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		subPanel0.add(NonFillButton);						
		NonFillButton.addActionListener(handler);				
		NonFillButton.setToolTipText("�� ä��� ���");			
		NonFillButton.setBounds(78, 25, 55, 55);			
		NonFillButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// �����гο� ä��� �г� ���̱�
		dataJPanel.add(subPanel0); 	
															   

		/**
		 * �� �β� ���� ���
		 */
		
		JPanel subPanel1 = new JPanel(); 				 		
		subPanel1.setBorder(new TitledBorder("���� �β�"));  		
		subPanel1.setLayout(null); 						 		

		thinnessButton = new JButton(new ImageIcon("resource/Thick1.jpg")); 
		normalButton = new JButton(new ImageIcon("resource/Thick2.jpg")); 	
		thicknessButton = new JButton(new ImageIcon("resource/Thick3.jpg")); 
		
		subPanel1.add(thinnessButton); 					 		
		thinnessButton.addActionListener(handler);		 		
		thinnessButton.setBounds(15, 25, 30, 30); 		 		
		thinnessButton.setToolTipText("������ ������ �β��� ��� �����մϴ�"); 
		thinnessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		subPanel1.add(normalButton); 							 
		normalButton.addActionListener(handler); 				 
		normalButton.setBounds(58, 25, 30, 30); 			  	 
		normalButton.setToolTipText("������ ������ �β��� ���� ����� �����մϴ�");
		normalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));   
		
		subPanel1.add(thicknessButton); 						  
		thicknessButton.addActionListener(handler); 			  
		thicknessButton.setBounds(101, 25, 30, 30); 				
		thicknessButton.setToolTipText("������ ������ �β��� �β��� �����մϴ�");
		thicknessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// �����гο� ä��� ���� �β� �г� ���̱�
		dataJPanel.add(subPanel1);
	
	}
		
	
	/**
	 * ä���, ���� �β� �̺�Ʈ ����
	 */

	class DataButton implements ActionListener {		// dataButton �� Mouse Event ������ ���� ��ü 

		public void actionPerformed(ActionEvent e) {

			JButton action = (JButton) e.getSource();	// e.getSource()�޼ҵ�� Object�� �����Ƿ� �ٿ�ĳ�����Ͽ� ���
			
			// ä��� �̺�Ʈ  ����
			if (action == NonFillButton) {
				Workspace.setFillMode(0);
			}
			else if (action == FillButton) {
				Workspace.setFillMode(1);
			}
			
			// ���� �β� �̺�Ʈ ����
			else if (action == thinnessButton) {
				Information.setThick(1);
			}
			else if (action == normalButton) {
				Information.setThick(4);
			}
			else if (action == thicknessButton) {
				Information.setThick(7);
			}
			else {
			}
		}
	}
}
