# Description

This library provides simple input listeners for keyboard, mouse and gamepad that work outside of AWT.
It wraps JInput 2.0.7 so writing custom listeners is also possible.


# Example

## Mouse

### All Registered Mice

```java
DeviceManager.create();
DeviceManager.addMouseListener(new MouseListener() {

	@Override
	public void onScroll(MouseEvent event) {
		
		System.out.println("Scrolled: " + (event.getDeltaZ() > 0.0F ? "Up" : "Down"));
	}
	
	@Override
	public void onRelease(MouseEvent event) {
		
		if(event.getButton() == MouseEvent.BUTTON_LEFT) {
		
			System.out.println("Left mouse button was released");
		}
	}
	
	@Override
	public void onMove(MouseEvent event) {
		
		System.out.println(event.getX() + " x " + event.getY());
	}
	
	@Override public void onDrag(MouseEvent event) {}
	@Override public void onClick(MouseEvent event) {}
});

for(Mouse[] mouse : DeviceManager.getMice()) {

	mouse.startListening();
}

// YOUR CODE

for(Mouse[] mouse : DeviceManager.getMice()) {

	mouse.stopListening();
}

DeviceManager.destroy();
```

### A Single Mouse
```java
DeviceManager.create();
DeviceManager.destroy();
```

# Requirements

Java 8 or higher