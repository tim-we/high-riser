package view;

import controller.Config;
import controller.input.UserInputReceiver;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFXView extends view.View implements UserInputReceiver {
	
	private Canvas canvas;
	private Scene scene;
	
	//private boolean resized = false;
	
	public JavaFXView(Stage stage) {	
		super(500, 540, 1d);
		
		stage.setTitle(Config.TITLE);
		
		StackPane root = new StackPane();
		
		scene = new Scene(root, VIEW_WIDTH, VIEW_HEIGHT, Color.BLACK);
		
        stage.setScene(scene);
        //setUpResizeListener(scene);
        
        // canvas
        canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);           
        root.getChildren().add(canvas);
        
        setCTX(canvas);
		
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
	
	/*private void applyResize() {
		VIEW_WIDTH = scene.getWidth();
		VIEW_HEIGHT = scene.getHeight();
		
		canvas.setWidth(VIEW_WIDTH);
		canvas.setHeight(VIEW_HEIGHT);
		
		resized = false;
	}*/
	
	public void draw() {
		//if(this.resized) { applyResize(); }
		
		render();
	}
	
	
}
