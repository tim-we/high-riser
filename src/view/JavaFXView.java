package view;

import controller.Config;
import controller.input.UserInputReceiver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import model.Camera;
import model.Game;
import model.Player;
import model.Tail;
import model.Vector;
import model.map.MapSegment;

public class JavaFXView implements View, UserInputReceiver {
	
	protected Canvas canvas;
	private GraphicsContext ctx;
	private Scene scene;
	
	private Game Model;
	private Camera Camera;
	
	protected double VIEW_WIDTH = 500;
	protected double VIEW_HEIGHT = 540;
	private boolean resized = false;
	
	public static final int BORDER = 10;
	
	private final Affine id;
	
	private static final double PLAYER_RADIUS = 0.02;
	
	public JavaFXView() {
		canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
        GraphicsContext ctx = canvas.getGraphicsContext2D();        
		this.ctx = ctx;
		
		this.id = ctx.getTransform();
	}
	
	public JavaFXView(Stage stage) {	
		
		stage.setTitle(Config.TITLE);
		
		StackPane root = new StackPane();
		
		scene = new Scene(root, VIEW_WIDTH, VIEW_HEIGHT, Color.BLACK);
		
        stage.setScene(scene);
        //setUpResizeListener(scene);
        
        // canvas
        canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
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
        
        this.id = ctx.getTransform();
	}
	
	public void setOnKeyPressed(EventHandler<KeyEvent> evh) {
		scene.setOnKeyPressed(evh);
	}
	
	public void setOnKeyReleased(EventHandler<KeyEvent> evh) {
		scene.setOnKeyReleased(evh);
	}
	
	/*private void setUpResizeListener(Scene scene) {
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        resized = true;
		    }
		});
		
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        resized = true;
		    }
		});
	}*/
	
	/**
	 * from viewport to screen
	 * @param p
	 * @return
	 */
	public Vector toScreen(Vector p) {
		return new Vector(
				p.x,
				1 - p.y
			);
	}
	
	private void applyResize() {
		VIEW_WIDTH = scene.getWidth();
		VIEW_HEIGHT = scene.getHeight();
		
		canvas.setWidth(VIEW_WIDTH);
		canvas.setHeight(VIEW_HEIGHT);
		
		resized = false;
	}
	
	protected void render() {
		assert(this.Model != null);
		
		ctx.setTransform(this.id);
		
		// draw background (clear)
		ctx.setFill(new Color(0,1,0,1));
		ctx.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
		
		// set up affine transform
		ctx.translate(0.5 * canvas.getWidth(), 0.5 * canvas.getHeight());
		ctx.rotate(Model.Rotation);
		ctx.translate(-0.5 * canvas.getWidth(), -0.5 * canvas.getHeight());
		ctx.scale(canvas.getWidth(), canvas.getHeight());
		
		for(MapSegment ms : Model.Map.data) {
			drawMapSegment(ms);
		}
        
		for(Player p : Model.Players) {
			drawPlayer(p);
		}
	}
	
	public void draw() {
		//if(this.resized) { applyResize(); }
		
		render();
	}
	
	private void drawMapSegment(MapSegment ms) {
		assert(ms != null);
		
		Vector a = toScreen(Camera.toViewport(ms.bottomLeft));
		Vector b = toScreen(Camera.toViewport(ms.bottomRight));
		Vector c = toScreen(Camera.toViewport(ms.topRight));
		Vector d = toScreen(Camera.toViewport(ms.topLeft));
		
		//ctx.setFill(new Color(0, 0, 0.15, 1));
		ctx.setFill(Color.BLACK);
		
		ctx.beginPath();
		ctx.moveTo(a.x, a.y);
		ctx.lineTo(b.x, b.y);
		ctx.lineTo(c.x, c.y);
		ctx.lineTo(d.x, d.y);
		
		ctx.closePath();
		
		ctx.fill();
		
		if(Config.fillGaps) {
			ctx.setStroke(ctx.getFill());
			ctx.setLineWidth(0.002);
			ctx.stroke();
		}
	}
	
	private void drawPlayer(Player p) {
		assert(p != null);
		
		Vector pos = toScreen( Camera.toViewport(p.Position) );
		
		drawTail(pos, p.Tail);
		
		ctx.beginPath();
		ctx.setFill(p.getColor());
		
		ctx.arc(pos.x, pos.y, PLAYER_RADIUS, PLAYER_RADIUS, 0, 360);
		ctx.closePath();
		
		ctx.fill();
	}
	
	private void drawTail(Vector start, Tail tail) {
		assert(start != null && tail != null);
		
		ctx.setStroke(tail.Color);
		ctx.setLineWidth(0.01);
		ctx.beginPath();
		
		ctx.moveTo(start.x, start.y);
		
		for(Vector point : tail.points) {
			Vector x = toScreen( Camera.toViewport(point) );
			
			ctx.lineTo(x.x, x.y);
		}
		
		ctx.stroke();
	}

	public void setModel(Game model) {
		assert(model != null);
		
		this.Model = model;
		this.Camera = model.Camera;
	}

}
