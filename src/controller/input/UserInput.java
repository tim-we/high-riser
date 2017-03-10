package controller.input;

import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UserInput {
	
	private LinkedList<KeyCode> pressed = new LinkedList<KeyCode>();
	
	public UserInput(UserInputReceiver receiver) {
		receiver.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				handleKeyDown(e);
			}
		});
		
		receiver.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				handleKeyUp(e);
			}
		});
	}
	
	private synchronized void handleKeyDown(KeyEvent e) {
		final KeyCode code = e.getCode();
		
		if(!pressed.contains(code)) {
			pressed.add(code);
		}
	}
	
	private synchronized void handleKeyUp(KeyEvent e) {
		final KeyCode code = e.getCode();
		
		pressed.remove(code);
	}
	
	public void clear() {
		pressed.clear();
	}
	
	public boolean isPressed(KeyCode code) {
		return pressed.contains(code);
	}
	
	public boolean anyKeyPressed() {
		return pressed.size() > 0;
	}
	
	public boolean fullscreenPressed() {
		return pressed.contains( KeyCode.F11 );
	}
}
