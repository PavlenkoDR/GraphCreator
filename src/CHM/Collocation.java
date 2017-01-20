package CHM;

public class Collocation  extends BaseDifferential{

	protected Collocation(String _func, double _leftX, double _rightX,
			double n, double _y0) {
		super(_func, _leftX, _rightX, n, _y0);
		// TODO Auto-generated constructor stub
	}
	public double[][] getSolve() throws Exception
	{
		if ((leftX > rightX)||(h <= 0)) throw new Exception("incorrect input data");
		double [][] xy = new double [2][(int)Math.abs((rightX - leftX)/h + h) + 1];
		xy[0][0] = leftX;
		xy[1][0] = y0;
		int k = 1;
		for (double i = leftX + h; i <= rightX; i += h)
		{
			xy[0][k] = i;
			if (Math.abs(xy[0][k] - xy[0][0]) < 0.00001) System.out.println("WTF");
			xy[1][k] = xy[1][k-1] + h*function(xy[0][k-1], xy[1][k-1]);
			k++;
		}
		if (k < xy[1].length)
			xy[1][k] = xy[1][k-1] + h*function(xy[0][k-1], xy[1][k-1]);
		return xy;
	}

}

/*
1
function Y = colloc(N)
2
h = (pi/(2 * N))
3
for i=1:1:N-1
4
b(i) = f(i*h);
5
for j=1:1:N-1
6
A(i,j) = d2fi(i*h, j) + fi(i*h,
j);
7
end
8
end
9
A;
10
d = inv(A) * b’;
11
for i=1:1:N+1
12
Y(i) = 0;
13
for j=1:1:N-1
14
Y(i) = Y(i) + d(j) * fi((i-1)*h,
j);
15
end
16
end
17
end
*/