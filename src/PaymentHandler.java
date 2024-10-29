import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentHandler {

    private static final double BASE_AMOUNT = 0.0;
    public ArrayList<Credit> creditList;
    private FileHandler fileHandler;
    BookingHandler bH;

    public PaymentHandler(FileHandler inputFileHandler) throws IOException {
        creditList = new ArrayList<>();
        fileHandler =inputFileHandler;
        bH = new BookingHandler();
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

    public void registerPayment(int appointmentId, double addons) throws IOException {


        double totalAmount = BASE_AMOUNT + addons;
        Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);

        if (appointment != null) {
            findAppointment_WithId(appointmentId, fileHandler).setPrice(totalAmount);
            System.out.println("Betaling registreret: " + totalAmount + " kr.");
            fileHandler.saveCalendar();
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    public void registerCredit(int appointmentId, double addons) throws IOException {
        double totalAmount = BASE_AMOUNT + addons;
        Credit credit = new Credit(appointmentId, totalAmount);
        creditList.add(credit);
        System.out.println("Kredit tilføjet: Aftale ID: " + appointmentId + ", Beløb: " + totalAmount);

        Appointment appointment = findAppointment_WithId(appointmentId, fileHandler);
        if (appointment != null) {
            findAppointment_WithId(appointmentId, fileHandler).setCredit(totalAmount);
            System.out.println("Kredit registreret for aftale nr: " + appointmentId + " med beløb: " + totalAmount + " kr.");
            fileHandler.saveCalendar();
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    public void payCredit(int appointmentId) throws IOException {
        boolean found = false;

        for (Appointment a : fileHandler.getList()) {
            if (a.getCredit()>0) {
                found = true;
                if (a != null) {
                    findAppointment_WithId(appointmentId, fileHandler).setPrice(findAppointment_WithId(appointmentId, fileHandler).getPrice() + findAppointment_WithId(appointmentId, fileHandler).getCredit());
                    findAppointment_WithId(appointmentId, fileHandler).setCredit(0.0);
                    System.out.println("Kredit betalt for aftale ID " + a.getBookingId());
                    fileHandler.saveCalendar();
                }
                else {
                    System.out.println("Ingen aftale fundet med ID: " + appointmentId);
                }
                return;
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

    public void startMenu() throws Exception {
        InputHandler input = new InputHandler();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Vælg en handling:");
            System.out.println("1. Registrer betaling");
            System.out.println("2. Registrer kredit");
            System.out.println("3. Betal kredit");
            System.out.println("4. Tilbage til hovedmenu");

            int choice = input.inputInt();

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

    private void handlePayment(Scanner scanner) throws IOException {
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

    private void handleCredit(Scanner scanner) throws IOException {
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

    private void handlePayCredit(Scanner scanner) throws IOException {
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
       /* PaymentHandler handler = new PaymentHandler();
        handler.startMenu();

        */
    }
}
