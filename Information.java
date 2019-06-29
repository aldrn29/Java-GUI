package paint;

import java.awt.Color; 
import java.awt.Point;
import java.util.Vector;

/**
 * 색깔, 선의 두께, 위치 정보를 가지고 있는 클래스
 */

public class Information {

	private static Color color = Color.black;	
	private static int thickness = 1;
	protected static Vector<Point> point = new Vector<Point>();

	/**
	 * 색깔, 선의 두께, 위치 get/set 함수 구현
	 */
	
	protected static void setColor(Color newercolor) {	
		color = newercolor;									
	}

	protected static void setThick(int newerthickness) {	
		thickness = newerthickness;	
	}

	protected void setPoint(Point newerpoint) {	
		point.add(newerpoint);					
	}

	protected Color getColor() {	
		return color;
	}

	protected int getThick() {		
		return thickness;
	}

	protected Vector<Point> getPoint() {
		return point;
	}

}
