package test.entities;

import test.Window;

public class Player extends Character
{
	private int direction = 0;
	
	public Player(String src)
	{
		super(src);
	}
	
	public Player()
	{
		super(0.00, 0.00, 24.00, 32.00, "src/idle01.png");
	}
	
	public void shootWeapon()
	{	
		
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	public void beginFire()
	{
		if (this.getHealth() > 0)
		{
			Projectile proj = new Projectile(this.getGraphics().x, this.getGraphics().y, 10.00,
					10.00);
			proj.setEmissive(true);
			if (this.getDirection() == 1)
			{
				proj.setXSpeed(-proj.getSpeed());
			} else
			{
				proj.setXSpeed(proj.getSpeed());
			}
			proj.setInstigator(this);
			Window.projectiles.add(proj);
		}
	}
	
}
