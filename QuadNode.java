import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

// Based on algorithm described in: http://arborjs.org/docs/barnes-hut
// and also: "A hierarchical O(N log N) force-calculation algorithm" by Barnes & Hut

class QuadNode{
	private QuadNode nw,ne,sw,se;		// quadrants
	private Particle val;				// only set for external nodes
	private double x,y;					// x,y coordinates of quad
	private double w; 					// width of quadrant
	private double mass;				// total mass of quad-node children
	private double center_mass_x;		// x coordinate for center-of-mass
	private double center_mass_y;		// y coordinate for center-of-mass
	private boolean empty;

	QuadNode(double x, double y,double w){
		this.x = x;
		this.y = y;
		this.w = w;
		empty = true;
	}

	void insert(Particle p){

		if(empty){
			val = p;
			empty = false;
			mass = p.getMass();
			center_mass_x = p.getX();
			center_mass_y = p.getY();
		}else{
			// place node in appropriate quadrant
			if(p.getX() < x + w){
				if(p.getY() < y + w){
					if(nw == null) nw = new QuadNode(x,y,w / 2);
					nw.insert(p);
				}else{
					if(sw == null) sw = new QuadNode(x,y+w,w / 2);
					sw.insert(p);
				}
			}else{
				if(p.getY() < y + w){
					if(ne == null) ne = new QuadNode(x+w,y,w / 2);
					ne.insert(p);
				}else{
					if(se == null) se = new QuadNode(x+w,y+w,w / 2);
					se.insert(p);
				}
			}

			// move val into appropriate branch
			if(val != null) {
				Particle temp = val;
				val = null;
				mass = 0;
				this.insert(temp);
			}

			//update mass and center of mass
			double old_mass = mass;
			mass += p.getMass();
			center_mass_x = (p.getMass()*p.getX() + center_mass_x * old_mass) / mass;
			center_mass_y = (p.getMass()*p.getY() + center_mass_y * old_mass) / mass;
		}

	}

	void updateForce(Particle p) {

		if(val != null) { //external node
			if(this.val != p) {
				p.updateForce(this.val);
			}else {
				return;
			}
		} else {	//internal node
			double s = w * 2.0; // width of region
			Particle center_mass = new Particle(center_mass_x,center_mass_y,mass);
			double d = p.getPosition().distance(center_mass.getPosition());
			double sd_ratio = s / d;

			if(sd_ratio < 0.5) { //sufficiently far away
				p.updateForce(center_mass);
			}else {
				if(nw != null) nw.updateForce(p);
				if(ne != null) ne.updateForce(p);
				if(sw != null) sw.updateForce(p);
				if(se != null) se.updateForce(p);
			}
		}

	}

	void resetContents() {
		empty = true;
		mass = 0;
		val = null;
		nw = null;
		ne = null;
		sw = null;
		se = null;
	}
}
