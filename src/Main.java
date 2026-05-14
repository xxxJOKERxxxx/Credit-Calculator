import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите сумму долга: ");
        double principal = scanner.nextDouble();

        System.out.println("Введите срок кредита в годах: ");
        int years = scanner.nextInt();

        System.out.println("Введите процентную ставку (%): ");
        double annualInterestRate = scanner.nextDouble();

        System.out.println("Выберите вид платежа (1 - аннуитетный, 2 - дифференцированный): ");
        int paymentType = scanner.nextInt();

        ICalculator calculator;
        if (paymentType == 1) {
            calculator = new AnnuityCalculator();
        } else {
            calculator = new DifferentiatedCalculator();
        }

        calculator.setPrincipal(principal);
        calculator.setAnnualInterestRate(annualInterestRate);
        calculator.setYears(years);
        calculator.calculatePayments();


        printSchedule(calculator);

        scanner.close(); // хорошая практика
    }

    private static void printSchedule(ICalculator calculator) {
        System.out.println("\nГрафик платежей:");
        System.out.println("--------------------------------------------------");

        for (Payment payment : calculator.getPaymentsSchedule()) {
            System.out.printf("Месяц: %d | Основной долг: %.2f | Проценты: %.2f | Итого: %.2f%n",
                    payment.getMonth(),
                    payment.getPrincipalPayment(),
                    payment.getInterestPayment(),
                    payment.getTotalPayment());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Общая сумма выплат: %.2f%n", calculator.getTotalPayment());
        System.out.printf("Общая сумма процентов: %.2f%n", calculator.getTotalInterest());
    }
}