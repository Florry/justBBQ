package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.*;

import test.entities.Air;
import test.entities.Light;
import test.entities.Player;
import test.entities.Character;
import test.entities.Projectile;
import test.entities.Water;
import test.entities.WorldBlock;
import test.entities.blocks.MovingPlatform_right;
import test.functions.Clamp;
import test.functions.Range;
import test.input.Input;
import test.input.MouseInput;
import test.input.MouseMovement;
import test.physics.Collision;
import test.physics.Gravity;
import test.physics.LightPosition;
import test.physics.Position;
import test.render.Lighting;
import test.render.Stats;
import test.render.WaterReflections;

public class Window extends JPanel
{

	private static final long serialVersionUID = 20140128;
	private JFrame frame;
	public static boolean renderLighting = true;
	public static boolean renderPerBlockLighting = true;
	public static boolean waterReflections = true;
	public static boolean useOld = true;
	public static boolean godMode = true;
	public static int shadowDetail = 32;
	public static int scrnWidth;
	public static int scrnHeight;
	public static BufferedImage frameImg;

	public static boolean isWalking = false;
	public double speed = 5;
	public static double globalXpos;
	public static double globalYpos;
	public static double globalWind = 0.4;
	public static float globalWidth;
	public static int collW;
	public static int collH;
	public static Player playerBlock;
	public static Light movingPlatformLight;
	public static Light mouseLight;
	public static MovingPlatform_right movingPlatform;

	public static ArrayList<WorldBlock> world = new ArrayList<WorldBlock>();
	public static WorldBlock[][] worldGrid = new WorldBlock[1024][100];
	public static ArrayList<Character> entities = new ArrayList<Character>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Light> lights = new ArrayList<Light>();

	public static WorldBlock bg;
	public static WorldBlock bg2;
	public static int bgX;
	public static int bgY;

	public static int renderedImages;
	public static int renderedImages2;
	public static int renderedLights;
	public static int FPS = 0;
	public static String fpsString = "";

	public static String mouseBlock = "src/assets/blocks/grass01.png";
	public static boolean mouse1Down = false;

	public static boolean shadowsSetup;
	public static Color frameColor;

	List<BufferedImage> blocks = new ArrayList<BufferedImage>();

	public static int[] colors =
	{ 255, 0 };

	public Window()
	{
		super();

		for (int p = 0; p < 10; p++)
		{
			for (int i = 0; i < 700; i++)
			{
				int random = (int) (125 + (Math.random() * (255 - 125)));
				WorldBlock block = new WorldBlock(10.00 + (Math.tan(i * 200 + 2) + i * 16),
						(600.00 - (Math.tan(i * 20 + 2) + i / 2)) + (p * 16), 64.00, 64.00);
				int R = Clamp.clamp(random - p, 255, 0);
				int G = Clamp.clamp(random - p, 255, 0);
				int B = Clamp.clamp(random - p, 255, 0);
				block.setColor(R, G, B);
				block.setxDistance(2);

				block.setHasCollision(false);
				world.add(block);
			}
		}

		WorldBlock groundBlock;
		WorldBlock sideCollisionTest;

		WorldBlock block = new WorldBlock(100.00, 580.00, 25.00, 25.00);

		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);

		block.setColor(R, G, B);

		world.add(block);

		block = new WorldBlock(375.00, 520.00, 100.00, 100.00);

		R = (int) (Math.random() * 255);
		G = (int) (Math.random() * 255);
		B = (int) (Math.random() * 255);

		block.setColor(R, G, B);

		world.add(block);

		block = new WorldBlock(425.00, 600.00, 25.00, 25.00);

		R = (int) (Math.random() * 255);
		G = (int) (Math.random() * 255);
		B = (int) (Math.random() * 255);

		block.setColor(R, G, B);

		world.add(block);

		movingPlatform = new MovingPlatform_right(20.00, 550.00, 480.00, 48.00);
		movingPlatform.setColor(0, 0, 255);
		movingPlatform.setImage("src/assets/blocks/block01.png");
		movingPlatform.setTileImage(true);
		movingPlatform.setCollideWithWorld(false);
		movingPlatform.setMoveable(true);

