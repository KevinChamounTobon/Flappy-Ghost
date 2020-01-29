public class SinObs extends Obstacle {
	
	private double initY;
	private double initMotionValue;
	private double timeSinceStart;
	private double motionRange;

	public SinObs(double x, double y, double speed) {

		super(x, y, speed);
		this.initY = this.getY();
		this.initMotionValue = Obstacle.random(0, 10);
		this.motionRange = 50;
		setImg("/GameAssets/sinObs.png");
	}

	@Override
	public void update(double dt) {
		
		timeSinceStart += dt;
		
		setX(getX() - (this.getSpeed() * dt));
		setY(initY + motionRange * Math.sin(initMotionValue + timeSinceStart * 10));
	}

	// GETTERS & SETTERS
	public double getInitY() {
		return initY;
	}

	public void setInitY(double initY) {
		this.initY = initY;
	}
	
	public double getInitMotionValue() {
		return initMotionValue;
	}

	public void setInitMotionValue(double initMotionValue) {
		this.initMotionValue = initMotionValue;
	}
	
	public double getTimeSinceStart() {
		return timeSinceStart;
	}

	public void setTimeSinceStart(double timeSinceStart) {
		this.timeSinceStart = timeSinceStart;
	}
	
	public double getMotionRange() {
		return motionRange;
	}

	public void setMotionRange(double motionRange) {
		this.motionRange = motionRange;
	}

}