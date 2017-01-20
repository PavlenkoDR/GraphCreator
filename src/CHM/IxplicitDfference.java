package CHM;

public class IxplicitDfference extends BaseDifferential{

	double [][]U;
	double X[];
	double T[];
	public IxplicitDfference(String _func, double _a, double _b, double _c, int _kol_x, int _kol_y) {
		super(_func, _a, _b, _c, _kol_x, _kol_y);
		/*
		if (h < tao)
			try {
				throw new Exception("Error! h < tao: " + h + " " + tao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
		//*/
		//System.out.println("Error! h >= tao: " + h + " " + tao);
		size = X.length;
		// TODO Auto-generated constructor stub
	}

	public double[] getX() {return X;}
	public double[] getY() {return T;}
	
	public double[][] getU()
	{
		return U;
	}
	

	public double[] getAverageU()
	{
		double[] tmp = new double[U[0].length];
		for (int i = 0; i < tmp.length; i++)
		{
			tmp[i] = 0;
			for(int j = 0; j < U.length; j++)
				tmp[i] += U[j][i];
			tmp[i]/=U.length;
		}
		return tmp;
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
		X = new double[kol_x];
		T = new double[kol_y];
		U = new double[kol_y][kol_x];
		for (double i = leftX; i <= rightX; i+=h)
		{
			X[k] = i;
			k++;
			if (k >= kol_x) break;
		}
		k=0;
		for (double i = 0; i <= y0; i+=tao)
		{
			T[k] = i;
			k++;
			if (k >= kol_y) break;
		}
		double alpha[] = new double[kol_x];
		double betta[] = new double[kol_x];
		double A = 1/Math.pow(h, 2);   
		double C = A;      
	    double B = 1/tao-2/Math.pow(h, 2);
	    System.out.println("A: " + A + ", B: " + B + ", C: " + C);
		//double [][]K = new double[kol_x][kol_y];

		for (int i = 0; i < kol_x; i++)
			for (int j = 0; j < kol_y; j++)
			{
				U[j][i] = 0;
				//K[i][j] = 0;
			}
		for(int i = 0; i < kol_x; i++)
		{
		       U[0][i] = X[i] + 1;  
		       //f(1, i) = phi(x(i));  
		}    
		for (int j = 0; j < kol_y; j++)
		{
	        U[j][0] = psi(T[j]);            
	        //%f(1, j) = psi(t(j));
	        U[j][kol_x-1] = psi(T[j]);      
	        //%f(kol_t+1, j) = psi(t(j));  
		}
		//double x[] = SweepMethod()
		for (int j = 1; j < kol_y; j++)
		{
		    alpha[0] = -C/B;
		    betta[0] = (df(X[0], T[0]) + U[j-1][0]/tao)/B;
		    //System.out.println("alpha0: " + alpha[0] + ", betta0: " + betta[0]);
			for (int i = 1; i < kol_x; i++)
			{
		        alpha[i] = -A/(alpha[i-1]*C+B);    
		        betta[i] = (betta[i-1]*C - df(X[i-1], T[j-1]) - U[j-1][i-1]/tao)/(alpha[i-1]*C+B);  
			    //System.out.println("alpha" + i + ": " + alpha[i] + ", betta" + i + ": " + betta[i]); 
				U[j][i] = alpha[i-1]*U[j-1][i-1]+betta[i-1];   
			}
		}
		/*
		for (int j = kol_y - 2; j >= 0 ; j--)
		{
			for (int i = kol_x - 2; i >= 0; i--)
			{
				U[i][j] = alpha[i+1]*U[i+1][j+1]+betta[i+1]; 
				//K[i][j] = function(X[i], T[j]);
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
		*/
		return U;
	}

	public void exportSolve(int dec_count)
	{
		exportSolve(dec_count, "IxplicitDfference.txt");
	}

}
