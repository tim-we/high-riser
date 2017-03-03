package view;

import controller.Config;
import controller.input.UserInputReceiver;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import model.Vector;

public class JavaFXView implements View, UserInputReceiver {
	
	private Canvas canvas;
	private GraphicsContext ctx;
	private Scene scene;
	
	public static final int VIEW_WIDTH = 420;
	public static final int VIEW_HEIGHT = 600;
	
	public static final int BORDER = 10;
	
	public JavaFXView(Stage stage) {
		super();		
		
		stage.setTitle(Config.TITLE);
		
		StackPane root = new StackPane();
		
		scene = new Scene(root, VIEW_WIDTH, VIEW_HEIGHT, Color.BLACK);
		
        stage.setScene(scene);
        
        // canvas
        Vector size = getSize();
        canvas = new Canvas(size.x, size.y);
        GraphicsContext ctx = canvas.getGraphicsContext2D();        
		this.ctx = ctx;              
        root.getChildren().add(canvas);
		
        // fullscreen button
//        Button btn = new Button();
//		btn.setText("Fullscreen");
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//			      stage.setFullScreen(true);
//			}
//		});   
//  
//		root.getChildren().add(btn);
        
        stage.show();
        stage.setFullScreen(true);
	}
	
	public void setOnKeyPressed(EventHandler<KeyEvent> evh) {
		scene.setOnKeyPressed(evh);
	}
	
	public void setOnKeyReleased(EventHandler<KeyEvent> evh) {
		scene.setOnKeyReleased(evh);
	}
	
	public void draw(Game model) {
		
		ctx.setFill(Color.BLACK);
		ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int y=0; y<HEIGHT; y++) {
			for(int x=0; x<WIDTH; x++) {
				final int xPos = BORDER + x * (WINDOW_WIDTH + WINDOW_X_OFFSET);
				final int yPos = BORDER + y * (WINDOW_HEIGHT + WINDOW_Y_OFFSET);
				
				ctx.setFill(pixels[y][x]);
				ctx.fillRect(xPos, yPos, WINDOW_WIDTH, WINDOW_HEIGHT);
			}
		}
        
	}

}
