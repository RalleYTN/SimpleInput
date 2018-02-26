package de.ralleytn.simple.input.test;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.GamepadListener;

public class GamepadTest {

	public static void main(String[] args) {
		
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		Gamepad gamepad = gamepads.get(0);
		System.out.println("Button Count: " + gamepad.getButtonCount());
		gamepad.setControlCursorWithAnalogStick(true);
		gamepad.addGamepadListener(new GamepadListener() {
			
			@Override
			public void onAnalogStickPush(GamepadEvent event) {
				
				System.out.println(String.format("%s | %s | %f", event.getAnalogStick() == GamepadEvent.ANALOG_STICK_LEFT ? "Left" : "Right", event.getDirection().toString(), event.getIntensity()));
			}
			
			@Override
			public void onPOVRelease(GamepadEvent event) {
				
				System.out.println("Release: " + event.getDirection());
			}
			
			@Override
			public void onPOVPress(GamepadEvent event) {
				
				System.out.println("Press: " + event.getDirection());
			}
			
			@Override
			public void onButtonRelease(GamepadEvent event) {
				
				System.out.println("Release: " + event.getButton());
			}
			
			@Override
			public void onButtonPress(GamepadEvent event) {
				
				System.out.println("Press: " + event.getButton());
			}
		});
		//gamepad.startListening();
		
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent event) {
				
				gamepad.stopListening();
				DeviceManager.destroy();
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
