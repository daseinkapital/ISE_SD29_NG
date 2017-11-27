import java.lang.Math;
/**
 * This class contains the ability to create and sample from distributions.
 */
public class Distribution
{
  double min;
  double mode;
  double max;

/**
 * This object holds the fields for the sampling of a distribution
 * @param  double minInput      the minimum of the distribution
 * @param  double modeInput     the maximum of the distribution
 * @param  double maxInput      the mode of the distribution
 * @return       returns the Distribution object with min, mode, and max
 */
  public Distribution(double minInput, double modeInput, double maxInput)
  {
    min = minInput;
    mode = modeInput;
    max = maxInput;
  }

/**
 * This method allows the user to sample a pre-set Distribution object.
 * @return the value pulled from the distribution
 */
  public double sampleTriangular()
  {
    double a = min;
    double b = max;
    double c = mode;
    double F = (c - a) / (b - a);
    double random = Math.random();
    if (random < F)
    {
      return a + Math.sqrt(random * (b - a) * (c - a));
    }
    else
    {
      return b - Math.sqrt((1 - random) * (b - a) * (b - c));
    }
  }

/**
 * This method allows the program to sample from a triangular distribution without having to set up a Distribution object.
 * @param  double minInput      the minimum of the distribution
 * @param  double modeInput     the maximum of the distribution
 * @param  double maxInput      the mode of the distribution
 * @return        the value pulled from the distribution
 */
  public static double sampleTriangular(double minInput, double maxInput, double modeInput)
  {
    double a = minInput;
    double b = maxInput;
    double c = modeInput;
    double F = (c - a) / (b - a);
    double random = Math.random();
    if (random < F)
    {
      return a + Math.sqrt(random * (b - a) * (c - a));
    }
    else
    {
      return b - Math.sqrt((1 - random) * (b - a) * (b - c));
    }
  }
}
