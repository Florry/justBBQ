package test.physics;

import test.entities.WorldBlock;

public class Gravity
{
	public static double gravity = 15;
	
	public static void gravity(WorldBlock entity)
	{
		if (entity.getYSpeed() < gravity)
		{
			entity.setYSpeed(entity.getYSpeed() + 1);
		} else
		{
			entity.setYSpeed(gravity);
		}
	}
}
