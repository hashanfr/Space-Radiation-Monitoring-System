import java.awt.*;
import javax.swing.*;

public class DashboardUI extends JFrame {

    private RadiationData data;
    private Predictor predictor;
    private Shielding shielding;

    private JTextField inputField;
    private JLabel statusLabel;
    private JLabel outputLabel;
    private GraphPanel graphPanel;

    public DashboardUI() {
        data = new RadiationData();
        predictor = new Predictor();
        shielding = new Shielding();

        setTitle("Space Radiation Monitoring System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 247, 252));
        add(mainPanel);

        JLabel title = new JLabel("Space Radiation Monitoring Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(title);

        JPanel graphCard = new JPanel(new BorderLayout());
        graphCard.setBackground(Color.WHITE);

        graphCard.setPreferredSize(new Dimension(900, 420));
        graphCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));
        graphCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        graphCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        graphPanel = new GraphPanel();
        graphCard.add(graphPanel, BorderLayout.CENTER);

        mainPanel.add(graphCard);
        mainPanel.add(Box.createVerticalStrut(20));
        JPanel outputCard = new JPanel(new BorderLayout());
        outputCard.setMaximumSize(new Dimension(600, 250));
        outputCard.setBackground(Color.WHITE);
        outputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        outputLabel = new JLabel("<html><center>Enter a value to begin</center></html>");
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        outputCard.add(outputLabel, BorderLayout.CENTER);

        mainPanel.add(outputCard);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(245, 247, 252));

        inputField = new JTextField(10);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btn = new JButton("Analyze");
        btn.setFocusPainted(false);

        statusLabel = new JLabel("Status: Waiting");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        inputPanel.add(new JLabel("Radiation (0–1): "));
        inputPanel.add(inputField);
        inputPanel.add(btn);
        inputPanel.add(Box.createHorizontalStrut(10));
        inputPanel.add(statusLabel);

        mainPanel.add(inputPanel);

        btn.addActionListener(e -> {
            try {
                double value = Double.parseDouble(inputField.getText());
                data.addData(value);

                double prediction = predictor.predict(data.getHistory());
                double normalized = prediction;

                if (normalized > 1) {
                    normalized = normalized / (normalized + 1);
                }

                String shield = shielding.getShield(normalized);

                graphPanel.setData(data.getHistory(), prediction);

                outputLabel.setText(
                        "<html><center>" +
                        "<h2>Radiation Report</h2>" +
                        "<b>Latest:</b> " + value + "<br>" +
                        "<b>Predicted:</b> " + String.format("%.2f", prediction) + "<br><br>" +
                        shield.replace("\n", "<br>") +
                        "</center></html>"
                );

                if (shield.contains("DANGEROUS")) {
                    statusLabel.setText("DANGEROUS");
                    statusLabel.setBackground(new Color(220, 53, 69));
                    statusLabel.setForeground(Color.WHITE);
                } 
                else if (shield.contains("MODERATE")) {
                    statusLabel.setText("MODERATE");
                    statusLabel.setBackground(new Color(255, 193, 7));
                    statusLabel.setForeground(Color.BLACK);
                } 
                else {
                    statusLabel.setText("SAFE");
                    statusLabel.setBackground(new Color(40, 167, 69));
                    statusLabel.setForeground(Color.WHITE);
                }

                inputField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid number");
            }
        });
    }
}