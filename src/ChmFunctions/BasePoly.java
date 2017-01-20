package ChmFunctions;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BasePoly {
	protected double X[];
	protected double F[];
	protected double a[];
	protected int size;
	protected Scanner in;
	protected double A[][];
	protected Solver LU;
	
	public BasePoly(int _size, double[] _X, double[] _F) {
		X = _X;
		F = _F;
		size = _size;
	}
	public BasePoly(File f) {
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
	//Получить значение интерполяции в заданной точке
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
}
