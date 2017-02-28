package controller.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface UserInputReceiver {
	public void setOnKeyPressed(EventHandler<KeyEvent> evh);
	
	public void setOnKeyReleased(EventHandler<KeyEvent> evh);
}
