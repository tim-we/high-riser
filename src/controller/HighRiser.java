package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.input.*;
import controller.state.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
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
		
		if(Config.enableLighthouse) {
			try {
				lhview = new LighthouseView();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.err.println("Error: Unknown host.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error: IO Exception.");
			}
		}
		
		Player p1 = new Player();
		game = new Game(p1);
		
		view.setModel(game);
		if(lhview != null) { lhview.setModel(game); }
		
		// set up user input (temp solution)	
		input = new UserInput((UserInputReceiver) view);
		
		lastFrame = System.nanoTime();
		
		// start main loop
		main = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	update(now);
            }
        };
        
        main.start();
        
        //update(System.nanoTime());
        
        System.out.println("Game started.");
        System.out.println("Press [F11] to go fullscreen.");
	}
	
	/**
	 * updates model & view
	 * @param now - time in ns               
	 */
	private synchronized void update(long now) {
		
		long diff = now - lastFrame;
		double delta = diff / DBL_SEC;
		
		handleUserInput();
		
		// update state
		game.update(delta);
		
		// update view(s)
		view.draw();
		if(lhview != null) { lhview.draw(); }
		
		this.lastFrame = now;
		
		// main.stop();
	}
	
	private synchronized void handleUserInput() {
		for(Player player : game.Players) {
			player.setUserInput(
					input.isPressed(player.keycode)
				);
		}
		
		if(!stage.isFullScreen() && input.fullscreenPressed()) {
			stage.setFullScreen(true);
		}
	}
	
}
