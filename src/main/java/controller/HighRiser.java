package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.input.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import model.GameState;
import model.Player;
import view.*;

public class HighRiser extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	public final static long SECOND = 1_000_000_000; //ns
	public final static double DBL_SEC = (double) SECOND;
	
	private long lastFrame;
	
	private Stage stage;

	private View view;
	private View lhview = null;
	private Game game;
	private AnimationTimer main;
	private UserInput input;
	
	@Override
	public void start(Stage primaryStage) {
		
		// set up view
		stage = primaryStage;
		stage.setResizable(false);
		view = new JavaFXView(stage);
		
		if(Config.ENABLE_LIGHTHOUSE) {
			try {
				lhview = new LighthouseView();
			} catch (UnknownHostException e) {
				System.err.println("Error: Unknown host.");
				lhview = null;
			} catch (IOException e) {
				System.err.println("Error: IO Exception.");
				lhview = null;
			}
		}
		
		// creates model & player instances
		newGame();
		
		// set up user input (temp solution)	
		input = new UserInput((UserInputReceiver) view);
		
		lastFrame = System.nanoTime();
		
		// the update method gets called for every frame
		main = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	update(now);
            }
        };
        
        // "start main loop"
        main.start();
        
        //update(System.nanoTime());
        
        System.out.println("Game started.");
        System.out.println("Press [F11] to go fullscreen.");
        System.out.println("Press any key to start the game...");
	}
	
	/**
	 * updates model & view
	 * @param now - time in ns               
	 */
	private synchronized void update(long now) {
		
		long diff = now - lastFrame;
		double delta = diff / DBL_SEC;
		
		switch(game.State) {
			case AwaitingInput:
				handleAwaitingInput();
				break;
			case GameOver:
				handleGameOver();
				break;
			default:
				handleInGame(delta);
		}
		
		// update view(s)
		view.draw();
		if(lhview != null) { lhview.draw(); }
		
		this.lastFrame = now;
		
		checkFullscreen();
	}
	
	// state handlers:
	
	private void handleInGame(double delta) {
		handleUserInput();
		
		// update state
		game.update(delta);
		
		if(game.numPlayersAlive() == 0) {
			game.GameOver = true;
			game.State = GameState.GameOver;
			
			System.out.println("Game over.");
			System.out.println("Press any key to try again...");
			
			input.clear();
		}
	}
	
	private void checkFullscreen() {
		if(!stage.isFullScreen() && input.fullscreenPressed()) {
			stage.setFullScreen(true);
		}
	}
	
	private void handleAwaitingInput() {
		if(input.anyKeyPressed()) {
			game.State = GameState.InGame;
		}
	}
	
	private void handleGameOver() {
		// waiting for user input
		if(input.anyKeyPressed()) {
			System.out.println("New Game.");
			newGame();
			game.State = GameState.AwaitingInput;
			
			input.clear();
			System.out.println("Press any key to start...");
		}	
	}
	
	
	private void handleUserInput() {
		for(Player player : game.Players) {
			player.setUserInput(
					input.isPressed(player.keycode)
				);
		}
	}
	
	private void newGame() {
		
		if(Config.NUM_PLAYERS == 2) {
			Player p1 = new Player(Color.RED, -Config.MULTIPLAYER_OFFSET);
			Player p2 = new Player(Color.BLUE, Config.MULTIPLAYER_OFFSET);
			p2.keycode = KeyCode.UP;
			game = new Game(p1,p2);
		} else {
			Player p1 = new Player();
			game = new Game(p1);
		}
		
		view.setModel(game);
		if(lhview != null) { lhview.setModel(game); }
	}
	
}
