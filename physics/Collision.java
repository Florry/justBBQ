package test.physics;

import test.entities.WorldBlock;

public class Collision
{
	public static final int COLLISION_TOP = 1;
	public static final int COLLISION_BOTTOM = 2;
	public static final int COLLISION_RIGHT = 3;
	public static final int COLLISION_LEFT = 4;

	public static boolean collide(WorldBlock collider, WorldBlock collideWith)
	{
		if (collideWith.hasCollision())
		{
			if (collider.getBottomBounds().intersects(collideWith.getTopBounds()))
			{
				collideWith.onTouch(COLLISION_TOP, collider);
				return true;
			}
			if (collider.getTopBounds().intersects(collideWith.getBottomBounds()))
			{
				collideWith.onTouch(COLLISION_BOTTOM, collider);
				return true;
			}
			if (collider.getRightBounds().intersects(collideWith.getLeftBounds()))
			{
				collideWith.onTouch(COLLISION_LEFT, collider);
				return true;
			}
			if (collider.getLeftBounds().intersects(collideWith.getRightBounds()))
			{
				collideWith.onTouch(COLLISION_RIGHT, collider);
				return true;
			}
		}
		return false;
	}

}
