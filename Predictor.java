import java.util.*;

public class Predictor {

    public double predict(List<Double> history) {
        int n = history.size();

        if (n < 3) return history.get(n - 1);

        double d1 = history.get(n - 1);
        double d2 = history.get(n - 2);
        double d3 = history.get(n - 3);

        return (0.5 * d1) + (0.3 * d2) + (0.2 * d3);
    }
}
