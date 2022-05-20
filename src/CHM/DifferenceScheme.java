package CHM;

/*

t,j
^
|--
| | - tau
|--
|            h
|           _|_
|          |   |
-----------------------> x,i

 */


import java.util.Arrays;

public class DifferenceScheme {
	final double C = 1.0/2.0;
	double tau;
	double h;
	double l_0;
	double l;
	double u[][];

	public class Answer{
		public double[] t_tau;
		public double[] x_h;
		public double[][] exact_u;
		public double[][] u;
		public double[][] error_absolute;
		public double error_absolute_delta;
	}
	public Answer answer;

	public DifferenceScheme(double tau, double l_0, double l, int n) {
		this.tau = tau;
		this.l = l;
		this.l_0 = l_0;
		this.h = l / n;
		this.u = new double[n][n];

		answer = new Answer();
		answer.t_tau = new double[n];
		answer.x_h = new double[n];
		answer.exact_u = new double[n][n];
		answer.u = new double[n][n];
		answer.error_absolute = new double[n][n];

		for (int j = 0; j < n; ++j) {
			for (int i = 0; i < n; ++i) {
				u[i][j] = 0;
			}
		}

		for (int j = 0; j < n; ++j) {
			answer.t_tau[j] = tau * j;
		}
		for (int i = 0; i < n; ++i) {
			answer.x_h[i] = l_0 + i * h;
		}
		for (int i = 0; i < n; ++i) {
			u[i][0] = mu1(answer.x_h[i]);
			u[i][1] = mu2(answer.x_h[i]) * tau + u[i][0];
		}
		for (int j = 0; j < n; ++j) {
			u[0][j] = 0;
			u[n-1][j] = 0;
		}
		double tau_pow2 =  Math.pow(tau, 2);
		double C_pow2 =  Math.pow(C, 2);
		double tau_C_pow2 =  tau_pow2 * C_pow2;
		double h_pow2 =  Math.pow(h, 2);
		double tau_C_pow2_div_h_pow2 = tau_C_pow2 / h_pow2;
		for (int j = 1; j < n - 1; ++j) {
			for (int i = 1; i < n - 1; ++i) {
				double f_answer = f(answer.x_h[i], answer.t_tau[j]);
				u[i][j + 1] =
					2 * u[i][j] * (1 - tau_C_pow2_div_h_pow2) +
					tau_C_pow2_div_h_pow2 * u[i + 1][j] +
					tau_C_pow2_div_h_pow2 * u[i - 1][j] -
					u[i][j-1] +
					tau_pow2 * f_answer;
			}
		}

//		double[] B = new double[n];
//		for (int j = 0; j < n; ++j) {
//			for (int i = 0; i < n; ++i) {
//				double f_answer = f(answer.x_h[i], answer.t_tau[j]);
//				B[i] = f_answer;
//			}
//			double[] result = KramerSLAE(u, B);
//			for (int i = 0; i < n; ++i) {
//				answer.u[j][i] = result[i];
//			}
//		}

		for (int j = 0; j < n; ++j) {
			for (int i = 0; i < n; ++i) {
				answer.u[j][i] = u[j][i];
			}
		}
		for (int j = 0; j < n; ++j) {
			for (int i = 0; i < n; ++i) {
				answer.exact_u[i][j] = exact_u_i_j(answer.x_h[i], answer.t_tau[j]);
				answer.error_absolute[i][j] = Math.abs(answer.exact_u[i][j] - answer.u[i][j]);
				answer.error_absolute_delta += answer.error_absolute[i][j] / (n * n);
			}
		}
	}

	public DifferenceScheme() {
		this(0.01, 0.0, 1.0, 10);
	}

	public static double matrixDeterminant(double A[][]){
		double delta = 0;
		int n = A.length;
		for (int j = 0; j < n; ++j) {
			double delta_plus = 1;
			double delta_minus = 1;
			for (int i = 0; i < n; ++i) {
				delta_plus *= A[i][(i + j) % n];
				delta_minus *= A[n - i - 1][(i + j) % n];
			}
			delta += delta_plus - delta_minus;
		}
		return delta;
	}

