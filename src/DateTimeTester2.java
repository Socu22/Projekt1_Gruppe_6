import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class DateTimeTester2 {
    public static void main(String[] args) throws IOException {
        LocalDate date = LocalDate.of(2023,12,31);
        LocalTime timeslot;
        Random gen = new Random();
        int chance = gen.nextInt(3);
        String firstName;
        String lastName;
        String fullname;
        int pN;
        String[] firstnames = new String[]{"Mikkel", "Rasmus", "Lucas", "Oliver", "Frederik", "Julius", "Bjørn", "Jannick", "Aleksander", "Lasse", "Luise", "Leonora", "Emma", "Thit", "Elsa", "Anna", "Sarah", "Julie"};
        String[] lastnames = new String[]{"Mikkelsen", "Guldborg", "Knudsen", "Ahlers", "Pedersen", "Fuglø", "Gissel", "Pape", "Rendtorff", "Lehrdam", "Lauritsen", "Åle", "Ostemand", "Smed", "Grave", "Schmirnoffistock", "Ravsborg"};
        double priceOfAppointment = 0;
        double creditOfAppointment = 0;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter jsonWriter = new FileWriter("src//Calendar2024-2025alt.json", false);
        ArrayList<Appointment> listOfAppointments = new ArrayList<>();

        jsonWriter.write("[");

        for (int i = 0; i<730; i++){
            timeslot = LocalTime.of(10,00);
            date = date.plusDays(1);

            if ((date.getDayOfWeek().getValue() == 6) || (date.getDayOfWeek().getValue() == 7)){
                System.out.println("WEEKENDDAY, not generating for this day");
                continue;
            }

            for (int x = 0; x<8 ; x++){
                if (date.isBefore(LocalDate.of(2025,01,01))){
                    chance = gen.nextInt(3);
                }
                else {
                    chance = gen.nextInt(8);
                }
                if (date.isAfter(LocalDate.of(2025,06,01))){
                    chance = gen.nextInt(15);
                }
                priceOfAppointment = 0;
                creditOfAppointment = 0;
                if(chance == 2){
                    firstName = firstnames[gen.nextInt(firstnames.length)];
                    lastName = lastnames[gen.nextInt(lastnames.length)];
                    fullname = firstName + " " + lastName;
                    if (date.isBefore(LocalDate.now())){
                        chance = gen.nextInt(4);
                        if (chance == 3){
                            creditOfAppointment = (gen.nextInt(9)+1)*100+700;
                        }
                        else {
                            priceOfAppointment = (gen.nextInt(9)+1)*100+700;
                        }
                    }

                    Appointment generatedAppointment = new Appointment(date, timeslot, fullname, gen.nextInt(89999999)+10000000);
                    generatedAppointment.setBookingstatus();
                    generatedAppointment.setPrice(priceOfAppointment);
                    generatedAppointment.setCredit(creditOfAppointment);
                    listOfAppointments.add(generatedAppointment);
                    System.out.println(date);
                    System.out.println(timeslot);
                    System.out.println(generatedAppointment);
                    timeslot = timeslot.plusHours(1);
                    AppointmentConverted aC = new AppointmentConverted(generatedAppointment);
                    String jsonToWrite = gson.toJson(aC);
                    jsonWriter.write(jsonToWrite);
                    jsonWriter.write(",\n");
                }
                else {
                    Appointment generatedAppointment = new Appointment(date, timeslot, "Empty", 00000000);
                    listOfAppointments.add(generatedAppointment);
                    System.out.println(date);
                    System.out.println(timeslot);
                    System.out.println(generatedAppointment);
                    timeslot = timeslot.plusHours(1);
                    AppointmentConverted aC = new AppointmentConverted(generatedAppointment);
                    String jsonToWrite = gson.toJson(aC);
                    jsonWriter.write(jsonToWrite);
                    jsonWriter.write(",\n");
                }
            }
        }
        jsonWriter.write("]");
        jsonWriter.close();


    }
}
