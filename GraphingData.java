import java.awt.*;
import java.awt.Graphics;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

public class GraphingData extends JPanel
{
	ArrayList<Float> results;
	final int padding = 200;

	public GraphingData(ArrayList<Float> arrayList)
	{
		if (arrayList.size() > 0)
			results = arrayList;
		else
			System.out.println("Results are empty.");
	}

	protected void paintComponent(Graphics graph)
	{
		ArrayList<Point2D.Float> data = setUpData();
		super.paintChildren(graph);
		Graphics2D graph2 = (Graphics2D) graph;
		graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();

		Line2D.Double xLine = new Line2D.Double(padding, padding, padding, height - padding);
		graph2.setStroke(new BasicStroke(5));
		graph2.draw(xLine);
		Line2D.Double yLine = new Line2D.Double(padding, height - padding, width - padding, height - padding);
		graph2.draw(yLine);
		Font font = graph2.getFont();
		FontRenderContext frc = graph2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();
		// Ordinate label.
		String s = "Cost Per Flight Hour";
		float sy = padding + ((height - 2 * padding) - s.length() * sh) / 2 + lm.getAscent();
		for (int i = 0; i < s.length(); i++)
		{
			String letter = String.valueOf(s.charAt(i));
			float sw = (float) font.getStringBounds(letter, frc).getWidth();
			float sx = (float) (yLine.getX1() - 20);
			graph2.drawString(letter, sx, sy);
			sy += sh;
		}
		// Abcissa label.
		s = "Confidence";
		sy = height - padding + (padding - sh) / 2 + lm.getAscent();
		float sw = (float) font.getStringBounds(s, frc).getWidth();
		float sx = (width - sw) / 2;
		graph2.drawString(s, sx, sy);
		// Draw lines.
		double xInc = (double) (width - 2 * padding) / (data.size() - 1);
		double scale = (double) (height - 2 * padding) / data.get(data.size() -1).getY();
		graph2.setPaint(Color.green.darker());
		for (int i = 0; i < data.size() - 1; i++)
		{
			double x1 = padding + i * xInc;
			double y1 = height - padding - scale * data.get(i).getY();
			double x2 = padding + (i + 1) * xInc;
			double y2 = height - padding - scale * data.get(i + 1).getY();
			graph2.draw(new Line2D.Double(x1, y1, x2, y2));
		}
		// Mark data points.
		graph2.setPaint(Color.red);
		for (int i = 0; i < data.size(); i++)
		{
			double x = padding + i * xInc;
			double y = height - padding - scale * data.get(i).getY();
			graph2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
		}
		
		
	}
	
	private ArrayList<Point2D.Float> setUpData()
	{
		ArrayList<Point2D.Float> data = new ArrayList<Point2D.Float>();
		for (float y = 0; y < results.size(); y++)
		{
			float value = findAndReturnMinimum();
			data.add(new Point2D.Float(y / results.size(), value));
		}
		return data;
	}
	
	private float findAndReturnMinimum ()
	{
		float minimum = Float.MAX_VALUE;
		int index = -1;
		for (int x = 0; x < results.size(); x++)
		{
			if (results.get(x) < minimum)
			{
				minimum = results.get(x);
				index = x;
			}
		}
		if (index > -1)
		{
			results.set(index, Float.MAX_VALUE);
		}
		return minimum;
	}

}
