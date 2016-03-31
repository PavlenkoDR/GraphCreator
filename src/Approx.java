import java.io.File;


public class Approx extends Interp{
	
	double SumArr[];
	double B[];
	
	public Approx(int _size, double[] _X, double[] _F) {
		super(_size, _X, _F);
		SumArr = new double[2*size];
		B = new double[size];
		InitSumm();
	}

	public Approx(File f) {
		super(f);
		SumArr = new double[2*size];
		B = new double[size];
		InitSumm();
	}
	
	private void InitSumm()
	{
		int m = size;
		double tmpx[] = new double[m];
		for (int i = 0; i < m; i++)
			tmpx[i] = 1;
		double sum = 0;
		for (int i = 0; i < m*2; i++)
		{
			sum = 0;
			for (int j = 0; j < m; j++)
			{
				sum += tmpx[j];
			}
			SumArr[i] = sum;
			for (int j = 0; j < m; j++)
			{
				tmpx[j] *= X[j];
				//System.out.print(tmpx[j] + " ");
			}
			//System.out.println();
			//System.out.println();
		}
		for (int i = 0; i < m; i++)
			tmpx[i] = 1;
		for (int i = 0; i < m; i++)
		{
			sum = 0;
			for (int j = 0; j < m; j++)
			{
				sum += tmpx[j]*F[j];
			}
			B[i] = sum;
			for (int j = 0; j < m; j++)
			{
				tmpx[j] *= X[j];
				//System.out.print(tmpx[j] + " ");
			}
			//System.out.println();
			//System.out.println();
		}
		
	}
	
	private void InitMatrix(int n)
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				A[i][j] = SumArr[i+j];
			}
		}
		LU = new Solver(n, A);
		a = LU.getSolv(B);
	}
	public double getValue(double x, int n)
	{
		InitMatrix(n);
		double tmp = 0;
		double xtmp = 1;
		for (int i = 0; i < n; i++)
		{
			tmp += xtmp*a[i];
			xtmp *= x;
		}
		return tmp;
	}
}
