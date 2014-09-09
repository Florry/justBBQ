package test.render;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class Gradient
{
	public static BufferedImage createGradientImage(int width, int height, Color gradient1,
			Color gradient2)
	{
		
		BufferedImage gradientImage = createCompatibleImage(width, height);
		GradientPaint gradient = new GradientPaint(0, 0, gradient1, 0, height, gradient2, false);
		Graphics2D g2 = (Graphics2D) gradientImage.getGraphics();
		g2.setPaint(gradient);
		g2.fillRect(0, 0, width, height);
		g2.dispose();
		
		return gradientImage;
	}
	
	private static BufferedImage createCompatibleImage(int width, int height)
	{
		
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration()
				.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		
	}
}
