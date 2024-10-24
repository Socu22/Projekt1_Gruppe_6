import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class FileHandler {
    //The arraylist of appointments in memory
    private ArrayList<Appointment2> listOfAppointment2s = new ArrayList<>();

    //Creates a Gsonbuilder to convert variables into Json. Will be used to convert the arraylist of appointments to and from Json.
    static Gson gson = new GsonBuilder()
            //Tells the GsonBuilder to write the json in a pretty way.
            .setPrettyPrinting()
            //tells the GsonBuilder to create itself.
            .create();

    FileHandler (){

    }

    public static void main(String[] args) throws IOException {


        /*
        //This is an imaginary appointment coming from the software
        TestObject t1 = new TestObject("t1", 1, LocalDate.of(2024,01,01));
        System.out.println(t1);

        //Convert the appointment into values that json can handle (date and time to strings)
        TestObjectForSerialization ts1 = new TestObjectForSerialization(t1);
        System.out.println(ts1);

        //Writes the object to a json file
        String jsonToWrite = gson.toJson(ts1);
        FileWriter jsonWriter = new FileWriter("TestFile.json", false);
        jsonWriter.write(jsonToWrite);
        jsonWriter.close();
        System.out.println("File created and saved");

        //Read the json file
        FileReader jsonReader = new FileReader("TestFile.json");
        JsonElement jsonText = gson.fromJson(jsonReader, JsonElement.class);
        System.out.println("File opened: ");
        System.out.println(jsonText);

        //Convert the json file back into the object
        TestObjectForSerialization ts2 = (gson.fromJson(jsonText, TestObjectForSerialization.class));
        System.out.println(ts2);
        t1 = new TestObject(ts2);
        System.out.println(t1);
         */




        //Test arraylist of objects.
        ArrayList<TestObject> listOfTestObjects = new ArrayList<>();

        for (int i = 1; i<=10; i++){
            TestObject t = new TestObject("t1", i, LocalDate.of(2024,01,i));
            listOfTestObjects.add(t);
        }
        System.out.println(listOfTestObjects);

        ArrayList<TestObjectForSerialization> convertedlist = new ArrayList<>();
        for (TestObject obj: listOfTestObjects){
            TestObjectForSerialization ts = new TestObjectForSerialization(obj);
            convertedlist.add(ts);
        }

        System.out.println(convertedlist);

        String json = gson.toJson(convertedlist);

        System.out.println(json);

        Type testObjectListType = new TypeToken<ArrayList<TestObjectForSerialization>>() {}.getType();
        ArrayList<TestObjectForSerialization> deserializedList = gson.fromJson(json, testObjectListType);
        System.out.println(deserializedList);

        for (TestObjectForSerialization object: deserializedList){
            TestObject t = new TestObject(object);
            System.out.println(t);
        }
    }
    //Booking File has to be created
}

class TestObjectForSerialization{
    private String convertedDate;
    private int testInt;
    private String testString;

    TestObjectForSerialization(TestObject testObject){
        this.convertedDate = testObject.dateGetter().toString();
        this.testString = testObject.stringGetter();
        this.testInt = testObject.numbergetter();
    }

    String dateGetter(){
        return convertedDate;
    }

    String stringGetter(){
        return testString;
    }

    int numberGetter(){
        return testInt;
    }

    public String toString(){
        return "CONVERTED: test: " + testString + " number: " + testInt + " date: " + convertedDate;
    }
}


class TestObject{
    private LocalDate date;
    private int testInt;
    private String testString;

    TestObject (String string, int number, LocalDate date){
        this.date = date;
        this.testString = string;
        this.testInt = number;
    }

    TestObject (TestObjectForSerialization testObjectForDeserialization){
        this.date = LocalDate.parse(testObjectForDeserialization.dateGetter());
        this.testString = testObjectForDeserialization.stringGetter();
        this.testInt = testObjectForDeserialization.numberGetter();
    }

    LocalDate dateGetter(){
        return date;
    }

    String stringGetter(){
        return testString;
    }

    int numbergetter(){
        return testInt;
    }

    public String toString(){
        return "test: " + testString + " number: " + testInt + " date: " + date;
    }
}