import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class EconomyLogHandler {

    private static final String PASSWORD = "hairyharry";
    public static InputHandler inputHandler= new InputHandler();

    private FileHandler fileHandler;


    public EconomyLogHandler(FileHandler inputFileHandler) throws IOException {
        this.fileHandler=inputFileHandler;

    }

    // Metode som printer regninger for en angiven dato
    public void seeEarnings(FileHandler fileHandler) {
        double totalEarningsForToday = 0;
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
            if (appointment.getDate().equals(dateInput) && appointment.getPrice() > 0) {
                System.out.println(appointment);
                totalEarningsForToday = totalEarningsForToday + appointment.getPrice();
            }
        }

        if (totalEarningsForToday == 0) {
            System.out.println("Ingen aftaler fundet for datoen: " + dateInput);
        }
        else {
            System.out.println("Den totale indkomst for dagen "+dateInput+" er : "+totalEarningsForToday);
        }
    }

    // Metode som viser aftaler med kredit
    public void showCredit(FileHandler fileHandler) {
        ArrayList<Appointment> appointments = fileHandler.getList(); // indlæser appointments
        double totalcredit = 0;

        for (Appointment appointment : fileHandler.getList()) {
            if (appointment.getCredit() != 0) {  // Sørg for at 'getCredit()' findes i Appointment-klassen
                System.out.println(appointment);
                totalcredit = totalcredit + appointment.getCredit();
            }
        }

        if (totalcredit==0) {
            System.out.println("Ingen aftaler med kredit fundet.");
        }
        else {
            System.out.println("Den totale kredit er: "+ totalcredit);
        }
    }
    public void startEconomyMenu() throws Exception {
        String inputPASSWORD= inputHandler.inputString("Password: ");
        if(PASSWORD.equals(inputPASSWORD)){
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("Økonomimenu:");
                System.out.println("1. Vis alle fakturaer");
                System.out.println("2. Vis fakturaer for en bestemt dato");
                System.out.println("3. Vis alle aftaler med kredit");
                System.out.println("4. Tilbage til hovedmenu");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.next());
                } catch (Exception e) {
                    System.out.println("Ugyldigt valg, prøv igen.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        showFinancialData(fileHandler);
                        break;
                    case 2:
                        seeEarnings(fileHandler);
                        break;
                    case 3:
                        showCredit(fileHandler);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt valg, prøv igen.");
                }
            }
        }
    }
    public void showFinancialData(FileHandler fileHandler) {

            System.out.println("Økonomioplysninger:");
            for (Appointment a : fileHandler.getList()) {
                if (!a.getname().equalsIgnoreCase("empty") && !a.getname().equalsIgnoreCase("feriedag")) {
                    System.out.printf("Kunde: %s, Betalt: %s, Total regning: %f \n", a.getname(), a.isPaid() ? "Ja" : "Nej", a.getPrice());
                }
            }

    }


    public static void main(String[] args) throws IOException {
        //EconomyLogHandler ehandler = new EconomyLogHandler();
       // ehandler.startEconomyMenu();
    }
}