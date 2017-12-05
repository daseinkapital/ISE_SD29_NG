import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
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
		simulation = new Simulation(0, parts, 0);
		ArrayList<ArrayList<Float>> results = simulation.runSimulation();
	}

	public static ArrayList<Part> readInFile(RandomAccessFile partsFile)
	{
		String line = null;
		ArrayList<Part> parts = new ArrayList<Part>();
		Part currentPart = null;
		PartType partType = null;
		Distribution distribution = null;
		String[] fields;

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
			if (fields[3].equals("Consumable"))
				partType = PartType.CONSUMABLE;
			else
				partType = PartType.AIRCRAFTPART;

			distribution = new Distribution(Float.parseFloat(fields[7]), Float.parseFloat(fields[8]),
					Float.parseFloat(fields[9]));
			currentPart = new Part(fields[0], fields[1], fields[2], partType, Integer.parseInt(fields[4]),
					Integer.parseInt(fields[5]), Float.parseFloat(fields[6]), distribution);
			parts.add(currentPart);
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
}
