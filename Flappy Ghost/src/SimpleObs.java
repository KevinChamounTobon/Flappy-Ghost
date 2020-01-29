public class SimpleObs extends Obstacle {

	public SimpleObs(double x, double y, double speed) {

		super(x, y, speed);
		setImg("/GameAssets/simpleObs.png");
	}

	@Override
	public void update(double dt) {
		setX(getX() - (this.getSpeed() * dt));
	}

}