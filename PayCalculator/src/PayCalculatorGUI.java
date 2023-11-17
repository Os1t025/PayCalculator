import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayCalculatorGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pay Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PayCalculatorPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class PayCalculatorPanel extends JPanel {
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;
    private JComboBox<String> payFrequencyComboBox;
    private JTextArea resultArea;

    public PayCalculatorPanel() {
        // Create components
        JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
        hourlyRateField = new JTextField(10);

        JLabel hoursWorkedLabel = new JLabel("Hours Worked:");
        hoursWorkedField = new JTextField(10);

        JLabel payFrequencyLabel = new JLabel("Pay Frequency:");
        String[] payFrequencies = {"Daily", "Weekly", "Biweekly", "Monthly"};
        payFrequencyComboBox = new JComboBox<>(payFrequencies);

        JButton calculateButton = new JButton("Calculate");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        // Add action listener to the Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplay();
            }
        });

        // Add components to the panel
        add(hourlyRateLabel);
        add(hourlyRateField);
        add(hoursWorkedLabel);
        add(hoursWorkedField);
        add(payFrequencyLabel);
        add(payFrequencyComboBox);
        add(calculateButton);
        add(resultArea);
    }

    private void calculateAndDisplay() {
        // Extract user input
        double hourlyRate = Double.parseDouble(hourlyRateField.getText());
        double hoursWorked = Double.parseDouble(hoursWorkedField.getText());
        String payFrequency = (String) payFrequencyComboBox.getSelectedItem();

        // Perform calculations
        double grossPay = hourlyRate * hoursWorked;
        double socialSecurityTax = 0.062 * grossPay;
        double medicareTax = 0.0145 * grossPay;
        double stateLocalTax = 0.0367 * grossPay;
        double netPay = grossPay - socialSecurityTax - medicareTax - stateLocalTax;

        // Pay periods
        int payPeriodsPerYear;
        switch (payFrequency.toLowerCase()) {
            case "daily":
                payPeriodsPerYear = 365;
                break;
            case "weekly":
                payPeriodsPerYear = 52;
                break;
            case "biweekly":
                payPeriodsPerYear = 26;
                break;
            case "monthly":
                payPeriodsPerYear = 12;
                break;
            default:
                payPeriodsPerYear = 0;
                break;
        }

        double grossAnnualPay = grossPay * payPeriodsPerYear;

        double wageThreshold = 0;
        double baseWithholding = 0;
        double rateOverThreshold = 0;

        // W2 federal tax information (Georgia)
        if (grossAnnualPay < 12950) {
            baseWithholding = 0;
            rateOverThreshold = 0;
        } else if (grossAnnualPay < 23225) {
            wageThreshold = 12950;
            baseWithholding = 0;
            rateOverThreshold = 0.10;
        } else if (grossAnnualPay < 54725) {
            wageThreshold = 23225;
            baseWithholding = 1027.50;
            rateOverThreshold = 0.12;
        } else if (grossAnnualPay < 102025) {
            wageThreshold = 54725;
            baseWithholding = 4807.50;
            rateOverThreshold = 0.22;
        } else if (grossAnnualPay < 183000) {
            wageThreshold = 102025;
            baseWithholding = 15213.50;
            rateOverThreshold = 0.24;
        } else if (grossAnnualPay < 228900) {
            wageThreshold = 183000;
            baseWithholding = 34647.50;
            rateOverThreshold = 0.32;
        } else if (grossAnnualPay < 552850) {
            wageThreshold = 228900;
            baseWithholding = 49335.50;
            rateOverThreshold = 0.35;
        } else {
            wageThreshold = 552850;
            baseWithholding = 162718;
            rateOverThreshold = 0.37;
        }

        double annualWithholding = baseWithholding + rateOverThreshold * (grossAnnualPay - wageThreshold);
        double federalTaxWithholdings = annualWithholding / payPeriodsPerYear;
        double netPayAfterTax = netPay - federalTaxWithholdings;

        // Update the result area
        resultArea.setText("Your gross pay for this period is: $" + grossPay + "\n" +
                "Social Security Tax (6.2%): $" + socialSecurityTax + "\n" +
                "Medicare Tax (1.45%): $" + medicareTax + "\n" +
                "State/Local Tax (3.67%): $" + stateLocalTax + "\n" +
                "Your NET take-home pay is: $" + netPay + "\n" +
                "Your gross annual pay is: $" + grossAnnualPay + "\n" +
                "Federal Tax Withholdings: $" + federalTaxWithholdings + "\n" +
                "Your NET take-home pay after federal tax withholdings is: $" + netPayAfterTax);
    }
}



