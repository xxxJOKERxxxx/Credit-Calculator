import java.util.ArrayList;
import java.util.List;

public class DifferentiatedCalculator implements ICalculator {
    private double principal;
    private double annualInterestRate;
    private int years;
    private List<Payment> payments;

    @Override
    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    @Override
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void setYears(int years) {
        this.years = years;
    }

    @Override
    public void calculatePayments() {
        int totalMonths = years * 12;
        double monthlyRate = annualInterestRate / 12 / 100;
        double monthlyPrincipalPayment = principal / totalMonths;  // фиксированная часть долга

        payments = new ArrayList<>();
        double remainingPrincipal = principal;   // ← важное исправление

        for (int month = 1; month <= totalMonths; month++) {
            double interestPayment = remainingPrincipal * monthlyRate;
            double principalPayment = monthlyPrincipalPayment;

            if (month == totalMonths) {
                principalPayment = remainingPrincipal;
            }

            remainingPrincipal -= principalPayment;
            if (remainingPrincipal < 0) remainingPrincipal = 0;

            payments.add(new Payment(month, principalPayment, interestPayment));
        }
    }

    @Override
    public double getTotalPayment() {
        return payments.stream().mapToDouble(Payment::getTotalPayment).sum();
    }

    @Override
    public double getTotalInterest() {
        return payments.stream().mapToDouble(Payment::getInterestPayment).sum();
    }

    @Override
    public List<Payment> getPaymentsSchedule() {
        return payments;
    }
}
