import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *  authors EL-HAMAOUI, Christian & TOBON, Kevin
 */
public class FlappyGhost extends Application {
	
	private final double sceneWidth = 640, sceneHeight = 440;
	private final double canvasWidth = 640, canvasHeight = 400;
	private double bgStartingX = 0, bgEndingX = canvasWidth;
	private GameController controller;
    private Canvas canvas = new Canvas(canvasWidth, canvasHeight);
	private GraphicsContext context = canvas.getGraphicsContext2D();

	public static void main(String[] args) {
		FlappyGhost.launch(args);
	}

	@Override
    public void start(Stage stage) throws Exception {

		controller = new GameController(this);

        BorderPane root = new BorderPane();
		Scene gameScene = new Scene(root, this.sceneWidth, this.sceneHeight);
		HBox gameMenu = createMenu();
		ImageView pauseImg = createPauseImg();


		root.setCenter(canvas);
		root.setBottom(gameMenu);
		root.getChildren().add(pauseImg);

		setSceneFocus(gameScene);
		listenToPlayerInput(gameScene);
		
        AnimationTimer timer = new AnimationTimer() {
        	
            private long lastTime = 0;
            private double deltaTime;
            
            @Override
            public void handle(long now) {
            	
            	Player player = controller.getGameModel().getPlayer();
            	ArrayList<Obstacle> obsList = controller.getGameModel().getObstacles();
            	ArrayList<Coin> coinList = controller.getGameModel().getCoins();
            	
                if (lastTime == 0) {
                    lastTime = now;
                }
                
                if (controller.getGameModel().isPaused()) {
                	deltaTime = 0;
            		blurEffect();
            		pauseImg.setVisible(true);
                } else  {
                	deltaTime = (now - lastTime) * 1e-9;
                	context.setEffect(null);
                	pauseImg.setVisible(false);
                }

                controller.update(deltaTime);
                updateMenu(gameMenu);
                drawGameScene(deltaTime, obsList, coinList, player);
                lastTime = now;
            }
        };
        timer.start();

        stage.getIcons().add(new Image("/GameAssets/player.png"));
		stage.setTitle("Flappy Ghost");
		stage.setResizable(true);
		stage.setScene(gameScene);
		stage.show();
    }
	
	public void setSceneFocus(Scene gameScene) {

		Platform.runLater(() -> {
			canvas.requestFocus();
		});
		gameScene.setOnMouseClicked((event) -> {
			canvas.requestFocus();
		});
	}
	
