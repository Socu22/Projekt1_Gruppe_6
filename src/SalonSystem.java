import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

// Konsolbaseret single-user bookingsystem.
public class SalonSystem {

    private FileHandler fileHandler;

    SalonSystem(FileHandler inputFileHandler){
        this.fileHandler=inputFileHandler;
    }


    public void run() throws Exception {
        BookingHandler b = new BookingHandler();
        InputHandler input = new InputHandler();

        Scanner scanner = new Scanner(System.in);




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
            int choice = input.inputInt();
                switch (choice) {
                    case 1: // Oprettelse af aftale
                        try {
                            b.createAppointment(fileHandler);
                        }
                        catch (Exception e){
                            System.out.println("Kunne ikke oprette booking");
                        }


                        break;

                    case 2: // Slet en aftale
                        b.deleteAppointment(fileHandler);
                        break;

                    case 3: // Viser ledige tider
                        b.showAppointments(fileHandler);
                        break;

                    case 4: // Ferie/lukkedage
                        b.registerHoliday(fileHandler);
                        break;

                    case 5: // Økonomioplysninger - kræver password
                         try {
                            EconomyLogHandler econmyHandler = new EconomyLogHandler(fileHandler);  // <-- Opretter PaymentHandler
                            econmyHandler.startEconomyMenu();  // <-- Starter PaymentHandler menuen
                        } catch (
                                IOException e) {  // <-- Håndtering af generel fil-IO-fejl inklusive FileNotFoundException
                            // Håndtering af IOException
                            System.out.println("Fejl: Der opstod en fejl ved indlæsning af filen.");
                            e.printStackTrace();  // Udskriver stack trace for yderligere fejldiagnose
                        }
                        break;

                    case 6: // Indregistrering af økonomi (Betaling og kredit)
                        try {
                            PaymentHandler paymentHandler = new PaymentHandler(fileHandler);  // <-- Opretter PaymentHandler
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