import java.util.Random;

class Particle{
	public static double epsilon;
	private double mass;
	private Point  position;
	private Vector force;
	private Vector velocity;
	private Vector acceleration;

	Particle(){
		mass = 1;
		position = new Point(0,0);
		velocity = new Vector(0,0);
		force = new Vector(0,0);
	}

	Particle(double x, double y, double mass){
		position =  new Point(x,y);
		this.mass = mass;
		velocity = new Vector(0,0);
		force = new Vector(0,0);
		acceleration = new Vector(0,0);
	}

	/* Update position and velocity using synchronized
	leap-frog-integration ("kick-drift-kick" method)*/
	void updatePosition(double dt) {
		Vector velocity_mid = velocity.add(acceleration.scale(dt/2));
		position = position.add(velocity_mid.scale(dt));
		//Newton's second law: F = M*A
		acceleration = force.scale((1/mass));
		velocity = velocity_mid.add(acceleration.scale(dt/2));
	}

	//Universal law of gravitation: F = (G*m1*m2) / r^2
	void updateForce(Particle p) {
		final double G = 6.674E-11;
		double r = this.distance(p)+epsilon;

		double force_magnitude = G * (mass * p.mass) / (r * r);
		Vector force_direction = (p.position.subtract(position)).norm();
		force = force.add(force_direction.scale(force_magnitude));
	}

	void resetForce() {
		force = new Vector(0,0);
	}

	/*Distance between two particles*/
	double distance(Particle p) {
		return position.distance(p.position);
	}

	Point getPosition() {
		return position;
	}

	double getX(){
		return position.getX();
	}

	double getY(){
		return position.getY();
	}

	double getMass(){
		return mass;
	}

	void setVelocity(Vector velocity){
		this.velocity = velocity;
	}

}
