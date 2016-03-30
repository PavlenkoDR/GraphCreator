import java.util.Scanner;

public class Solver {
	private double L[][], U[][], A[][];
	private int size = 0;
	private double sum = 0;
	private Scanner in = new Scanner(System.in);
	public int GetSize()
	{
		return size;
	}
	public void Init()
	{
		boolean ask = false;
		if (size != 0)
		{
			System.out.println("Rewrite?\n");
			ask = in.nextBoolean();
		}
		else
			ask = true;
		if (ask)
		{
			System.out.print("Select matrix size n = ");
			size = in.nextInt();
			A = new double[size][size];
			L = new double[size][size];
			U = new double[size][size];

			//задаем матрицу A[][] ...

			for (int i  = 0; i < size; i++)
			{
				for (int j = 0; j < size; j++)
				{
					System.out.print("a[" + i + "][" + j + "] = ");
					A [i][j] = in.nextDouble();

					L [i][j] = 0;
					U [i][j] = 0;

					if (i == j)
						U [i][j] = 1;
				}
			}
			System.out.println();
		}
		else
			return;
	}
	public Solver()
	{
		size = 0;
	}
	public Solver(int _size, double[][]_A)
	{
		size = _size;
		A = _A;
		L = new double[size][size];
		U = new double[size][size];
	}
	public void Init(int _size, double[][]_A)
	{
		boolean ask = false;
		if (size != 0)
		{
			System.out.println("Rewrite?\n");
			ask = in.nextBoolean();
		}
		else
			ask = true;
		if (ask)
		{
			size = _size;
			A = _A;
			L = new double[size][size];
			U = new double[size][size];
		}
		else
			return;
	}
	public void GetLU()
	{
		if (size == 0) 
		{
			System.out.print("!!!!!!!!");
			return;
		}
		//находим первый столбец L[][] и первую строку U[][]

		for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                U[0][i] = A[0][i];
                L[i][0] = A[i][0] / U[0][0];
                double sum = 0;
                for (int k = 0; k < i; k++)
                {
                    sum += L[i][k] * U[k][j];
                }
                U[i][j] = A[i][j] - sum;
                if (i > j)
                {
                    L[j][i] = 0;
                }
                else
                {
                    sum = 0;
                    for (int k = 0; k < i; k++)
                    {
                        sum += L[j][k] * U[k][i];
                    }
                    L[j][i] = (A[j][i] - sum) / U[i][i];
                }
            }
        }
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

		System.out.println("L =");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				System.out.print(L[i][j] + " ");
			System.out.println();
			
		}
		System.out.println();
		
		System.out.println("U =");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				System.out.print(U[i][j] + " ");
			System.out.println();
			
		}
		System.out.println();
	}
	public double[] getSolv(double[] F)
	{
		double y[] = new double[size];
		for (int i = 0; i < size; i++)
		{
			sum = 0;
			for (int j = 0; j < i; j++)
			{
				sum += L[i][j]*y[j];
			}
			y[i] = (F[i] - sum)/L[i][i];
		}
		//for (int i = 0; i < size; i++)
		//	System.out.print(y[i] + " ");
		//System.out.println();

		double x[] = new double[size];
		for (int i = size - 1; i >= 0; i--)
		{
			sum = 0;
			for (int j = size - 1; j > i; j--)
			{
				sum += U[i][j]*x[j];
			}
			x[i] = (y[i] - sum)/U[i][i];
		}	
		//for (int i = 0; i < size; i++)
		//	System.out.print(x[i] + " ");
		return x;
	}
    public static void main(String[] args) {
        Solver obj = new Solver();
        double[] F = new double[4];
        F[0] = -14;
        F[1] = 44;
        F[2] = 142;
        F[3] = -76;
        double [][]_A = 
        {
        	{3, 		4, 		-9, 	5	},
        	{-15, 		-12, 	50, 	-16	},
        	{-27, 		-36, 	73, 	8	},
        	{9, 		12, 	-10, 	-16	}
        };
    	obj.Init(4, _A);
    	obj.GetLU();
    	//obj.OutputData();
    	double[] x = obj.getSolv(F);
    	for (int i = 0; i < obj.GetSize(); i++)
    		System.out.print(x[i] + " ");
    }
}
