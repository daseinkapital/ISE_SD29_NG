
public class PartWithLifetime extends Part
{
	int lifetime;
	public PartWithLifetime(String inputID, String inputPartNumber, String inputDescription, PartType inputPartType,
			int inputQuantity, int inputSpareQuantity, float inputPrice, Distribution inputDistribution, int inputLifetime)
	{
		super(inputID, inputPartNumber, inputDescription, inputPartType, inputQuantity, inputSpareQuantity, inputPrice,
				inputDistribution);
		lifetime = inputLifetime;
	}

}
