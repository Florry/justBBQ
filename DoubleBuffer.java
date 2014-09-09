package test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DoubleBuffer extends JPanel
{
	// variables
	static Color rectColor;
	
	public DoubleBuffer()
	{}
	
	public void paint(Graphics g)
	{
		// initial setup, such as
		// graphics.setColor(rectColor);
		// graphics.fillRect(0, 0, getWidth(), getHeight());
		Graphics offgc;
		Image offscreen = null;
		
		// create the offscreen buffer and associated Graphics
		offscreen = createImage(getWidth(), getHeight());
		offgc = offscreen.getGraphics();
		// clear the exposed area
		offgc.setColor(Color.red);
		offgc.fillRect(0, 0, getWidth(), getHeight());
		offgc.setColor(Color.green);
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(offscreen, 0, 0, this);
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		DoubleBuffer canvas = new DoubleBuffer();
		canvas.setDoubleBuffered(true);
		JFrame frame = new JFrame("GUI");
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		while (true)
		{
			canvas.validate();
			tick(canvas);
			canvas.repaint();
			Thread.sleep(500);
		}
	}
	
	private static void tick(DoubleBuffer canvas)
	{
		BufferedImage test = null;
		
		try
		{
			test = ImageIO.read(new File("src/idle01.png"));
		} catch (IOException e)
		{}
		Graphics g = test.getGraphics();
		g.drawImage(test, 0, 0, 40, 40, null);
		canvas.print(g);
	}
}