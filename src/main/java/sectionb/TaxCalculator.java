package sectionb;

import java.util.List;

public class TaxCalculator {

    private final List<TaxBracket> taxBrackets;

    public TaxCalculator(List<TaxBracket> taxBrackets) {
        this.taxBrackets = taxBrackets;
    }

    public double calculateTax(double annualIncome) {
        if (annualIncome <= 0) {
            return 0.0;
        }

        double tax = 0.0;

        for (TaxBracket bracket : taxBrackets) {
            if (annualIncome > bracket.getLowerLimit()) {
                double taxableAmount = Math.min(annualIncome, bracket.getUpperLimit())
                        - bracket.getLowerLimit();
                tax += taxableAmount * bracket.getTaxRate();
            }
        }

        return tax;
    }
}
