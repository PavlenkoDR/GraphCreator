package CHM;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ChmFunctions.Function;
import Graph.GraphFrame;
import MathPars.MatchParser;

public class ExplicitDfference extends BaseDifferential{
	double [][]U;
	double X[];
	double T[];
	protected MatchParser p = new MatchParser();
	public ExplicitDfference(String _func, double _a, double _b, double _c, int _kol_x, int _kol_y) {
		super(_func, _a, _b, _c, _kol_x, _kol_y);
		
		if (h < tao)
			try {
				throw new Exception("Error! h < tao: " + h + " " + tao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
		
		//System.out.println("Error! h >= tao: " + h + " " + tao);
		size = X.length;
	}
	public double[] getX() {return X;}
	public double[] getY() {return T;}

	public double[] getAverageU()
	{
		double[] tmp = new double[U[0].length];
		for (int i = 0; i < tmp.length; i++)
		{
			tmp[i] = 0;
			for(int j = 0; j < U.length; j++)
				tmp[i] += U[j][i];
			tmp[i]/=U.length;
		}
		return tmp;
	}
	
	private double phi(double x)
	{
	  return function(x, 0);
	}
	
	private double psi(double t)
	{
	  return function(0, t);
	}
	
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		int k = 0;
		//size = kol_x;
		X = new double[kol_x];
		T = new double[kol_y];
		for (double i = leftX; i <= rightX; i+=h)
		{
			if (k >= kol_x) break;
			X[k] = i;
			k++;
		}
		k=0;
		for (double i = 0; i <= y0; i+=tao)
		{
			if (k >= kol_y) break;
			T[k] = i;
			k++;
		}

		U = new double[kol_y][kol_x];
		for (int j = 0; j < kol_y; j++)
			for (int i = 0; i < kol_x; i++)
				U[j][i] = 0;
		for (int i = 0; i < kol_x; i++)
		{
			U[0][i] = phi(X[i]);
		}
		for (int i = 0; i < kol_y; i++)
		{
			U[i][0] = psi(T[i]);
		}
		for (int i = 0; i < kol_x - 1; i++)
			for (int j = 1; j < kol_y; j++)
				U[j][i+1] = h*(function(X[i], T[j]) - (U[j][i] - U[j-1][i])/tao) + U[j][i];

		return U;
	}
	
	public double[][] getU()
	{
		return U;
	}
	
	public void exportSolve(int dec_count)
	{

		File file = new File("ExplicitDfference.txt");
		FileWriter fout;
		try {
			fout = new FileWriter(file);
			fout.write(U.length + "\n");
			for (int i = 0; i < U.length; i++)
			{
				for (int j = 0; j < U[0].length; j++)
				{
					fout.write(GetRound(U[i][j], dec_count) + " ");
				}
				fout.write("\n\n");
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public double[] getError(String s)
	{
		double err[] = new double[U[0].length];
		p.setVariable("x", X[0]);
		double C = 0;
		try {
			C = Math.abs(p.Parse(s) - U[U.length-1][0]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < size; i++)
		{
			p.setVariable("x", X[i]);
			try {
				err[i] = -p.Parse(s) + U[U.length-1][i] + C;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return err;
	}
	@Override
	public void paintError(String func, double scale, GraphFrame frame, String name, Color color)
	{
		double Y[] = this.getError(func);
    	for (int i = 0; i < size; i++)
    		Y[i] *= scale;
    	Function errfunc = new Function(size, X, Y);
    	frame.AddFunc(errfunc, color, name);
	}
	
}