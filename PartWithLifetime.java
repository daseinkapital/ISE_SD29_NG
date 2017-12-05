
/**
 * This is a subclass of part that adds the lifetime limitation.
 * 
 * @author Rebeca Dominguez
 */
public class PartWithLifetime extends Part
{
	// Lifetime of a part, regardless of condition when lifetime is met
	int lifetime;

	/**
	 * This is the constructor for a part with a lifetime.
	 * 
	 * @param inputID
	 *            unique id
	 * @param inputPartNumber
	 *            part number
	 * @param inputDescription
	 *            description of part
	 * @param inputPartType
	 *            whether it is consummable or repairable
	 * @param inputQuantity
	 *            quantity on plane
	 * @param inputSpareQuantity
	 *            inventory
	 * @param inputPrice
	 *            price to fix
	 * @param inputDistribution
	 *            distribution linked to part
	 * @param inputLifetime
	 *            lifetime of part
	 */
	public PartWithLifetime(String inputID, String inputPartNumber, String inputDescription, PartType inputPartType,
			int inputQuantity, int inputSpareQuantity, float inputPrice, Distribution inputDistribution,
			int inputLifetime)
	{
		super(inputID, inputPartNumber, inputDescription, inputPartType, inputQuantity, inputSpareQuantity, inputPrice,
				inputDistribution);
		lifetime = inputLifetime;
	}

}
