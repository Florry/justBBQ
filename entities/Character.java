package test.entities;

import test.Window;
import test.functions.Range;

public class Character extends WorldBlock
{
	private static double height = 33.00;
	private static double width = 18.00;
	private int health = 100;
	private int range = 200;
	Projectile proj = null;
	private int fireInterval = 350;
	public int DIRECTION_LEFT = 1;
	public int DIRECTION_RIGHT = 2;
	public Long lastShot = 2000L;
	
	public Character(String src)
	{
		super(0.00, 0.00, width, height);
		this.setImage(src, false);
		this.setGravity(true);
		this.setCollideWithWorld(true);
		this.setHasCollision(true);
	}
	
	public Character()
	{
		super(0.00, 0.00, width, height);
		this.setImage("src/assets/blocks/misc/oldGuy01.png", false);
		this.setGravity(true);
		this.setCollideWithWorld(true);
		this.setHasCollision(true);
	}
	
	public Character(double x, double y, double w, double h, String src)
	{
		super(x, y, w, h);
		this.setImage(src, false);
		this.setGravity(true);
		this.setCollideWithWorld(true);
		this.setHasCollision(true);
	}
	
	public void setLocation(double x, double y)
	{
		this.getGraphics().x = x;
		this.getGraphics().y = y;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public void takeDamage(int damage, int direction, Projectile instigator)
	{
		if (direction == DIRECTION_LEFT)
		{
			this.setXSpeed(-instigator.getKnockBack());
		} else
		{
			this.setXSpeed(instigator.getKnockBack());
		}
		this.health -= damage;
	}
	
	public void ai(Player player)
	{
		if (this.getHealth() > 0)
		{
			if (Range.isInRange(player, this, range))
			{
				if (player.getGraphics().getCenterY() > this.getGraphics().y - 50
						&& player.getGraphics().getCenterY() < this.getGraphics().y + 50)
				{
					
					// if (!Window.projectiles.contains(proj))
					// {
					if (System.currentTimeMillis() - lastShot > fireInterval)
					{
						lastShot = System.currentTimeMillis();
						int direction = 0;
						if (player.getGraphics().x > this.getGraphics().x)
						{
							direction = DIRECTION_RIGHT;
						} else
						{
							direction = DIRECTION_LEFT;
						}
						proj = new Projectile(this.getGraphics().x, this.getGraphics().y, 10.00,
								10.00);
						proj.setEmissive(true);
						if (direction == DIRECTION_LEFT)
						{
							proj.setXSpeed(-proj.getSpeed());
						} else
						{
							proj.setXSpeed(proj.getSpeed());
						}
						proj.setInstigator(this);
						Window.projectiles.add(proj);
						// }
					}
				}
			}
		}
	}
}
