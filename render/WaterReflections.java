package test.render;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import test.Window;
import test.entities.WorldBlock;

public class WaterReflections
{
	public static boolean first;
	private static Robot robot;
	private static BufferedImage reflections;

	public static void calculateReflections(WorldBlock block, Graphics2D brush, double scrnWidth,
			double scrnHeight)
	{
		try
		{
			robot = new Robot();
		} catch (AWTException e)
		{
			e.printStackTrace();
		}

		block.doRenderBlock(false);
		if (first == true)
		{

			reflections = robot.createScreenCapture(new Rectangle((int) scrnWidth,
					(int) (scrnHeight - scrnHeight / 4)));
			// Graphics2D g2d = reflections.createGraphics();
			// BufferedImage awtImage = new BufferedImage((int) scrnWidth, (int)
			// scrnHeight,
			// BufferedImage.TYPE_INT_RGB);
			// Graphics g = (Graphics) awtImage.getGraphics();
			// Window.printAll(g);

			first = false;
		}

		Rectangle2D.Double graphics = block.getGraphics();

		boolean noReflecions = false;
		int x = (int) graphics.x;
		int y = (int) graphics.y;
		int height = (int) graphics.height;
		int width = (int) graphics.width;

		if (x + width > scrnWidth)
		{
			width = (int) (width - ((width + x) - scrnWidth + 15));

		} else if (x < 0)
		{
			width = width + x;
			x = 1;
		}
		if (y + height > scrnHeight)
		{
			height = (int) (height - ((height + y) - scrnHeight - 15));
		} else if (y < 0)
		{
			height = height + y;
			y = 1;
		}
		// else if (y < scrnHeight - height)
		// {
		// height = (int) (scrnHeight - (scrnHeight - y));
		// }

		int reflectionY = (int) (y - height + 44);

		if (Window.waterReflections == true)
		{
			if (noReflecions == false)
			{
				BufferedImage subImage = reflections.getSubimage(x + 4, reflectionY, width, height);
				AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -subImage.getHeight(null));
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				subImage = op.filter(subImage, null);
				brush.drawImage(subImage, x, y, width, height, null);
			}

		}
		BufferedImage gradient = Gradient.createGradientImage((int) graphics.width, (int) graphics.height,
				new Color(0, 84, 137, 0), new Color(0, 84, 137, 255));
		brush.drawImage(gradient, (int) graphics.x, (int) graphics.y, (int) graphics.width,
				(int) graphics.height, null);

	}
}
