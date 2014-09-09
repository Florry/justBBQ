package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import test.entities.WorldBlock;
import test.functions.Clamp;
import test.functions.Range;
import test.render.Gradient;

public class SmoothMove extends JPanel implements MouseMotionListener
{
	
	/**This is just a test class for coming up with an algorithm for hiding and showing sprites based on criteria. 
	 * In this case a 2d array of objects are shown/hidden in a circle around the mouse pointer.
	 */
	private static final long serialVersionUID = 1L;
	private static boolean useSearch = true;
	private static int gridSizeW = 1024;
	private static int gridSizeH = 100;
	private static WorldBlock[][] level = new WorldBlock[gridSizeW][gridSizeH];
	
	private int mX, mY;
	
	private BufferedImage mImage;
	
	public static void main(String[] args)
	{
		BufferedImage grass = null;
		BufferedImage dirtMoss = null;
		BufferedImage dirt = null;
		BufferedImage bricks = null;
		BufferedImage burger = null;
		BufferedImage bush = null;
		try
		{
			grass = ImageIO.read(new File("src/assets/blocks/grass01.png"));
			dirtMoss = ImageIO.read(new File("src/assets/blocks/dirt01MossyNoTop.png"));
			dirt = ImageIO.read(new File("src/assets/blocks/dirt01.png"));
			bricks = ImageIO.read(new File("src/assets/blocks/block01.png"));
			burger = ImageIO.read(new File("src/assets/blocks/burger01.png"));
			bush = ImageIO.read(new File("src/assets/blocks/box01.png"));
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		List<BufferedImage> blocks = new ArrayList<BufferedImage>();
		blocks.add(grass);
		blocks.add(dirtMoss);
		blocks.add(dirt);
		blocks.add(bricks);
		blocks.add(burger);
		blocks.add(bush);
		
		for (int i = 0; i < gridSizeW; i++)
		{
			for (int p = 0; p < gridSizeH; p++)
			{
				int r = (int) 255 - p * 5;
				int g = (int) (255 - (p * Math.cos(i * Math.E + p)) + 255 / (i * Math.cos(p
						* Math.E + i)));
				int b = (int) Math.random() * 128 - i;
				int a = (int) (Math.random() * 255 + Math.sin(Math.sin(p) + Math.cos(i) + Math.PI));
				r = Clamp.clamp(r, 255, 0);
				g = Clamp.clamp(g, 255, 0);
				b = Clamp.clamp(b, 255, 0);
				a = Clamp.clamp(a, 255, 0);
				Color color = new Color(r, g, b, a);
				level[i][p] = new WorldBlock((double) (i * 16), (double) (p * 16), 16.00, 16.00);
				level[i][p].setImage(blocks.get((int) (Math.random() * blocks.size())));
				level[i][p].setColor(color);
			}
		}
		JFrame f = new JFrame();
		f.getContentPane().add(new SmoothMove());
		f.setSize(500, 500);
		f.show();
	}
	
	public SmoothMove()
	{
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	public void mouseMoved(MouseEvent me)
	{
		mX = (int) me.getPoint().getX();
		mY = (int) me.getPoint().getY();
		repaint();
	}
	
	public void mouseDragged(MouseEvent me)
	{
		mouseMoved(me);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		// Clear the offscreen image.
		Dimension d = getSize();
		checkOffscreenImage();
		Graphics offG = mImage.getGraphics();
		offG.setColor(getBackground());
		offG.fillRect(0, 0, d.width, d.height);
		for (int i = 0; i < 10; i++)
		{
			BufferedImage test = null;
			
			try
			{
				test = ImageIO.read(new File("src/idle01.png"));
				
			} catch (IOException e)
			{}
			for (int p = 0; p < 1; p++)
			{
				offG.drawImage(test, 0 + (i * 20), 108, 24, 32, null);
			}
		}
		
		int size = 300;
		int range = 300;
		if (useSearch)
		{
			for (int i = (mX / 16) - (size / 16); i < (mX / 16) + (size / 16); i++)
			{
				int w = Clamp.clamp(i, gridSizeW - 1, 0);
				for (int p = (mY / 16) - (size / 16); p < (mY / 16) + (size / 16); p++)
				{
					int h = Clamp.clamp(p, gridSizeH - 1, 0);
					if (Range.getDistance(level[w][h].getGraphics().getX(), level[w][h]
							.getGraphics().getY(), mX, mY) < range)
					{
						offG.drawImage(level[w][h].getImage(), (int) level[w][h].getGraphics()
								.getX(), (int) level[w][h].getGraphics().getY(), 16, 16, null);

						offG.setColor(level[w][h].getColor());
						offG.fillRect((int) level[w][h].getGraphics().x,
								(int) level[w][h].getGraphics().y, 16, 16);
					}
				}
			}
		} else
		{
			for (int i = 0; i < getWidth() / 16; i++)
			{
				int w = Clamp.clamp(i, gridSizeW - 1, 0);
				for (int p = 0; p < getHeight() / 16; p++)
				{
					int h = Clamp.clamp(p, gridSizeH - 1, 0);
					
					offG.drawImage(level[w][h].getImage(), (int) level[w][h].getGraphics().getX(),
							(int) level[w][h].getGraphics().getY(), 16, 16, null);
					offG.setColor(level[w][h].getColor());
					offG.fillRect((int) level[w][h].getGraphics().x,
							(int) level[w][h].getGraphics().y, 16, 16);
				}
			}
		}
		paintOffscreen(mImage.getGraphics());
		
		BufferedImage gradient = Gradient.createGradientImage(500, (int) 40, new Color(0, 84, 137,
				0), new Color(0, 84, 137, 255));
		
		g.drawImage(mImage, 0, 0, null);
		g.drawImage(gradient, 0, 140, null);
		g.setColor(new Color(0, 84, 137, 255));
		
	}
	
	private void checkOffscreenImage()
	{
		Dimension d = getSize();
		if (mImage == null || mImage.getWidth(null) != d.width
				|| mImage.getHeight(null) != d.height)
		{
			mImage = (BufferedImage) createImage(d.width, d.height);
		}
	}
	
}
