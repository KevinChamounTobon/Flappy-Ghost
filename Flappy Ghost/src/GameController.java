public class GameController {
	
	private FlappyGhost view;
	private GameModel gameModel;

	public GameController(FlappyGhost view) {
		
		this.view = view;
		this.gameModel = new GameModel(view.getCanvasWidth(), view.getCanvasHeight());
	}
	
	public void update(double dt) {
		gameModel.update(dt);
	}

	// GETTERS & SETTERS
	public FlappyGhost getView() {
		return view;
	}

	public void setView(FlappyGhost view) {
		this.view = view;
	}

	public GameModel getGameModel() {
		return gameModel;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

}