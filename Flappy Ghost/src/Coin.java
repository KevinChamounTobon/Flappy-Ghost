public class Coin extends GameObject {
	
	private int value;
	private double speed;
	private boolean taken;
	
	public Coin(double x, double y, double speed) {

		super(x, y, 20, "/GameAssets/coin.png");
		this.value = 10;
		this.speed = speed;
		this.taken = false;
	}

	@Override
	public void update(double dt) {
		setX(getX() - (speed * dt));
	}

	// GETTERS & SETTERS
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

}