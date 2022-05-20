
package CHM;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import ChmFunctions.Function;
import Graph.GraphFrame;
import Graph.Paint;
import MathPars.MatchParser;


public class BaseDifferential {
	public interface FunctionProvider {
		public double function(double x);
	}
	protected MatchParser p = new MatchParser();
	protected double leftX, rightX, h, y0, tao;
	protected String func;
	protected double[][] XY;
	protected int size = 0, kol_x, kol_y;

	public double[] getX() {return XY[0];}
	public double[] getY() {return XY[1];}
	public int getSize()   {return size;}
	
	private BaseDifferential(){}
	protected BaseDifferential(String _func, double _leftX, double _rightX, double n, double _y0) {
    	leftX = _leftX;
    	rightX = _rightX;
    	h = (rightX - leftX)/(n-1);
    	y0 = _y0;
    	func = _func;
    	try {
			XY = getSolve();
			size = XY[0].length;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("left: " + leftX);
			System.err.println("right: " + rightX);
			System.err.println("h: " + h);
			System.err.println("Log:");
			e.printStackTrace();
		}
	}
	protected BaseDifferential(String _func, double _leftX, double _rightX, double _y0, int _kol_x, int _kol_y) {
    	leftX = _leftX;
    	rightX = _rightX;
    	kol_x = _kol_x;
    	kol_y = _kol_y;
    	y0 = _y0;
    	h = (rightX - leftX)/(kol_x-1);
    	tao = (y0 - leftX)/(kol_y-1);
    	func = _func;
    	try {
			XY = getSolve();
			size = XY[0].length;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("left: " + leftX);
			System.err.println("right: " + rightX);
			System.err.println("y0: " + y0);
			System.err.println("kol_x: " + kol_x);
			System.err.println("kol_y: " + kol_y);
			System.err.println("(y0 - leftX)/tao: " + (y0 - leftX)/tao);
			System.err.println("h: " + h);
			System.err.println("tao: " + tao);
			System.err.println("Log:");
			e.printStackTrace();
		}
	}

	protected static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	double ans = 0;
    	try
    	{
    		ans = new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    	}
    	catch(Exception e)
    	{
    		ans = 0;
    	}
    	return ans;
    }
    
    protected double function(double x, double y)
	{
    	//*
    	p.setVariable("x", x);
    	p.setVariable("y", y);
		try {
			//System.out.println(func);
			return p.Parse(func);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		//*/
    	//return sin(x)-2*y;
	}
    
    public void exportFunctionAnswer(FunctionProvider provider, int dec_count)
    {
		File file = new File("Answer.txt");
		FileWriter fout;
		try {
			fout = new FileWriter(file);
			fout.write(kol_x + "\n");
			for (double i = leftX; i < rightX; i+=h)
			{
				fout.write(i + " " + provider.function(i) + "\n");
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static double [][]returnfunctionAnswer(FunctionProvider provider, double left, double right, int n)
    {
    	double tmp[][] = new double[2][n];
    	for (int i = 0; i < n; i++)
    	{
    		System.out.print(left + (double)i*(right-left)/(double)n);
    		System.out.print(" ");
    		System.out.println(provider.function(left + ((double)i)*(right-left)/((double)n)));
    		tmp[0][i] = (left + ((double)i)*(right-left)/((double)n));
    		tmp[1][i] = -(provider.function(left + (double)i*(right-left)/(double)n));
    	}
    	return tmp;
    }
    
    protected double df(double x, double y)
	{
    	double delta = 0.0001;
    	p.setVariable("x", x + delta);
    	p.setVariable("y", y);
		try {
			double deltaX = p.Parse(func);
	    	p.setVariable("x", x);
			return ((deltaX - p.Parse(func))/delta);
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

	public void paintSolve(double c, GraphFrame frame, String name, Color color)
	{
		double Y[] = new double[XY[0].length];
    	for (int i = 0; i < size; i++)
    		Y[i] = -XY[1][i] - c;
    	frame.AddFunc(new Function(XY[0].length, XY[0], Y), color, name);
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
				err[i] = -p.Parse(s) + XY[1][i] + C;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return err;
	}
	
	public double[] getError(FunctionProvider provider)
	{
		double err[] = new double[XY[1].length];
		double C = Math.abs(provider.function(XY[0][0]) - XY[1][0]);
		for (int i = 0; i < size; i++)
		{
			err[i] = -provider.function(XY[0][i]) + XY[1][i] + C;
		}
		return err;
	}
	public double getAverageError(String s)
	{
		double err[] = getError(s);
		double sumErr = 0;
		double sumSolve = 0;
		int k = 0;
		try {
			for (double i = leftX; i <= rightX; i+=h)
			{
				p.setVariable("x", i);
				sumErr += Math.abs(err[k]);
				sumSolve += Math.abs(p.Parse(s));
				k++;
				if (k == err.length) break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sumErr/sumSolve*100;
	}

	public void paintError(String func, double scale, GraphFrame frame, String name, Color color)
	{
		double Y[] = getError(func);
    	for (int i = 0; i < size; i++)
    		Y[i] *= scale;
    	Function errfunc = new Function(size, XY[0], Y);
    	frame.AddFunc(errfunc, color, name);
	}

	public void paintError(FunctionProvider provider, double scale, GraphFrame frame, String name, Color color)
	{
		double Y[] = getError(provider);
    	for (int i = 0; i < size; i++)
    		Y[i] *= scale;
    	Function errfunc = new Function(size, XY[0], Y);
    	frame.AddFunc(errfunc, color, name);
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
