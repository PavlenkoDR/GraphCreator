package ChmFunctions;
import java.io.File;

public class Approx extends BasePoly{
	double SumArr[];
	double B[];
	
	//Конструктор по X и F
	public Approx(int _size, double[] _X, double[] _F) {
		super(_size, _X, _F);
		A = new double [size][size];
		SumArr = new double[2*size];
		B = new double[size];
	}

	//Конструктор по файлу
	public Approx(File f) {
		super(f);
		A = new double [size][size];
		SumArr = new double[2*size];
		B = new double[size];
	}
	//Выносим в отдельный массив повторяющиеся суммы, чтобы далее их не пересчитывать
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
				sum += tmpx[j];
			SumArr[i] = sum;
			for (int j = 0; j < m; j++)
				tmpx[j] *= X[j];
		}
		for (int i = 0; i < m; i++)
			tmpx[i] = 1;
		for (int i = 0; i < m; i++)
		{
			sum = 0;
			for (int j = 0; j < m; j++)
				sum += tmpx[j]*F[j];
			B[i] = sum;
			for (int j = 0; j < m; j++)
				tmpx[j] *= X[j];
		}
		
	}
	//Инициализировать матрицу по количуству узлов аппроксимации
	public void InitMatrix(int n)
	{
		InitSumm();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				A[i][j] = SumArr[i+j];
		LU = new Solver(n, A);
		a = LU.getSolv(B);
	}
	//Получить значение в заданном узле аппроксимации
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
