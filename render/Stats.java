package test.render;

import java.awt.Color;
import java.awt.Graphics2D;

import test.Window;

public class Stats
{
	public static void renderStats(Graphics2D brush, double scrnWidth, double scrnHeight)
	{
		brush.setColor(new Color(0, 0, 0));
		brush.drawString(Window.fpsString, 20, 20);
		brush.drawString("Rendered Images: " + Window.renderedImages, 20, 40);
		brush.drawString("Total images: " + Window.world.size(), 20, 60);
		brush.drawString("Number of entities: " + Window.entities.size(), 20, 80);
		brush.drawString("Rendered lights: " + Window.renderedLights, 20, 100);
		brush.drawString("Number of lights: " + Window.lights.size(), 20, 120);
		
		brush.drawString("Health: " + Window.playerBlock.getHealth(), (int) (scrnWidth - 120),
				(int) (scrnHeight - 20));
		
		brush.setColor(new Color(255, 255, 255));
		brush.drawString(Window.fpsString, 21, 21);
		brush.drawString("Rendered Images: " + Window.renderedImages, 21, 41);
		brush.drawString("Total images: " + Window.world.size(), 21, 61);
		brush.drawString("Number of entities: " + Window.entities.size(), 21, 81);
		brush.drawString("Rendered lights: " + Window.renderedLights, 21, 101);
		brush.drawString("Number of lights: " + Window.lights.size(), 21, 121);
		
		brush.drawString("Health: " + Window.playerBlock.getHealth(), (int) (scrnWidth - 121),
				(int) (scrnHeight - 21));
	}
}
