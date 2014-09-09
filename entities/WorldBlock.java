package test.entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import test.physics.Collision;

public class WorldBlock
{
	private Rectangle2D.Double graphics;
	private Color color;
	private boolean hasImage = false;
	private boolean tileImage = false;
	public BufferedImage image = null;
	private boolean renderBlock = true;
	private String id;
	private boolean hasCollision = true;
	private double xSpeed;
	private double ySpeed;
	private boolean collideWithWorld;
	private boolean hasGravity;
	private boolean moveable;
	private int distance;
	private int yDistance;
	private int xDistance;
	private int rot;
	public boolean onGround;
	public boolean hasCollidedThisTick;
	private double lightLevel = 1;
	private boolean emissive;
	private int collisionSize;
	private boolean lightRendered;
	
	private WorldBlock collidingBlock;
	
	public WorldBlock(Double x, Double y, Double w, Double h)
	{
		UUID id = UUID.randomUUID();
		this.graphics = new Rectangle2D.Double(x, y, w, h);
		this.id = id.toString();
		this.xSpeed = 0;
		this.setCollideWithWorld(true);
		this.hasGravity = false;
		this.onGround = false;
		this.renderBlock = true;
		this.xSpeed = 0.00;
		this.ySpeed = 0.00;
		this.moveable = false;
		this.setCollidingBlock(null);
		this.hasCollidedThisTick = false;
		this.setDistance(1);
		this.setxDistance(1);
		this.setyDistance(1);
		this.setRot(0);
		this.setEmissive(false);
		this.setCollisionSize(8);
	}
	
	public void onTouch(int direction, WorldBlock collideWith)
	{
		switch (direction)
		{
			case (Collision.COLLISION_TOP):
				collideWith.setYSpeed(0);
				collideWith.setXSpeed(this.getXSpeed());
				collideWith.getGraphics().y = this.getGraphics().getY()
						- collideWith.getGraphics().height;
				this.setHasCollidedThisTick(true);
				collideWith.onGround = true;
				break;
			
			case (Collision.COLLISION_BOTTOM):
				collideWith.setYSpeed(-collideWith.getYSpeed());
				collideWith.getGraphics().y = this.getGraphics().getY() + this.getGraphics().height;
				this.setHasCollidedThisTick(true);
				break;
			
			case (Collision.COLLISION_LEFT):
				collideWith.setXSpeed(0);
				collideWith.getGraphics().x = this.getGraphics().getX()
						- collideWith.getGraphics().width;
				this.setHasCollidedThisTick(true);
				break;
			
			case (Collision.COLLISION_RIGHT):
				collideWith.setXSpeed(0);
				collideWith.getGraphics().x = this.getGraphics().getX()
						+ this.getGraphics().getWidth();
				this.setHasCollidedThisTick(true);
				break;
		}
		
	}
	
	public Rectangle getTopBounds()
	{
		return new Rectangle((int) getGraphics().x, (int) getGraphics().y,
				(int) getGraphics().width, getCollisionSize());
	}
	
	public Rectangle getBottomBounds()
	{
		return new Rectangle(((int) getGraphics().x),
				(int) (getGraphics().y + (getGraphics().height - getCollisionSize())),
				((int) getGraphics().width), getCollisionSize());
	}
	
	public Rectangle getRightBounds()
	{
		return new Rectangle((int) (getGraphics().x + (getGraphics().width - getCollisionSize())),
				(int) getGraphics().y, getCollisionSize(), (int) getGraphics().height);
	}
	
	public Rectangle getLeftBounds()
	{
		return new Rectangle((int) getGraphics().x, (int) getGraphics().y, getCollisionSize(),
				(int) getGraphics().height);
	}
	
