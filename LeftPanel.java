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

	// 채우기, 선의 두께 버튼 
	private JButton FillButton, NonFillButton;						  
	private JButton thinnessButton, normalButton, thicknessButton;		
	
	// 그림판 왼쪽에 보여지는 패널
	static JPanel dataJPanel = new JPanel();

	static Toolkit tk 	= Toolkit.getDefaultToolkit(); 			// 이미지를 붙일 툴킷 설정
	static Image pen 	= tk.getImage("resource/pencil.png");  	// 펜 이미지
	static Image eraser = tk.getImage("resource/eraser.png");	// 지우개 이미지
	
	static Cursor cs_1	= tk.createCustomCursor(pen, new Point(8,8),"pen");
	static Cursor cs_2	= tk.createCustomCursor(eraser, new Point(30,30),"eraser");
	

	/**
	 * 그림판 왼쪽 패널
	 */
	
	public void dataJPanel() {

		// 그림판 왼쪽에 보여지는 패널
		dataJPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		dataJPanel.setPreferredSize(new Dimension(150, 180));
		dataJPanel.setBackground(Color.LIGHT_GRAY);
		dataJPanel.setLayout(new GridLayout(2, 1));
		
		DataButton handler = new DataButton();
		
		/**
		 * 채우기 버튼 기능
		 */
		
		JPanel subPanel0 = new JPanel(); 						
		subPanel0.setBorder(new TitledBorder("채우기"));			
		subPanel0.setLayout(null); 								 
		FillButton = new JButton(new ImageIcon("resource/varnish.png"));
		NonFillButton = new JButton(new ImageIcon("resource/varnish2.png"));

		subPanel0.add(FillButton);								
		FillButton.addActionListener(handler);					
		FillButton.setToolTipText("색 채우기");						
		FillButton.setBounds(12, 25, 55, 55);					
		FillButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		subPanel0.add(NonFillButton);						
		NonFillButton.addActionListener(handler);				
		NonFillButton.setToolTipText("색 채우기 취소");			
		NonFillButton.setBounds(78, 25, 55, 55);			
		NonFillButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// 왼쪽패널에 채우기 패널 붙이기
		dataJPanel.add(subPanel0); 	
															   

		/**
		 * 선 두께 조절 기능
		 */
		
		JPanel subPanel1 = new JPanel(); 				 		
		subPanel1.setBorder(new TitledBorder("선의 두께"));  		
		subPanel1.setLayout(null); 						 		

		thinnessButton = new JButton(new ImageIcon("resource/Thick1.jpg")); 
		normalButton = new JButton(new ImageIcon("resource/Thick2.jpg")); 	
		thicknessButton = new JButton(new ImageIcon("resource/Thick3.jpg")); 
		
		subPanel1.add(thinnessButton); 					 		
		thinnessButton.addActionListener(handler);		 		
		thinnessButton.setBounds(15, 25, 30, 30); 		 		
		thinnessButton.setToolTipText("선택한 도구의 두께를 얇게 조절합니다"); 
		thinnessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		subPanel1.add(normalButton); 							 
		normalButton.addActionListener(handler); 				 
		normalButton.setBounds(58, 25, 30, 30); 			  	 
		normalButton.setToolTipText("선택한 도구의 두께를 보통 굵기로 조절합니다");
		normalButton.setCursor(new Cursor(Cursor.HAND_CURSOR));   
		
		subPanel1.add(thicknessButton); 						  
		thicknessButton.addActionListener(handler); 			  
		thicknessButton.setBounds(101, 25, 30, 30); 				
		thicknessButton.setToolTipText("선택한 도구의 두께를 두껍게 조절합니다");
		thicknessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// 왼쪽패널에 채우기 선의 두께 패널 붙이기
		dataJPanel.add(subPanel1);
	
	}
		
	
	/**
	 * 채우기, 선의 두께 이벤트 연결
	 */

	class DataButton implements ActionListener {		// dataButton 에 Mouse Event 설정을 위한 객체 

		public void actionPerformed(ActionEvent e) {

			JButton action = (JButton) e.getSource();	// e.getSource()메소드는 Object에 있으므로 다운캐스팅하여 사용
			
			// 채우기 이벤트  연결
			if (action == NonFillButton) {
				Workspace.setFillMode(0);
			}
			else if (action == FillButton) {
				Workspace.setFillMode(1);
			}
			
			// 선의 두께 이벤트 연결
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