		groundBlock = new WorldBlock(0.00, 620.00, 800.00, 200.00);
		groundBlock.setColor(new Color(0, 255, 0));
		groundBlock.setCollideWithWorld(false);

		sideCollisionTest = new WorldBlock(500.00, 300.00, 200.00, 100.00);
		sideCollisionTest.setColor(255, 0, 0);
		sideCollisionTest.setCollideWithWorld(true);
		sideCollisionTest.setMoveable(true);
		sideCollisionTest.setXSpeed(-0.5);
		sideCollisionTest.setYSpeed(-0.5);

		playerBlock = new Player();
		playerBlock.setGravity(true);

		bg = new WorldBlock(0.00, 0.00, 0.00, 0.00);
		bg.setImage("src/assets/background02.png", false);
		bg.doRenderBlock(false);
		bg.setDistance(5);

		bg2 = new WorldBlock(0.00, 0.00, 0.00, 0.00);
		bg2.setImage("src/assets/background01.png", false);
		bg2.doRenderBlock(false);
		bg2.setDistance(5);

		for (int i = 0; i < 100; i++)
		{
			Light tempLight = new Light();
			tempLight.setRange(500);
			tempLight.setFallOff(0.00);
			tempLight.x = i * 500;

			// y = 600
			tempLight.y = 600 - (i * 10);
			tempLight.setIntensity(40);
			int red = colors[(int) Math.round(Math.random())];
			int green = colors[(int) Math.round(Math.random())];
			int blue = colors[(int) Math.round(Math.random())];
			int color = 255;
			tempLight.setColor(new Color(red, green, blue));
			lights.add(tempLight);
		}
		movingPlatformLight = new Light();
		movingPlatformLight.setRange(500);
		movingPlatformLight.setIntensity(30);
		movingPlatformLight.setMoving(true);
		mouseLight = new Light();
		mouseLight.setRange(500);
		mouseLight.setFallOff(10);
		mouseLight.setIntensity(20);
		mouseLight.x = -500;
		mouseLight.y = -500;
		mouseLight.setColor(new Color(0, 64, 255));
		mouseLight.setMoving(true);

		world.add(movingPlatform);
		world.add(groundBlock);
		world.add(sideCollisionTest);
		world.add(bg);
		entities.add(playerBlock);
		world.add(bg);
		lights.add(movingPlatformLight);
		lights.add(mouseLight);

		BufferedImage grass = null;
		BufferedImage dirtMoss = null;
		BufferedImage dirt = null;
		BufferedImage bricks = null;
		try
		{
			grass = ImageIO.read(new File("src/assets/blocks/grass01.png"));
			dirtMoss = ImageIO.read(new File("src/assets/blocks/dirt01MossyNoTop.png"));
			dirt = ImageIO.read(new File("src/assets/blocks/dirt01.png"));
			bricks = ImageIO.read(new File("src/assets/blocks/block01.png"));
		} catch (IOException e)
		{

		}

		for (int p = 0; p < 10; p++)
		{
			for (int i = 0; i < 700; i++)
			{
				// int random = (int) (0 + (Math.random() * (100 - 0)));
				// block = new WorldBlock(425.00 + (Math.tan(i * 200 + 2) + i *
				// 16) + (80 * i),
				// (600.00 - (Math.tan(i * 20 + 2) + i / 2)) + (p * 16), 16.00,
				// 16.00);
				// R = random;
				// G = random;
				// B = random;
				// block.setColor(R, G, B);
				//
				// block.setHasCollision(true);
				// world.add(block);

				block = new WorldBlock(425.00 + (Math.tan(i * 200 + 2) + i * 16) + (80 * i),
						(600.00 - (Math.tan(i * 20 + 2) + i / 2)) + (p * 16), 16.00, 16.00);

				if (i % 5 == 0 || i % 4 == 0)
				{
					if (p == 0)
					{
						block.setImage(grass, false);
					} else if (p == 1)
					{
						block.setImage(dirtMoss, false);
					} else
					{
						block.setImage(dirt, false);
					}

				} else
				{
					block.setImage(bricks, false);
				}
				block.doRenderBlock(false);
				block.setHasCollision(true);
				world.add(block);
			}
		}

