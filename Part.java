/**
* This class represents the parts of the plane.
*/
public class Part
{
  private String uniqueID;
  private String partNumber;
  private String description;
  private PartType partType;
  private int quantity;
  private int spareQuantity;
  private float price;
  private Distribution distribution;
  private int failureTime;


  public Part(String inputID, String inputPartNumber, String inputDescription, PartType inputPartType, int inputQuantity, int inputSpareQuantity, float inputPrice, Distribution inputDistribution)
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

  public void generateNewFailureTime(int currentTime)
  {
	  failureTime = currentTime + (int) Math.round(distribution.sampleTriangular());
  }
  
  public float getPrice()
  {
	  return price;
  }
  
  public int getFailureTime()
  {
	  return failureTime;
  }
  
  public void resetFailureTime()
  {
	  failureTime = (int) Math.round(distribution.sampleTriangular());
  }
}
