package CHM;

public class SweepMethod {
	public static double []getSolve(double A[], double B[], double C[], double F[], int n)
	{
		double [] res = new double[n];
		double m;
		for (int i = 1; i < n; i++)
		{
			m = A[i]/C[i-1];
			C[i] = C[i] - m*B[i-1];
			F[i] = F[i] - m*F[i-1];
		}

		res[n-1] = F[n-1]/C[n-1];

		for (int i = n - 2; i >= 0; i--)
			res[i]=(F[i]-B[i]*res[i+1])/C[i];
		return res;	
		/**
		 * n - число уравнений (строк матрицы)
		 * B - диагональ, лежащая над главной (нумеруется: [0;n-2])
		 * C - главная диагональ матрицы A (нумеруется: [0;n-1])
		 * A - диагональ, лежащая под главной (нумеруется: [1;n-1])
		 * F - правая часть (столбец)
		 * res - решение, массив res будет содержать ответ
		 */
	}
}
