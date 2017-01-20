package CHM;

public class IxplicitDfference extends BaseDifferential{

	public IxplicitDfference(String _func, double _leftX, double _rightX,
			double _y0, int _kol_x, int _kol_y) {
		super(_func, _leftX, _rightX, _y0, _kol_x, _kol_y);
		// TODO Auto-generated constructor stub
	}

	  
	double phi(double x)
	{
	    return 0;
	}
	
	double psi(double x)
	{
	    return 0;
	}
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		int k = 0;
		double X[] = new double[kol_x + 1];
		double T[] = new double[kol_y + 1];
		for (double i = leftX; i <= rightX; i+=h)
		{
			X[k] = i;
			k++;
		}
		k=0;
		for (double i = 0; i <= y0; i+=tao)
		{
			T[k] = i;
			k++;
		}
		double alpha[] = new double[kol_x+1];
		double betta[] = new double[kol_x+1];
		double A = 1/Math.pow(h, 2);   
		double C = A;      
	    double B = 1/tao-2/Math.pow(h, 2);
		double [][]U = new double[kol_x+1][kol_y+1];
		double [][]K = new double[kol_x+1][kol_y+1];
		  
		for(int i = 1; i < kol_x+1; i++)
		{
		       U[i][0] = X[i] + 1;  
		       //f(1, i) = phi(x(i));  
		}    
		for (int j = 1; j < kol_y+1; j++)
		{
	        U[0][j] = psi(T[j]);            
	        //%f(1, j) = psi(t(j));
	        U[kol_x][j] = psi(T[j]);      
	        //%f(kol_t+1, j) = psi(t(j));  
		}
		for (int i = 0; i <= kol_x; i++)
			for (int j = 0; j <= kol_y; j++)
			{
				U[i][j] = 0;
				K[i][j] = 0;
			}
		//double x[] = SweepMethod()
	    alpha[1] = -C/B;
	    betta[1] = (df(X[1], T[1]) + U[1][1]/tao)/B;
		for (int j = 0; j < kol_y; j++)
			for (int i = 1; i < kol_x+1; i++)
			{
		        alpha[i] = -A/(alpha[i-1]*C+B);    
		        betta[i] = (betta[i-1]*C - df(X[i], T[j+1]) - U[i-1][j]/tao)/(alpha[i-1]*C+B);     
			}
		
		U[kol_x][kol_y] = betta[kol_x];
		for (int j = kol_y - 1; j >= 0 ; j--)
		{
			for (int i = kol_x - 1; i >= 0; i--)
			{
				U[i][j] = alpha[i+1]*U[i+1][j+1]+betta[i+1]; 
				K[i][j] = function(X[i], T[j]);
			}
		}
		for (int j = 0; j < kol_y; j++)
		{
			for (int i = 0; i < kol_x; i++)
			{
				System.out.print(GetRound(U[i][j], 4) + " ");
			}
			System.out.println();
			System.out.println();
		}
		return U;
	}

	public void exportSolve(int dec_count)
	{
		exportSolve(dec_count, "IxplicitDfference.txt");
	}

}