	public void setColor(int r, int g, int b)
	{
		this.color = new Color(r, g, b);
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Rectangle2D.Double getGraphics()
	{
		return graphics;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setImage(String source, boolean renderBlock)
	{
		this.hasImage = true;
		this.renderBlock = renderBlock;
		try
		{
			this.image = ImageIO.read(new File(source));
		} catch (IOException e)
		{
			System.out.println(source + " could not be read");
		}
	}
	
	public void setImage(BufferedImage source, boolean renderBlock)
	{
		this.hasImage = true;
		this.renderBlock = renderBlock;
		this.image = source;
	}
	
	public void setImage(BufferedImage source)
	{
		this.hasImage = true;
		this.renderBlock = true;
		this.image = source;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean hasImage()
	{
		return hasImage;
	}
	
	public void setImage(String source)
	{
		this.hasImage = true;
		try
		{
			this.image = ImageIO.read(new File(source));
		} catch (IOException e)
		{
			System.out.println(source + " could not be read");
		}
	}
	
	public void removeImage()
	{
		this.hasImage = false;
		this.image = null;
	}
	
	public boolean renderBlock()
	{
		return renderBlock;
	}
	
	public void doRenderBlock(boolean render)
	{
		this.renderBlock = render;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setXSpeed(double speed)
	{
		this.xSpeed = speed;
	}
	
	public double getXSpeed()
	{
		return xSpeed;
	}
	
	public boolean isCollideWithWorld()
	{
		return collideWithWorld;
	}
	
	public void setCollideWithWorld(boolean collideWithWorld)
	{
		this.collideWithWorld = collideWithWorld;
	}
	
	public void setGravity(boolean gravity)
	{
		this.hasGravity = gravity;
	}
	
	public boolean hasGravity()
	{
		return hasGravity;
	}
	
	public double getYSpeed()
	{
		return ySpeed;
	}
	
	public void setYSpeed(double ySpeed)
	{
		this.ySpeed = ySpeed;
	}
	
	public WorldBlock getCollidingBlock()
	{
		return collidingBlock;
	}
	
	public void setCollidingBlock(WorldBlock collidingBlock)
	{
		this.collidingBlock = collidingBlock;
	}
	
	public boolean isMoveable()
	{
		return moveable;
	}
	
	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}
	
	public boolean hasCollidedThisTick()
	{
		return hasCollidedThisTick;
	}
	
	public void setHasCollidedThisTick(boolean hasCollidedThisTick)
	{
		this.hasCollidedThisTick = hasCollidedThisTick;
	}
	
	public boolean tileImage()
	{
		return tileImage;
	}
	
	public void setTileImage(boolean tile)
	{
		this.tileImage = tile;
	}
	
	public int getDistance()
	{
		return distance;
	}
	
	public void setDistance(int distance)
	{
		this.distance = distance;
		this.setxDistance(distance);
		this.setyDistance(distance);
	}
	
	public boolean hasCollision()
	{
		return hasCollision;
	}
	
	public void setHasCollision(boolean hasCollision)
	{
		this.hasCollision = hasCollision;
	}
	
	public int getRot()
	{
		return rot;
	}
	
	public void setRot(int rot)
	{
		this.rot = rot;
	}
	
	public double getLightLevel()
	{
		return lightLevel;
	}
	
	public void setLightLevel(double lightLevel)
	{
		this.lightLevel = lightLevel;
	}
	
	public boolean isEmissive()
	{
		return emissive;
	}
	
	public void setEmissive(boolean emissive)
	{
		this.emissive = emissive;
	}
	
	public String toString()
	{
		return id;
		
	}
	
	public int getCollisionSize()
	{
		return collisionSize;
	}
	
	public void setCollisionSize(int collisionSize)
	{
		this.collisionSize = collisionSize;
	}
	
	public int getyDistance()
	{
		return yDistance;
	}
	
	public void setyDistance(int yDistance)
	{
		this.yDistance = yDistance;
	}
	
	public int getxDistance()
	{
		return xDistance;
	}
	
	public void setxDistance(int xDistance)
	{
		this.xDistance = xDistance;
	}
	
	public boolean lightIsRendered()
	{
		return lightRendered;
	}
	
	public void setLightRendered(boolean lightRendered)
	{
		this.lightRendered = lightRendered;
	}
	
}
