package CHM;

public class AdamsaMethod4 extends BaseDifferential{
	public AdamsaMethod4(String _func, double _leftX, double _rightX, double _h, double _y0) {
		super(_func, _leftX, _rightX, _h, _y0);
	}
	@Override
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		int n = (int)Math.abs((rightX - leftX)/h + h) + 1;
		double [][] xy = new double [2][n];
		xy[0][0] = leftX;
		xy[1][0] = y0;
		int k;
		double k1, k2, k3, k4, deltay;
		for(k = 1; k <= 3; k++)
		{
			k1 = function(xy[0][k-1], xy[1][k-1]);
			k2 = function(xy[0][k-1] + h/2, xy[1][k-1] + h*k1/2);
			k3 = function(xy[0][k-1] + h/2, xy[1][k-1] + h*k2/2);
			k4 = function(xy[0][k-1] + h, xy[1][k-1] + h*k3);
			deltay = h*(k1+ 2*k2 + 2*k3 + k4)/6;
			xy[0][k] =xy[0][k-1]+h;
			xy[1][k] = xy[1][k-1] + deltay;
		}
		for (k = 3; k < n - 1; k++)
		{
			xy[1][k+1] = xy[1][k]+(h/24)*(55*function(xy[0][k], xy[1][k])-59*function(xy[0][k-1], xy[1][k-1])+37*function(xy[0][k-2], xy[1][k-2])-9*function(xy[0][k-3], xy[1][k-3]));
			xy[0][k+1] = xy[0][k]+h;
		}
		return xy;
	}
	public void exportSolve(int dec_count)
	{
		exportSolve(dec_count, "AdamsaMethod4.txt");
	}
}
