package aml373.extras;


public class Polygon {
	public class Pair {
		public double x,y;
		
		public Pair(double x, double y){
			this.x = x;
			this.y = y;
		}
	}

	Pair[] points;
	
	public Polygon(Pair[] coordinates) {
		this.points = coordinates;
	}
	
	/**
	 * Calculate the area of this polygon.
	 * Algorithm from 
	 * http://www.mathopenref.com/coordpolygonarea2.html
	 * @return
	 */
	public double area() {
		int area = 0;
		int j = points.length - 1;
		for (int i= 0; i < points.length; i++) {
			Pair p = points[i];
			area += (points[i].x + points[j].x) * (points[j].y - points[i].y);
			j = i;
		}
		
		return area;
	}
}
