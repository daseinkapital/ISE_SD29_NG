import java.util.ArrayList;

/**
 * This class runs a simulation and outputs all of the results of those simulations
 * 
 * @author Rebeca Dominguez
 */
public class Simulation
{
	//the maximum number of iterations
	int numberOfIterations;
	//the list of parts to put through the simulation
	ArrayList<Part> listOfParts;
	//the lifetime of the aircraft in operating hours
	int aircraftLifetime;

	/**
	 * This method creates a Simulation object.
	 * @param inputNumberOfIterations the number of iterations to put the simulation through
	 * @param inputListOfParts the parts to simulate
	 * @param inputAircraftLifetime the lifetime of the aircraft (or when to stop the simulation)
	 */
	public Simulation(int inputNumberOfIterations, ArrayList<Part> inputListOfParts, int inputAircraftLifetime)
	{
		numberOfIterations = inputNumberOfIterations;
		listOfParts = inputListOfParts;
		aircraftLifetime = inputAircraftLifetime;
	}

	/**
	 * This method goes through the number of iterations and collects the results into one place
	 * @return the MCR and CPFH results of the multiple iterations
	 */
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

	/**
	 * This method runs through a single iteration for a simulation
	 * @return the MCR and CPFH of the simulation
	 */
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

	/**
	 * This method returns the index of the next part to break.
	 * @return the index of the next part to break.
	 */
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

	/**
	 * This method resets all of the failureTimes since they've all been changed from the iteration.
	 */
	private void resetForNextIteration()
	{
		for (int i = 0; i < listOfParts.size(); i++)
		{
			listOfParts.get(i).resetFailureTime();
		}
	}
}
