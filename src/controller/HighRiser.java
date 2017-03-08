package controller;

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

	private View view;
	private Game game;
	private AnimationTimer main;
	private UserInput input;
	
	@Override
	public void start(Stage primaryStage) {
		
		// set up view
		view = new JavaFXView(primaryStage);
		
		Player p1 = new Player();
		game = new Game(p1);
		
		view.setModel(game);
		
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
        
        //main.start();
        
        update(System.nanoTime());
	}
	
	/**
	 * updates model & view
	 * @param now - time in ns               
	 */
	private synchronized void update(long now) {
		
		long diff = now - lastFrame;
		double delta = diff / DBL_SEC;
		
		// update state
		game.update(delta);
		
		// update view(s)
		view.draw();
		
		this.lastFrame = now;
		
		// main.stop();
	}
	
}
