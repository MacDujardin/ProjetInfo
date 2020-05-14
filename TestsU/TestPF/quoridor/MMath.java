package quoridor;
public class MMath {
	
	public static int intSup(int x) {
		return x+1;
	}
	
	public static boolean isPair (int x) {
		return (int) x/2 ==  (float) x/2;
	}
	public static double distance (Vector veca , Vector vecb) {
		int dx = vecb.x - veca.x;
		int dy = vecb.y -  veca.y;
		double dist = java.lang.Math.sqrt(java.lang.Math.pow(dx, 2)+ java.lang.Math.pow(dy, 2));
		return dist;
	}
}