	public static double[] sweepSLAE(double[][] A, double[] B) {
		int n = A.length;

		double[] x = new double[n];

		double[] d = new double[n + 1];
		double[][] s = new double[n + 1][n + 1];
		double[][] a = new double[n + 1][n + 1];
		double[] b = new double[n + 1];

		for (int i = 0; i < n; ++i){
			b[i+1] = B[i];
			for (int j = 0; j < n; ++j){
				a[i+1][j+1] = A[i][j];
			}
		}

		// for i = 1
		d[1] = Math.signum(a[1][1]);
		s[1][1] = Math.sqrt(Math.abs(a[1][1]));
		for (int j = 2; j <= n; j++) {
			s[1][j] = a[1][j] / (s[1][1] * d[1]);
		}

		// for i > 1
		//searching S and D matrix
		for (int i = 2; i <= n; i++) {
			double sum = 0;
			for (int k = 1; k <= i - 1; k++) {
				sum += s[k][i] * s[k][i] * d[k];
			}
			d[i] = Math.signum(a[i][i] - sum);
			s[i][i] = Math.sqrt(Math.abs(a[i][i] - sum));

			double l = 1 / (s[i][i] * d[i]);
			for (int j = i + 1; j <= n; j++) {
				double SDSsum = 0;
				for (int k = 1; k <= i - 1; k++) {
					SDSsum += s[k][i] * d[k] * s[k][j];
				}
				s[i][j] = l * (a[i][j] - SDSsum);
			}
		}

		//solve of the system (s^t * d)y = b
		double[] y = new double[n + 1];
		y[1] = b[1] / (s[1][1] * d[1]);

		for (int i = 2; i <= n; i++) {
			double sum = 0;

			for (int j = 1; j <= i - 1; j++) {
				sum += s[j][i] * d[j] * y[j];
			}

			y[i] = (b[i] - sum) / (s[i][i] * d[i]);
		}

		//solve of the system sx = y
		x[n - 1] = y[n] / s[n][n];

		for (int i = n - 1; i >= 1; i--) {
			double sum = 0;

			for (int k = i + 1; k <= n; k++) {
				sum += s[i][k] * x[k - 1];
			}

			x[i - 1] = (y[i] - sum) / s[i][i];
		}

		//output
		return x;
	}

	public static double[] KramerSLAE(double[][] A, double[] B){
		int n = A.length;
		double[] x = new double[n];
		boolean isMatrixHomogeneous = true;
		for (int i = 0; i < n; ++i) {
			if (B[i] != 0) {
				isMatrixHomogeneous = false;
			}
			x[i] = 0;
		}
		if (isMatrixHomogeneous){
			return x;
		}
		double delta = matrixDeterminant(A);
		double[] delta_x = new double[n];
		double[][] A_copy = new double[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				for (int k = 0; k < n; ++k) {
					A_copy[j][k] = A[j][k];
				}
			}
			for (int j = 0; j < n; ++j) {
				A_copy[j][i] = B[j];
			}
			delta_x[i] = matrixDeterminant(A_copy);
		}
		for (int i = 0; i < n; ++i) {
			x[i] = delta_x[i] / delta;
		}
		return x;
	}

	public double exact_u_i_j(double x_i, double t_j) {
		return Math.sin(2 * Math.PI * x_i * t_j);
		//return Math.pow(x_i, 2) + Math.pow(t_j, 2);
		//return Math.pow(x_i + 1, 3) + Math.pow(t_j + 1, 3);
		//return Math.sin(x_i + t_j);
	}

	public double exact_diff_t_u_i_j(double x_i, double t_j) {
		return 2 * Math.PI * x_i * Math.cos(2 * Math.PI * x_i * t_j);
		//return 2 * t_j;
		//return 3 * Math.pow(t_j + 1, 2);
		//return Math.cos(x_i + t_j);
	}

	public double f(double x_i, double t_j) {
		return          -4 * Math.pow(Math.PI, 2) * Math.pow(x_i, 2) * Math.sin(2 * Math.PI * x_i * t_j) +
		Math.pow(C, 2) * 4 * Math.pow(Math.PI, 2) * Math.pow(t_j, 2) * Math.sin(2 * Math.PI * x_i * t_j);

		//return 2 - Math.pow(C, 2) * 2;
		//return 6 * (x_i + 1) - Math.pow(C, 2) * 6 * (t_j + 1);
		//return -Math.sin(x_i + t_j) + Math.pow(C, 2) * Math.sin(x_i + t_j);
	}

	public double mu1(double x_i) {
		return exact_u_i_j(x_i, 0);
	}

	public double mu2(double x_i) {
		return exact_diff_t_u_i_j(x_i, 0);
	}

	public double u_0_i(double x_i) {
		return mu1(x_i);
	}

	public double u_1_i(double x_i) {
		return mu2(x_i) * tau + u_0_i(x_i);
	}

	public double u_j_0(double t_j) {
		return 0;
	}

	public double u_j_l(double t_j) {
		return 0;
	}
}
