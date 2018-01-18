import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The class that runs the program and creates the GUI.
 * 
 * @author Rebeca Dominguez
 */
public class Runner
{
	// the simulation to run
	static Simulation simulation;
	static File returnFile;

	/**
	 * This is the main method that runs.
	 * 
	 * @param args
	 *            the arguments form the command line
	 */
	public static void main(String args[])
	{
		launchFrame();
		RandomAccessFile partFile = null;

		if (args.length > 1)
		{
			System.out.println("Wrong number of arugments used. Please only include file name.");
			return;
		}

		try
		{
			if (returnFile != null)
				partFile = new RandomAccessFile(returnFile, "r");
			else
				System.exit(0);
		} catch (IOException e1)
		{
			System.out.println("Issue with opening file.");
		}

		ArrayList<Part> parts = readInFile(partFile);
		simulation = new Simulation(1000, parts, 10000);
		ArrayList<ArrayList<Float>> results = simulation.runSimulation();
		System.out.println(results.get(0).toString());
	}

	/**
	 * This method reads the file in and creates a list of parts
	 * 
	 * @param partsFile
	 *            the file opened to read data in from
	 * @return the ArrayList of parts
	 */
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

			// For PartType
			if (fields[3].equals("Consumable"))
				partType = PartType.CONSUMABLE;
			else
				partType = PartType.AIRCRAFTPART;

			// Give a default value if no quantity is present.
			if (fields[6].equals(""))
				quantity = 1;
			else
				quantity = Integer.parseInt(fields[6]);

			// Give a default value if no spareQuantity is present
			if (fields[7].equals(""))
				spareQuantity = 0;
			else
				spareQuantity = Integer.parseInt(fields[7]);

			// Gives a default price if one is not given
			if (fields[8].equals("") || fields[8] == null || fields[8].contains("-") || fields[8].trim().length() == 0
					|| fields[8].contains("N/A"))
				price = 0;
			else
			{
				String stringPrice = fields[8].trim().substring(1);
				price = Float.parseFloat(stringPrice);
			}

			// determines distribution for part
			distribution = new Distribution(Float.parseFloat(fields[10]), Float.parseFloat(fields[11]),
					Float.parseFloat(fields[12]));

			if (fields[4].equals("")) // if the part does not have a lifetime
			{
				currentPart = new Part(fields[0], fields[1], fields[2], partType, quantity, spareQuantity, price,
						distribution);
				for (int x = quantity; x > 0; x--)
				{
					parts.add(currentPart);
				}
			} else // if the part has a lifetime
			{
				currentPartWithLifetime = new PartWithLifetime(fields[0], fields[1], fields[2], partType, quantity,
						spareQuantity, price, distribution, Integer.parseInt(fields[4]));
				for (int y = quantity; y > 0; y--)
				{
					parts.add(currentPartWithLifetime);
				}
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

	/**
	 * Since the input file has numbers stored as text, we need to ensure that
	 * commas don't seperate numbers like "1,000". This method ensures that
	 * everything goes into the proper index as a line that would not have that
	 * problem.
	 * 
	 * @param line
	 *            the line to clean up
	 * @return a standard version of the line
	 */
	private static String[] cleanUpLine(String[] line)
	{
		String[] properLine = new String[13];
		int counter = 0;
		if (line.length == 13) // if the line is already standarized
			return line;
		else
		{
			boolean joining = false;
			String toJoin = "";
			for (int i = 0; i < line.length; i++)
			{
				if (joining) // if we're in the middle of something like "1,000"
				{
					toJoin += line[i].replace("\"", "");
					if (line[i].contains("\"")) // if we're at the end of an accidentally split number
					{
						properLine[counter] = toJoin.trim();
						counter++;
						toJoin = "";
						joining = false;
					}
				} else if (line[i].contains("\"")) // if we're at the beginning of an accidentally split number
				{
					joining = true;
					toJoin = line[i].replace("\"", "");
				} else
				{
					properLine[counter] = line[i].trim();
					counter++;
				}
			}
		}
		return properLine;
	}

	private static void launchFrame()
	{
		JFrame frame = new JFrame("CPFH Simulator");
		JDialog dialog = new JDialog(frame, "CPFH Simulator", true);
		JButton accept = new JButton("Upload File");
		JButton cancel = new JButton("Cancel");

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		chooser.setFileFilter(filter);
		chooser.setControlButtonsAreShown(false);

		accept.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				returnFile = chooser.getSelectedFile();
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		dialog.getContentPane().add(chooser);
		dialog.getContentPane().add(accept);
		dialog.getContentPane().add(cancel);
		frame.pack();
		dialog.pack();
		frame.setVisible(true);
		dialog.setVisible(true);

	}
}
