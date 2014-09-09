package test.entities.blocks;

import test.entities.WorldBlock;
import test.physics.Collision;

public class MovingPlatform_right extends WorldBlock
{
	
	public MovingPlatform_right(Double x, Double y, Double w, Double h)
	{
		super(x, y, w, h);
	}
	
	public void onTouch(int direction, WorldBlock collideWith)
	{
		if (direction == Collision.COLLISION_TOP)
		{
			this.setXSpeed(5);
			collideWith.setYSpeed(0);
			collideWith.setXSpeed(this.getXSpeed());
			collideWith.getGraphics().y = this.getGraphics().getY()
					- collideWith.getGraphics().height;
			this.setHasCollidedThisTick(true);
			collideWith.onGround = true;
		} else
		{
			super.onTouch(direction, collideWith);
		}
	}
	
}
