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

        //NOTE: THIS FILE SHOULD ONLY BE RUN ONCE, TO CREATE THE CALENDAR. HAS NO USE OTHERWISE, RUN AT YOUR OWN RISK (WILL DELETE THE CURRENT CALENDAR)
        //variables used to create random appointments
        String firstName;
        String lastName;
        String fullname;
        int pN;
        String[] firstnames = new String[]{"Mikkel", "Rasmus", "Lucas", "Oliver", "Frederik", "Julius", "Bjørn", "Jannick", "Aleksander", "Lasse", "Luise", "Leonora", "Emma", "Thit", "Elsa", "Anna", "Sarah", "Julie"};
        String[] lastnames = new String[]{"Mikkelsen", "Guldborg", "Knudsen", "Ahlers", "Pedersen", "Fuglø", "Gissel", "Pape", "Rendtorff", "Lehrdam", "Lauritsen", "Åle", "Ostemand", "Smed", "Grave", "Schmirnoffistock", "Ravsborg"};

        double priceOfAppointment = 0;
        double creditOfAppointment = 0;

        //Create a gsonbuilder to write the json file
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //Create the calendar file
        FileWriter jsonWriter = new FileWriter("src//Calendar2024-2025alt.json", false); //Set the file name to something else if you want to generate a new calendar for fun
        ArrayList<Appointment> listOfAppointments = new ArrayList<>();

        jsonWriter.write("["); //First square bracket required for formatting

        //Loop through two years worth of days
        for (int i = 0; i<730; i++){
            timeslot = LocalTime.of(10,00);
            date = date.plusDays(1);

            //Check if it is a weekend day. If it is, skip this cycle
            if ((date.getDayOfWeek().getValue() == 6) || (date.getDayOfWeek().getValue() == 7)){
                System.out.println("WEEKENDDAY, not generating for this day");
                continue;
            }

            //Create 8 appointments for every day
            for (int x = 0; x<8 ; x++){
                //Checks if it is before 2025, if it is, there are more random appointment booked
                if (date.isBefore(LocalDate.of(2025,01,01))){
                    chance = gen.nextInt(3);
                }
                //If it is after 01/01/2025, there are less appointments
                else {
                    chance = gen.nextInt(8);
                }
                //if it is after 01/06/2025 there are even less (Yes, we acknowledge it is unrealistic for people to book appointments at a hairdresser more than half a year in advance, but these appointments are good for testing purposes)
                if (date.isAfter(LocalDate.of(2025,06,01))){
                    chance = gen.nextInt(15);
                }
                priceOfAppointment = 0;
                creditOfAppointment = 0;

                //If the generator hits the target, it creates an appointment that is not empty, but randomly filled
                if(chance == 2){
                    firstName = firstnames[gen.nextInt(firstnames.length)]; //Find a random first name from the list
                    lastName = lastnames[gen.nextInt(lastnames.length)]; //find a random last name from the list
                    fullname = firstName + " " + lastName; //combine the random names

                    //if the date has passed at generation, set it to either be paid or have credit.
                    if (date.isBefore(LocalDate.now())){
                        chance = gen.nextInt(4);
                        if (chance == 3){
                            creditOfAppointment = (gen.nextInt(9)+1)*100+700;
                        }
                        else {
                            priceOfAppointment = (gen.nextInt(9)+1)*100+700;
                        }
                    }

                    //Set all the info in the appointment and write it to the file
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
                    jsonWriter.write(",\n"); //Writing this to the file for formatting.
                }

                //if the target is not hit, generates a random appointment instead
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
        jsonWriter.write("]"); //Json formatting
        jsonWriter.close();


    }
}
