// Класс, который хранит информацию об одном месячном платеже
public class Payment {

    private final int month;              // Номер месяца
    private final double principalPayment; // Платёж по основному долгу
    private final double interestPayment;  // Платёж по процентам
    private final double totalPayment;     // Общий платёж

    // Конструктор
    public Payment(int month, double principalPayment, double interestPayment) {
        this.month = month;
        this.principalPayment = Math.max(0, principalPayment); // защита от отрицательных чисел
        this.interestPayment = interestPayment;
        this.totalPayment = this.principalPayment + interestPayment;
    }

    // Геттеры (методы для получения значений)
    public int getMonth() { return month; }
    public double getPrincipalPayment() { return principalPayment; }
    public double getInterestPayment() { return interestPayment; }
    public double getTotalPayment() { return totalPayment; }
}