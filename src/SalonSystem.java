import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

// Konsolbaseret single-user bookingsystem.
public class SalonSystem {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        PaymentHandler2 paymentHandler2 = new PaymentHandler2(); // Ny paymenthandler

        FileHandler fileHandler;
        fileHandler = new FileHandler();

        System.out.println("Velkommen til Hairy Harry's salonsystem");

        boolean running = true;

        while (running) {
            System.out.println("Vælg en af følgende handlinger:");
            System.out.println("1: Opret en aftale");
            System.out.println("2: Slet en aftale");
            System.out.println("3: Vis ledige tider");
            System.out.println("4: Registrer ferie- eller lukkedage");
            System.out.println("5: Se økonomioplysniger (kræver adgangskode)");
            System.out.println("6: Registrer Økonomi");
            System.out.println("7: Afslut program");

            if (scanner.hasNextInt()) {
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

                        paymentHandler2.createAppointment(date, time, customer);
                        break;

                    case 2: // Slet en aftale
                        System.out.println("Indtast kundens navn:");
                        String customerToDelete = scanner.nextLine();
                        Customer customerDel = new Customer(customerToDelete);

                        System.out.println("Indtast aftalte dato (ÅR-MÅNED-DAG)");
                        LocalDate dateToDelete = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.deleteAppointment(dateToDelete, customerDel);
                        break;

                    case 3: // Viser ledige tider
                        System.out.println("Indtast ønsket dato (ÅR-MÅNED-DAG)");
                        LocalDate dateForTimes = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.showAvailableTimes(dateForTimes);
                        break;

                    case 4: // Ferie/lukkedage
                        System.out.println("Indtast feriedato (ÅR-MÅNED-DAG)");
                        LocalDate holiday = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.registerHoliday(holiday);
                        break;

                    case 5: // Økonomioplysninger - kræver password
                        System.out.println("Indtast adgangskode:");
                        String password = scanner.nextLine();
                        paymentHandler2.showFinancialData(password);

                        try {
                            EconomyLogHandler econmyHandler = new EconomyLogHandler();  // <-- Opretter PaymentHandler
                            econmyHandler.startEconomyMenu();  // <-- Starter PaymentHandler menuen
                        } catch (IOException e) {  // <-- Håndtering af generel fil-IO-fejl inklusive FileNotFoundException
                            // Håndtering af IOException
                            System.out.println("Fejl: Der opstod en fejl ved indlæsning af filen.");
                            e.printStackTrace();  // Udskriver stack trace for yderligere fejldiagnose
                        }
                        break;

                    case 6: // Indregistrering af økonomi (Betaling og kredit)
                        try {
                            PaymentHandler paymentHandler = new PaymentHandler();  // <-- Opretter PaymentHandler
                            paymentHandler.startMenu();  // <-- Starter PaymentHandler menuen
                        } catch (IOException e) {  // <-- Håndtering af generel fil-IO-fejl inklusive FileNotFoundException
                            // Håndtering af IOException
                            System.out.println("Fejl: Der opstod en fejl ved indlæsning af filen.");
                            e.printStackTrace();  // Udskriver stack trace for yderligere fejldiagnose
                        }

                        // Når PaymentHandler er færdig, returnerer vi til hovedmenuen i SalonSystem
                        System.out.println("Går tilbage til hovedmenuen");
                        System.out.println("");
                        break;



                    case 7: // Afslutter programmet
                        running = false;
                        System.out.println("Programmet afsluttes.");
                        break;

                    default:
                        System.out.println("Ugyldigt valg. Prøv igen.");
                        break;
                }
            }
        }
    }
}

class Customer {
    String name;
    boolean hasPaid;
    double totalBill;

    public Customer(String name) {
        this.name = name;
        this.hasPaid = true;
        this.totalBill = 0.0;
    }
}

class Appointment2 {
    LocalDate date;
    LocalTime time;
    Customer customer;
    boolean isPaid;

    public Appointment2(LocalDate date, LocalTime time, Customer customer) {
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.isPaid = true;
    }

    public void setPaymentStatus(boolean status) {
        this.isPaid = status;
    }
}

class PaymentHandler2 {
    private static final String PASSWORD = "hairyharry";
    private static final LocalTime OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(18, 0);

    private List<Appointment2> appointments;
    private List<LocalDate> holidays;

    public PaymentHandler2() {
        this.appointments = new ArrayList<>();
        this.holidays = new ArrayList<>();
    }

    public void createAppointment(LocalDate date, LocalTime time, Customer customer) {
        if (isSalonOpen(date, time)) {
            appointments.add(new Appointment2(date, time, customer));
            System.out.println("Aftale oprettet for " + customer.name + " på " + date + " kl " + time);
        } else {
            System.out.println("Salonen holder lukket i dette tidsinterval, på ferie eller i weekenden.");
        }
    }

    public void deleteAppointment(LocalDate date, Customer customer) {
        appointments.removeIf(appointment -> appointment.date.equals(date) && appointment.customer.equals(customer));
        System.out.println("Aftale for " + customer.name + " på " + date + " er slettet.");
    }

