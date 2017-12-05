
/**
 * This class represents the generic part of the plane without a lifetime.
 * 
 * @author Rebeca Dominguez
 */
public class Part
{
	// The unique ID of a part
	private String uniqueID;
	// The part number of a part
	private String partNumber;
	// The description given to a part
	private String description;
	// whether the part is consumable or reparable
	private PartType partType;
	// the quantity on a plane of a part
	private int quantity;
	// the quantity kept in inventory
	private int spareQuantity;
	// the price to fix the part
	private float price;
	// the distribution for durability of the part
	private Distribution distribution;
	// the first failure of a part
	private int failureTime;

	/**
	 * This constructor allows the user to build a part.
	 * 
	 * @param inputID
	 *            unique id
	 * @param inputPartNumber
	 *            part number
	 * @param inputDescription
	 *            description
	 * @param inputPartType
	 *            whether part is consumable or reparable
	 * @param inputQuantity
	 *            quantity of part on plane
	 * @param inputSpareQuantity
	 *            number kept in inventory
	 * @param inputPrice
	 *            price to fix part
	 * @param inputDistribution
	 *            distribution to associate to part
	 */
	public Part(String inputID, String inputPartNumber, String inputDescription, PartType inputPartType,
			int inputQuantity, int inputSpareQuantity, float inputPrice, Distribution inputDistribution)
	{
		uniqueID = inputID;
		partNumber = inputPartNumber;
		description = inputDescription;
		partType = inputPartType;
		quantity = inputQuantity;
		spareQuantity = inputSpareQuantity;
		price = inputPrice;
		distribution = inputDistribution;
		failureTime = (int) Math.round(distribution.sampleTriangular());
	}

	/**
	 * Generates a new failureTime given the current time in the simulation
	 * 
	 * @param currentTime
	 *            the current time in the simulation
	 */
	public void generateNewFailureTime(int currentTime)
	{
		failureTime = currentTime + (int) Math.round(distribution.sampleTriangular());
	}

	/**
	 * Gets the price of the part
	 * 
	 * @return the price of the part
	 */
	public float getPrice()
	{
		return price;
	}

	/**
	 * Gets the failureTime of the part
	 * 
	 * @return the failureTime of the part
	 */
	public int getFailureTime()
	{
		return failureTime;
	}

	/**
	 * Resets failureTime from currentTime = 0
	 */
	public void resetFailureTime()
	{
		generateNewFailureTime(0);
	}
}
