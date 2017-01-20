package ChmFunctions;
import java.io.File;


public class InterpMatrix extends BasePoly{

	//Инициализировать класс по X и F
	public InterpMatrix(int _size, double[] _X, double[] _F) {
		super(_size, _X, _F);
		A = new double [size][size];
		InitMatrix();
	}
	//Инициализировать по файлу
	public InterpMatrix(File f) {
		super(f);
		A = new double [size][size];
		InitMatrix();
	}
	//Создать матрицу. Нужна для LU
	public void InitMatrix()
	{
		for (int i = 0; i < size - 1; i++)
		{
			for (int j = i + 1; j < size; j++)
			{
				if (X[i] == X[j])
				{
					System.out.println("Error: X[" + i + "] == X[" + j + "]");
					System.exit(0);
				}
			}
		}
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
	//Отобразить матрицу
	public void OutputData()
	{
		if (LU == null)
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
		else
		{
			LU.OutputData();
		}
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
