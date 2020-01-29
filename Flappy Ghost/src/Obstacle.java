import java.util.Random;

public abstract class Obstacle extends GameObject {
	
	private String type;
	private double speed;
	private boolean isPassed;
	private final String[] obstacleTypes = {"Simple", "Sinus", "Quantum"};
	
	public Obstacle(double x, double y, double speed) {

		super(x, y, random(10, 45), "img");
		this.type = obstacleTypes[random(0, 2)];
		this.speed = speed;
		this.isPassed = false;
	}
	
	public static int random(int min, int max) {

		Random random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}
	
	public abstract void update(double dt);

	// GETTERS & SETTERS
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}

	public String[] getObstacleTypes() {
		return obstacleTypes;
	}

}