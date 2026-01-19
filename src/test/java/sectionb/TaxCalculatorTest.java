package sectionb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxCalculatorTest {

    private TaxCalculator getCalculator() {
        List<TaxBracket> brackets = List.of(
                new TaxBracket(0, 250000, 0.0),
                new TaxBracket(250000, 500000, 0.05),
                new TaxBracket(500000, 1000000, 0.20),
                new TaxBracket(1000000, Double.MAX_VALUE, 0.30)
        );
        return new TaxCalculator(brackets);
    }

    @Test
    void zeroIncome_shouldReturnZeroTax() {
        TaxCalculator calculator = getCalculator();
        assertEquals(0.0, calculator.calculateTax(0));
    }

    @Test
    void incomeBelowExemption_shouldReturnZeroTax() {
        TaxCalculator calculator = getCalculator();
        assertEquals(0.0, calculator.calculateTax(200000));
    }

    @Test
    void incomeInSecondSlab_shouldCalculateCorrectTax() {
        TaxCalculator calculator = getCalculator();
        assertEquals(12500.0, calculator.calculateTax(500000));
    }

    @Test
    void incomeInThirdSlab_shouldCalculateCorrectTax() {
        TaxCalculator calculator = getCalculator();
        assertEquals(112500.0, calculator.calculateTax(1000000));
    }

    @Test
    void incomeAboveAllSlabs_shouldCalculateCorrectTax() {
        TaxCalculator calculator = getCalculator();
        assertEquals(262500.0, calculator.calculateTax(1500000));
    }
}
