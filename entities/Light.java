package test.entities;

import java.awt.Color;
import java.util.UUID;

public class Light
{
	private double range;
	public double x;
	public double y;
	private double xSpeed;
	private double ySpeed;
	private double fallOff;
	private Color color;
	private double intensity;
	private String id;
	private boolean isMoving;
	
	public Light()
	{
		UUID uuid = UUID.randomUUID();
		this.range = 50;
		this.x = 0;
		this.y = 0;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.fallOff = 1;
		this.color = new Color(255, 255, 255);
		this.setIntensity(2);
		this.id = uuid.toString();
		this.setMoving(false);
	}
	
	public double getRange()
	{
		return range;
	}
	
	public void setRange(double range)
	{
		this.range = range;
	}
	
	public double getXSpeed()
	{
		return xSpeed;
	}
	
	public void setxSpeed(double xSpeed)
	{
		this.xSpeed = xSpeed;
	}
	
	public double getYSpeed()
	{
		return ySpeed;
	}
	
	public void setySpeed(double ySpeed)
	{
		this.ySpeed = ySpeed;
	}
	
	public double getFallOff()
	{
		return fallOff;
	}
	
	public void setFallOff(double fallOff)
	{
		this.fallOff = fallOff;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public double getIntensity()
	{
		return intensity;
	}
	
	public void setIntensity(double intensity)
	{
		this.intensity = intensity;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Light)
		{
			Light otherLight = (Light) obj;
			if (this.getId().equals(otherLight.getId()))
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
}
