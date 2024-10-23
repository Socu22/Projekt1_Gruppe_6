import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// Konsolbaseret single-user bookingsystem.
public class SalonSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentHandler paymentHandler = new PaymentHandler(); // Ny paymenthandler

        System.out.println("Velkommen til Hairy Harry's salonsystem");

        boolean running = true;

        while (running) {
            System.out.println("Vælg en af følgende handlinger:");
            System.out.println("1: Opret en aftale");
            System.out.println("2: Slet en aftale");
            System.out.println("3: Vis ledige tider");
            System.out.println("4: Registrer ferie- eller lukkedage");
            System.out.println("5: Se økonomioplysniger (kræver adgangskode)");
            System.out.println("6: Registrer betaling");
            System.out.println("7: Giv kredit");
            System.out.println("8: Afslut program");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Oprettelse af aftale
                    System.out.println("Indtast kundens navn:");
                    String name = scanner.nextLine();
                    Customer customer = new Customer(name);

                    System.out.println("Indtast aftaledato (ÅR-MÅNED-DAG)");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    System.out.println("Indtast aftaletidspunkt (TIME:MINUT)");
                    LocalTime time = LocalTime.parse(scanner.nextLine());

                    paymentHandler.createAppointment(date, time, customer);
                    break;

                case 2: // Slet en aftale
                    System.out.println("Indtast kundens navn:");
                    String customerToDelete = scanner.nextLine();
                    Customer customerDel = new Customer(customerToDelete);

                    System.out.println("Indtast aftalte dato (ÅR-MÅNED-DAG)");
                    LocalDate dateToDelete = LocalDate.parse(scanner.nextLine());

                    paymentHandler.deleteAppointment(dateToDelete, customerDel);
                    break;

                case 3: // Viser ledige tider
                    System.out.println("Indtast ønsket dato (ÅR-MÅNED-DAG)");
                    LocalDate dateForTimes = LocalDate.parse(scanner.nextLine());

                    paymentHandler.showAvailableTimes(dateForTimes);
                    break;

                case 4: // Ferie/lukkedage
                    System.out.println("Indtast feriedato (ÅR-MÅNED-DAG)");
                    LocalDate holiday = LocalDate.parse(scanner.nextLine());

                    paymentHandler.registerHoliday(holiday);
                    break;

                case 5: // Økonomioplysninger - kræver password
                    System.out.println("Indtast adgangskode:");
                    String password = scanner.nextLine();

                    paymentHandler.showFinancialData(password);
                    break;

                case 6: // Indregistrering af betaling
                    System.out.println("Indtast kundens navn:");
                    String payingCustomer = scanner.nextLine();
                    Customer customerPay = new Customer(payingCustomer);

                    System.out.println("Indtast beløb for betaling:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Sørger for at den ikke bugger

                    paymentHandler.registerPayment(customerPay, amount);
                    break;

                case 7: // Giv kredit til kunde
                    System.out.println("Indtast kundens navn:");
                    String creditCustomer = scanner.nextLine();
                    Customer customerCredit = new Customer(creditCustomer);

                    paymentHandler.setCredit(customerCredit);
                    break;

                case 8: // Afslutter programmet
                    running = false;
                    System.out.println("Programmet afsluttes.");
                    break;

                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
                    break;
            }
        }
        scanner.close();
    }
}
