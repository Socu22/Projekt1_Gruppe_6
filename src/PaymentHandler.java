import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentHandler {

    private static final double BASE_AMOUNT = 0.0;
    public ArrayList<Credit> creditList;
    private FileHandler fileHandler;

    public PaymentHandler() throws IOException {
        creditList = new ArrayList<>();
        fileHandler = new FileHandler();
    }

    class Credit {
        int appointmentId;
        double amount;
        boolean isPaid;

        Credit(int appointmentId, double amount) {
            this.appointmentId = appointmentId;
            this.amount = amount;
            this.isPaid = false;
        }

        public void pay() {
            this.isPaid = true;
        }

        public boolean isPaid() {
            return this.isPaid;
        }

        public int getAppointmentId() {
            return appointmentId;
        }
    }

    public void registerPayment(int appointmentId, double addons) {
        double totalAmount = BASE_AMOUNT + addons;
        Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);

        if (appointment != null) {
            findAppointment_WithId(appointmentId, fileHandler).setPrice(totalAmount);
            System.out.println("Betaling registreret: " + totalAmount + " kr.");

            try {
                fileHandler.saveCalendar();
            } catch (IOException e) {
                System.out.println("Fejl ved gemning af kalender: " + e.getMessage());
            }
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    public void registerCredit(int appointmentId, double addons) {
        double totalAmount = BASE_AMOUNT + addons;
        Credit credit = new Credit(appointmentId, totalAmount);
        creditList.add(credit);

        Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
        if (appointment != null) {
            findAppointment_WithId(appointmentId, fileHandler).setCredit(totalAmount);
            System.out.println("Kredit registreret for aftale nr: " + appointmentId + " med beløb: " + totalAmount + " kr.");

            try {
                fileHandler.saveCalendar();
            } catch (IOException e) {
                System.out.println("Fejl ved gemning af kalender: " + e.getMessage());
            }
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    public void payCredit(int appointmentId) {
        boolean found = false;

        for (Credit credit : creditList) {
            if (credit.getAppointmentId() == appointmentId) {
                found = true;

                Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
                if (appointment != null) {
                    if (!credit.isPaid()) {  // Kontroller, om kreditten allerede er betalt
                        appointment.setCredit(0.0);
                        credit.pay();
                        System.out.println("Kredit betalt for aftale ID " + appointmentId);

                        try {
                            fileHandler.saveCalendar();
                        } catch (IOException e) {
                            System.out.println("Fejl ved gemning af kalender: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Kredit for aftale nr: " + appointmentId + " er allerede betalt.");
                    }
                } else {
                    System.out.println("Ingen aftale fundet med ID: " + appointmentId);
                }
                return;
            }

        if (!found) {
            System.out.println("Ingen kredit blev fundet for aftale nr: " + appointmentId);
        }
    }

        if (!found) {
            System.out.println("Ingen kredit blev fundet for aftale nr: " + appointmentId);
        }
    }

    public boolean validateAppointmentId(int appointmentId) {
        ArrayList<Appointment> appointments = fileHandler.getList();
        for (Appointment appointment : appointments) {
            if (appointment.getBookingId() == appointmentId) {
                return true;
            }
        }
        return false;
    }

    public Appointment findAppointment_WithId(int idInput, FileHandler fileHandler) {
        for (Appointment a : fileHandler.getList()) {
            if (idInput == a.getBookingId()) {
                return a;
            }
        }
        return null;
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Vælg en handling:");
            System.out.println("1. Registrer betaling");
            System.out.println("2. Registrer kredit");
            System.out.println("3. Betal kredit");
            System.out.println("4. Tilbage til hovedmenu");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handlePayment(scanner);
                    break;
                case 2:
                    handleCredit(scanner);
                    break;
                case 3:
                    handlePayCredit(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg, prøv igen.");
            }
        }
    }

    private void handlePayment(Scanner scanner) {
        System.out.println("Indtast aftale nummer: ");
        int appointmentId = scanner.nextInt();
        if (validateAppointmentId(appointmentId)) {
            Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil registrere betaling for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    System.out.println("Indtast det fulde beløb: ");
                    double totalAmount = scanner.nextDouble();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerPayment(appointmentId, totalAmount + addons);
                } else {
                    System.out.println("Betaling annulleret.");
                }
            }
        } else {
            System.out.println("Ugyldigt aftale nummer.");
        }
    }

    private void handleCredit(Scanner scanner) {
        System.out.println("Indtast aftale nummer: ");
        int appointmentId = scanner.nextInt();
        if (validateAppointmentId(appointmentId)) {
            Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil registrere kredit for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    System.out.println("Indtast det fulde beløb: ");
                    double totalAmount = scanner.nextDouble();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerCredit(appointmentId, totalAmount + addons);
                } else {
                    System.out.println("Registrering af kredit annulleret.");
                }
            }
        } else {
            System.out.println("Ugyldigt aftale nummer.");
        }
    }

    private void handlePayCredit(Scanner scanner) {
        System.out.println("Indtast aftale nummer: ");
        int appointmentId = scanner.nextInt();
        if (validateAppointmentId(appointmentId)) {
            Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil betale kredit for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    payCredit(appointmentId);
                } else {
                    System.out.println("Betaling af kredit annulleret.");
                }
            }
        } else {
            System.out.println("Ugyldigt aftale nummer.");
        }
    }

    public static void main(String[] args) throws IOException {
        PaymentHandler handler = new PaymentHandler();
        handler.startMenu();
    }
}
