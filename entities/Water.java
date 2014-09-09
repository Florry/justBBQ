package test.entities;

import java.awt.Color;

public class Water extends WorldBlock
{
	
	public Water(Double x, Double y, Double w, Double h)
	{
		super(x, y, w, h);
		this.setColor(new Color(0, 84, 137, 0));
		this.removeImage();
		this.setCollisionSize((int) this.getGraphics().getWidth());
	}
	
	@Override
	public void onTouch(int direction, WorldBlock collideWith)
	{
		
		if (collideWith instanceof Character)
		{
			Character collidingCharacter;
			collidingCharacter = (Character) collideWith;
			collidingCharacter.setHealth(0);
		}
		
	}
}
