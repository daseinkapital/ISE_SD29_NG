/**
* This class represents the parts of the plane.
*/
public class Part
{
  String uniqueID;
  String partNumber;
  String description;
  PartType partType;
  int quantity;
  int spareQuantity;
  float price;
  Distribution distribution;


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
  }

  public
}
