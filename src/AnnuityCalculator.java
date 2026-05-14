import java.util.ArrayList;
import java.util.List;

// Калькулятор аннуитетных платежей
public class AnnuityCalculator implements ICalculator {

    private double principal;
    private double downPayment;
    private double annualInterestRate;
    private int years;
    private List<Payment> payments;

    @Override
    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    @Override
    public void setDownPayment(double downPayment) {  // ДЗ: обработка первоначального взноса
        this.downPayment = downPayment;
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
        double loanAmount = principal - downPayment;           // Сумма реального кредита   // ДЗ: расчёт с учётом взноса
        double monthlyRate = annualInterestRate / 12 / 100;   // Месячная процентная ставка
        int totalMonths = years * 12;

        // Формула аннуитетного платежа
        double monthlyPayment = loanAmount *
                (monthlyRate * Math.pow(1 + monthlyRate, totalMonths))
                / (Math.pow(1 + monthlyRate, totalMonths) - 1);

        payments = new ArrayList<>();
        double remaining = loanAmount;

        for (int month = 1; month <= totalMonths; month++) {
            double interestPayment = remaining * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;

            if (month == totalMonths) {
                principalPayment = remaining;   // Последний платёж — остаток
            }

            remaining -= principalPayment;
            if (remaining < 0) remaining = 0;

            payments.add(new Payment(month, principalPayment, interestPayment));
        }
    }

    @Override
    public double getTotalPayment() {
        return payments.stream().mapToDouble(Payment::getTotalPayment).sum();
    }

    @Override
    public double getTotalInterest() {   // ДЗ: сумма всех процентов
        return payments.stream().mapToDouble(Payment::getInterestPayment).sum();
    }

    @Override
    public double getDownPayment() {
        return downPayment;
    }

    @Override
    public List<Payment> getPaymentsSchedule() {
        return payments;
    }
}