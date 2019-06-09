package paint;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonPanel extends JFrame{
	
	static JPanel bp = new JPanel();	
	private JButton[] button = new JButton[12];
	
	
	/**
	 * 그림판 상단 버튼 생성
	 */
	
	public void buttonJPanel() {
		
		// 패널 생성
		Color back = new Color(95, 95, 95); 									 		
		bp.setBackground(back);                         			 
		bp.setBorder(BorderFactory.createRaisedBevelBorder()); 		 
		bp.setPreferredSize(new Dimension(0, 80));
		bp.setLayout(null);
		this.add(bp, BorderLayout.NORTH);                      		 

		// 버튼 생성
		button[0] = new JButton();     		      	
		button[1] = new JButton();        	     
		button[2] = new JButton();             	
		button[3] = new JButton();             	
		button[4] = new JButton();
		button[5] = new JButton();
		button[6] = new JButton();
		button[7] = new JButton();
		button[8] = new JButton();
		button[9] = new JButton();
		button[10] = new JButton();
		button[11] = new JButton();
		
		button[0].setIcon(new ImageIcon("resource/Oval.png"));
		button[1].setIcon(new ImageIcon("resource/Triangle.png"));
		button[2].setIcon(new ImageIcon("resource/Rectangle.png"));
		button[3].setIcon(new ImageIcon("resource/roundRectButton.png"));
		button[4].setIcon(new ImageIcon("resource/Polygon.png"));
		button[5].setIcon(new ImageIcon("resource/artistic-brush.png"));
		button[6].setIcon(new ImageIcon("resource/pen.png"));
		button[7].setIcon(new ImageIcon("resource/dots.png"));
		button[8].setIcon(new ImageIcon("resource/Line.png"));
		button[9].setIcon(new ImageIcon("resource/eraserr.png"));
		button[10].setIcon(new ImageIcon("resource/crop.png"));
		button[11].setIcon(new ImageIcon("resource/text.png"));
		
		button[0].setToolTipText("원 선택");  			
		button[1].setToolTipText("삼각형 선택");    			
		button[2].setToolTipText("사각형 선택");  		  	
		button[3].setToolTipText("둥근사각형 선택");    			
		button[4].setToolTipText("오각형 선택");     
		button[5].setToolTipText("붓 선택");   
		button[6].setToolTipText("펜 선택");  
		button[7].setToolTipText("점선 선택");   
		button[8].setToolTipText("직선 선택");   
		button[9].setToolTipText("지우기 선택");   
		button[10].setToolTipText("선택영역 지우기 선택"); 
		button[11].setToolTipText("텍스트 선택"); 
		

		for (int count = 0; count < button.length; count++) {
			
			button[count].setBounds((100 * count) + 20, 15, 90, 50);      
			button[count].setBorder(BorderFactory.createRaisedBevelBorder());
			button[count].setCursor(new Cursor(Cursor.HAND_CURSOR));
			bp.add(button[count]);
			
			CategoryButton handler = new CategoryButton();
			button[count].addActionListener(handler);
		}
	}
	
	
	/**
	 * text 기능 추가
	 */

	public static JTextField text;
	public static int textofStyle 	= 0;	
	public static String textofFont = "고딕체";	
	public static int textofSize 	= 10;
	
	private JFrame frame;
	private JLabel comboLable;
	private JComboBox imagesJComboBox1;
	private JComboBox imagesJComboBox2;
	private JComboBox imagesJComboBox3;
	
	static JButton enter;

	private static final String[] fonts 	= { "고딕체", "돋움체", "궁서체" };
	private static final String[] styles 	= { "plain", "bold", "tilt" };
	private static final String[] sizes 	= { "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", 
			"30", "32", "34", "36", "38", "40", "42", "44", "46", "48", "50" };

	public void ComboBoxArea() {
		
		frame = new JFrame("Text selecting");
		frame.setBounds(500, 200, 400, 120);
		frame.setBackground(new Color(234, 234, 234));
		frame.setVisible(true);
		
		comboLable = new JLabel();
		comboLable.setLayout(new FlowLayout());
		
		text = new JTextField("문자를 입력하세요", 15);
		comboLable.add(text);
		
		imagesJComboBox1 = new JComboBox(fonts);
		imagesJComboBox1.setMaximumRowCount(3);
		imagesJComboBox1.setSelectedIndex(0);

		imagesJComboBox2 = new JComboBox(styles);
		imagesJComboBox2.setMaximumRowCount(4);
		imagesJComboBox2.setSelectedIndex(0);
		
		imagesJComboBox3 = new JComboBox(sizes);
		imagesJComboBox3.setMaximumRowCount(5);
		imagesJComboBox3.setSelectedIndex(0);
		

		// 폰트  설정
		imagesJComboBox1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					textofFont = fonts[imagesJComboBox1.getSelectedIndex()];
				}
			}
		});
		
		// 폰트 스타일 설정
		imagesJComboBox2.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					textofStyle = imagesJComboBox2.getSelectedIndex();
				}
			}
		});
		
		// 폰트 사이즈  설정
		imagesJComboBox3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					textofSize = Integer.parseInt(sizes[imagesJComboBox3.getSelectedIndex()]);
				}
			}
		});

		comboLable.add(imagesJComboBox1);
		comboLable.add(imagesJComboBox2);
		comboLable.add(imagesJComboBox3);
		frame.add(comboLable);
		
	
		// 확인 버튼 생성
		enter = new JButton("확인");
		
		// 확인 버튼 이벤트 연결
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Workspace.setDrawMode(11);	
				frame.setVisible(false);
			}
		});
		
		comboLable.add(enter);
		frame.add(comboLable);
	}
	
	
	/**
	 * 버튼 이벤트 연결
	 */
	
	class CategoryButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			JButton action = (JButton) e.getSource();	
			for (int i = 0; i < button.length - 1; ++i)
			{
				if (action == button[i]) {			
					Workspace.setDrawMode(i);			
				}
			}
			
			// 텍스트 버튼 눌렸을 때
			if (action == button[11])
				ComboBoxArea();
			
		}
	}
	
}

