import java.util.ArrayList;

public class Simulation
{
	int numberOfIterations;
	ArrayList<Part> listOfParts;
	int aircraftLifetime;

	public Simulation(int inputNumberOfIterations, ArrayList<Part> inputListOfParts, int inputAircraftLifetime)
	{
		numberOfIterations = inputNumberOfIterations;
		listOfParts = inputListOfParts;
		aircraftLifetime = inputAircraftLifetime;
	}

	public ArrayList<ArrayList<Float>> runSimulation()
	{
		ArrayList<Float> MCR = new ArrayList<Float>(numberOfIterations);
		ArrayList<Float> CPFH = new ArrayList<Float>(numberOfIterations);
		ArrayList<ArrayList<Float>> output = new ArrayList<ArrayList<Float>>();
		float[] iterationOutput = new float[2];

		for (int i = 0; i < numberOfIterations; i++)
		{
			iterationOutput = runIteration();
			CPFH.add(iterationOutput[0]);
			MCR.add(iterationOutput[1]);
		}

		output.add(CPFH);
		output.add(MCR);
		return output;
	}

	private float[] runIteration()
	{
		float[] results = new float[2];
		float totalCost = 0;
		int currentTime;
		float totalDowntime = 0;
		int earliestIndex;

		do
		{
			earliestIndex = findEarliestFailureTime();
			if (earliestIndex == -1)
				break;
			totalCost += listOfParts.get(earliestIndex).getPrice();
			currentTime = listOfParts.get(earliestIndex).getFailureTime();
			listOfParts.get(earliestIndex).generateNewFailureTime(currentTime);
		} while (currentTime <= aircraftLifetime);

		results[0] = totalCost / aircraftLifetime;
		results[1] = (float) 0.0;
		resetForNextIteration();
		return results;
	}

	private int findEarliestFailureTime()
	{
		int minFailureTime = aircraftLifetime;
		int indexOfPart = -1;

		for (int i = 0; i < listOfParts.size(); i++)
		{
			if (minFailureTime > listOfParts.get(i).getFailureTime())
			{
				minFailureTime = listOfParts.get(i).getFailureTime();
				indexOfPart = i;
			}
		}
		return indexOfPart;
	}

	private void resetForNextIteration()
	{
		for (int i = 0; i < listOfParts.size(); i++)
		{
			listOfParts.get(i).resetFailureTime();
		}
	}
}