		for (int i = 0; i < lights.size(); i++)
		{
			Light light = lights.get(i);
			block = new WorldBlock(light.x, light.y, 16.00, 64.00);
			R = (int) (255);
			G = (int) (255);
			B = (int) (0);
			block.setColor(R, G, B);

			block.setHasCollision(true);
			world.add(block);
		}

		for (int i = 0; i < 100; i++)
		{
			int random = (int) (0 + (Math.random() * (100 - 0)));
			block = new WorldBlock(-70.00 - (i * Math.cos(i * Math.PI)), 900.00 - (i * 64.00), 64.00, 64.00);
			R = random;
			G = random;
			B = random;
			block.setColor(R, G, B);

			block.setHasCollision(true);
			world.add(block);
		}
		for (int i = 0; i < 1; i++)
		{
			block = new Water(0.00 + (i * 1000), 650.00, 600.00, (200.00));
			block.setHasCollision(true);
			world.add(block);
		}

		{

			blocks.add(grass);
			blocks.add(dirtMoss);
			blocks.add(dirt);
			blocks.add(bricks);

			// for (int w = 0; w < 200000; w++)
			// {
			// int i = (int) (Math.random() * 1024);
			// int p = (int) (Math.random() * 100);
			// worldGrid[i][p] = new WorldBlock((double) (i * 16) - globalXpos,
			// (double) (p * 16)
			// - globalYpos, 16.00, 16.00);
			// worldGrid[i][p].setImage(blocks.get((int) (Math.random() *
			// blocks.size())), false);
			// }

			if (useOld == false)
			{
				for (int x = 100; x > 0; x--)
				{
					for (int y = 99; y > 0; y--)
					{
						// if (Math.cos(y) + Math.cos(x) % 4 > 1)
						// {
						// worldGrid[x][y] = new WorldBlock((double) (x * 16) -
						// globalXpos,
						// (double) (y * 16) - globalYpos, 16.00, 16.00);
						// worldGrid[x][y].setImage(blocks.get((int)
						// (Math.random()
						// * blocks.size())),
						// false);
						// }
						if (y > 87)
						{
							worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
									(double) (y * 16) - globalYpos, 16.00, 16.00);
							worldGrid[x][y].setImage(blocks.get(2), false);
						} else if (y == 87)
						{
							worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
									(double) (y * 16) - globalYpos, 16.00, 16.00);
							worldGrid[x][y].setImage(blocks.get(1), false);
						} else if (y == 86)
						{
							worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
									(double) (y * 16) - globalYpos, 16.00, 16.00);
							worldGrid[x][y].setImage(blocks.get(0), false);
						} else if (y == 85)
						{
							if (x > 35 && x < 50)
							{
								worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
										(double) (y * 16) - globalYpos, 16.00, 16.00);
								worldGrid[x][y].setImage(blocks.get(0), false);

								worldGrid[x][y + 1].setImage(blocks.get(1));
								worldGrid[x][y + 2].setImage(blocks.get(2));
							}
						} else if (y == 84)
						{
							if (x > 36 && x < 49)
							{
								worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
										(double) (y * 16) - globalYpos, 16.00, 16.00);
								worldGrid[x][y].setImage(blocks.get(0), false);

								worldGrid[x][y + 1].setImage(blocks.get(1));
								worldGrid[x][y + 2].setImage(blocks.get(2));
							}
						} else if (y == 83)
						{
							if (x > 37 && x < 48)
							{
								worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
										(double) (y * 16) - globalYpos, 16.00, 16.00);
								worldGrid[x][y].setImage(blocks.get(0), false);

								worldGrid[x][y + 1].setImage(blocks.get(1));
								worldGrid[x][y + 2].setImage(blocks.get(2));
							}
						} else if (y == 82)
						{
							if (x > 40 && x < 45)
							{
								worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
										(double) (y * 16) - globalYpos, 16.00, 16.00);
								worldGrid[x][y].setImage(blocks.get(0), false);

								worldGrid[x][y + 1].setImage(blocks.get(1));
								worldGrid[x][y + 2].setImage(blocks.get(2));
							}
						} else if (y < 82 && y > 75)
						{
							if (x > 40 && x < 45)
							{
								worldGrid[x][y] = new WorldBlock((double) (x * 16) - globalXpos,
										(double) (y * 16) - globalYpos, 16.00, 16.00);
								worldGrid[x][y].setImage(blocks.get(0), false);

								worldGrid[x][y + 1].setImage(blocks.get(1));
								worldGrid[x][y + 2].setImage(blocks.get(2));
							}
						} else
						{
							worldGrid[x][y] = new Air();
						}
					}
				}

			}
		}

		bgX = getWidth() / 2;
		bgY = getHeight() / 2;
		optimise();

	}

	public static void optimise()
	{
		for (Light light : lights)
		{
			if (light.isMoving() == false)
			{
				for (Light otherLight : lights)
				{
					if (otherLight.isMoving() == false)
					{
						if (!light.equals(otherLight))
						{
							if (Range.isInRange(light, otherLight, 100))
							{
								if (isInCircularRange(light, otherLight, 100))
								{

									if (light.getIntensity() < 255)
									{
										light.setIntensity(light.getIntensity() + otherLight.getIntensity());

										Color lightColor = light.getColor();
										Color otherLightColor = otherLight.getColor();
										Color newColor = new Color(lightColor.getRGB()
												+ otherLightColor.getRGB());

										light.setColor(newColor);
										lights.remove(otherLight);
									}

								}

							}
						}
					}
				}
			}
		}
	}

	public static void moveLight(double x, double y)
	{
		mouseLight.x = x;
		mouseLight.y = y;
	}

	public Dimension getPreferredSize()
	{
		return (new Dimension(scrnWidth, scrnHeight));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponents(g);

		Graphics2D brush = (Graphics2D) g;
		scrnWidth = getWidth();
		scrnHeight = getHeight();
		g.clearRect(0, 0, scrnWidth, scrnHeight);

		// Background
		Rectangle r = new Rectangle((int) bg.getGraphics().x, (int) bg.getGraphics().y, 209, 285);
		Rectangle r2 = new Rectangle((int) 0, (int) 0, scrnWidth, scrnHeight);
		brush.setPaint(new TexturePaint(bg.getImage(), r));
		brush.fill(r2);

		if (useOld == true)
		{
			for (WorldBlock block : world)
			{
				render(block, g);
			}
		} else
		{

			for (int y = (int) globalYpos - (getHeight() / 2) - 16; y < globalYpos + (getHeight() / 2) + 16; y++)
			{
				for (int x = (int) globalXpos - (getWidth() / 2) - 16; x < globalXpos + (getWidth() / 2) + 16; x++)
				{
					if (y > 0 && y < 100 && x > 0 && x < 1024)
					{
						WorldBlock block = worldGrid[x][y];
						if (block != null && !(block instanceof Air))
						{
							block.getGraphics().x -= globalXpos;
							block.getGraphics().y -= globalYpos;
							render(block, g);
						}
					}
				}
			}
		}
		for (WorldBlock entity : entities)
		{
			render(entity, g);
		}

		for (Projectile projectile : projectiles)
		{
			render(projectile, g);
		}

		// Lighting
		if (renderLighting == true)
		{
			Lighting.calculateLighting(lights, brush, scrnWidth, scrnHeight);
		}

		if (useOld == false)
		{
			for (int y = (int) globalYpos - (getHeight() / 2) - 16; y < globalYpos + (getHeight() / 2) + 16; y++)
			{
				for (int x = (int) globalXpos - (getWidth() / 2) - 16; x < globalXpos + (getWidth() / 2) + 16; x++)
				{
					if (y > 0 && y < 100 && x > 0 && x < 1024)
					{
						if (worldGrid[x][y] != null)
						{
							if (worldGrid[x][y].lightIsRendered() == false)
							{
								for (Light light : lights)
								{
									Lighting.calculateLight(worldGrid[x][y], light);
									worldGrid[x][y].setLightRendered(true);
								}
							}
						}
					}
				}
			}
		}

		for (Light light : lights)
		{
			if (Range.insideScreen(light, light.getRange(), scrnWidth, scrnHeight))
			{
				renderedLights += 1;
			}
		}

		// Water
		WaterReflections.first = true;
		for (WorldBlock block : world)
		{
			if (waterReflections == true)
			{
				if (block instanceof Water)
				{
					if (Range.insideScreen(block, scrnWidth, scrnHeight))
					{
						WaterReflections.calculateReflections(block, brush, scrnWidth, scrnHeight);
					}
				}
			} else
			{
				if (block instanceof Water)
				{
					block.doRenderBlock(true);
					block.setColor(new Color(0, 84, 137, 0));
					// System.out.println("setting color of block " + block);
				}
			}

		}

		// stats
		Stats.renderStats(brush, scrnWidth, scrnHeight);
		FPS += 1;
	}

	public void render(WorldBlock block, Graphics g)
	{
		Graphics2D brush = (Graphics2D) g;

		if (Range.insideScreen(block, scrnWidth, scrnHeight))
		{
			renderedImages += 1;
			Color color = new Color(0, 0, 0, (int) (block.getLightLevel() / 255));

			if (block.renderBlock())
			{
				if (block.tileImage())
				{
					Rectangle r = new Rectangle((int) (block.getGraphics().x), (int) (block.getGraphics().y),
							16, 16);
					brush.setPaint(new TexturePaint(block.getImage(), r));
				} else
				{
					brush.setColor(block.getColor());
				}
				brush.fill(block.getGraphics());
			}
			if (block.hasImage() && !block.tileImage())
			{
				brush.drawImage(block.getImage(), (int) block.getGraphics().getX(), (int) block.getGraphics()
						.getY(), (int) block.getGraphics().getWidth(), (int) block.getGraphics().getHeight(),
						null);
			}

			brush.setColor(color);
			brush.fill(new Rectangle((int) block.getGraphics().x, (int) block.getGraphics().y, 16, 16));
		}

	}

	public static BufferedImage renderLightLevel(BufferedImage bi, WorldBlock block)
	{
		BufferedImage newImage = deepCopy(block.getImage());

		for (int h = 0; h < newImage.getTileHeight(); h++)
		{
			for (int w = 0; w < newImage.getTileWidth(); w++)
			{

				if (!(((newImage.getRGB(w, h) & 0xff000000) >> 24) == 0x00))
				{
					Color pixelColor = new Color(newImage.getRGB(w, h));
					int red = pixelColor.getRed();
					int green = pixelColor.getGreen();
					int blue = pixelColor.getBlue();

					if (!block.isEmissive())
					{
						if (block.getLightLevel() < 1)
						{
							red *= block.getLightLevel();
							green *= block.getLightLevel();
							blue *= block.getLightLevel();
						}
					}
					Color color = new Color(red, green, blue);
					newImage.setRGB(w, h, color.getRGB());
				}
			}
		}
		return newImage;
	}

	public static Color renderLightLevelColor(WorldBlock block)
	{
		int red = block.getColor().getRed();
		int green = block.getColor().getGreen();
		int blue = block.getColor().getBlue();

		if (!block.isEmissive())
		{
			if (block.getLightLevel() < 1)
			{
				red *= block.getLightLevel();
				green *= block.getLightLevel();
				blue *= block.getLightLevel();
			}
		}
		Color color = new Color(red, green, blue);
		return color;
	}

	public static BufferedImage deepCopy(BufferedImage bi)
	{
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static boolean isInCircularRange(Light light1, Light light2, double range)
	{
		double x1 = light1.x;
		double x2 = light2.x;
		double y1 = light1.y;
		double y2 = light2.y;
		double range2 = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y2 - y1), 2));

		if (range2 < range)
		{
			return true;
		} else
		{
			return false;
		}

	}

	public boolean inRange(WorldBlock worldBlock, WorldBlock world)
	{
		Rectangle2D.Double block = worldBlock.getGraphics();
		Rectangle2D.Double collide = world.getGraphics();
		int checkRange = 50;

		if (block.x < block.x + checkRange && block.x > block.x - checkRange
				&& block.y < collide.y + checkRange && block.y > collide.y - checkRange)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void tick()
	{
		if (godMode == true)
		{
			playerBlock.setHealth(100);
		}
		bg.getGraphics().x += globalWind;
		globalWidth = scrnWidth;
		movingPlatformLight.x = movingPlatform.getGraphics().getCenterX();
		movingPlatformLight.y = movingPlatform.getGraphics().getCenterY();

		renderedImages = 0;
		renderedLights = 0;

		// Gravity / Position / Collision
		// WorldBlock entity;

		for (WorldBlock block : world)
		{
			Position.position(block);
		}

		for (WorldBlock entity : entities)
		{

			if (entity.hasGravity())
			{
				Gravity.gravity(entity);
			}
			Position.position(entity);

			if (useOld == true)
			{
				for (WorldBlock block : world)
				{
					if (entity instanceof Player)
					{
						if (Range.insideScreen(block, scrnWidth, scrnHeight))
						{
							if (inRange(entity, block))
							{
								Collision.collide(entity, block);
							}
						}
					} else
					{
						if (inRange(entity, block))
						{
							Collision.collide(entity, block);
						}
					}
				}
			} else
			{

				double range = 10.95;
				for (int y = (int) (globalYpos - 200); y < globalYpos + (200) + 2; y++)
				{
					for (int x = (int) (globalXpos - 200); x < globalXpos + (200) + 2; x++)
					{
						collH = Clamp.clamp(y, 99, 0);
						collW = Clamp.clamp(x, 1023, 0);
						WorldBlock block = worldGrid[collW][collH];
						if (block != null)
						{
							if (Collision.collide(entity, block))
							{

							}
						}
					}
				}
			}

		}

		for (Projectile proj : projectiles)
		{
			Position.position(proj);

			for (Character entity : entities)
			{
				if (proj.getInstigator() != entity && proj.getGraphics().intersects(entity.getGraphics()))
				{
					int direction = 0;
					if (proj.getGraphics().x > entity.getGraphics().x)
					{
						direction = entity.DIRECTION_LEFT;
					} else
					{
						direction = entity.DIRECTION_RIGHT;
					}
					entity.takeDamage(proj.getDamage(), direction, proj);
					if (entity.getHealth() <= 0)
					{
						entity.setHealth(0);
						entities.remove(entity);
					}
					lights.remove(proj.getProjLight());
					projectiles.remove(proj);
					break;
				}

			}
			for (WorldBlock block : world)
			{
				if (block.hasCollision())
				{
					if (proj.getRightBounds().intersects(block.getGraphics()))
					{
						lights.remove(proj.getProjLight());
						projectiles.remove(proj);
						break;
					}
				}
			}
			if (proj.getLifeTime() >= proj.getMaxLife())
			{
				lights.remove(proj.getProjLight());
				projectiles.remove(proj);
				break;
			}
		}
		for (Light light : lights)
		{
			LightPosition.position(light);
		}

		for (WorldBlock entity : entities)
		{
			if (!(entity instanceof Player))
			{
				if (entity instanceof Character)
				{
					((Character) entity).ai(playerBlock);
				}
			}
		}

		if (isWalking == true)
		{
			if (playerBlock.getDirection() == 1)
			{

				playerBlock.setXSpeed(-speed);

			}
			if (playerBlock.getDirection() == 2)
			{

				playerBlock.setXSpeed(speed);

			}
		}

		// float lerp = 0.9f;

		globalYpos = playerBlock.getGraphics().y - (scrnHeight / 2);
		globalXpos = playerBlock.getGraphics().x - (scrnWidth / 2);
		// globalYpos += ((playerBlock.getGraphics().y - (frame.getHeight() /
		// 2)) - globalYpos) * lerp;

		optimise();

		if (playerBlock.getHealth() <= 0)
		{
			System.out.println("Player is dead!");
			playerBlock.setHealth(100);
			playerBlock.getGraphics().x = -200 + globalXpos;
			playerBlock.getGraphics().y = 0;
		}

		repaint();
	}

	public static void spawnProjectile()
	{
		Projectile proj = new Projectile(playerBlock.getGraphics().x + 10, playerBlock.getGraphics().y + 10,
				10.00, 10.00);
		proj.setXSpeed(-proj.getSpeed());
		proj.setInstigator(new Character());
		Window.projectiles.add(proj);
	}

	public static void makeJumps()
	{
		for (WorldBlock entity : entities)
		{
			if (entity != playerBlock)
			{
				entity.setYSpeed(-(Gravity.gravity));
			}
		}
	}

	public static void createBlock()
	{
		double w = Math.random() * 100;
		double h = Math.random() * 100;
		double y = Math.random() * 600;
		double x = Math.random() * 800;

		WorldBlock block = new WorldBlock(w, h, y, x);

		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);
		block.setColor(R, G, B);

		double random = Math.round(Math.random());

		if (random == 1)
		{
			block.setImage("src/idle01.png", false);
		}

		block.setCollideWithWorld(false);
		world.add(block);
	}

	public static void createGravityBlock()
	{
		Character block = new Character(400.00, 10.00, 25.00, 25.00, "");

		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);

		block.setColor(R, G, B);

		block.setCollideWithWorld(true);
		block.setGravity(true);
		entities.add(block);
	}

	public static void createGravityBlockAtMouse(int x, int y)
	{
		Character block = new Character();
		block.setLocation(x, y);
		block.setCollideWithWorld(true);
		block.setGravity(true);
		entities.add(block);
	}

	public static void createBlockAtMouse(int x, int y)
	{
		WorldBlock block = new WorldBlock((double) x, (double) y, 112.00, 16.00);

		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);

		block.setColor(R, G, B);

		block.setCollideWithWorld(true);
		block.setGravity(true);
		block.setImage(mouseBlock);
		block.setTileImage(true);
		world.add(block);
	}

	private void createAndDisplayGUI(Window window)
	{
		frame = new JFrame();
		Container container = frame.getContentPane();
		container.add(window);
		window.addKeyListener(new Input());
		window.addMouseListener(new MouseInput());
		window.addMouseMotionListener(new MouseMovement());
		frame.setSize(800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("BBQ");
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.createBufferStrategy(1);

		window.requestFocusInWindow();

		JButton blockButton = new JButton("Block");
		JButton lightButton = new JButton("Light");
		JButton entityButton = new JButton("Entity");

		add(blockButton);
		add(lightButton);
		add(entityButton);

	}

	double smoothNoise(double x, double y, double z)
	{
		int noiseWidth = 16;
		int noiseHeight = 16;
		int noiseDepth = 16;
		// get fractional part of x and y
		double fractX = x - (int) (x);
		double fractY = y - (int) (y);
		double fractZ = z - (int) (z);

		// wrap around
		int x1 = (int) (x) + noiseWidth % noiseWidth;
		int y1 = (int) (y) + noiseHeight % noiseHeight;
		int z1 = (int) (z) + noiseDepth % noiseDepth;

		// neighbor values
		int x2 = (x1 + noiseWidth - 1) % noiseWidth;
		int y2 = (y1 + noiseHeight - 1) % noiseHeight;
		int z2 = (z1 + noiseDepth - 1) % noiseDepth;

		// smooth the noise with bilinear interpolation
		double value = 0.0;
		value += fractX * fractY * fractZ * noiseWidth;
		value += fractX * (1 - fractY) * fractZ * noiseWidth;
		value += (1 - fractX) * fractY * fractZ * noiseWidth;
		value += (1 - fractX) * (1 - fractY) * fractZ * noiseWidth;

		value += fractX * fractY * (1 - fractZ) * noiseWidth;
		value += fractX * (1 - fractY) * (1 - fractZ) * noiseWidth;
		value += (1 - fractX) * fractY * (1 - fractZ) * noiseWidth;
		value += (1 - fractX) * (1 - fractY) * (1 - fractZ) * noiseWidth;

		return value;
	}

	public static void main(String[] args)
	{

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				final Window window = new Window();

				window.createAndDisplayGUI(window);
				Timer timer = new Timer(1000 / 60, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						window.tick();
					}
				});
				timer.setRepeats(true);
				timer.setCoalesce(false);
				timer.start();

				Timer timer2 = new Timer(1000, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						fpsString = String.format("FPS: %s", FPS);
						FPS = 0;
						renderedImages = 0;
						renderedImages2 = 0;
						// createBlock();
					}
				});

				timer2.setRepeats(true);
				timer2.setCoalesce(false);
				timer2.start();
				globalWidth = window.getWidth();
				playerBlock.getGraphics().x = 400;
				playerBlock.getGraphics().y = 300;
				playerBlock.getGraphics().y = 0;
			}
		});

	}
}