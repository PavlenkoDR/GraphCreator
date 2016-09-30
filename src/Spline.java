
public class Spline
{
	public Spline(int _size, double[] _X, double[] _F) {
		X = _X;
		Y = _F;
		n = _size;
		System.out.println(n);
		build_spline();
	}
	class spline_tuple {
		spline_tuple(){}
		double a, b, c, d, x;
	}
	double[]funcX, funcY,
			splineX, splineY;
	
	double[] X, Y;

	private spline_tuple splines[] = null; // Сплайн
	private int n; // Количество узлов сетки

	// Построение сплайна
	// x - узлы сетки, должны быть упорядочены по возрастанию, кратные узлы запрещены
	// y - значения функции в узлах сетки
	// n - количество узлов сетки
	public void build_spline()
	{
		// Инициализация массива сплайнов
		splines = new spline_tuple[n];
		for (int i = 0; i < n; ++i)
		{
			splines[i] = new spline_tuple();
			splines[i].x = X[i];
			splines[i].a = Y[i];
		}
		splines[0].c = 0.0;
		// Решение СЛАУ относительно коэффициентов сплайнов c[i] методом прогонки для трехдиагональных матриц
		// Вычисление прогоночных коэффициентов - прямой ход метода прогонки
		double alpha[] = new double[n - 1];
		double beta[] = new double[n - 1];
		double A = 0, B, C = 0, F = 0, h_i, h_i1, z;
		alpha[0] = beta[0] = 0.;
		for (int i = 1; i < n - 1; ++i)
		{
			h_i = X[i] - X[i - 1];
			h_i1 = X[i + 1] - X[i];
			A = h_i;
			C = 2. * (h_i + h_i1);
			B = h_i1;
			F = 6. * ((Y[i + 1] - Y[i]) / h_i1 - (Y[i] - Y[i - 1]) / h_i);
			z = (A * alpha[i - 1] + C);
			alpha[i] = -B / z;
			beta[i] = (F - A * beta[i - 1]) / z;
		}
		splines[n - 1].c = (F - A * beta[n - 2]) / (C + A * alpha[n - 2]);
		// Нахождение решения - обратный ход метода прогонки
		for (int i = n - 2; i > 0; --i)
			splines[i].c = alpha[i] * splines[i + 1].c + beta[i];
		// По известным коэффициентам c[i] находим значения b[i] и d[i]
		for (int i = n - 1; i > 0; --i)
		{
			h_i = X[i] - X[i - 1];
			splines[i].d = (splines[i].c - splines[i - 1].c) / h_i;
			splines[i].b = h_i * (2. * splines[i].c + splines[i - 1].c) / 6. + (Y[i] - Y[i - 1]) / h_i;
		}
	}

	// Вычисление значения интерполированной функции в произвольной точке
	public double getValue(double x)
	{
		if (splines == null)
			return 0; // Если сплайны ещё не построены - возвращаем NaN
		spline_tuple s;
		if (x <= splines[0].x) // Если x меньше точки сетки x[0] - пользуемся первым эл-том массива
			s = splines[1];
		else if (x >= splines[n - 1].x) // Если x больше точки сетки x[n - 1] - пользуемся последним эл-том массива
			s = splines[n - 1];
		else // Иначе x лежит между граничными точками сетки - производим бинарный поиск нужного эл-та массива
		{
			int i = 0, j = n - 1;
			while (i + 1 < j)
			{
				int k = i + (j - i) / 2;
				if (x <= splines[k].x)
					j = k;
				else
					i = k;
			}
			s = splines[j];
		}

		double dx = (x - s.x);
		return s.a + (s.b + (s.c / 2. + s.d * dx / 6.) * dx) * dx; // Вычисляем значение сплайна в заданной точке.
	}
    
};
