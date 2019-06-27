package paint;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Frame extends JFrame {

	static String filename;
	
	public void frameApplication() {

		// �޴� �߰�
		Menu menu = new Menu();
		this.setJMenuBar(menu);
		
		// �׸��� �� �г� �߰�
		ButtonPanel buttonPanel = new ButtonPanel();
		buttonPanel.buttonJPanel();
		this.add(ButtonPanel.bp, BorderLayout.NORTH); 
							
		// �׸��� ���� �÷� �г� �߰�
		ColorData data = new ColorData(); 	
		data.dataColorJPanel(); 			
		this.add(ColorData.colorDataPanel, BorderLayout.WEST);
																	
		// WorkspaceArea ��ü �߰�
		Workspace workspace = new Workspace("image/���� ����2.jpg");
		this.add(workspace, BorderLayout.CENTER); 				

		frameJPanel(); 				
	}

	
	/**
	 * framePanel ���� �Լ�
	 */
	
	public void frameJPanel() {

		this.setTitle("�� �ڹ� 1���� �׸��� ��"); 							// Frame ����
		this.setSize(1300, 700); 									// Frame ũ��
		this.setLocation(30, 20);									// Frame ������ġ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Frame �� �����ϱ� ���� �޼ҵ�
		this.setResizable(false);									// Frame �� ũ�������� �Ұ����ϰ� �Ѵ�.
		this.setEnabled(true); 										// ������Ʈ�� ��ȿ�� ���·� �����Ѵ�.
		this.setVisible(true); 										// Frame �� �������� �Ѵ�.

	}

}
