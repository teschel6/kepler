
public class Vector {
	private double x;
	private double y;
	
	Vector(){
		x = 0;
		y = 0;
	}
	
	Vector(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	Vector norm() {
		double x_component = x * (1 / magnitude());
		double y_component = y * (1 / magnitude());
		return new Vector(x_component,y_component);
	}
	
	double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	
	Vector add(Vector v) {
		return new Vector(x + v.x,y + v.y);
	}
	
	Vector subtract(Vector v) {
		return new Vector(x - v.x,y-v.y);
	}
	
	Vector scale(double k) {
		return new Vector(k*x,k*y);
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
