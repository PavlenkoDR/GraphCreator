import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Interp {
	private double X[], F[], a[];
	int size;
	private double A[][];
	Solver LU;
	Scanner in;
	public Interp(int _size, double[] _X, double[] _F) {
		X = _X;
		F = _F;
		size = _size;
		A = new double [size][size];
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
		LU.GetLU();
		a = LU.getSolv(F);
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
		LU.GetLU();
		a = LU.getSolv(F);
	}
	public void OutputData()
	{
		System.out.println("A =");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				System.out.print(A[i][j] + " ");
			System.out.println();
		}
		System.out.println();
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
}
