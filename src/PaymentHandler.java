import java.util.ArrayList;
import java.util.Scanner;

public class PaymentHandler {

    // Grundbeløbet på en timeslot er 700 kroner, som bruges i alle betalinger
    // Værdien er private og final fordi den ikke skal kunne ændres og kun tilgås her fra klassen
    private static final double baseAmount = 700.0;

    // Jeg laver en arraylist til at gemme kredit
    // Arraylisten er public så den kan tilgås af andre klasser, som fx vores kalender
    public ArrayList<Credit> creditList;

    // Jeg starter arraylisten
    public PaymentHandler() {
        creditList = new ArrayList<>();
    }

    // Jeg laver en kredit-klasse for at gemme kredit hvor en aftale igennem et appointment-id
    class Credit {
        int appointmentId;
        double amount;
        boolean isPaid;

        // Jeg laver en constructor
        Credit(int appointmentId, double amount) {
            this.appointmentId = appointmentId;
            this.amount = amount;
            this.isPaid = false;
        }

        // Jeg laver en metode der hedder pay for at kunne fortælle om kreditten er betalt
        public void pay() {
            this.isPaid = true;
        }

        // Returnerer om kredit er betalt
        public boolean isPaid() {
            return this.isPaid;
        }

        // Returnere et aftale id
        public int getAppointmentId() {
            return appointmentId;

        }
    }

    //Jeg laver en metode der skal kunne registrere en betaling
    // Metoden tager summen af prisen for en aftale sammen med add-ons og uskrifter det samlet
    public void registerPayment(double addons) {
        double totalAmount = baseAmount + addons;
        System.out.println("Betaling registreret: " + totalAmount + " kr.");
    }

    // Jeg laver en metode der skal kunne registrere kredit
    // Når vi registrerer kredit vil det blive gemt i en arraylist og derefter udskrives
    public void registerCredit(int appointmentId, double addons) {
        double totalAmount = baseAmount + addons;
        Credit credit = new Credit(appointmentId, totalAmount);
        creditList.add(credit);
        System.out.println("Kredit er registreret for aftale nr: " + appointmentId + " Med pris: " + totalAmount + " kr.");

    }

    // Jeg laver en metode der skal kunne betale kredit baseret på aftale nummer
    public void payCredit(int appointmentId) {
        boolean found = false;

        // Vi laver et for loop, der fortæller os om kreditten der skal betales er fundet
        for (Credit credit : creditList) {
            if (credit.getAppointmentId() == appointmentId) {
                found = true;

                // Vi laver en if statement der siger at hvis kreditten ikke er betalt, går den ind og betaler den
                // Ellers vil den skrive at den allerede er betalt
                if (!credit.isPaid()) {
                    credit.pay();
                    System.out.println("Kredit betalt for appointmentId " + appointmentId);
                } else {
                    System.out.println("Kredit for aftale nr: " + appointmentId + " er allerede betalt.");
                }
                break;
            }
        }

        // Hvis der ikke blev fundet udestående kredit under aftale nummeret, vil det blive udskrevet
        if (!found) {
            System.out.println("Ingen kredit blev fundet for aftale nr: " + appointmentId);
        }

    }

    // Jeg opretter en metode der viser menuen og tilføjer en scanner så vi kan tage imod input
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Vælg en handling:");
            System.out.println("1. Registrer betaling");
            System.out.println("2. Registrer kredit");
            System.out.println("3. Betal kredit");
            System.out.println("4. Afslut");

            // Scanneren kan nu modtage et input fra brugeren mellem 1-4
            int choice = scanner.nextInt();

            // Jeg opretter en switch case baseret på brugerens valg
            switch (choice) {
                // Case 1 er til at registrere en betaling
                case 1:
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    double addons = scanner.nextDouble();
                    registerPayment(addons);
                    break;

                // Case 2 er til at registrere kredit på et aftale nummer
                case 2:
                    System.out.println("Indtast aftale nummer: ");
                    int appointmentId = scanner.nextInt();
                    System.out.println("Indtast eventuelle ekstra køb i kr: ");
                    addons = scanner.nextDouble();
                    scanner.nextLine();
                    registerCredit(appointmentId, addons);
                    break;

                // Case 3 er til at betale udestående kredit
                case 3:
                    System.out.println("Indtast aftale nummer: ");
                    appointmentId = scanner.nextInt();
                    payCredit(appointmentId);
                    break;

                // Case 4 er til at afslutte programmet
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
        // Vi opretter et objekt "handler" som man vil kunne hente fra andre steder af senere hen
        PaymentHandler handler = new PaymentHandler();
        handler.startMenu();
    }
}
