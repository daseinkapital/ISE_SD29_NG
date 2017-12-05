import java.lang.Math;

/**
 * This class gives the user the ability to sample from a distribution and
 * create a distribution object.
 * 
 * @author Rebeca Dominguez
 */
public class Distribution
{
	// The minimum of the distribution
	private float min;
	// The mode of the distribution
	private float mode;
	// The maximum of the distribution
	private float max;

	/**
	 * This constructor creates a triangular distribution object for easier
	 * sampling.
	 * 
	 * @param minInput
	 *            the minimum of the distribution
	 * @param modeInput
	 *            the mode of the distribution
	 * @param maxInput
	 *            the maximum of the distribution
	 */
	public Distribution(float minInput, float modeInput, float maxInput)
	{
		min = minInput;
		mode = modeInput;
		max = maxInput;
	}

	/**
	 * This method allows you to sample from a triangular distribution
	 * 
	 * @return the number sampled
	 */
	public double sampleTriangular()
	{
		float a = min;
		float b = max;
		float c = mode;
		float F = (c - a) / (b - a); // equation generate an F
		float random = (float) Math.random();
		if (random < F)
		{
			return a + Math.sqrt(random * (b - a) * (c - a)); // equation to
																// sample from
																// distribution
		} else
		{
			return b - Math.sqrt((1 - random) * (b - a) * (b - c)); // equation
																	// to sample
																	// from
																	// distribution
		}
	}

	/**
	 * This method allows you to sample from a triangular distribution
	 * 
	 * @param minInput
	 *            the minimum of the distribution
	 * @param modeInput
	 *            the mode of the distribution
	 * @param maxInput
	 *            the maximum of the distribution
	 * @return the number sampled
	 */
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
