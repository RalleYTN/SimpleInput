[![Build Status](https://travis-ci.org/RalleYTN/SimpleInput.svg?branch=master)](https://travis-ci.org/RalleYTN/SimpleInput)
[![Coverage Status](https://coveralls.io/repos/github/RalleYTN/SimpleInput/badge.svg?branch=master)](https://coveralls.io/github/RalleYTN/SimpleInput?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a133cfa2a84d47b3b6f1ab7b71995999)](https://www.codacy.com/app/ralph.niemitz/SimpleInput?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=RalleYTN/SimpleInput&amp;utm_campaign=Badge_Grade)

# Description

This library provides simple input listeners for keyboard, mouse and gamepad that work outside of AWT.
It wraps JInput 2.0.7 so writing custom listeners is also possible.

### Example code

#### Mouse

For all mice:

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

## Changelog

### Version 1.0.0

- Release

## License

```
MIT License

Copyright (c) 2018 Ralph Niemitz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Links

- [Download](https://github.com/RalleYTN/SimpleInput/releases)
- [Online Documentation](https://ralleytn.github.io/SimpleInput/)