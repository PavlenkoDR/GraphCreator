package CHM;

public class RungeKutta4 extends BaseDifferential{
	public RungeKutta4(String _func, double _leftX, double _rightX, double _h, double _y0) {
		super(_func, _leftX, _rightX, _h, _y0);
	}
	@Override
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		double [][] xy = new double [2][(int)Math.abs((rightX - leftX)/h + h)];
		int k = 1;
		xy[0][0] = leftX;
		xy[1][0] = y0;
		double k1, k2, k3, k4, deltay;
		for (double i = leftX + h; i <= rightX; i += h)
		{
			if (k > (rightX - leftX)/h + h - 1) break;
			xy[0][k] = i;
			k1 = function(xy[0][k-1], xy[1][k-1]);
			k2 = function(xy[0][k-1] + h/2, xy[1][k-1] + h*k1/2);
			k3 = function(xy[0][k-1] + h/2, xy[1][k-1] + h*k2/2);
			k4 = function(xy[0][k-1] + h, xy[1][k-1] + h*k3);
			deltay = h*(k1+ 2*k2 + 2*k3 + k4)/6;
			xy[1][k] = xy[1][k-1] + deltay;
			k++;
		}
		return xy;
	}
	public void exportSolve(int dec_count)
	{
		exportSolve(dec_count, "RungeKutta4.txt");
	}
}
