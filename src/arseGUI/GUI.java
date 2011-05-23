package arseGUI;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

public abstract class GUI {
	public abstract Shell open(Display display);
	
	public void centerWindow(Shell shell, Display display){
		shell.pack();
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	}
}
