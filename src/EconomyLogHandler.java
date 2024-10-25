import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class EconomyLogHandler {

    private FileHandler fileHandler;

    public EconomyLogHandler() throws IOException {
        this.fileHandler = new FileHandler();
    }

    // Metode som printer regninger for en angiven dato
    public void seeEarnings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indtast en dato (ÅR-MÅNED-DAG): ");
        LocalDate dateInput;

        try {
            dateInput = LocalDate.parse(scanner.next());
        } catch (Exception e) {
            System.out.println("Ugyldigt format! Prøv igen.");
            return;
        }

        ArrayList<Appointment> appointments = fileHandler.getList();
        boolean found = false;

        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(dateInput)) {
                System.out.println(appointment);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Ingen aftaler fundet for datoen: " + dateInput);
        }
    }

    // Metode som viser aftaler med kredit
    public void showCredit() {
        ArrayList<Appointment> appointments = fileHandler.getList();
        boolean hasCredit = false;

        for (Appointment appointment : appointments) {
            if (appointment.getCredit() > 0) {  // Sørg for at 'getCredit()' findes i Appointment-klassen
                System.out.println(appointment);
                hasCredit = true;
            }
        }

        if (!hasCredit) {
            System.out.println("Ingen aftaler med kredit fundet.");
        }
    }


    public void startEconomyMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Økonomimenu:");
            System.out.println("1. Vis fakturaer for en bestemt dato");
            System.out.println("2. Vis alle aftaler med kredit");
            System.out.println("3. Tilbage til hovedmenu");

            int choice;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Ugyldigt valg, prøv igen.");
                continue;
            }

            switch (choice) {
                case 1:
                    seeEarnings();
                    break;
                case 2:
                    showCredit();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg, prøv igen.");
            }
        }
    }
    public static void main(String[] args) throws IOException {
        EconomyLogHandler ehandler = new EconomyLogHandler();
        ehandler.startEconomyMenu();
    }
}