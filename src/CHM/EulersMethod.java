package CHM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Graph.Function;
import Graph.GraphFrame;
import MathPars.MatchParser;


public class EulersMethod {
	private MatchParser p = new MatchParser();
	private double leftX, rightX, h, y0;
	private String func;
	private double[][] XY;
	public int size = 0;

	public double[] getX() {return XY[0];}
	public double[] getY() {return XY[1];}
	
	public EulersMethod(String _func, double _leftX, double _rightX, double _h, double _y0) {
    	leftX = _leftX;
    	rightX = _rightX;
    	h = _h;
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

    public static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	return new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    }
    
	private double function(double x, double y)
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
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		double [][] xy = new double [2][(int)Math.abs((rightX - leftX)/h + h) + 1];
		int k = 1;
		xy[0][0] = leftX;
		xy[1][0] = y0;
		for (double i = leftX + h; i <= rightX; i += h)
		{
			xy[0][k] = i;
			//System.out.print(k + " ");
			xy[1][k] = xy[1][k-1] + function(xy[0][k-1], xy[1][k-1])*h;
			k++;
		}
		System.out.println();
		return xy;
		
	}

	public void printSolve(int dec_count)
	{
		for (int i = 0; i < XY[0].length; i++)
		{
			System.out.println(GetRound(XY[0][i], dec_count) + " " + GetRound(XY[1][i], dec_count));
		}
	}

	public GraphFrame paintSolve(double c)
	{
    	GraphFrame frame = new GraphFrame("Frame", 800, 600);
    	//frame.SetDrawExtraPoly(true);
    	frame.SetDrawLine(true);
    	frame.requestFocusInWindow();
    	double Y[] = new double[size];
    	for (int i = 0; i < size; i++)
    		Y[i] = -XY[1][i] - c;
    	frame.setSize(800, 600);
    	frame.SetFunc(new Function(XY[0].length, XY[0], Y));
    	GraphFrame.setQuality(10);
    	return frame;
	}
	
	public double[] getError(String s)
	{
		double err[] = new double[XY[1].length];
		p.setVariable("x", XY[0][0]);
		double C = 0;
		try {
			C = Math.abs(p.Parse(s) - XY[1][0]);
			System.out.println("!!" + C);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < size; i++)
		{
			p.setVariable("x", XY[0][i]);
			try {
				err[i] = -Math.abs(Math.abs(p.Parse(s) - XY[1][i]) - C);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return err;
	}

	public void exportSolve(int dec_count)
	{
		File file = new File("EulersMethodExport.txt");
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
