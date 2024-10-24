import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentHandler {

    // Grundbeløbet på en timeslot er 700 kroner, som bruges i alle betalinger
    private static final double baseAmount = 700.0;

    // ArrayList til at gemme kredit
    public ArrayList<Credit> creditList;

    // NYT: Liste til at holde alle aftaler (Appointments) fra JSON
    private List<Appointment> appointments;

    // Konstruktor hvor vi også læser JSON-filen ind
    public PaymentHandler() {
        creditList = new ArrayList<>();
        loadAppointmentsFromJson();
    }

    // NYT: Læs JSON-fil og indlæs alle appointments
    private void loadAppointmentsFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("appointments.json")) {
            Type appointmentListType = new TypeToken<List<Appointment>>() {}.getType();
            appointments = gson.fromJson(reader, appointmentListType);
        } catch (IOException e) {
            System.out.println("Fejl ved læsning af JSON-fil: " + e.getMessage());
        }
    }

    // NYT: Find en aftale ud fra ID
    public Appointment findAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null; // Returner null hvis ingen aftale findes
    }

    // Klasse til at repræsentere en kredit
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

    // NYT: Klasse til Appointment (som læses fra JSON)
    class Appointment {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    // Registrer betaling
    public void registerPayment(double addons) {
        double totalAmount = baseAmount + addons;
        System.out.println("Betaling registreret: " + totalAmount + " kr.");
    }

    // ÆNDRET: Registrer kredit ved brug af appointment fra JSON
    public void registerCredit(int appointmentId, double addons) {
        Appointment appointment = findAppointmentById(appointmentId);

        if (appointment != null) {
            double totalAmount = baseAmount + addons;
            Credit credit = new Credit(appointmentId, totalAmount);
            creditList.add(credit);
            System.out.println("Kredit er registreret for aftale: " + appointment.getName() + " (ID: " + appointmentId + ") Med pris: " + totalAmount + " kr.");
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    // ÆNDRET: Betal kredit ved brug af appointment fra JSON
    public void payCredit(int appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);

        if (appointment != null) {
            boolean found = false;
            for (Credit credit : creditList) {
                if (credit.getAppointmentId() == appointmentId) {
                    found = true;
                    if (!credit.isPaid()) {
                        credit.pay();
                        System.out.println("Kredit betalt for aftale: " + appointment.getName() + " (ID: " + appointmentId + ")");
                    } else {
                        System.out.println("Kredit for aftale nr: " + appointmentId + " er allerede betalt.");
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("Ingen kredit blev fundet for aftale nr: " + appointmentId);
            }
        } else {
            System.out.println("Ingen aftale fundet med ID: " + appointmentId);
        }
    }

    // Menu med scanner
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Vælg en handling:");
            System.out.println("1. Registrer betaling");
            System.out.println("2. Registrer kredit");
            System.out.println("3. Betal kredit");
            System.out.println("4. Afslut");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerPayment(addons);
                    break;

                case 2:
                    System.out.println("Indtast aftale nummer: ");
                    int appointmentId = scanner.nextInt();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    addons = scanner.nextDouble();
                    scanner.nextLine();
                    registerCredit(appointmentId, addons);
                    break;

                case 3:
                    System.out.println("Indtast aftale nummer: ");
                    appointmentId = scanner.nextInt();
                    payCredit(appointmentId);
                    break;

                case 4:
                    System.out.println("Afslutter programmet.");
                    exit = true;
                    break;

                default:
                    System.out.println("Ugyldigt valg, prøv igen.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        PaymentHandler handler = new PaymentHandler();
        handler.startMenu();
    }
}
