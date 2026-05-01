public class Shielding {

    public String getShield(double radiation) {

        String material;
        int thickness;
        double reductionFactor;

        if (radiation > 0.7) {
            material = "Polyethylene";
            thickness = 12; 
            reductionFactor = 0.6; 
        } 
        else if (radiation > 0.4) {
            material = "Polyethylene + Aluminum";
            thickness = 10; 
            reductionFactor = 0.7;
        } 
        else {
            material = "Aluminum";
            thickness = 5; 
            reductionFactor = 0.85; 
        }

        double remainingRadiation = radiation * reductionFactor;

        String status;
        if (remainingRadiation > 0.5) {
            status = "DANGEROUS ⚠️";
        } 
        else if (remainingRadiation > 0.2) {
            status = "MODERATE ⚠️";
        } 
        else {
            status = "SAFE ✅";
        }

        return "Material: " + material +
               "\nThickness: " + thickness + " cm" +
               "\nRemaining Radiation: " + String.format("%.2f", remainingRadiation) +
               "\nStatus: " + status;
    }
}