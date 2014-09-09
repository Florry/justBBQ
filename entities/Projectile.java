package test.entities;

import java.awt.Color;

import test.Window;

public class Projectile extends WorldBlock
{
	private int damage = 35;
	private Character instigator;
	private double speed = 10;
	private double lifeTime = 0;
	private double maxLife = 60;
	private Light projLight;
	private int knockBack = 2;
	
	public Projectile(Double x, Double y, Double w, Double h)
	{
		super(x, y, w, h);
		this.setColor(255, 150, 0);
		this.projLight = new Light();
		this.projLight.x = this.getGraphics().getX();
		this.projLight.y = this.getGraphics().getY();
		this.projLight.setRange(48);
		this.projLight.setFallOff(1);
		this.projLight.setxSpeed(speed);
		this.projLight.setMoving(true);
		this.projLight.setColor(new Color(255, 255, 0));
		Window.lights.add(this.projLight);
	}
	
	public Light getProjLight()
	{
		return projLight;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public Character getInstigator()
	{
		return instigator;
	}
	
	public void setInstigator(Character instigator)
	{
		this.instigator = instigator;
	}
	
	@Override
	public void setXSpeed(double speed)
	{
		projLight.setxSpeed(speed);
		super.setXSpeed(speed);
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public double getLifeTime()
	{
		return lifeTime;
	}
	
	public void setLifeTime(double lifeTime)
	{
		this.lifeTime = lifeTime;
	}
	
	public double getMaxLife()
	{
		return maxLife;
	}
	
	public void setMaxLife(double maxLife)
	{
		this.maxLife = maxLife;
	}
	
	public int getKnockBack()
	{
		return knockBack;
	}
	
	public void setKnockBack(int knockBack)
	{
		this.knockBack = knockBack;
	}
	
}
