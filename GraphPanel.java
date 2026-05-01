import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GraphPanel extends JPanel {

    private List<Double> data;
    private double prediction;

    public void setData(List<Double> data, double prediction) {
        this.data = data;
        this.prediction = prediction;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (data == null || data.size() < 2) return;

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int padding = 30;
        int graphWidth = width - 2 * padding;
        int graphHeight = height - 2 * padding;

        int n = data.size();

        double max = prediction;

        for (double val : data) {
            if (val > max) {
                max = val;
            }
        }

        max = max * 1.2;

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.DARK_GRAY);

        g2.drawLine(padding, height - padding, width - padding, height - padding); 
        g2.drawLine(padding, padding, padding, height - padding); 

        g2.setColor(new Color(220, 220, 220));

        for (int i = 1; i <= 5; i++) {
            int y = height - padding - (i * graphHeight / 5);
            g2.drawLine(padding, y, width - padding, y);

            double val = (max * i) / 5;
            g2.setColor(Color.BLACK);
            g2.drawString(String.format("%.1f", val), 5, y + 5);
            g2.setColor(new Color(220, 220, 220));
        }

        g2.setColor(new Color(30, 144, 255)); 
        g2.setStroke(new BasicStroke(3));

        int prevX = 0, prevY = 0;

        for (int i = 0; i < n; i++) {

            int x = padding + (i * graphWidth) / (n - 1);
            int y = height - padding - (int)(data.get(i) * graphHeight / max);

            g2.fillOval(x - 5, y - 5, 10, 10);

            if (i > 0) {
                g2.drawLine(prevX, prevY, x, y);
            }

            prevX = x;
            prevY = y;
        }

        int predX = padding + graphWidth;
        int predY = height - padding - (int)(prediction * graphHeight / max);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));

        g2.fillOval(predX - 6, predY - 6, 12, 12);
        g2.drawLine(prevX, prevY, predX, predY);

        g2.drawString("Prediction", predX - 40, predY - 10);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        g2.drawString("Radiation Level", 10, padding - 5);
        g2.drawString("Time →", width - 70, height - 5);

        g2.drawString("Max: " + String.format("%.2f", max), padding, padding - 20);
    }
}