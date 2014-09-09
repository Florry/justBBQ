package test.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import test.Window;
import test.entities.Light;
import test.entities.WorldBlock;
import test.functions.Clamp;
import test.functions.Range;

public class Lighting
{
	private static boolean noLights = true;
	private static int max = 255;
	private static int min = 0;
	
	public static void calculateLighting(ArrayList<Light> lights, Graphics2D brush, double width,
			double height)
	{
		for (int h = 0; h < height; h += Window.shadowDetail)
		{
			for (int w = 0; w < width; w += Window.shadowDetail)
			{
				Color color = new Color(0, 0, 0);
				int a = 255;
				int red = 0;
				int green = 0;
				int blue = 0;
				
				for (Light light : lights)
				{
					if (Range.insideScreen(light, light.getRange(), width, height))
					{
						double x1 = w;
						double x2 = light.x;
						double y1 = h;
						double y2 = light.y;
						double range = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y2 - y1), 2));
						
						if (Range.isInRange(w, h, light, Window.shadowDetail))
						{
							if (range < light.getRange())
							{
								if (a > 0)
								{
									if (range < light.getFallOff())
									{
										a = 0;
									} else
									{
										a -= (255 / (range - light.getFallOff())
												* light.getIntensity() / 2);
									}
								}
							}
						}
						Color lightColor = light.getColor();
						
						if (range < light.getRange())
						{
							if (!lightColor.equals(new Color(255, 255, 255)))
							{
								red += (lightColor.getRed() / range) * light.getIntensity();
								blue += (lightColor.getBlue() / range) * light.getIntensity();
								green += (lightColor.getGreen() / range) * light.getIntensity();
							}
						}
						
						red = Clamp.clamp(red, max, min);
						green = Clamp.clamp(green, max, min);
						blue = Clamp.clamp(blue, max, min);
						a = Clamp.clamp(a, max, min);
						
						color = new Color(red, green, blue, a);
					}
					
				}
				noLights = false;
				brush.setColor(color);
				brush.fill(new Rectangle(w, h, Window.shadowDetail, Window.shadowDetail));
			}
		}
		if (noLights == true)
		{
			brush.setColor(new Color(0, 0, 0));
			brush.fill(new Rectangle(0, 0, (int) width, (int) height));
		}
		noLights = true;
	}
	
	public static void calculateLight(WorldBlock block, Light light)
	{
		if (Range.getDistance(block, light) < light.getRange())
		{
			double distance = Range.getDistance(block, light);
			if (distance < light.getFallOff())
			{
				block.setLightLevel(1);
			} else
			{
				block.setLightLevel(Clamp.clamp(
						block.getLightLevel() + (1 / ((distance - light.getFallOff())) * 2), 1, 0));
			}
		}
	}
}
