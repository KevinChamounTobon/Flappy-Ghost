public class QuantumObs extends Obstacle {

	private double initX, initY;
	private double timeSinceLastMove;
	private int tpRadius;
	
	public QuantumObs(double x, double y, double speed) {

		super(x, y, speed);
		this.initX = this.getX();
		this.initY = this.getY();
		this.tpRadius = 30;
		setImg("/GameAssets/quantumObs.png");
	}
	
	@Override
	public void update(double dt) {

		timeSinceLastMove += dt;

		if (timeSinceLastMove >= 0.2) {
			initX = getX();
			setX(initX - (Obstacle.random(-tpRadius, tpRadius) + this.getSpeed() * dt));
			setY(initY - Obstacle.random(-tpRadius, tpRadius));
			timeSinceLastMove = 0;
		} else {
			setX(getX() - this.getSpeed() * dt);
		}
	}

	// GETTERS & SETTERS
	public double getInitX() {
		return initX;
	}

	public void setInitX(double initX) {
		this.initX = initX;
	}

	public double getInitY() {
		return initY;
	}

	public void setInitY(double initY) {
		this.initY = initY;
	}

	public double getTimeSinceLastMove() {
		return timeSinceLastMove;
	}

	public void setTimeSinceLastMove(double timeSinceLastMove) {
		this.timeSinceLastMove = timeSinceLastMove;
	}
	
	public int getTpRadius() {
		return tpRadius;
	}

	public void setTpRadius(int tpRadius) {
		this.tpRadius = tpRadius;
	}

}