	public void listenToPlayerInput(Scene gameScene) {
		
		Player player = controller.getGameModel().getPlayer();

        gameScene.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.SPACE) {
            	player.setVy(player.getJumpSpeed());
            } else if (value.getCode() == KeyCode.ESCAPE) {
            	Platform.exit();
            }
        });
	}
	
	public void blurEffect() {

		BoxBlur blurEffect = new BoxBlur();

		blurEffect.setWidth(8);
		blurEffect.setHeight(8);
		context.setEffect(blurEffect);
	}
	
	public ImageView createPauseImg() {

		ImageView pauseTxt = new ImageView(new Image("/GameAssets/pauseTxt.png"));

		pauseTxt.setX(canvasWidth / 2 - (pauseTxt.getImage().getWidth() / 2));
		pauseTxt.setY(canvasHeight / 2 - (pauseTxt.getImage().getHeight() / 2));
		pauseTxt.setVisible(false);

		return pauseTxt;
	}
	
	public HBox createMenu() {
		
		HBox gameMenu = new HBox();
		gameMenu.setSpacing(5);

		Button pauseButton = new Button();
		ImageView pauseImg = new ImageView(new Image("/GameAssets/pauseButton.png"));
		pauseButton.setGraphic(pauseImg);

		CheckBox debugBox = new CheckBox();
		ImageView debugImg = new ImageView(new Image("/GameAssets/debugModeTxt.png"));
		debugBox.setGraphic(debugImg);

		ImageView scoreImg = new ImageView(new Image("/GameAssets/scoreTxt.png"));
		Text scoreTxt = new Text();
		scoreTxt.setFill(Color.BLACK);
		scoreTxt.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		
		ImageView highScoreImg = new ImageView(new Image("/GameAssets/highScoreTxt.png"));
		Text highScoreTxt = new Text();
		highScoreTxt.setFill(Color.BLACK);
		highScoreTxt.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		
		Separator sprt1 = new Separator();
		Separator sprt2 = new Separator();
		Separator sprt3 = new Separator();
		sprt1.setOrientation(Orientation.VERTICAL);
		sprt2.setOrientation(Orientation.VERTICAL);
		sprt3.setOrientation(Orientation.VERTICAL);
		
		gameMenu.setBackground(new Background(new BackgroundFill(Color.web("#d9d9d9"), CornerRadii.EMPTY, Insets.EMPTY)));
		gameMenu.getChildren().addAll(pauseButton, sprt1, debugBox, sprt2, scoreImg, scoreTxt, sprt3, highScoreImg, highScoreTxt);
		gameMenu.setAlignment(Pos.CENTER);

		return gameMenu;
	}
	
	public void updateMenu(HBox gameMenu) {
		
		updatePause(gameMenu);
		updateDebugMode(gameMenu);
		updateScoreTxt(gameMenu);
		updateHighScoreTxt(gameMenu);
	}
	
	public void updatePause(HBox gameMenu) {
		
		Button pauseButton = (Button) gameMenu.getChildren().get(0);
		boolean isPaused = controller.getGameModel().isPaused();

		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        controller.getGameModel().setPaused(!isPaused);
		    }
		});

		if (isPaused) {
			ImageView playImg = new ImageView(new Image("/GameAssets/playButton.png"));
			pauseButton.setGraphic(playImg);
		} else {
			ImageView pauseImg = new ImageView(new Image("/GameAssets/pauseButton.png"));
			pauseButton.setGraphic(pauseImg);
		}
	}
	
	public void updateDebugMode(HBox gameMenu) {
		
		CheckBox debugBox = (CheckBox) gameMenu.getChildren().get(2);
		boolean debugModeUsed = controller.getGameModel().isDebugMode();
		boolean debugMode = controller.getGameModel().isDebugMode();
		
		debugBox.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if (!debugModeUsed) {
		    		controller.getGameModel().setDebugModeUsed(!debugModeUsed);
		    	}
		        controller.getGameModel().setDebugMode(!debugMode);
		    }
		});
	}
	
	public void updateScoreTxt(HBox gameMenu) {

		Text scoreTxt = (Text) gameMenu.getChildren().get(5);

		scoreTxt.setText(" " + controller.getGameModel().getPlayer().getScore());
	}
	
	public void updateHighScoreTxt(HBox gameMenu) {
		
		Text highScoreTxt = (Text) gameMenu.getChildren().get(8);

		highScoreTxt.setText(" " + controller.getGameModel().getPlayer().getHighScore());
	}
	
	public void drawGameScene(double dt, ArrayList<Obstacle> obsList, ArrayList<Coin> coinList, Player player) {

        drawBg(dt);
        drawObstacles(obsList);
        drawCoins(coinList, player);
        drawPlayer(player);
        drawStartImg(obsList, player);
        drawJumpInstruction(obsList);
	}
	
	public void drawBg(double dt) {
		
		Image bgImg = new Image("/GameAssets/background.png");
		double scrollingSpeed = controller.getGameModel().getPlayer().getAx() * dt;
		
		if (bgEndingX <= 0) {
			bgStartingX = 0;
			bgEndingX = canvasWidth;
		}

		bgStartingX -= scrollingSpeed;
		bgEndingX -= scrollingSpeed;
		
		context.drawImage(bgImg, bgStartingX, 0);
		context.drawImage(bgImg, bgStartingX + canvasWidth, 0);
	}

	public void drawObstacles(ArrayList<Obstacle> obsList) {
		
		for (int i = 0; i < obsList.size(); i++) {
			Obstacle obs = obsList.get(i);
			Image obsImg = new Image(obs.getImg(), obs.getDiameter(), obs.getDiameter(), false, false);
			double obsX = obs.getX() - (obsImg.getWidth() / 2);
			double obsY = obs.getY() - (obsImg.getHeight() / 2);
			
			if (controller.getGameModel().isDebugMode()) {
				if (obs.intersects(controller.getGameModel().getPlayer())) {
					context.setFill(Color.web("#ff0000"));
					context.fillOval(obsX, obsY, obs.getDiameter(), obs.getDiameter());
				} else {
					context.setFill(Color.web("#2f1165"));
					context.fillOval(obsX, obsY, obs.getDiameter(), obs.getDiameter());
				}
			} else {
				context.drawImage(obsImg, obsX, obsY);
			}
		}
	}
	
	public void drawCoins(ArrayList<Coin> coinList, Player player) {
		
		for (int i = 0; i < coinList.size(); i++) {
			Coin coin = coinList.get(i);
			Image coinImg = new Image(coin.getImg(), coin.getDiameter(), coin.getDiameter(), false, false);
			double coinX = coin.getX() - (coinImg.getWidth() / 2);
			double coinY = coin.getY() - (coinImg.getHeight() / 2);
			
			if (controller.getGameModel().isDebugMode()) {
				if (coin.intersects(player)) {
					context.setFill(Color.web("#ff0000"));
					context.fillOval(coinX, coinY, coin.getDiameter(), coin.getDiameter());
					if (!coin.isTaken()) {
						player.setScore(player.getScore() + coin.getValue());
						coin.setTaken(true);
					}
				} else {
					context.setFill(Color.web("#ffd700"));
					context.fillOval(coinX, coinY, coin.getDiameter(), coin.getDiameter());
				}
			} else {
				context.drawImage(coinImg, coinX, coinY);
			}
		}
	}
	
	public void drawPlayer(Player player) {

		Image playerImg = new Image(player.getImg(), player.getDiameter(), player.getDiameter(), false, false);
		double playerX = player.getX() - (playerImg.getWidth() / 2);
		double playerY = player.getY() - (playerImg.getHeight() / 2);

		if (controller.getGameModel().isDebugMode()) {
			context.setFill(Color.web("#ffffff"));
			context.fillOval(playerX, playerY, player.getDiameter(), player.getDiameter());
		} else {
			context.drawImage(playerImg, playerX, playerY);
		}
	}
	
	public void drawStartImg(ArrayList<Obstacle> obsList, Player player) {

        if (obsList.size() == 0) {
        	Image startImg = new Image("/GameAssets/startTxt.png");
        	double startImgX = player.getX() - (startImg.getWidth() / 2);
        	double startImgY = player.getY() + (startImg.getHeight() / 2 - 80);

        	context.drawImage(startImg, startImgX, startImgY);
        }
	}
	
	public void drawJumpInstruction(ArrayList<Obstacle> obsList) {
		
        if (controller.getGameModel().isFirstStart() && obsList.size() == 0) {
        	Image jumpInstructionImg = new Image("/GameAssets/jumpInstructionImg.png");
        	double jumpInstructionImgX = canvasWidth / 2 - (jumpInstructionImg.getWidth() / 2);
        	double jumpInstructionImgY = canvasHeight / 5 - (jumpInstructionImg.getHeight() / 2);

        	context.drawImage(jumpInstructionImg, jumpInstructionImgX, jumpInstructionImgY);
        }
	}

	// GETTERS & SETTERS
	public double getBgStartingX() {
		return bgStartingX;
	}

	public void setBgStartingX(double bgStartingX) {
		this.bgStartingX = bgStartingX;
	}

	public double getBgEndingX() {
		return bgEndingX;
	}

	public void setBgEndingX(double bgEndingX) {
		this.bgEndingX = bgEndingX;
	}

	public GameController getController() {
		return controller;
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public GraphicsContext getContext() {
		return context;
	}

	public void setContext(GraphicsContext context) {
		this.context = context;
	}

	public double getSceneWidth() {
		return sceneWidth;
	}

	public double getSceneHeight() {
		return sceneHeight;
	}

	public double getCanvasWidth() {
		return canvasWidth;
	}

	public double getCanvasHeight() {
		return canvasHeight;
	}

}