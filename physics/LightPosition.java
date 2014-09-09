package test.physics;

import test.Window;
import test.entities.Light;

public class LightPosition extends Position
{
	public static void position(Light light)
	{
		if (pause == false)
		{
			light.y = light.y - (Window.globalYpos) + light.getYSpeed();
			light.x = light.x - (Window.globalXpos) + light.getXSpeed();
		}
	}
}
