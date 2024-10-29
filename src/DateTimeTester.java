import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DateTimeTester {
    public static void main(String[] args) throws IOException {
        LocalDate date = LocalDate.of(2023,12,31);
        LocalTime timeslot;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter jsonWriter = new FileWriter("src//Calendar2024-2025.json", false);
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
        jsonWriter.write("]");
        jsonWriter.close();


    }
}
