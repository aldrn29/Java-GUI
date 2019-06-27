package paint;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Frame extends JFrame {

	static String filename;
	
	public void frameApplication() {

		// 메뉴 추가
		Menu menu = new Menu();
		this.setJMenuBar(menu);
		
		// 그림판 위 패널 추가
		ButtonPanel buttonPanel = new ButtonPanel();
		buttonPanel.buttonJPanel();
		this.add(ButtonPanel.bp, BorderLayout.NORTH); 
							
		// 그림판 왼쪽 컬러 패널 추가
		ColorData data = new ColorData(); 	
		data.dataColorJPanel(); 			
		this.add(ColorData.colorDataPanel, BorderLayout.WEST);
																	
		// WorkspaceArea 객체 추가
		Workspace workspace = new Workspace("image/제목 없음2.jpg");
		this.add(workspace, BorderLayout.CENTER); 				

		frameJPanel(); 				
	}

	
	/**
	 * framePanel 생성 함수
	 */
	
	public void frameJPanel() {

		this.setTitle("♥ 자바 1조의 그림판 ♥"); 							// Frame 제목
		this.setSize(1300, 700); 									// Frame 크기
		this.setLocation(30, 20);									// Frame 시작위치
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Frame 을 종료하기 위한 메소드
		this.setResizable(false);									// Frame 을 크기조정을 불가능하게 한다.
		this.setEnabled(true); 										// 오브젝트를 유효한 상태로 설정한다.
		this.setVisible(true); 										// Frame 을 보여지게 한다.

	}

}
