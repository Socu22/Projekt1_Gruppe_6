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

    //Method for saving the calendar, should be done each time changes are made to the list
    void saveCalendar() throws IOException {
        Appointment.accountCounter = 1; //make sure Booking ideas do not change
        AppointmentConverted.accountCounter = 1; //Same as above
        FileWriter jsonWriter = new FileWriter("src//Calendar2024-2025alt.json", false); //Create the file, false makes sure to overwrite existing file
        convertToStrings(listOfAppointments); //Uses a method to convert the list of appointments to appointmenst that have strings instead of Local date and Local time
        String jsonToWrite; //Create a string that will be written to the json file
        jsonWriter.write("["); //Start with square brackets for formatting
        for (int i = 0; i<convertedAppointments.size();i++){ //Loops through the converted appointments and writes them to the json file
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

    //Loads the calendar from the file, done at program start
    ArrayList loadCalendar() throws FileNotFoundException {
        Appointment.accountCounter = 1; //Makes sure booking id's stay the same
        AppointmentConverted.accountCounter = 1; //Same as above
        FileReader jsonReader = new FileReader("src//Calendar2024-2025alt.json"); //find the file
        JsonElement jsonText = gson.fromJson(jsonReader, JsonElement.class); //Creates json elements for the objects noted in the file

        Type appointmentListType = new TypeToken<ArrayList<AppointmentConverted>>() {}.getType(); //Creates a type, so the program knows how to translate the objects to normal Appointment objects
        ArrayList<AppointmentConverted> deserializedList = gson.fromJson(jsonText, appointmentListType); //Translates the json objects into converted appointment objects
        return convertToDates(deserializedList); //Converts into real appointments with local date and local time objects
    }

    //Converts all objects in the list of appointments to object compatible with jSon
    ArrayList convertToStrings(ArrayList<Appointment> unconvertedAppointments){
        convertedAppointments = new ArrayList<>();
        AppointmentConverted aC;
        for (Appointment a: unconvertedAppointments){
            aC= new AppointmentConverted(a); //Take the provided apppintment and convert it using the constructor in the appointmentconverted class
            convertedAppointments.add(aC);
        }
        return convertedAppointments;
    }

    //Converts the objects retrieved from the json file into objets that have a Local date and local time variable instead of strings.
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
