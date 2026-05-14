import java.util.List;

// Интерфейс — это "договор", который должны выполнять все калькуляторы
public interface ICalculator {

    void setPrincipal(double principal);           // Установить сумму кредита
    void setDownPayment(double downPayment);       // Установить первоначальный взнос  // ДЗ: поддержка первоначального взноса
    void setAnnualInterestRate(double annualInterestRate); // Установить процентную ставку
    void setYears(int years);                      // Установить срок в годах

    void calculatePayments();                      // Запустить расчёт графика

    double getTotalPayment();                      // Получить общую сумму выплат
    double getTotalInterest();                     // Получить сумму переплат// ДЗ: сумма переплат
    double getDownPayment();                       // Получить первоначальный взнос// ДЗ: получение взноса

    List<Payment> getPaymentsSchedule();           // Получить список всех платежей
}