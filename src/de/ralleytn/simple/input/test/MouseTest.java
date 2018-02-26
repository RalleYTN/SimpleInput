package de.ralleytn.simple.input.test;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Mouse;
import de.ralleytn.simple.input.MouseEvent;
import de.ralleytn.simple.input.MouseListener;

public class MouseTest {

	public static void main(String[] args) {
		
		DeviceManager.create();
		List<Mouse> mice = DeviceManager.getMice();
		Mouse mouse = mice.get(0);
		mouse.addMouseListener(new MouseListener() {
			
			@Override
			public void onScroll(MouseEvent event) {
				
				System.out.println(event.getDeltaZ());
			}
			
			@Override
			public void onRelease(MouseEvent event) {
				
				System.out.println(event.getButton());
			}
			
			@Override
			public void onMove(MouseEvent event) {
				
				System.out.println(event.getX() + " x " + event.getY());
			}
			
			@Override
			public void onDrag(MouseEvent event) {
				
				System.out.println(event.getButton());
			}
			
			@Override
			public void onClick(MouseEvent event) {
				
				System.out.println(event.getButton());
			}
		});
		mouse.startListening();
		
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent event) {
				
				mouse.stopListening();
				DeviceManager.destroy();
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