    public void showAvailableTimes(LocalDate date) {
        if (date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
            System.out.println("Salonen er lukket i weekenden på " + date + ".");
            System.out.println("");
            return;
        }

        if (!holidays.contains(date)) {
            System.out.println("Ledige tider på " + date + ":");
            for (LocalTime time = OPENING_TIME; time.isBefore(CLOSING_TIME); time = time.plusMinutes(60)) {
                if (isTimeAvailable(date, time)) {
                    System.out.println(" - " + time);
                } else {
                    System.out.println("Optaget på " + time);
                }
            }
        } else {
            System.out.println("Salonen er lukket på " + date + " (feriedag).");
        }

        // Kræver at brugeren skriver "t" for at komme tilbage til hovedmenuen
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equalsIgnoreCase("t")) {
            System.out.println("Skriv 't' for at komme tilbage til hovedmenuen:");
            input = scanner.nextLine();
        }
    }

    private boolean isSalonOpen(LocalDate date, LocalTime time) {
        // Tjek for weekender
        if (date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
            return false;
        }
        // Tjek for feriedage og åbningstider
        return !holidays.contains(date) && (time.isAfter(OPENING_TIME.minusSeconds(1)) && time.isBefore(CLOSING_TIME));
    }

    private boolean isTimeAvailable(LocalDate date, LocalTime time) {
        for (Appointment2 appointment2 : appointments) {
            if (appointment2.date.equals(date) && appointment2.time.equals(time)) {
                return false;
            }
        }
        return true;
    }

    public void registerHoliday(LocalDate date) {
        holidays.add(date);
        System.out.println("Feriedag tilføjet: " + date);
    }

    public void showFinancialData(String inputPassword) {
        if (PASSWORD.equals(inputPassword)) {
            System.out.println("Økonomioplysninger:");
            for (Appointment2 appointment2 : appointments) {
                System.out.println("Kunde: " + appointment2.customer.name + " Betalt: " + (appointment2.isPaid ? "Ja" : "Nej") + " Total regning: " + appointment2.customer.totalBill);
            }
        } else {
            System.out.println("Forkert adgangskode");
        }
    }

    public void run () throws IOException {
        Scanner scanner = new Scanner(System.in);
        PaymentHandler2 paymentHandler2 = new PaymentHandler2(); // Ny paymenthandler

        FileHandler fileHandler;
        fileHandler = new FileHandler();

        System.out.println("Velkommen til Hairy Harry's salonsystem");

        boolean running = true;

        while (running) {
            System.out.println("Vælg en af følgende handlinger:");
            System.out.println("1: Opret en aftale");
            System.out.println("2: Slet en aftale");
            System.out.println("3: Vis ledige tider");
            System.out.println("4: Registrer ferie- eller lukkedage");
            System.out.println("5: Se økonomioplysniger (kræver adgangskode)");
            System.out.println("6: Registrer Økonomi");
            System.out.println("7: Afslut program");

            if (scanner.hasNextInt()) {
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

                        paymentHandler2.createAppointment(date, time, customer);
                        break;

                    case 2: // Slet en aftale
                        System.out.println("Indtast kundens navn:");
                        String customerToDelete = scanner.nextLine();
                        Customer customerDel = new Customer(customerToDelete);

                        System.out.println("Indtast aftalte dato (ÅR-MÅNED-DAG)");
                        LocalDate dateToDelete = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.deleteAppointment(dateToDelete, customerDel);
                        break;

                    case 3: // Viser ledige tider
                        System.out.println("Indtast ønsket dato (ÅR-MÅNED-DAG)");
                        LocalDate dateForTimes = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.showAvailableTimes(dateForTimes);
                        break;

                    case 4: // Ferie/lukkedage
                        System.out.println("Indtast feriedato (ÅR-MÅNED-DAG)");
                        LocalDate holiday = LocalDate.parse(scanner.nextLine());

                        paymentHandler2.registerHoliday(holiday);
                        break;

                    case 5: // Økonomioplysninger - kræver password
                        System.out.println("Indtast adgangskode:");
                        String password = scanner.nextLine();

                        paymentHandler2.showFinancialData(password);
                        break;

                    case 6: // Indregistrering af økonomi (Betaling og kredit)
                        try {
                            PaymentHandler paymentHandler = new PaymentHandler();  // <-- Opretter PaymentHandler
                            paymentHandler.startMenu();  // <-- Starter PaymentHandler menuen
                        } catch (
                                IOException e) {  // <-- Håndtering af generel fil-IO-fejl inklusive FileNotFoundException
                            // Håndtering af IOException
                            System.out.println("Fejl: Der opstod en fejl ved indlæsning af filen.");
                            e.printStackTrace();  // Udskriver stack trace for yderligere fejldiagnose
                        }

                        // Når PaymentHandler er færdig, returnerer vi til hovedmenuen i SalonSystem
                        System.out.println("Går tilbage til hovedmenuen");
                        System.out.println("");
                        break;


                    case 7: // Afslutter programmet
                        running = false;
                        System.out.println("Programmet afsluttes.");
                        break;

                    default:
                        System.out.println("Ugyldigt valg. Prøv igen.");
                        break;
                }
            }
        }
    }
}