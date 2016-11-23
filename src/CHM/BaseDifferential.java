
package CHM;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Graph.Function;
import Graph.GraphFrame;
import MathPars.MatchParser;


public class BaseDifferential {
	protected MatchParser p = new MatchParser();
	protected double leftX, rightX, h, y0;
	protected String func;
	protected double[][] XY;
	protected int size = 0;

	public double[] getX() {return XY[0];}
	public double[] getY() {return XY[1];}
	public int getSize()   {return size;}
	
	private BaseDifferential(){}
	protected BaseDifferential(String _func, double _leftX, double _rightX, double n, double _y0) {
    	leftX = _leftX;
    	rightX = _rightX;
    	h = (leftX - rightX)/n;
    	y0 = _y0;
    	func = _func;
    	try {
			XY = getSolve();
			size = XY[0].length;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//p.setVariable("x", (double)i);
    	//p.Parse(func);
	}

	protected static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	return new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    }
    
    protected double function(double x, double y)
	{
    	p.setVariable("x", x);
    	//p.setVariable("y", y);
		try {
			return p.Parse(func);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public double[][] getSolve() throws Exception
	{
		return null;
	}

	public void printSolve(int dec_count)
	{
		for (int i = 0; i < XY[0].length; i++)
		{
			System.out.println(GetRound(XY[0][i], dec_count) + " " + GetRound(XY[1][i], dec_count));
		}
	}

	public void paintSolve(double c, GraphFrame frame)
	{
    	for (int i = 0; i < size; i++)
    		XY[1][i] = XY[1][i] - c;
    	frame.AddFunc(new Function(XY[0].length, XY[0], XY[1]), new Color(122, 255, 0), "Euler");
	}
	
	public double[] getError(String s)
	{
		double err[] = new double[XY[1].length];
		p.setVariable("x", XY[0][0]);
		double C = 0;
		try {
			C = Math.abs(p.Parse(s) - XY[1][0]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < size; i++)
		{
			p.setVariable("x", XY[0][i]);
			try {
				err[i] = - p.Parse(s) + XY[1][i] + C;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return err;
	}

	protected void exportSolve(int dec_count, String name)
	{
		File file = new File(name);
		FileWriter fout;
		try {
			fout = new FileWriter(file);
			fout.write(XY[0].length + "\n");
			for (int i = 0; i < XY[0].length; i++)
			{
				fout.write(GetRound(XY[0][i], dec_count) + " " + GetRound(XY[1][i], dec_count) + "\n");
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
