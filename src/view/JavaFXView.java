package view;

import controller.Config;
import controller.input.UserInputReceiver;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
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
	
	private Color caveColor = Color.BLACK;
	
	protected double VIEW_WIDTH = 500;
	protected double VIEW_HEIGHT = 540;
	private double Ratio = VIEW_WIDTH / VIEW_HEIGHT;
	private double WorldScale = 1d;
	private double PixelRatio = 1d;
	//private boolean resized = false;
	
	public static final int BORDER = 10;
	
	private final Affine id; // identity
	
	private double PLAYER_RADIUS = 0.02;
	private double TAIL_WIDTH = 0.015;
	private double gapFillWidth = 0.002;
	
	public JavaFXView() {
		canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
        GraphicsContext ctx = canvas.getGraphicsContext2D();        
		this.ctx = ctx;
		
		setSize(28,14);
		//setPixelRatio(392d/364d);
		setPixelRatio(2d);
		
		setValues(0.05, 0.042, 0.03);
		//setValues(0, 0.045, 0.03);
		
		setCaveColor(new Color(0, 0, 0.15, 1));
		
		this.id = ctx.getTransform();
	}
	
	public JavaFXView(Stage stage) {	
		
		setSize(500, 540);
		
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
	
	/*private void applyResize() {
		VIEW_WIDTH = scene.getWidth();
		VIEW_HEIGHT = scene.getHeight();
		
		canvas.setWidth(VIEW_WIDTH);
		canvas.setHeight(VIEW_HEIGHT);
		
		resized = false;
	}*/
	
	protected void render() {
		assert(this.Model != null);
		
		ctx.setTransform(this.id);
		
		// draw background (clear)
		ctx.setFill(new Color(0,1,0,1));
		ctx.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
		
		// set up affine transform 
		setTransform();
		
		for(MapSegment ms : Model.Map.data) {
			drawMapSegment(ms);
		}
        
		for(Player p : Model.Players) {
			drawPlayer(p);
		}
		
		boolean assertionsEnabled = false;
		assert(assertionsEnabled = true);
		if(assertionsEnabled) {
			drawUnitBox();
		}
	}
	
	private void applyPixelRatio() {
		Affine t = ctx.getTransform();
			t.setMxx(t.getMxx() * PixelRatio);
			t.setMxy(t.getMxy() * PixelRatio);
			//t.setTx(t.getTx() * PixelRatio);
		ctx.setTransform(t);
	}
	
	public void draw() {
		//if(this.resized) { applyResize(); }
		
		render();
	}
	
	private void drawMapSegment(MapSegment ms) {
		assert(ms != null);
		
		Vector a = toScreen(Camera.toViewport(ms.bottomLeft, Ratio));
		Vector b = toScreen(Camera.toViewport(ms.bottomRight, Ratio));
		Vector c = toScreen(Camera.toViewport(ms.topRight, Ratio));
		Vector d = toScreen(Camera.toViewport(ms.topLeft, Ratio));
		
		ctx.setFill(caveColor);
		
		ctx.beginPath();
		ctx.moveTo(a.x, a.y);
		ctx.lineTo(b.x, b.y);
		ctx.lineTo(c.x, c.y);
		ctx.lineTo(d.x, d.y);
		
		ctx.closePath();
		
		ctx.fill();
		
		if(Config.fillGaps) {
			ctx.setStroke(caveColor);
			ctx.setStroke(ctx.getFill());
			ctx.setLineWidth(gapFillWidth);
			ctx.stroke();
		}
	}
	
	private void drawPlayer(Player p) {
		assert(p != null);
		
		Vector pos = toScreen( Camera.toViewport(p.Position, Ratio) );
		
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
		ctx.setLineWidth(TAIL_WIDTH);
		ctx.beginPath();
		
		ctx.moveTo(start.x, start.y);
		
		for(Vector point : tail.points) {
			Vector x = toScreen( Camera.toViewport(point, Ratio) );
			
			ctx.lineTo(x.x, x.y);
		}
		
		ctx.stroke();
	}

	public void setModel(Game model) {
		assert(model != null);
		
		this.Model = model;
		this.Camera = model.Camera;
	}
	
	public void setSize(double width, double height) {
		VIEW_WIDTH = width;
		VIEW_HEIGHT = height;
		
		WorldScale = height;
		Ratio = VIEW_WIDTH / VIEW_HEIGHT;
	}
	
	public void setPixelRatio(double r) {
		assert(r != 0);
		this.PixelRatio = r;
	}
	
	public void setValues(double radius, double tail, double gaps) {
		assert(radius >= 0 && tail >= 0 && gaps >= 0);
		
		PLAYER_RADIUS = radius;
		TAIL_WIDTH = tail;
		gapFillWidth = gaps;
	}
	
	public void setCaveColor(Color clr) {
		assert(clr != null);
		
		this.caveColor = clr;
	}
	
	private void drawUnitBox() {
		assert(WorldScale > 0 && ctx != null);
		
		ctx.setStroke(Color.DEEPPINK);
		
		Vector a = toScreen( new Vector(0,0) );
		Vector b = toScreen( new Vector(1,0) );
		Vector c = toScreen( new Vector(1,1) );
		Vector d = toScreen( new Vector(0,1) );
		
		ctx.setLineWidth(0.005);
		ctx.beginPath();
		
		ctx.moveTo(a.x, a.y);
		ctx.lineTo(b.x, b.y);
		ctx.lineTo(c.x, c.y);
		ctx.lineTo(d.x, d.y);
		ctx.closePath();
		
		ctx.stroke();
	}
	
	private void setTransform() {
		ctx.translate(0.5 * VIEW_WIDTH, 0.5 * VIEW_HEIGHT);
		ctx.rotate(Model.Rotation);
		//ctx.scale(PixelRatio, 1d);
		
		// because scale does not work as expected :/
		applyPixelRatio();
		
		ctx.translate(-0.5 * VIEW_WIDTH, -0.5 * VIEW_HEIGHT);
		ctx.scale(WorldScale, WorldScale);
		//ctx.translate(-0.5 * WorldSize * PixelRatio, -0.5 * WorldSize);
	}
}
