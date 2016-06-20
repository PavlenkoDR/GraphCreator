import java.math.BigDecimal;
import java.math.RoundingMode;


public class Bicycles {
    public static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	return new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    }
    public static double Factorial(int num) {
    	double fact=1;
    	for (int i = 1; i <= num; i++)
    		fact*=i;
    	return fact;
    }

}
