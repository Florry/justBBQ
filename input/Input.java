package test.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import test.Window;
import test.physics.Gravity;
import test.physics.Position;

public class Input implements KeyListener
{
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			if (Window.isWalking == false)
			{
				Window.isWalking = true;
				Window.playerBlock.setDirection(1);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			if (Window.isWalking == false)
			{
				Window.isWalking = true;
				Window.playerBlock.setDirection(2);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			
			Window.playerBlock.getGraphics().x = (Window.globalWidth / 2) - Window.globalXpos;
			Window.playerBlock.getGraphics().y = 0 - Window.globalYpos;
		}
		if (e.getKeyCode() == KeyEvent.VK_V)
		{
			Window.createGravityBlock();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if (Window.playerBlock.onGround)
			{
				Window.playerBlock.setYSpeed(-(Gravity.gravity));
				Window.playerBlock.onGround = false;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Position.pause = !Position.pause;
			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			// Window.makeJumps();
		}
		if (e.getKeyCode() == KeyEvent.VK_C)
		{
			Window.playerBlock.beginFire();
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
		{
			Window.isWalking = false;
			Window.playerBlock.setXSpeed(0);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}