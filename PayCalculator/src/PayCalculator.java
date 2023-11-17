import java.util.Scanner;

public class PayCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.print("Enter your hourly rate: ");
        double hourlyRate = scanner.nextDouble();

        System.out.print("Enter the number of hours worked: ");
        double hoursWorked = scanner.nextDouble();

        System.out.print("Enter your pay frequency (weekly, biweekly, monthly, etc.): ");
        String payFrequency = scanner.next();

        // Calculate gross pay
        double grossPay = hourlyRate * hoursWorked;

        // Calculate social security tax, Medicare tax, and state/local tax
        double socialSecurityTax = 0.062 * grossPay;
        double medicareTax = 0.0145 * grossPay;
        double stateLocalTax = 0.0367 * grossPay;

        // Calculate NET take-home pay
        double netPay = grossPay - socialSecurityTax - medicareTax - stateLocalTax;

        // Calculate pay periods and pay periods per year
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

        // Calculate gross annual pay
        double grossAnnualPay = grossPay * payPeriodsPerYear;

        // Additional information for withholdings
        double wageThreshold = 0;
        double baseWithholding = 0;
        double rateOverThreshold = 0;

        // Determine withholding based on annual income
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

        // Calculate annual withholding
        double annualWithholding = baseWithholding + rateOverThreshold * (grossAnnualPay - wageThreshold);

        // Calculate federal tax withholdings per pay period
        double federalTaxWithholdings = annualWithholding / payPeriodsPerYear;

        // Calculate NET take-home pay after federal tax withholdings
        double netPayAfterTax = netPay - federalTaxWithholdings;

        // Output results
        System.out.println("Your gross pay for this period is: $" + grossPay);
        System.out.println("Social Security Tax (6.2%): $" + socialSecurityTax);
        System.out.println("Medicare Tax (1.45%): $" + medicareTax);
        System.out.println("State/Local Tax (3.67%): $" + stateLocalTax);
        System.out.println("Your NET take-home pay is: $" + netPay);
        System.out.println("Your gross annual pay is: $" + grossAnnualPay);
        System.out.println("Federal Tax Withholdings: $" + federalTaxWithholdings);
        System.out.println("Your NET take-home pay after federal tax withholdings is: $" + netPayAfterTax);

        // Close the scanner
        scanner.close();
    }
}
