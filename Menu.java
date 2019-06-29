package paint;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;

public class Menu extends JMenuBar {

	private JMenu fileMenu;
	private JMenuItem newFile, openFile, saveFile, exit;

	// �׸����� �׸��� �׷����� ���� �̹����� �ҷ��ͼ� ����
	Workspace work = new Workspace("resource/���.png"); 

	public Menu() {
		this.setBackground(Color.WHITE);

		// 1. fileMenu
		fileMenu = new JMenu("File");

		// 1.1. New
		ImageIcon newfile = new ImageIcon("resource/newfile.png");
		newFile = new JMenuItem("New", newfile);
		newFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JLabel msg = new JLabel();
				msg.setText("���� ������ �����ϰڽ��ϱ�?");			
				
				// ���� ���� �������� �ʰ� �� ���� ����
				if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					work.newimage();										
				} 
				
				// ���� �� �� ���� ����
				else 
				{																
					FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG(*.png)", "png");									
					FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JPG(*.jpg)", "jpg");								
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(filter);								
					fc.setFileFilter(filter2);
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int returnVal = fc.showSaveDialog(null);

					if (returnVal == JFileChooser.CANCEL_OPTION) {
						return;
					}
					
					// ����� ������ �̸��� ������ ����
					String filename;						    			
					File file = fc.getSelectedFile();
					
					// ���� ������ �̷��� 4���� �����̸� ���� �̸��� filename�� ����
					if (file.getName().contains(".jpg")							
							|| file.getName().contains(".JPG")
							|| file.getName().contains(".PNG")
							|| file.getName().contains(".png"))
						filename = file.getName(); 	
					else {														
						file = new File(fc.getSelectedFile() + ".jpg");
						filename = file.getName();
					}

					if (file.isFile())							 				
					{
						JLabel msg1 = new JLabel();
						msg1.setText("���� ������ �ֽ��ϴ�. �ٲٽðڽ��ϱ�?");
						if (!(JOptionPane.showConfirmDialog(null, msg1, "Ȯ��",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)) {
							return;
						}
					}

					try 
					{
						ImageIO.write(work.paper_show, "png", file);
					} catch (IOException error) {							
						error.printStackTrace();								
					}
				}
				work.newimage();										
			}
		});

		// 1.2. Open
		ImageIcon openfile = new ImageIcon("resource/openfile.png");
		openFile = new JMenuItem("Open", openfile);
		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG(*.png)", "png"); 
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JPG(*.jpg)", "jpg");
				
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);			
				fc.setFileFilter(filter2);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(null);

				// ���� �ҷ�����
				if (returnVal != JFileChooser.CANCEL_OPTION) {		
					File file = fc.getSelectedFile(); 		
					work.show_graphic.drawImage(
							new ImageIcon("image/���� ����.jpg").getImage(), 0, 0,null); 	
																	
					work.show_graphic.drawImage(					
							new ImageIcon(file.getPath()).getImage(), 0, 0,null); 
																
				}
			}
		});

		// 1.3. Save
		ImageIcon savefile = new ImageIcon("resource/savefile.png");
		saveFile = new JMenuItem("Save", savefile);
		saveFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG(*.png)", "png");		
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JPG(*.jpg)", "jpg");							
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);					
				fc.setFileFilter(filter2);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showSaveDialog(null);

				if (returnVal == JFileChooser.CANCEL_OPTION) {
					return;
				}
				String filename;						   	    	
				File file = fc.getSelectedFile();
				if (file.getName().contains(".jpg")					
						|| file.getName().contains(".JPG")
						|| file.getName().contains(".PNG")
						|| file.getName().contains(".png"))
					filename = file.getName(); 				
				else {												
					file = new File(fc.getSelectedFile() + ".jpg");
					filename = file.getName();
				}

				if (file.isFile())							 			
				{
					JLabel msg = new JLabel();
					msg.setText("���� ������ �ֽ��ϴ�. �ٲٽðڽ��ϱ�?");
					if (!(JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)) {
						return;
					}
				}

				try {
					ImageIO.write(work.paper_show, "png", file);
				} catch (IOException error) {			
					error.printStackTrace();				
																
				}
			}

		});

		// 1.4. Exit
		ImageIcon exitfile = new ImageIcon("resource/exit.png");
		exit = new JMenuItem("Exit", exitfile);
		exit.setMnemonic('E');
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JLabel msg = new JLabel();							
				msg.setText("�׸����� �����ϰڽ��ϱ�?");						
				if (JOptionPane.showConfirmDialog(null, msg, "Ȯ��",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(1);									
				} 
			}

		});

		// fileMenu�� �߰�
		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(exit);
		this.add(fileMenu);


	}

}
