import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class InterpPoly {

	public double X[];
	public double F[];
	double a[];
	int size;
	Scanner in;
	public InterpPoly(int _size, double[] _X, double[] _F) {
		X = _X;
		F = _F;
		size = _size;
		a = new double[size];
	}
	public InterpPoly(File f) {
		try {
			in = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		size = in.nextInt();
		X = new double[size];
		F = new double[size];
		a = new double[size];
		for (int i = 0; i < size; i++)
			X[i] = in.nextDouble();
		for (int i = 0; i < size; i++)
			F[i] = in.nextDouble();
	}
	public double getValue(double x)
	{
		double tmp = 0;
		for (int i = 0; i < size; i++)
		{
			a[i] = 1;
			for (int j = 0; j < size; j++)
			{
				if (i != j)
				{
					if (X[i] == X[j])
					{
						System.out.println("Error: X[" + i + "] == X[" + j + "]");
						System.exit(0);
					}
					a[i] *= (x - X[j])/(X[i] - X[j]);
				}
			}
			tmp += F[i]*a[i];
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
