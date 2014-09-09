package test.functions;

import java.awt.geom.Rectangle2D;

import test.entities.Light;
import test.entities.WorldBlock;

public class Range
{
	public static boolean isInRange(double x, double y, Light light, double extra)
	{
		if (x + extra < light.x + light.getRange() && x + extra > light.x - light.getRange()
				&& y + extra < light.y + light.getRange() && y + extra > light.y - light.getRange())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public boolean isInRange(WorldBlock worldBlock, Light light)
	{
		Rectangle2D.Double block = worldBlock.getGraphics();
		
		if (block.x < light.x + light.getRange() && block.x > light.x - light.getRange()
				&& block.y < light.y + light.getRange() && block.y > light.y - light.getRange())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static boolean isInRange(WorldBlock worldBlock, WorldBlock worldBlock2, double range)
	{
		Rectangle2D.Double block = worldBlock.getGraphics();
		Rectangle2D.Double block2 = worldBlock2.getGraphics();
		
		if (block.x < block2.x + range && block.x > block2.x - range && block.y < block2.y + range
				&& block.y > block2.y - range)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static boolean isInRange(Light light1, Light light2, double range)
	{
		if (light1.x < light2.x + range && light1.x > light2.x - range
				&& light1.y < light2.y + range && light1.y > light2.y - range)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static boolean insideScreen(Light light, double extra, double width, double height)
	{
		if (light.y + extra > 0 && light.y - extra < height && light.x + extra > 0
				&& light.x - extra < width)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static boolean insideScreen(WorldBlock block, double width, double height)
	{
		Rectangle2D.Double graphics = block.getGraphics();
		
		if (graphics.getY() + graphics.getHeight() > 0 && graphics.getY() < height
				&& graphics.getX() + graphics.getWidth() > 0 && graphics.getX() < width)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static double getDistance(WorldBlock block, Light distanceTo)
	{
		double x1 = block.getGraphics().getCenterX();
		double x2 = distanceTo.x;
		double y1 = block.getGraphics().getCenterY();
		double y2 = distanceTo.y;
		
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y2 - y1), 2));
	}
	
	public static double getDistance(WorldBlock block, WorldBlock block2)
	{
		double x1 = block.getGraphics().getCenterX();
		double x2 = block2.getGraphics().x;
		double y1 = block.getGraphics().getCenterY();
		double y2 = block2.getGraphics().y;
		
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y2 - y1), 2));
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y2 - y1), 2));
	}
}
