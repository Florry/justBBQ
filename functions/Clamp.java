package test.functions;

public class Clamp
{
	public static int clamp(int toClamp, int maxValue, int minValue)
	{
		if (toClamp > maxValue)
		{
			return maxValue;
		} else if (toClamp < minValue)
		{
			return minValue;
		} else
		{
			return toClamp;
		}
	}

	public static double clamp(double toClamp, double maxValue, double minValue)
	{
		if (toClamp > maxValue)
		{
			return maxValue;
		} else if (toClamp < minValue)
		{
			return minValue;
		} else
		{
			return toClamp;
		}
	}

	public static int clampRound(int toClamp, int maxPosValue, int minPosValue, int maxNegValue,
			int minNegValue)
	{
		if (toClamp > maxPosValue)
		{
			return maxPosValue;
		} else if (toClamp < minPosValue && toClamp > minPosValue)
		{
			return minPosValue;
		} else if (toClamp < maxNegValue)
		{
			return maxNegValue;
		} else
		{
			return toClamp;
		}
	}

	public static int clampMin(int toClamp, int minValue)
	{
		if (toClamp < minValue)
		{
			return minValue;
		} else
		{
			return toClamp;
		}
	}

	public static int clampMax(int toClamp, int maxValue)
	{
		if (toClamp > maxValue)
		{
			return maxValue;
		} else
		{
			return toClamp;
		}
	}
}
