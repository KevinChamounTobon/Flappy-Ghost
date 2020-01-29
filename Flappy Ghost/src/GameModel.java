import java.util.ArrayList;

public class GameModel {

	private Player player;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private double gameWidth, gameHeight;
	private double obsMaxRadius, obsSpeed;
	private double initSpawnX, ySpawnRange;
	private double spawnTimer, coinTimer;
	private boolean debugModeUsed, debugMode;
	private boolean firstStart, isPaused;
	private double numObsPassed;
	
	public GameModel(double canvasWidth, double canvasHeight) {

		this.player = new Player(canvasWidth / 2, canvasHeight / 2, "/GameAssets/player.png");
		this.gameWidth = canvasWidth;
		this.gameHeight = canvasHeight;
		this.obsMaxRadius = 45;
		this.obsSpeed = player.getAx();
		this.initSpawnX = gameWidth + obsMaxRadius;
		this.ySpawnRange = gameHeight;
		this.spawnTimer = 0;
		this.coinTimer = 0;
		this.debugModeUsed = false;
		this.debugMode = false;
		this.firstStart = true;
		this.isPaused = false;
		this.numObsPassed = 0;
	}

	public void update(double dt) {

		updateCoins(dt);
		updateObstacles(dt);
		updateScore();
		updateHighScore();
		checkObsCollision();
		checkCoinCollision();
		player.update(dt);
	}
	
	public void obsCreator() {
		
		double initSpawnY = Obstacle.random(0, (int) ySpawnRange);

		switch (Obstacle.random(1, 3)) {

			case 1:
				obstacles.add(new SimpleObs(initSpawnX, initSpawnY, obsSpeed));
				break;
			case 2:
				obstacles.add(new SinObs(initSpawnX, initSpawnY, obsSpeed));
				break;
			case 3:
				obstacles.add(new QuantumObs(initSpawnX, initSpawnY, obsSpeed));
				break;
		}
	}
	
	public void updateCoins(double dt) {

		coinTimer += dt;
		
		double initSpawnY = Obstacle.random(0, (int) ySpawnRange);
		
		if (coinTimer >= 7) {
			Coin coin = new Coin(initSpawnX, initSpawnY, obsSpeed);
			coins.add(coin);

			for (int i = 0; i < coins.size(); i++) {
				if (coins.get(i).getX() <= coins.get(i).getRadius() * 2) {
					coins.remove(i);
				}
			}
			coinTimer = 0;
		}
		
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update(dt);
		}
	}
	
	public void updateObstacles(double dt) {
		
		spawnTimer += dt;

		if (spawnTimer >= 3) {
			obsCreator();

			for (int i = 0; i < obstacles.size(); i++) {
				if (obstacles.get(i).getX() <= -obstacles.get(i).getRadius() * 2) {
					obstacles.remove(i);
				}
			}
			spawnTimer = 0;
		}

		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).update(dt);
		}
	}

	public void updateScore() {
		
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle obs = obstacles.get(i);
			
			if (player.getX() > obs.getX() && !obs.isPassed()) {
				numObsPassed++;
				obs.setPassed(true);
				if (numObsPassed == 2) {
					player.setScore(player.getScore() + 5);
					player.setAx(player.getAx() + 15);
					player.setAy(player.getAy() + 15);
					setObsSpeed(obsSpeed + 15);
					numObsPassed = 0;
				}
			}
		}
	}
	
	public void updateHighScore() {
		
		if (player.getScore() > player.getHighScore() && !debugModeUsed) {
			player.setHighScore(player.getScore());
		}
	}
	
	public void checkObsCollision() {
		
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle obs = obstacles.get(i);
			
			if (player.intersects(obs) && !debugMode && !isPaused) {
				restart();
			}
		}
	}
	
	public void checkCoinCollision() {
		
		for (int i = 0; i < coins.size(); i++) {
			Coin coin = coins.get(i);
			
			if (player.intersects(coin) && !debugMode && !isPaused) {
				if (!coin.isTaken()) {
					player.setScore(player.getScore() + coin.getValue());
					coins.remove(i);
				}
			}
		}
	}
	
	public void restart() {

		player.setAx(120);
		player.setAy(500);
		player.setX(gameWidth / 2);
		player.setY(gameHeight / 2);
		player.setScore(0);
		setObsSpeed(120);
		setSpawnTimer(0);
		setCoinTimer(0);
		this.debugModeUsed = false;
		this.firstStart = false;
		obstacles.clear();
		coins.clear();
	}
	
	// GETTERS & SETTERS
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	public void setCoins(ArrayList<Coin> coins) {
		this.coins = coins;
	}

	public double getGameWidth() {
		return gameWidth;
	}

	public void setGameWidth(double gameWidth) {
		this.gameWidth = gameWidth;
	}

	public double getGameHeight() {
		return gameHeight;
	}

	public void setGameHeight(double gameHeight) {
		this.gameHeight = gameHeight;
	}

	public double getObsMaxRadius() {
		return obsMaxRadius;
	}

	public void setObsMaxRadius(double obsMaxRadius) {
		this.obsMaxRadius = obsMaxRadius;
	}

	public double getObsSpeed() {
		return obsSpeed;
	}

	public void setObsSpeed(double obsSpeed) {
		this.obsSpeed = obsSpeed;
	}

	public double getInitSpawnX() {
		return initSpawnX;
	}

	public void setInitSpawnX(double initSpawnX) {
		this.initSpawnX = initSpawnX;
	}

	public double getySpawnRange() {
		return ySpawnRange;
	}

	public void setySpawnRange(double ySpawnRange) {
		this.ySpawnRange = ySpawnRange;
	}

	public double getSpawnTimer() {
		return spawnTimer;
	}

	public void setSpawnTimer(double spawnTimer) {
		this.spawnTimer = spawnTimer;
	}

	public double getCoinTimer() {
		return coinTimer;
	}

	public void setCoinTimer(double coinTimer) {
		this.coinTimer = coinTimer;
	}

	public boolean isDebugModeUsed() {
		return debugModeUsed;
	}

	public void setDebugModeUsed(boolean debugModeUsed) {
		this.debugModeUsed = debugModeUsed;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public boolean isFirstStart() {
		return firstStart;
	}

	public void setFirstStart(boolean firstStart) {
		this.firstStart = firstStart;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public double getNumObsPassed() {
		return numObsPassed;
	}

	public void setNumObsPassed(double numObsPassed) {
		this.numObsPassed = numObsPassed;
	}

}