public abstract class GameObject {
	
	private double x, y;
    private double vx, vy;
    private double ax = 120, ay = 500;
	private double radius, diameter;
	private String img;

	public GameObject (double x, double y, double radius, String img) {
		
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.img = img;
		this.diameter = this.radius * 2;
	}
	
    public void update(double dt) {

    	setVy(getVy() + dt * ay);
        y += dt * vy;

        if (y + getRadius() > 400 || y - getRadius() < 0) {
        	setVy(getVy() * -0.9);
        }

        y = Math.min(y, 400 - getRadius());
        y = Math.max(y, getRadius());
    }
    
    public boolean intersects(GameObject gameObj) {

        double distX = this.getX() - gameObj.getX();
        double distY = this.getY() - gameObj.getY();;
        double distanceBetween = distX * distX + distY * distY;

        return distanceBetween < (this.getRadius() + gameObj.getRadius()) * (this.getRadius() + gameObj.getRadius());
    }
    
	// GETTERS & SETTERS
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {

		if (vy > 300) {
			vy = 300;
		} else if (vy < -300) {
			vy = -300;
		} else {
			this.vy = vy;
		}
	}

	public double getAx() {
		return ax;
	}

	public void setAx(double ax) {
		this.ax = ax;
	}

	public double getAy() {
		return ay;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

}