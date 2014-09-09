package test.input;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import test.Window;
import test.entities.Light;

public class MouseInput implements MouseListener
{
	private int blockNumb = 1;
	
	@Override
	public void mouseClicked(MouseEvent event)
	{	
		
	}
	
	@Override
	public void mouseEntered(MouseEvent event)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent event)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent event)
	{
		if (event.getButton() == MouseEvent.BUTTON1)
		{
			
			// Window.createBlockAtMouse(event.getX(), event.getY());
			Window.mouse1Down = true;
			Light light = new Light();
			light.setRange(100);
			light.x = event.getX();
			light.y = event.getY();
			
			light.setColor(new Color(Window.colors[(int) Math.round(Math.random())],
					Window.colors[(int) Math.round(Math.random())], Window.colors[(int) Math
							.round(Math.random())]));
			Window.lights.add(light);
		}
		if (event.getButton() == MouseEvent.BUTTON3)
		{
			Window.createGravityBlockAtMouse(event.getX(), event.getY());
		}
		if (event.getButton() == MouseEvent.BUTTON2)
		{
			if (blockNumb == 1)
			{
				Window.mouseBlock = "src/assets/blocks/dirt01MossyNoTop.png";
				blockNumb += 1;
			} else if (blockNumb == 2)
			{
				Window.mouseBlock = "src/assets/blocks/dirt01.png";
				blockNumb += 1;
			} else if (blockNumb == 3)
			{
				Window.mouseBlock = "src/assets/blocks/grass01.png";
				blockNumb = 1;
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent event)
	{
		if (event.getButton() == MouseEvent.BUTTON1)
		{
			// Window.createBlockAtMouse(event.getX(), event.getY());
			Window.mouse1Down = false;
		}
	}
	
}
