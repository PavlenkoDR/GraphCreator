package CHM;

public class EulersMethod extends BaseDifferential{
	public EulersMethod(String _func, double _leftX, double _rightX, double n, double _y0) {
		super(_func, _leftX, _rightX, n, _y0);
	}
	@Override
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		double [][] xy = new double [2][(int)Math.abs((rightX - leftX)/h + h) + 1];
		int k = 1;
		xy[0][0] = leftX;
		xy[1][0] = y0;
		for (double i = leftX + h; i <= rightX; i += h)
		{
			xy[0][k] = i;
			xy[1][k] = xy[1][k-1] + h*function(xy[0][k-1], xy[1][k-1]);
			k++;
		}
		if (k < xy[1].length)
			xy[1][k] = xy[1][k-1] + h*function(xy[0][k-1], xy[1][k-1]);
		return xy;
	}
	public void exportSolve(int dec_count)
	{
		exportSolve(dec_count, "EulersMethod.txt");
	}
}
