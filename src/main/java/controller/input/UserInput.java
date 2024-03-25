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
	
	public synchronized void clear() {
		pressed.clear();
	}
	
	public synchronized boolean isPressed(KeyCode code) {
		return pressed.contains(code);
	}
	
	public synchronized boolean anyKeyPressed() {
		if(pressed.size() == 1 && pressed.getFirst() == KeyCode.F11) { return false; }
		return pressed.size() > 0;
	}
	
	public synchronized boolean fullscreenPressed() {
		return pressed.contains( KeyCode.F11 );
	}
}
