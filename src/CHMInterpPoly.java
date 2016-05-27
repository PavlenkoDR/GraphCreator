import java.io.File;


public class CHMInterpPoly extends BasePoly{

	public CHMInterpPoly(int _size, double[] _X, double[] _F) {
		super(_size, _X, _F);
		a = new double[size];
	}
	public CHMInterpPoly(File f) {
		super(f);
		a = new double[size];
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
}
