package test.physics;

import java.awt.geom.Rectangle2D;

import test.Window;
import test.entities.Projectile;
import test.entities.WorldBlock;

public class Position
{
	
	public static boolean pause = false;
	
	public static void position(WorldBlock block)
	{
		Rectangle2D.Double blockpos = block.getGraphics();
		if (pause == false)
		{
			blockpos.y = blockpos.y - (Window.globalYpos / block.getyDistance())
					+ block.getYSpeed();
			blockpos.x = blockpos.x - (Window.globalXpos / block.getxDistance())
					+ block.getXSpeed();
			if (block instanceof Projectile)
			{
				Projectile character = (Projectile) block;
				character.setLifeTime(character.getLifeTime() + 1);
			}
		}
	}
}
