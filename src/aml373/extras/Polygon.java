package aml373.extras;

import java.util.LinkedList;
import java.util.List;

public class Polygon {

	Pair[] points;
	
	public Polygon(Pair[] coordinates) {
		this.points = coordinates;
	}
	
	/**
	 * Calculate the area of this polygon.
	 * Uses shoelace algorithm.
	 * @return
	 */
	public double area() {
		if (points.length <= 1) return 0;
		double area = 0;
		int prev = points.length - 1;
		
		for (int i= 0; i < points.length; i++) {
			area += points[prev].x * points[i].y -  points[i].x * points[prev].y;
			prev = i;
		}
		
		return area / 2;
	}
	
	//examples
	public static void main(String args[]){
		List<Pair[]> shapes = new LinkedList<>();
		//L-shaped polygon
		Pair[] coords1 = {new Pair(-1,2), new Pair(-1,-2), new Pair(3,-2), new Pair(3,-1), new Pair(0,-1), new Pair(0,2)};
		shapes.add(coords1);
		//square
		Pair[] coords2 = {new Pair(0, 0), new Pair(-1, 0), new Pair(-1, -1), new Pair(0, -1)};
		shapes.add(coords2);
		//v-shaped polygon
		Pair[] coords3 = {new Pair(0, 0), new Pair(2, 2), new Pair(4, 0), new Pair(2, 4)};
		shapes.add(coords3);
		
		for (Pair[] shape : shapes) {
			System.out.println((new Polygon(shape)).area());
		}
	}
	
}
