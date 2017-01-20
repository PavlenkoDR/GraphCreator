package ChmFunctions;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Interp {
	public double X[];
	public double F[];
	protected double a[];
	int size;
	protected double A[][];
	Solver LU;
	Scanner in;
	public Interp(int _size, double[] _X, double[] _F) {
		X = _X;
		F = _F;
		size = _size;
		A = new double [size][size];
		InitMatrix();
	}
	public Interp(File f) {
		try {
			in = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		size = in.nextInt();
		X = new double[size];
		F = new double[size];
		for (int i = 0; i < size; i++)
			X[i] = in.nextDouble();
		for (int i = 0; i < size; i++)
			F[i] = in.nextDouble();
		A = new double [size][size];
		InitMatrix();
	}
	public void InitMatrix()
	{
		/*
		for (int i = 0; i < size - 1; i++)
		{
			for (int j = i + 1; j < size; j++)
			{
				if (X[i] == X[j])
				{
					//System.out.println("Error: X[" + i + "] == X[" + j + "]");
					try {
						throw new Exception("Error: X[" + i + "] == X[" + j + "]");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		*/
		double tmp;
		for (int i = 0; i < size; i++)
		{
			tmp = 1;
			for (int j = 0; j < size; j++)
			{
				A[i][j] = tmp;
				tmp *= X[i];
			}
		}
		LU = new Solver(size, A);
		a = LU.getSolv(F);
	}
	public double getValue(double x)
	{
		double tmp = 0;
		double xtmp = 1;
		for (int i = 0; i < size; i++)
		{
			tmp += xtmp*a[i];
			xtmp *= x;
		}
		return tmp;
	}
	public double [] GetPointsX()
	{
		return X;
	}
	public double [] GetPointsY()
	{
		return F;
	}
	public int GetSize()
	{
		return size;
	}
}
