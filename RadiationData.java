import java.util.*;

public class RadiationData {
    private List<Double> radiationHistory;

    public RadiationData() {
        radiationHistory = new ArrayList<>();

        radiationHistory.add(0.4);
        radiationHistory.add(0.5);
        radiationHistory.add(0.6);
    }

    public void addData(double value) {
        radiationHistory.add(value);

        if (radiationHistory.size() > 10) {
            radiationHistory.remove(0);
        }
    }

    public List<Double> getHistory() {
        return radiationHistory;
    }
}