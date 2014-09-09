package test.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import test.Window;
import test.entities.Light;

public class MouseMovement implements MouseMotionListener
{
	
	@Override
	public void mouseDragged(MouseEvent event)
	{
		// TODO Auto-generated method stub
		if (Window.mouse1Down == true)
		{
			// Window.createBlockAtMouse(event.getX(), event.getY());
			Light light = new Light();
			light.setRange(100);
			light.x = event.getX();
			light.y = event.getY();
			Window.lights.add(light);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent event)
	{
		Window.moveLight(event.getX(), event.getY());
	}
	
}
