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
import model.Camera;
import model.Game;
import model.Player;
import model.Vector;
import model.map.MapSegment;

public class JavaFXView implements View, UserInputReceiver {
	
	private Canvas canvas;
	private GraphicsContext ctx;
	private Scene scene;
	
	private Game Model;
	private Camera Camera;
	
	private double offsetX;
	private double offsetY;
	
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
        canvas = new Canvas(500, 540);
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
        //stage.setFullScreen(true);
	}
	
	public void setOnKeyPressed(EventHandler<KeyEvent> evh) {
		scene.setOnKeyPressed(evh);
	}
	
	public void setOnKeyReleased(EventHandler<KeyEvent> evh) {
		scene.setOnKeyReleased(evh);
		
		// WHY?!
	}
	
	public Vector toScreen(Vector p) {
		return new Vector(
				p.x - offsetX,
				1 - (p.y - offsetY)
			);
	}
	
	public void draw() {
		assert(this.Model != null);

		this.updateCamera();
		
		ctx.setFill(Color.GREEN);
		ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(MapSegment ms : Model.Map.data) {
			drawMapSegment(ms);
		}
        
	}
	
	private void drawMapSegment(MapSegment ms) {
		assert(ms != null);
		
		Vector a = toScreen(new Vector(ms.xLowLeft, ms.yLow));
		Vector b = toScreen(new Vector(ms.xLowRight, ms.yLow));
		Vector c = toScreen(new Vector(ms.xHighRight, ms.yHigh));
		Vector d = toScreen(new Vector(ms.xHighLeft, ms.yHigh));
		
		ctx.setFill(Color.BLACK);
		
		ctx.beginPath();
		ctx.moveTo(a.x, a.y);
		ctx.lineTo(b.x, b.y);
		ctx.lineTo(c.x, c.y);
		ctx.lineTo(d.x, d.y);
		
		ctx.closePath();
		
		ctx.fill();
	}
	
	private void drawPlayer(Player p) {
		
	}

	public void setModel(Game model) {
		this.Model = model;
		this.Camera = model.Camera;
	}
	
	public void updateCamera() {
		assert(this.Camera != null);
		
		this.offsetX = Camera.leftBound();
		this.offsetY = Camera.upperBound();
	}

}
