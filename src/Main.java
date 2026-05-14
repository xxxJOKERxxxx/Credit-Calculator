import java.util.Scanner;   // Импортируем Scanner, чтобы читать данные с клавиатуры

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);  // Создаём объект для ввода данных

        System.out.println("=== Кредитный калькулятор ===\n");

        // Ввод данных с проверкой (валидацией)
        double principal = readPositiveDouble(scanner, "Введите сумму кредита (руб.): ");
        double downPayment = readNonNegativeDouble(scanner, "Введите первоначальный взнос (0, если нет): ");

        // Проверяем, чтобы взнос не был больше суммы кредита
        while (downPayment >= principal) {
            System.out.println("Ошибка! Первоначальный взнос не может быть больше или равен сумме кредита.");
            downPayment = readNonNegativeDouble(scanner, "Введите первоначальный взнос (руб.): ");
        }

        int years = readPositiveInt(scanner, "Введите срок кредита в годах: ");
        double annualInterestRate = readNonNegativeDouble(scanner, "Введите годовую процентную ставку (%): ");

        int paymentType = readPaymentType(scanner);  // Выбор типа платежа

        // Создаём объект нужного калькулятора (полиморфизм)
        ICalculator calculator = (paymentType == 1)
                ? new AnnuityCalculator()
                : new DifferentiatedCalculator();

        // Передаём введённые данные в калькулятор
        calculator.setPrincipal(principal);
        calculator.setDownPayment(downPayment);// ДЗ: поддержка первоначального взноса
        calculator.setAnnualInterestRate(annualInterestRate);
        calculator.setYears(years);

        calculator.calculatePayments();   // Запускаем расчёт

        printSchedule(calculator);        // Выводим результат

        scanner.close();  // Закрываем Scanner (хорошая практика)
    }

    // ====================== МЕТОДЫ ДЛЯ ВВОДА ======================

    // === ВЫПОЛНЕНИЕ ДЗ: Защита от некорректного ввода ===
    private static int readPaymentType(Scanner scanner) {
        int type;
        do {
            System.out.print("Выберите тип платежа (1 - аннуитетный, 2 - дифференцированный): ");
            while (!scanner.hasNextInt()) {           // Пока пользователь вводит не число
                System.out.println("Ошибка! Введите 1 или 2.");
                scanner.next();
            }
            type = scanner.nextInt();
        } while (type != 1 && type != 2);   // Повторяем, пока не введёт 1 или 2
        return type;
    }

    // Метод для ввода положительного числа (больше 0)
    private static double readPositiveDouble(Scanner scanner, String message) {
        double value;
        do {
            System.out.print(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("Ошибка! Введите число.");
                scanner.next();
            }
            value = scanner.nextDouble();
            if (value <= 0) {
                System.out.println("Ошибка! Число должно быть больше 0.");
            }
        } while (value <= 0);
        return value;
    }

    // Метод для ввода неотрицательного числа (0 и больше)
    private static double readNonNegativeDouble(Scanner scanner, String message) {
        double value;
        do {
            System.out.print(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("Ошибка! Введите число.");
                scanner.next();
            }
            value = scanner.nextDouble();
            if (value < 0) {
                System.out.println("Ошибка! Число не может быть отрицательным.");
            }
        } while (value < 0);
        return value;
    }

    // Метод для ввода положительного целого числа
    private static int readPositiveInt(Scanner scanner, String message) {
        int value;
        do {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Введите целое число.");
                scanner.next();
            }
            value = scanner.nextInt();
            if (value <= 0) {
                System.out.println("Ошибка! Число должно быть больше 0.");
            }
        } while (value <= 0);
        return value;
    }

    // Метод для красивого вывода графика
    private static void printSchedule(ICalculator calculator) {
        System.out.println("\n=== График платежей ===");
        System.out.println("--------------------------------------------------");

        for (Payment payment : calculator.getPaymentsSchedule()) {
            System.out.printf("Месяц %3d | Основной долг: %10.2f | Проценты: %10.2f | Итого: %10.2f%n",
                    payment.getMonth(),
                    payment.getPrincipalPayment(),
                    payment.getInterestPayment(),
                    payment.getTotalPayment());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Общая сумма выплат:      %.2f руб.%n", calculator.getTotalPayment());
        System.out.printf("Сумма переплат:          %.2f руб.%n", calculator.getTotalInterest());  // === ВЫПОЛНЕНИЕ ДЗ: Расчёт суммы переплат ===
        System.out.printf("Первоначальный взнос:    %.2f руб.%n", calculator.getDownPayment());
    }
}