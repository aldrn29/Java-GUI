package paint;

import java.awt.Color; 
import java.awt.Point;
import java.util.Vector;

/**
 * ����, ���� �β�, ��ġ ������ ������ �ִ� Ŭ����
 */

public class Information {

	private static Color color = Color.black;	
	private static int thickness = 1;
	protected static Vector<Point> point = new Vector<Point>();

	/**
	 * ����, ���� �β�, ��ġ get/set �Լ� ����
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
