import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Runner
{
	static Simulation simulation;

	public static void main(String args[])
	{
		args = new String[1];
		args[0] = "Test.csv";
		RandomAccessFile partFile = null;
		if (args.length > 1)
		{
			System.out.println("Wrong number of arugments used. Please only include file name.");
			return;
		}

		try
		{
			partFile = new RandomAccessFile(args[0], "r");
		} catch (IOException e1)
		{
			System.out.println("Issue with opening file.");
		}
		ArrayList<Part> parts = readInFile(partFile);
		simulation = new Simulation(10, parts, 10000);
		ArrayList<ArrayList<Float>> results = simulation.runSimulation();
		System.out.println(results.get(0).toString());
	}

	private static ArrayList<Part> readInFile(RandomAccessFile partsFile)
	{
		String line = null;
		ArrayList<Part> parts = new ArrayList<Part>();
		Part currentPart = null;
		PartWithLifetime currentPartWithLifetime = null;
		PartType partType = null;
		Distribution distribution = null;
		String[] fields;
		int lifetime = 0;
		int quantity;
		int spareQuantity;
		float price;

		try
		{
			line = partsFile.readLine();
			line = partsFile.readLine();
		} catch (IOException e)
		{
			System.out.println("File is empty or only contains 1 line.");
		}

		while (line != null)
		{
			fields = line.split(",");
			fields = cleanUpLine(fields);
			if (fields[3].equals("Consumable"))
				partType = PartType.CONSUMABLE;
			else
				partType = PartType.AIRCRAFTPART;
			
			if (fields[6].equals(""))
				quantity = 1;
			else
				quantity = Integer.parseInt(fields[6]);
			
			if (fields[7].equals(""))
				spareQuantity = 0;
			else
				spareQuantity = Integer.parseInt(fields[7]);

			if (fields[8].equals("") || fields[8] == null || fields[8].contains("-") || fields[8].trim().length() == 0 || fields[8].contains("N/A"))
				price = 0;
			else
			{
				String stringPrice = fields[8].trim().substring(1);
				price = Float.parseFloat(stringPrice);
			}
			
			distribution = new Distribution(Float.parseFloat(fields[10]), Float.parseFloat(fields[11]), Float.parseFloat(fields[12]));
			
			if (fields[4].equals(""))
			{
				currentPart = new Part(fields[0], fields[1], fields[2], partType, quantity, spareQuantity, price, distribution);
				parts.add(currentPart);
			}
			else
			{
				currentPartWithLifetime = new PartWithLifetime(fields[0], fields[1], fields[2], partType, quantity, spareQuantity, price, distribution, Integer.parseInt(fields[4]));
				parts.add(currentPartWithLifetime);
			}
			
			try
			{
				line = partsFile.readLine();
			} catch (IOException e)
			{
				System.out.println("Had trouble reading a line.");
			}
		}
		return parts;
	}
	
	private static String[] cleanUpLine(String[] line)
	{
		String[] properLine = new String[13];
		int counter = 0;
		if (line.length == 13)
			return line;
		else
		{
			boolean joining = false;
			String toJoin = "";
			for (int i = 0; i < line.length; i++)
			{
				if (joining)
				{
					toJoin += line[i].replace("\"", "");
					if (line[i].contains("\""))
					{
						properLine[counter] = toJoin.trim();
						counter++;
						toJoin = "";
						joining = false;
					}
				}
				else if (line[i].contains("\""))
				{
					joining = true;
					toJoin = line[i].replace("\"", "");
				}
				else
				{
					properLine[counter] = line[i].trim();
					counter++;
				}
			}
		}
		return properLine;
	}
}
