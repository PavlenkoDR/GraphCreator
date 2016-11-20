package Graph;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Bicycles {
    public static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	return new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    }
}
