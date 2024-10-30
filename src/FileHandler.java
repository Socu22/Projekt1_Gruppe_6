import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class FileHandler {
    //The arraylist of appointments in memory
    private ArrayList<Appointment> listOfAppointments = new ArrayList<>();
    //The arraylist of converted appopintments
    private ArrayList<AppointmentConverted> convertedAppointments = new ArrayList<>();

    //Creates a Gsonbuilder to convert variables into Json. Will be used to convert the arraylist of appointments to and from Json.
    static Gson gson = new GsonBuilder()
            //Tells the GsonBuilder to write the json in a pretty way.
            .setPrettyPrinting()
            //tells the GsonBuilder to create itself.
            .create();


    //Constructor for filehandler object, starts by loading the calendar file.
    FileHandler () throws IOException {
        this.listOfAppointments = this.loadCalendar();
    }

    //Method for accessing the list of appointments in the filehandler
    ArrayList<Appointment> getList(){
        return listOfAppointments;
    }

    //Method for saving the calendar, should be done after changes are made to the list
    void saveCalendar() throws IOException {
        Appointment.accountCounter = 1;
        AppointmentConverted.accountCounter = 1;
        FileWriter jsonWriter = new FileWriter("src//Calendar2024-2025alt.json", false);
        convertToStrings(listOfAppointments);
        String jsonToWrite;
        jsonWriter.write("[");
        for (int i = 0; i<convertedAppointments.size();i++){
            jsonToWrite = gson.toJson(convertedAppointments.get(i));
            jsonWriter.write(jsonToWrite);
            if (i != listOfAppointments.size()-1){
                jsonWriter.write(",\n");
            }
        }
        jsonWriter.write("\n]");
        jsonWriter.close();
        System.out.println("File created and saved");
    }

    //Loads the calendar from the file
    ArrayList loadCalendar() throws FileNotFoundException {
        Appointment.accountCounter = 1;
        AppointmentConverted.accountCounter = 1;
        FileReader jsonReader = new FileReader("src//Calendar2024-2025alt.json");
        JsonElement jsonText = gson.fromJson(jsonReader, JsonElement.class);

        Type appointmentListType = new TypeToken<ArrayList<AppointmentConverted>>() {}.getType();
        ArrayList<AppointmentConverted> deserializedList = gson.fromJson(jsonText, appointmentListType);
        return convertToDates(deserializedList);
    }

    //Converts all objects in the list of appointments to object compatible with jSon
    ArrayList convertToStrings(ArrayList<Appointment> unconvertedAppointments){
        convertedAppointments = new ArrayList<>();
        AppointmentConverted aC;
        for (Appointment a: unconvertedAppointments){
            aC= new AppointmentConverted(a);
            convertedAppointments.add(aC);
        }
        return convertedAppointments;
    }

    //Converts the json file into a list of appointments.
    ArrayList convertToDates(ArrayList<AppointmentConverted> deSerializedList){
        ArrayList<Appointment> convertedlist = new ArrayList<>();
        for (AppointmentConverted obj: deSerializedList){
            Appointment a = new Appointment(obj);
            convertedlist.add(a);
        }
        return convertedlist;
    }



    public static void main(String[] args) throws Exception {
        /*
        FileHandler f1 = new FileHandler();
        BookingHandler b = new BookingHandler();
        b.findAppointment_WithId(1).setName("Mikkel");
        f1.saveCalendar();
        f1.saveCalendar();

         */
    }
}
