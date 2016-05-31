
public class CHMSpline
{
	
	static double a = 1, b = 2;
	
	static double[] funcX, funcY,
					splineX, splineY,
					deltaX, deltaY;
	
	static int		sizeFunc,
					sizeSpline,
					sizeDelta;
	
	static boolean GetPointsFlag = false;

	static double Func(double x)
	{
		return Math.pow(x, 3) - Math.cos(2*x);
	}
	
	// Структура, описывающая сплайн на каждом сегменте сетки
	private static spline_tuple splines[] = null; // Сплайн
	private static int n; // Количество узлов сетки

	// Построение сплайна
	// x - узлы сетки, должны быть упорядочены по возрастанию, кратные узлы запрещены
	// y - значения функции в узлах сетки
	// n - количество узлов сетки
	public static void build_spline(double x[], double y[], int _n)
	{
		n = _n;

		// Инициализация массива сплайнов
		splines = new spline_tuple[n];
		for (int i = 0; i < n; ++i)
		{
			splines[i] = new spline_tuple();
			splines[i].x = x[i];
			splines[i].a = y[i];
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
			h_i = x[i] - x[i - 1];
			h_i1 = x[i + 1] - x[i];
			A = h_i;
			C = 2. * (h_i + h_i1);
			B = h_i1;
			F = 6. * ((y[i + 1] - y[i]) / h_i1 - (y[i] - y[i - 1]) / h_i);
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
			h_i = x[i] - x[i - 1];
			splines[i].d = (splines[i].c - splines[i - 1].c) / h_i;
			splines[i].b = h_i * (2. * splines[i].c + splines[i - 1].c) / 6. + (y[i] - y[i - 1]) / h_i;
		}
	}

	// Вычисление значения интерполированной функции в произвольной точке
	public static double GetY(double x)
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
	
	static void CreateDots()
	{
		int n;
		double h;
		
		n = 100;
		sizeFunc = n;
		h = (b-a)/n;
		funcX = new double[n + 1];
		funcY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			funcX[i] = a + h*i;
			funcY[i] = -Func(funcX[i]);
		}
		
		build_spline(funcX, funcY, sizeFunc);
		sizeSpline = n;
		splineX = new double[n + 1];
		splineY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			splineX[i] = a + h*i;
			splineY[i] = GetY(splineX[i]);
		}

		sizeDelta = n;
		deltaX = new double[n + 1];
		deltaY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			deltaX[i] = a + h*i;
			deltaY[i] = -Math.abs(funcY[i] - splineY[i]);
		}
		
		double sum = 0;
		
		for (int i = 0; i <= n; i++)
			sum += -deltaY[i];
		System.out.println("Midle absolute delta: " + sum/(n+1));
		
		sum = 0;
		for (int i = 0; i <= n; i++)
			sum += deltaY[i]*100/funcY[i];
		System.out.println("Midle otnosit delta: " + sum/(n+1) + "%");
		
		GetPointsFlag = true;
	}
	
};
