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
	
	/* 그리던 그림을 임시 저장할 BufferedImage 선언 사이즈가 1250 X 700 인 BufferedImage 객체를 초기화한다
	   .getGraphics()의 의미는 papar_show, paper_temp 로 부터 Graphics 객체를 반환하는 것이고
	   show_graphic, after_graphic 은 그것을 담기위한 변수 이다.*/
	protected static BufferedImage paper_show = new BufferedImage(1250, 700,BufferedImage.TYPE_3BYTE_BGR); 
	protected Graphics show_graphic = paper_show.getGraphics();
	private static BufferedImage paper_temp = new BufferedImage(1250, 700,BufferedImage.TYPE_3BYTE_BGR);
	private Graphics after_graphic = paper_temp.getGraphics();

	// 현재 그리기 모드와 채우기 정보
	private static int drawMode, fillmode = 0; 
			
	// 마우스 드래그 이벤트에서 사용될 x와 y의 좌표를 가져올 포인터
	private Point p1, p2;				
	
	// center는 마우스의 시작점 end는 마우스가 끝나는점, width_x는 가로의 길이 x값들의 차이, heigh_y는 높이 y값들의 차이
	private int center_x, center_y, width_x, height_y;
	
	// x, y, x2, y2는 값들을 저장할 변수
	private int end_x, end_y, x, y, x2, y2;			  	
													
	private int[] xpoint = null, ypoint = null, x2point = null, y2point = null;
				
	// line에 저장된 굵기를 저장하는 변수
	private static int outline = 1;							
	
	
	
	/**
	 * switch 문에서 사용할 Workspace에 표현 될 고유의 숫자들
	 */
	
	static final int CIRCLE 		= 0;					// 원
	static final int TRIANGLE 		= 1;					// 삼각형
	static final int RECTANGLE 		= 2;					// 사각형
	static final int ROUNDRECT 		= 3;					// 둥근사각형
	static final int Pentagon		= 4; 					// 오각형
	static final int Brush 			= 5;					// 붓
	static final int Pen 			= 6;					// 펜(자유곡선)
	static final int dottedLine 	= 7;					// 점선
	static final int straightLine 	= 8;					// 직선
	static final int Eraser 		= 9;					// 지우기
	static final int Delete 		= 10;					// 선택영역 지우기 
	static final int Text 			= 11;					// 텍스트 상자		
	
	
	/**
	 * 
	 * 도형, 선 그리는 함수 구현
	 */
	
	public Workspace(String name) {
		show_graphic.fillRect(0, 0, 1250, 700);								
		show_graphic.drawImage(new ImageIcon(name).getImage(), 0, 0, null);	
		
		MouseHandler handler = new MouseHandler();	
		addMouseListener(handler);				
		addMouseMotionListener(handler);
	}
	
	// 그림 그리는 함수
	public void paint(Graphics g) {	
		g.drawImage(paper_show, 0, 0, null);
		repaint();	
	}

	// 그림 업데이트 함수
	public void update(Graphics g) {				
		paint(g);
	} 									
	
	// DataArea에서 Button ActionListener가 발생되었을 때 실핼할 모드
	public static void setDrawMode(int dmode) {
													
		drawMode = dmode;	
	}

	// DataArea에서 Fill ActionListener가 발생되었을 때 채우기 값  유무 변수
	public static void setFillMode(int fmode) {
													
		fillmode = fmode;								
	}


	/**
	 * 
	 * 마우스 모션 이벤트
	 */
	
	class MouseHandler implements MouseListener, MouseMotionListener 
	{		
      
		// 마우스가 눌렸을 때
		public void mousePressed(MouseEvent e) {
			// 인식하기 위해서 MouseListener와 MouseMotionListener를 구현한다	
			// ImageObserver은 이미지 drawImage를 하기 위해서 구현하였다.	
			// 마우스가 눌렸을때 시작점을 저장하고 지우개모드 일땐 흰색으로 설정한다.
			Information line2 = null;	
			line2 = new Information();							// LineMode클래스의 line2 객체 생성

			if (drawMode == Eraser) {						// 지우개일땐 흰색으로 설정
				Information.setColor(Color.white); 	
			} else {										// 그외엔 현재 설정된 색으로
				Information.setColor(ColorPanel.color); 
			}
			info = line2;									// line객체에 line2의 주소값을 준다.							 
			info.setPoint(e.getPoint());    	    		// LineMode의 객체인 line에 마우스 눌림 시 좌표를 저장한다

		}

		// 마우스 떨어졌을 때
		public void mouseReleased(MouseEvent event) {

			Graphics2D show_graphic2D = (Graphics2D) show_graphic;			// 2D를 구현하기 위해 생성한 객체 
			p1 = info.getPoint().firstElement();							// line에 저장시켜 놓은 첫번째 좌표를 p1에 저장시켜 놓는다
			p2 = info.getPoint().lastElement();								// line에 저장시켜 놓은 마지막 좌표를 p2에 저장시켜 놓는다
			outline = info.getThick();										// line에 저장시켜 있던 굵기를 outline에 저장시켜 놓는다
			
			if (p1.x > p2.x) {								// x좌표와 y좌표 중에서 가장 작은 값과 가장 큰 값을 구분하기 위한 if문
				center_x = p2.x;							// 작은 좌표를 center에 큰 값을 end에 넣는다
				end_x = p1.x;								// 큰 값과 작은 값의 차를 width에 넣는다
				width_x = p1.x - p2.x;										
			} else {										// 즉, center_x는 두 좌표중 x값이 작은 값을 저장하는 int형 변수
				center_x = p1.x;							// 즉, end_x는 두 좌표중 x값이 큰 값을 저장하는 int형 변수 
				end_x = p2.x;								// 즉, width_x는 두 x좌표의 차이
				width_x = p2.x - p1.x;						// 즉, center_y는 두 좌표중 y값이 작은 값을 저장하는 int형 변수
			}												// 즉, end_y는 두 좌표중 y값이 큰 값을 저장하는 int형 변수 
			if (p1.y > p2.y) {								// 즉, height_y는 두 y좌표의 차이
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
				show_graphic2D.setColor(info.getColor());					// 객체 show_graphic2D의 색을 line에 저장된 값으로 설정한다
				show_graphic2D.setStroke(new BasicStroke(outline));			// 객체 show_graphic2D의 굵기를 line에 저장된 값으로 설정한다
					x = (center_x + end_x)/2;   							// x에 처음 x좌표와 마지막 x좌표를 더하고 2를 나눈 값을 넣는다
					xpoint = new int[] {x, center_x, end_x};				// xpoint 배열에 x, center_x, end_x의 값을 차례로 저장한다
					ypoint = new int[] {center_y, end_y, end_y};			// ypoint 배열에 center_y, end_y, end_y의 값을 차례로 저장한다
				if (fillmode == 1)											// fillmode가 1일 경우
					show_graphic2D.fillPolygon(xpoint,ypoint,xpoint.length);// fillPolygon 메소드를 이용하여 채우기 삼각형을 그려낸다
				else														// fillmode가 0일 경우
					show_graphic2D.drawPolygon(xpoint,ypoint,xpoint.length);// drawPolygon 메소드를 이용하여 채우지 않은 삼각형을 그려낸다
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
				
			case Pentagon: 												// drawMode가 Pentagon 일 경우
				show_graphic2D.setColor(info.getColor());				// 객체 show_graphic2D의 색을 line에 저장된 값으로 설정한다
				show_graphic2D.setStroke(new BasicStroke(outline));		// 객체 show_graphic2D의 굵기를 line에 저장된 값으로 설정한다
				x = (end_x - center_x)/4;								// x에 마지막 x좌표에 처음 x좌표를 빼고 4을 나눈 값을 저장한다
				y = (end_y - center_y)/2;								// y에 마지막 y좌표에 처음 y좌표를 빼고 2을 나눈 값을 저장한다
				xpoint = new int[] {center_x+x+x,end_x,center_x+x+x+x,center_x+x,center_x};
				// xpoint 배열에 center_x+x+x,end_x,center_x+x+x+x,center_x+x,center_x의 값을 차례로 저장한다
				ypoint = new int[] {center_y,center_y+y,center_y+y+y,end_y,center_y+y};	
				// ypoint 배열에 enter_y,center_y+y,center_y+y+y,end_y,center_y+y의 값을 차례로 저장한다
				if(fillmode == 1)											// fillmode가 1일 경우
					show_graphic2D.fillPolygon(xpoint,ypoint,xpoint.length);// fillPolygon 메소드를 이용하여 채우기 사각형을 그려낸다
				else														// fillmode가 1일 경우
					show_graphic2D.drawPolygon(xpoint,ypoint,xpoint.length);// fillPolygon 메소드를 이용하여 채우지 않은 육각형을 그려낸다
				break;
				
			case dottedLine:												// drawMode가 dottedLine 일 경우
				float dotpoint[] = { 10.0f };								// dotpoint를 10.0f로 설정한다. 이는 점들의 간격을 의미
				show_graphic2D.setStroke(new BasicStroke(outline));			// 객체 show_graphic2D의 굵기를 line에 저장된 값으로 설정한다
				show_graphic2D.setColor(info.getColor());					// 객체 show_graphic2D의 색을 line에 저장된 값으로 설정한다
				show_graphic2D.setStroke(new BasicStroke(outline,0,BasicStroke.JOIN_MITER,10.0f,dotpoint, 0));
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);			// drawLine 메소드를 이용하여 점선을 그려낸다
				setCursor(LeftPanel.cs_1); 									// 점선 버튼 커서 설정
				break; 	
				
			case straightLine:												// drawMode가 straightLine 일 경우
				show_graphic2D.setColor(info.getColor());					// 객체 show_graphic2D의 색을 line에 저장된 값으로 설정한다
				show_graphic2D.setStroke(new BasicStroke(outline));			// 객체 show_graphic2D의 굵기를 line에 저장된 값으로 설정한다
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);			// drawLine 메소드를 이용하여 선을 그려낸다
				setCursor(LeftPanel.cs_1);    								// 직선 버튼 커서 설정
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
				
				ButtonPanel.textofFont = "고딕체";	
				ButtonPanel.textofStyle = 0;
				ButtonPanel.textofSize = 10;
				break;
				
			default:
			}
			
			repaint();									
			Information.point.clear();					

		}
		
		// 마우스 드래그 중일 때
		public void mouseDragged(MouseEvent e) {

			Graphics after_graphic = getGraphics();							//잔상을 나타내기 위한 그래픽 객체 after_graphic을 생성
			Graphics2D show_graphic2D = (Graphics2D) show_graphic;			//2D를 구현하기 위해 생성한 객체 ???????? 어떻게 설명???
			outline = info.getThick();										//line에 저장시켜 있던 굵기를 outline에 저장시켜 놓는다
			info.setPoint(e.getPoint());									//마우스 드래그 시 발생되는 좌표의 값을 line에 저장시켜 놓는다
			Point p1 = info.getPoint().size() > 1 ? info.getPoint().get(	//line에 저장시켜 놓은 좌표가 2개 이상일 경우 
					info.getPoint().size() - 2) : info.getPoint()			//마지막에서 앞의 값을, 2개보다 작을 경우 가장 처음 좌표를 p1에 저장시켜 놓는다
					.firstElement();
			Point p2 = info.getPoint().lastElement();						//Point형 변수 p2에는 가장 마지막 좌표를 저장시켜 놓는다
			Point p4 = info.getPoint().firstElement();						//Point형 변수 p4에는 가장 처음 좌표를 저장시켜 놓는다

			if (p2.x >= p4.x) {												//x좌표와 y좌표 중에서 가장 작은 값과 가장 큰 값을 구분하기 위한 if문
				center_x = p4.x;											//작은 좌표를 center에 큰 값을 end에 넣는다
				end_x = p2.x;												//큰 값과 작은 값의 차를 width에 넣는다
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
			case Eraser:															// drawMode가 Eraser 일 경우
				show_graphic.setColor(Color.white);									// 객체 show_graphic2D의 색을 하얀색으로 설정한다
				show_graphic.fillRect(p2.x, p2.y, outline * 10, outline * 10);		// fillRect 메소드를 이용하여 채우기 사각형을 그려낸다
				setCursor(LeftPanel.cs_2);    										// 지우개 버튼 커서 설정
				break; 
				
			case Pen:																// drawMode가 Pen 일 경우	
				show_graphic2D.setStroke(new BasicStroke(outline));					// 객체 show_graphic2D의 굵기를 line에 저장된 값으로 설정한다
				show_graphic2D.setColor(info.getColor());							// 객체 show_graphic2D의 색을 line에 저장된 색으로 설정한다
				setCursor(LeftPanel.cs_1);    										// pen 버튼 커서 설정
				show_graphic2D.drawLine(p1.x, p1.y, p2.x, p2.y);					// drawLine 메소드를 이용하여 선을 그려낸다
				break; 
				
			case Brush:																// drawMode가 Brush 일 경우
				show_graphic.setColor(info.getColor());								// 객체 show_graphic의 색을 line에 저장된 색으로 설정한다
				show_graphic.fillOval(p2.x, p2.y, outline * 10, outline * 10);		// fillOval 메소드를 이용하여 채우기 원을 그려낸다
				setCursor(LeftPanel.cs_1);											// 붓 버튼 커서 설정
				break; 
				
			case straightLine:
				after_graphic.setColor(info.getColor());							// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다
				after_graphic.drawLine(p4.x, p4.y, p2.x, p2.y);						// drawLine 메소드를 이용하여 선을 그려낸다
				setCursor(LeftPanel.cs_1);											// drawMode가 straightLine, dottedLine 일 경우
				break;	
				
			case dottedLine:
				after_graphic.setColor(info.getColor());							// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다
				after_graphic.drawLine(p4.x, p4.y, p2.x, p2.y);						// drawLine 메소드를 이용하여 선을 그려낸다
				setCursor(LeftPanel.cs_1);    										// 점선 버튼 커서 설정
				break;
				
			case CIRCLE:															// drawMode가 CIRCLE 일 경우
				after_graphic.setColor(info.getColor());							// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));						// 버튼모양을 십자가로 만듬
				if (fillmode == 1)													// fillmode가 1일 경우
					after_graphic.fillOval(center_x, center_y, width_x, height_y);	// fillOval 메소드를 이용하여 채우기 원을 그려낸다
				else 																// fillmode가 0일 경우
					after_graphic.drawOval(center_x, center_y, width_x, height_y);	// drawOval 메소드를 이용하여 채우지 않은 원을 그려낸다
				break;
	
			case TRIANGLE:														// drawMode가 TRIANGLE 일 경우
				after_graphic.setColor(info.getColor());						// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// 버튼모양을 십자가로 만듬
					x2 = (center_x + end_x)/2;   								// x2에 처음 x좌표와 마지막 x좌표를 더하고 2를 나눈 값을 저장한다
					x2point = new int[] {x2, center_x, end_x};					// x2point 배열에 x2, center_x, end_x의 값을 차례로 저장한다
					y2point = new int[] {center_y, end_y, end_y};				// y2point 배열에 center_y, end_y, end_y의 값을 차례로 저장한다
				if (fillmode == 1)												
					after_graphic.fillPolygon(x2point,y2point,x2point.length);
				else														
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	
				break;
			
			case RECTANGLE:														// drawMode가 RECTANGLE 일 경우
				after_graphic.setColor(info.getColor());						// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// 버튼모양을 십자가로 만듬
				x2point = new int[] {center_x, end_x,end_x,center_x};			// x2point 배열에 center_x, end_x, end_x, center_x의 값을 차례로 저장한다
				y2point = new int[] {center_y, center_y, end_y,end_y};			// y2point 배열에 center_y, center_x, end_x, end_y의 값을 차례로 저장한다
				if (fillmode == 1)												// fillmode가 1일 경우
					after_graphic.fillPolygon(x2point,y2point,x2point.length);	// fillPolygon 메소드를 이용하여 채우기 사각형을 그려낸다
				else 															// fillmode가 0일 경우
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	// drawPolygon 메소드를 이용하여 채우지 않은 사각형을 그려낸다
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
				
			case Pentagon: 														// drawMode가 Pentagon 일 경우
				after_graphic.setColor(info.getColor());						// 객체 after_graphic의 색을 line에 저장된 색으로 설정한다	
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));					// 버튼모양을 십자가로 만듬
				x2 = (int)(end_x - center_x)/4;									// x2에 처음 x좌표와 마지막 x좌표를 빼고 4를 나눈 값을 저장한다
				y2 = (int)(end_y - center_y)/2;									// y2에 처음 x좌표와 마지막 x좌표를 빼고 2를 나눈 값을 저장한다
				x2point = new int[] {center_x+x2+x2,end_x,center_x+x2+x2+x2,center_x+x2,center_x};
				// x2point 배열에 center_x+x2+x2,end_x,center_x+x2+x2+x2,center_x+x2,center_x 의 값을 차례로 저장한다
				y2point = new int[] {center_y,center_y+y2,center_y+y2+y2,end_y,center_y+y2};
				// center_y,center_y+y2,center_y+y2+y2,end_y,center_y+y2의 값을 차례로 저장한다
				
				if(fillmode == 1)												// fillmode가 1일 경우
					after_graphic.fillPolygon(x2point,y2point,x2point.length);	// fillPolygon 메소드를 이용하여 채우기 육각형을 그려낸다
				else															// fillmode가 0일 경우
					after_graphic.drawPolygon(x2point,y2point,x2point.length);	// drawPolygon 메소드를 이용하여 채우지 않은 육각형을 그려낸다
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
	
	// 새 이미지 파일 불러오기
	public void newimage() {	
		show_graphic.drawImage(new ImageIcon("resource/흰색.png").getImage(), 0, 0, null);
	}

}