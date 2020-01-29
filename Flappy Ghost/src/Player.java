public class Player extends GameObject {
	
	private int score, highScore;
	private double jumpSpeed;

	public Player(double x, double y, String img) {

		super(x, y, 30, img);
		this.score = 0;
		this.highScore = 0;
		this.jumpSpeed = -300;
	}

	// GETTERS & SETTERS
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public double getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

}