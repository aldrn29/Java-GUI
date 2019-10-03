package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Workspace extends JPanel {


	static Information info;
	
	/* �׸��� �׸��� �ӽ� ������ BufferedImage ���� ����� 1250 X 700 �� BufferedImage ��ü�� �ʱ�ȭ�Ѵ�
	   .getGraphics()�� �ǹ̴� papar_show, paper_temp �� ���� Graphics ��ü�� ��ȯ�ϴ� ���̰�
	   show_graphic, after_graphic �� �װ��� ������� ���� �̴�.*/
	protected static BufferedImage paper_show = new BufferedImage(1250, 700,BufferedImage.TYPE_3BYTE_BGR); 
	protected Graphics show_graphic = paper_show.getGraphics();
	private static BufferedImage paper_temp = new BufferedImage(1250, 700,BufferedImage.TYPE_3BYTE_BGR);
	private Graphics after_graphic = paper_temp.getGraphics();

	// ���� �׸��� ���� ä��� ����
	private static int drawMode, fillmode = 0; 
			
	// ���콺 �巡�� �̺�Ʈ���� ���� x�� y�� ��ǥ�� ������ ������
	private Point p1, p2;				
	
	// center�� ���콺�� ������ end�� ���콺�� ��������, width_x�� ������ ���� x������ ����, heigh_y�� ���� y������ ����
	private int center_x, center_y, width_x, height_y;
	
	// x, y, x2, y2�� ������ ������ ����
	private int end_x, end_y, x, y, x2, y2;			  	
													
	private int[] xpoint = null, ypoint = null, x2point = null, y2point = null;
				
	// line�� ����� ���⸦ �����ϴ� ����
	private static int outline = 1;							
	
	
	
	/**
	 * switch ������ ����� Workspace�� ǥ�� �� ������ ���ڵ�
	 */
	
	static final int CIRCLE 		= 0;					// ��
	static final int TRIANGLE 		= 1;					// �ﰢ��
	static final int RECTANGLE 		= 2;					// �簢��
	static final int ROUNDRECT 		= 3;					// �ձٻ簢��
	static final int Pentagon		= 4; 					// ������
	static final int Brush 			= 5;					// ��
	static final int Pen 			= 6;					// ��(�����)
	static final int dottedLine 	= 7;					// ����
	static final int straightLine 	= 8;					// ����
	static final int Eraser 		= 9;					// �����
	static final int Delete 		= 10;					// ���ÿ��� ����� 
	static final int Text 			= 11;					// �ؽ�Ʈ ����		
	
	
	/**
	 * 
	 * ����, �� �׸��� �Լ� ����
	 */
	
	public Workspace(String name) {
		show_graphic.fillRect(0, 0, 1250, 700);								
		show_graphic.drawImage(new ImageIcon(name).getImage(), 0, 0, null);	
		
		MouseHandler handler = new MouseHandler();	
		addMouseListener(handler);				
		addMouseMotionListener(handler);
	}
	
	// �׸� �׸��� �Լ�
	public void paint(Graphics g) {	
		g.drawImage(paper_show, 0, 0, null);
		repaint();	
	}

	// �׸� ������Ʈ �Լ�
	public void update(Graphics g) {				
		paint(g);
	} 									
	
	// DataArea���� Button ActionListener�� �߻��Ǿ��� �� ������ ���
	public static void setDrawMode(int dmode) {
													
		drawMode = dmode;	
	}

	// DataArea���� Fill ActionListener�� �߻��Ǿ��� �� ä��� ��  ���� ����
	public static void setFillMode(int fmode) {
													
		fillmode = fmode;								
	}


	/**
	 * 
	 * ���콺 ��� �̺�Ʈ
	 */
	
	class MouseHandler implements MouseListener, MouseMotionListener 
	{		
      
		// ���콺�� ������ ��
		public void mousePressed(MouseEvent e) {
			// �ν��ϱ� ���ؼ� MouseListener�� MouseMotionListener�� �����Ѵ�	
			// ImageObserver�� �̹��� drawImage�� �ϱ� ���ؼ� �����Ͽ���.	
			// ���콺�� �������� �������� �����ϰ� ���찳��� �϶� ������� �����Ѵ�.
			Information line2 = null;	
			line2 = new Information();							// LineModeŬ������ line2 ��ü ����

			if (drawMode == Eraser) {						// ���찳�϶� ������� ����
				Information.setColor(Color.white); 	
			} else {										// �׿ܿ� ���� ������ ������
				Information.setColor(ColorPanel.color); 
			}
			info = line2;									// line��ü�� line2�� �ּҰ��� �ش�.							 
			info.setPoint(e.getPoint());    	    		// LineMode�� ��ü�� line�� ���콺 ���� �� ��ǥ�� �����Ѵ�

		}

		// ���콺 �������� ��
		public void mouseReleased(MouseEvent event) {

			Graphics2D show_graphic2D = (Graphics2D) show_graphic;			// 2D�� �����ϱ� ���� ������ ��ü 
			p1 = info.getPoint().firstElement();							// line�� ������� ���� ù��° ��ǥ�� p1�� ������� ���´�
			p2 = info.getPoint().lastElement();								// line�� ������� ���� ������ ��ǥ�� p2�� ������� ���´�
			outline = info.getThick();										// line�� ������� �ִ� ���⸦ outline�� ������� ���´�
			
			if (p1.x > p2.x) {								// x��ǥ�� y��ǥ �߿��� ���� ���� ���� ���� ū ���� �����ϱ� ���� if��
				center_x = p2.x;							// ���� ��ǥ�� center�� ū ���� end�� �ִ´�
				end_x = p1.x;								// ū ���� ���� ���� ���� width�� �ִ´�
				width_x = p1.x - p2.x;										
			} else {										// ��, center_x�� �� ��ǥ�� x���� ���� ���� �����ϴ� int�� ����
				center_x = p1.x;							// ��, end_x�� �� ��ǥ�� x���� ū ���� �����ϴ� int�� ���� 
				end_x = p2.x;								// ��, width_x�� �� x��ǥ�� ����
				width_x = p2.x - p1.x;						// ��, center_y�� �� ��ǥ�� y���� ���� ���� �����ϴ� int�� ����
			}												// ��, end_y�� �� ��ǥ�� y���� ū ���� �����ϴ� int�� ���� 
			if (p1.y > p2.y) {								// ��, height_y�� �� y��ǥ�� ����
				center_y = p2.y;
				end_y = p1.y;
				height_y = p1.y - p2.y;
			} else {
				center_y = p1.y;
				end_y = p2.y;
				height_y = p2.y - p1.y;
			}
			
			
			switch (drawMode) 
			{												
			case CIRCLE:												
				show_graphic2D.setColor(info.getColor());					
				show_graphic2D.setStroke(new BasicStroke(outline));	
				if (fillmode == 1) {
					show_graphic2D.fillOval(center_x, center_y, width_x, height_y);	
				} else {											
					show_graphic2D.drawOval(center_x, center_y, width_x, height_y);
				}
				break;
				
			case TRIANGLE:													
				show_graphic2D.setColor(info.getColor());					// ��ü show_graphic2D�� ���� line�� ����� ������ �����Ѵ�
				show_graphic2D.setStroke(new BasicStroke(outline));			// ��ü show_graphic2D�� ���⸦ line�� ����� ������ �����Ѵ�
					x = (center_x + end_x)/2;   							// x�� ó�� x��ǥ�� ������ x��ǥ�� ���ϰ� 2�� ���� ���� �ִ´�
					xpoint = new int[] {x, center_x, end_x};				// xpoint �迭�� x, center_x, end_x�� ���� ���ʷ� �����Ѵ�
					ypoint = new int[] {center_y, end_y, end_y};			// ypoint �迭�� center_y, end_y, end_y�� ���� ���ʷ� �����Ѵ�
				if (fillmode == 1)											// fillmode�� 1�� ���
					show_graphic2D.fillPolygon(xpoint,ypoint,xpoint.length);// fillPolygon �޼ҵ带 �̿��Ͽ� ä��� �ﰢ���� �׷�����
				else														// fillmode�� 0�� ���
					show_graphic2D.drawPolygon(xpoint,ypoint,xpoint.length);// drawPolygon �޼ҵ带 �̿��Ͽ� ä���� ���� �ﰢ���� �׷�����
				break;
				
			case RECTANGLE:
				show_graphic2D.setColor(info.getColor());
				show_graphic2D.setStroke(new BasicStroke(outline));
				xpoint = new int[] {center_x, end_x,end_x,center_x};
				ypoint = new int[] {center_y, center_y, end_y,end_y};
				if (fillmode == 1)
					show_graphic2D.fillPolygon(xpoint,ypoint,xpoint.length);
				else
					show_graphic2D.drawPolygon(xpoint,ypoint,xpoint.length);
				break;
				
			case ROUNDRECT:	
				show_graphic2D.setColor(info.getColor());
				show_graphic2D.setStroke(new BasicStroke(outline));	
				xpoint = new int[] {center_x, end_x,end_x,center_x};
				ypoint = new int[] {center_y, center_y, end_y,end_y};
				if (fillmode == 1)
					show_graphic2D.fillRoundRect(center_x,center_y,end_x-center_x,end_y-center_y,50,50);	
				else 
					show_graphic2D.drawRoundRect(center_x,center_y,end_x-center_x,end_y-center_y,50,50);	
				break;
				
			case Pentagon: 												// drawMode�� Pentagon �� ���
				show_graphic2D.setColor(info.getColor());				// ��ü show_graphic2D�� ���� line�� ����� ������ �����Ѵ�
				show_graphic2D.setStroke(new BasicStroke(outline));		// ��ü show_graphic2D�� ���⸦ line�� ����� ������ �����Ѵ�
				x = (end_x - center_x)/4;								// x�� ������ x��ǥ�� ó�� x��ǥ�� ���� 4�� ���� ���� �����Ѵ�
				y = (end_y - center_y)/2;								// y�� ������ y��ǥ�� ó�� y��ǥ�� ���� 2�� ���� ���� �����Ѵ�
				xpoint = new int[] {center_x+x+x,end_x,center_x+x+x+x,center_x+x,center_x};
				// xpoint �迭�� center_x+x+x,end_x,center_x+x+x+x,center_x+x,center_x�� ���� ���ʷ� �����Ѵ�
				ypoint = new int[] {center_y,center_y+y,center_y+y+y,end_y,center_y+y};	
				// ypoint �迭�� enter_y,center_y+y,center_y+y+y,end_y,center_y+y�� ���� ���ʷ� �����Ѵ�
				if(fillmode == 1)											// fillmode�� 1�� ���
					show_graphic2D.fillPolygon(xpoint,ypoint,xpoint.length);// fillPolygon �޼ҵ带 �̿��Ͽ� ä��� �簢���� �׷�����
				else														// fillmode�� 1�� ���
					show_graphic2D.drawPolygon(xpoint,ypoint,xpoint.length);// fillPolygon �޼ҵ带 �̿��Ͽ� ä���� ���� �������� �׷�����
				break;
				
			case dottedLine:												// drawMode�� dottedLine �� ���
				float dotpoint[] = { 10.0f };								// dotpoint�� 10.0f�� �����Ѵ�. �̴� ������ ������ �ǹ�
				show_graphic2D.setStroke(new BasicStroke(outline));			// ��ü show_graphic2D�� ���⸦ line�� ����� ������ �����Ѵ�
				show_graphic2D.setColor(info.getColor());					// ��ü show_graphic2D�� ���� line�� ����� ������ �����Ѵ�
				show_graphic2D.setStroke(new BasicStroke(outline,0,BasicStroke.JOIN_MITER,10.0f,dotpoint, 0));
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);			// drawLine �޼ҵ带 �̿��Ͽ� ������ �׷�����
				setCursor(LeftPanel.cs_1); 									// ���� ��ư Ŀ�� ����
				break; 	
				
			case straightLine:												// drawMode�� straightLine �� ���
				show_graphic2D.setColor(info.getColor());					// ��ü show_graphic2D�� ���� line�� ����� ������ �����Ѵ�
				show_graphic2D.setStroke(new BasicStroke(outline));			// ��ü show_graphic2D�� ���⸦ line�� ����� ������ �����Ѵ�
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);			// drawLine �޼ҵ带 �̿��Ͽ� ���� �׷�����
				setCursor(LeftPanel.cs_1);    								// ���� ��ư Ŀ�� ����
				break; 
				
			case Delete:						
				show_graphic.setColor(Color.white);					
				show_graphic.fillRect(center_x, center_y, width_x, height_y);
				break;
				
			case Text:		
				String font = ButtonPanel.textofFont;
				int style = ButtonPanel.textofStyle;
				int size = ButtonPanel.textofSize;

				show_graphic.setFont(new Font(font, style, size));		
				show_graphic.setColor(info.getColor());	
				show_graphic.drawString(ButtonPanel.text.getText(), p2.x, p2.y);
				
				ButtonPanel.textofFont = "���ü";	
				ButtonPanel.textofStyle = 0;
				ButtonPanel.textofSize = 10;
				break;
				
			default:
			}
			
			repaint();									
			Information.point.clear();					

		}
		
		// ���콺 �巡�� ���� ��
		public void mouseDragged(MouseEvent e) {

			Graphics after_graphic = getGraphics();							//�ܻ��� ��Ÿ���� ���� �׷��� ��ü after_graphic�� ����
			Graphics2D show_graphic2D = (Graphics2D) show_graphic;			//2D�� �����ϱ� ���� ������ ��ü ???????? ��� ����???
			outline = info.getThick();										//line�� ������� �ִ� ���⸦ outline�� ������� ���´�
			info.setPoint(e.getPoint());									//���콺 �巡�� �� �߻��Ǵ� ��ǥ�� ���� line�� ������� ���´�
			Point p1 = info.getPoint().size() > 1 ? info.getPoint().get(	//line�� ������� ���� ��ǥ�� 2�� �̻��� ��� 
					info.getPoint().size() - 2) : info.getPoint()			//���������� ���� ����, 2������ ���� ��� ���� ó�� ��ǥ�� p1�� ������� ���´�
					.firstElement();
			Point p2 = info.getPoint().lastElement();						//Point�� ���� p2���� ���� ������ ��ǥ�� ������� ���´�
			Point p4 = info.getPoint().firstElement();						//Point�� ���� p4���� ���� ó�� ��ǥ�� ������� ���´�

			if (p2.x >= p4.x) {												//x��ǥ�� y��ǥ �߿��� ���� ���� ���� ���� ū ���� �����ϱ� ���� if��
				center_x = p4.x;											//���� ��ǥ�� center�� ū ���� end�� �ִ´�
				end_x = p2.x;												//ū ���� ���� ���� ���� width�� �ִ´�
				width_x = p2.x - p4.x;	
			} else {
				center_x = p2.x;
				end_x = p4.x;
				width_x = p4.x - p2.x;
			}
			if (p2.y >= p4.y) {
				center_y = p4.y;
				end_y = p2.y;
				height_y = p2.y - p4.y;
			} else {
				center_y = p2.y;
				end_y = p4.y;
				height_y = p4.y - p2.y;
			}

			switch (drawMode) 
			{																		
			case Eraser:															// drawMode�� Eraser �� ���
				show_graphic.setColor(Color.white);									// ��ü show_graphic2D�� ���� �Ͼ������ �����Ѵ�
				show_graphic.fillRect(p2.x, p2.y, outline * 10, outline * 10);		// fillRect �޼ҵ带 �̿��Ͽ� ä��� �簢���� �׷�����
				setCursor(LeftPanel.cs_2);    										// ���찳 ��ư Ŀ�� ����
				break; 
				
			case Pen:																// drawMode�� Pen �� ���	
				show_graphic2D.setStroke(new BasicStroke(outline));					// ��ü show_graphic2D�� ���⸦ line�� ����� ������ �����Ѵ�
				show_graphic2D.setColor(info.getColor());							// ��ü show_graphic2D�� ���� line�� ����� ������ �����Ѵ�
				setCursor(LeftPanel.cs_1);    										// pen ��ư Ŀ�� ����
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);					// drawLine �޼ҵ带 �̿��Ͽ� ���� �׷�����
				break; 
				
			case Brush:																// drawMode�� Brush �� ���
				show_graphic.setColor(info.getColor());								// ��ü show_graphic�� ���� line�� ����� ������ �����Ѵ�
				show_graphic.fillOval(p2.x, p2.y, outline * 10, outline * 10);		// fillOval �޼ҵ带 �̿��Ͽ� ä��� ���� �׷�����
				setCursor(LeftPanel.cs_1);											// �� ��ư Ŀ�� ����
				break; 
				
			case straightLine:
				after_graphic.setColor(info.getColor());							// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�
				after_graphic.drawLine(p4.x, p4.y, p2.x, p2.y);						// drawLine �޼ҵ带 �̿��Ͽ� ���� �׷�����
				setCursor(LeftPanel.cs_1);											// drawMode�� straightLine, dottedLine �� ���
				break;	
				
			case dottedLine:
				after_graphic.setColor(info.getColor());							// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�
				after_graphic.drawLine(p4.x, p4.y, p2.x, p2.y);						// drawLine �޼ҵ带 �̿��Ͽ� ���� �׷�����
				setCursor(LeftPanel.cs_1);    										// ���� ��ư Ŀ�� ����
				break;
				
			case CIRCLE:															// drawMode�� CIRCLE �� ���
				after_graphic.setColor(info.getColor());							// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));						// ��ư����� ���ڰ��� ����
				if (fillmode == 1)													// fillmode�� 1�� ���
					after_graphic.fillOval(center_x, center_y, width_x, height_y);	// fillOval �޼ҵ带 �̿��Ͽ� ä��� ���� �׷�����
				else 																// fillmode�� 0�� ���
					after_graphic.drawOval(center_x, center_y, width_x, height_y);	// drawOval �޼ҵ带 �̿��Ͽ� ä���� ���� ���� �׷�����
				break;
	
			case TRIANGLE:														// drawMode�� TRIANGLE �� ���
				after_graphic.setColor(info.getColor());						// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// ��ư����� ���ڰ��� ����
					x2 = (center_x + end_x)/2;   								// x2�� ó�� x��ǥ�� ������ x��ǥ�� ���ϰ� 2�� ���� ���� �����Ѵ�
					x2point = new int[] {x2, center_x, end_x};					// x2point �迭�� x2, center_x, end_x�� ���� ���ʷ� �����Ѵ�
					y2point = new int[] {center_y, end_y, end_y};				// y2point �迭�� center_y, end_y, end_y�� ���� ���ʷ� �����Ѵ�
				if (fillmode == 1)												
					after_graphic.fillPolygon(x2point,y2point,x2point.length);
				else														
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	
				break;
			
			case RECTANGLE:														// drawMode�� RECTANGLE �� ���
				after_graphic.setColor(info.getColor());						// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// ��ư����� ���ڰ��� ����
				x2point = new int[] {center_x, end_x,end_x,center_x};			// x2point �迭�� center_x, end_x, end_x, center_x�� ���� ���ʷ� �����Ѵ�
				y2point = new int[] {center_y, center_y, end_y,end_y};			// y2point �迭�� center_y, center_x, end_x, end_y�� ���� ���ʷ� �����Ѵ�
				if (fillmode == 1)												// fillmode�� 1�� ���
					after_graphic.fillPolygon(x2point,y2point,x2point.length);	// fillPolygon �޼ҵ带 �̿��Ͽ� ä��� �簢���� �׷�����
				else 															// fillmode�� 0�� ���
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	// drawPolygon �޼ҵ带 �̿��Ͽ� ä���� ���� �簢���� �׷�����
				break;
				
			case ROUNDRECT:												
				after_graphic.setColor(info.getColor());				
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));			
				x2point = new int[] {center_x, end_x,end_x,center_x};	
				y2point = new int[] {center_y, center_y, end_y,end_y};	
				if (fillmode == 1)											
					after_graphic.fillRoundRect(center_x,center_y,end_x-center_x,end_y-center_y,50,50);
				else 													
					after_graphic.drawRoundRect(center_x,center_y,end_x-center_x,end_y-center_y,50,50);	
																		
				break;
				
			case Pentagon: 														// drawMode�� Pentagon �� ���
				after_graphic.setColor(info.getColor());						// ��ü after_graphic�� ���� line�� ����� ������ �����Ѵ�	
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// ��ư����� ���ڰ��� ����
				x2 = (int)(end_x - center_x)/4;									// x2�� ó�� x��ǥ�� ������ x��ǥ�� ���� 4�� ���� ���� �����Ѵ�
				y2 = (int)(end_y - center_y)/2;									// y2�� ó�� x��ǥ�� ������ x��ǥ�� ���� 2�� ���� ���� �����Ѵ�
				x2point = new int[] {center_x+x2+x2,end_x,center_x+x2+x2+x2,center_x+x2,center_x};
				// x2point �迭�� center_x+x2+x2,end_x,center_x+x2+x2+x2,center_x+x2,center_x �� ���� ���ʷ� �����Ѵ�
				y2point = new int[] {center_y,center_y+y2,center_y+y2+y2,end_y,center_y+y2};
				// center_y,center_y+y2,center_y+y2+y2,end_y,center_y+y2�� ���� ���ʷ� �����Ѵ�
				
				if(fillmode == 1)												// fillmode�� 1�� ���
					after_graphic.fillPolygon(x2point,y2point,x2point.length);	// fillPolygon �޼ҵ带 �̿��Ͽ� ä��� �������� �׷�����
				else															// fillmode�� 0�� ���
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	// drawPolygon �޼ҵ带 �̿��Ͽ� ä���� ���� �������� �׷�����
				break;
				
			case Delete:													 	
				after_graphic.drawRect(center_x, center_y, width_x, height_y);  
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					
				break;
				
			case Text: 
				break;
				
			default:
				break;
			}
			
			repaint();							

		}
		
		public void mouseMoved(MouseEvent event) 	{ }
		public void mouseEntered(MouseEvent event) 	{ }
		public void mouseExited(MouseEvent event) 	{ }
		public void mouseClicked(MouseEvent e) {}

	}
	
	// �� �̹��� ���� �ҷ�����
	public void newimage() {	
		show_graphic.drawImage(new ImageIcon("resource/���.png").getImage(), 0, 0, null);
	}

}