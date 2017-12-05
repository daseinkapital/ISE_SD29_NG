import java.lang.Math;

/**
 * This class contains the ability to create and sample from distributions.
 */
public class Distribution
{
	private float min;
	private float mode;
	private float max;

	public Distribution(float minInput, float modeInput, float maxInput)
	{
		min = minInput;
		mode = modeInput;
		max = maxInput;
	}


	public double sampleTriangular()
	{
		float a = min;
		float b = max;
		float c = mode;
		float F = (c - a) / (b - a);
		float random = (float) Math.random();
		if (random < F)
		{
			return a + Math.sqrt(random * (b - a) * (c - a));
		} else
		{
			return b - Math.sqrt((1 - random) * (b - a) * (b - c));
		}
	}


	public static float sampleTriangular(float minInput, float maxInput, float modeInput)
	{
		float a = minInput;
		float b = maxInput;
		float c = modeInput;
		float F = (c - a) / (b - a);
		float random = (float) Math.random();
		if (random < F)
		{
			return (float) (a + Math.sqrt(random * (b - a) * (c - a)));
		} else
		{
			return (float) (b - Math.sqrt((1 - random) * (b - a) * (b - c)));
		}
	}
}
