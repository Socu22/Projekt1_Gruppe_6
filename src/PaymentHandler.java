import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentHandler {

    private static final double BASE_AMOUNT = 0.0;
    public ArrayList<Credit> creditList;
    private FileHandler fileHandler;
    BookingHandler bH;
    InputHandler iH = new InputHandler();

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

    public void registerPayment(Appointment a, double addons) throws Exception {


        double totalAmount = BASE_AMOUNT + addons;
        Appointment appointment = a;

        if (appointment != null) {
            appointment.setPrice(totalAmount);
            System.out.println("Betaling registreret: " + totalAmount + " kr.");
            fileHandler.saveCalendar();
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointment.getBookingId());
        }
    }

    public void registerCredit(Appointment a, double addons) throws IOException {
        double totalAmount = BASE_AMOUNT + addons;

        Appointment appointment = a;
        if (appointment != null) {
            a.setCredit(totalAmount);
            System.out.println("Kredit registreret for aftale nr: " + a.getBookingId() + " med beløb: " + totalAmount + " kr.");
            fileHandler.saveCalendar();
        } else {
            System.out.println("Ingen aftale fundet med ID: " + a.getBookingId());
        }
    }

    public void payCredit(Appointment a) throws IOException {
        boolean found = false;

            if (a.getCredit()>0) {
                found = true;
                if (a != null) {
                    a.setPrice(a.getPrice() + a.getCredit());
                    a.setCredit(0.0);
                    System.out.println("Kredit betalt for aftale ID " + a.getBookingId());
                    fileHandler.saveCalendar();
                }
                else {
                    System.out.println("Ingen aftale fundet med ID: " + a.getBookingId());
                }
                return;
            }


        if (!found) {
            System.out.println("Ingen kredit blev fundet for aftale nr: " + a.getBookingId());
        }
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
                    try {
                        handlePayment(scanner);
                    }
                    catch (Exception e){
                        System.out.println("Kunne ikke registrere betaling.");
                    }
                    break;
                case 2:
                    try {
                        handleCredit(scanner);
                    } catch (Exception e) {
                        System.out.println("Kunne ikke registrere kredit.");;
                    }
                    break;
                case 3:
                    try {
                        handlePayCredit(scanner);
                    }
                    catch (Exception e){
                        System.out.println("kunne ikke betale kredit.");
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg, prøv igen.");
            }
        }
    }

    private void handlePayment(Scanner scanner) throws Exception {
        //System.out.println("Indtast aftale nummer: ");
            Appointment appointment = bH.searchforAppointment(fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil registrere betaling for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    System.out.println("Indtast det fulde beløb: ");
                    double totalAmount = scanner.nextDouble();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerPayment(appointment, totalAmount + addons);
                } else {
                    System.out.println("Betaling annulleret.");
                }
        }
    }

    private void handleCredit(Scanner scanner) throws Exception {
            Appointment appointment = bH.searchforAppointment(fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil registrere kredit for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    System.out.println("Indtast det fulde beløb: ");
                    double totalAmount = scanner.nextDouble();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerCredit(appointment, totalAmount + addons);
                } else {
                    System.out.println("Registrering af kredit annulleret.");
                }
            }

    }

    private void handlePayCredit(Scanner scanner) throws Exception {
            Appointment appointment = bH.searchforAppointment(fileHandler);
            if (appointment != null) {
                System.out.println("Er du sikker på, at du vil betale kredit for kunde: " + appointment.getname() + "? (ja/nej)");
                String confirmation = scanner.next();
                if (confirmation.equalsIgnoreCase("ja")) {
                    payCredit(appointment);
                } else {
                    System.out.println("Betaling af kredit annulleret.");
                }
            }
    }

    public static void main(String[] args) throws IOException {
       /* PaymentHandler handler = new PaymentHandler();
        handler.startMenu();

        */
    }
}
