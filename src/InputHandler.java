import java.sql.SQLOutput;
import java.util.Date;
import java.util.Scanner;
import java.time.*;

public class InputHandler {
    Scanner input = new Scanner(System.in);
    static String userInput;
    static boolean isInvalid;

    public void getInput(){
        userInput = input.nextLine();
    }

    boolean isInOpeningHours(int hour){
        if (hour >= 10 && hour <=17){
            return true;
        }
        else{
            return false;
        }
    }

    public int inputInt() throws Exception{
        isInvalid = true;
        System.out.println("Indtast et tal: ");
        getInput();
        int intInput = 9;
        while (isInvalid) {
            try {
                intInput = Integer.parseInt(userInput);
                isInvalid = false;
            } catch (Exception e) {
                System.out.println("invalid input: " + userInput + "\n Prøv igen (helt tal):");
                getInput();
            }
        }
        return intInput;
    }

    public String inputString(String desiredTyepOfString) throws Exception{
        isInvalid = true;
        System.out.println("Indtast " + desiredTyepOfString);
        getInput();
        String stringInput = "Error while inputting";
        while (isInvalid){
            try{
                stringInput = userInput;
                isInvalid = false;
            }
            catch (Exception e){
                System.out.println("invalid input: " + userInput + "\n Prøv igen (anything goes):");
                getInput();
            }
        }
        return stringInput;
    }

    public LocalDate inputDate() throws Exception{
        isInvalid = true;
        System.out.println("Indtast en dato: (YYYY-MM-DD)");
        getInput();
        LocalDate dateInput = LocalDate.of(0000,01,01);
        while (isInvalid){
            try{
                dateInput = LocalDate.parse(userInput);
                isInvalid = false;
            }
            catch (Exception e){
                System.out.println("invalid input: " + userInput + "\n Prøv igen (YYYY-MM-DD):");
                getInput();
            }
        }
        return dateInput;
    }

    public LocalTime inputTime() throws Exception{
        isInvalid = true;
        System.out.println("Indtast en time (10-17):");
        getInput();
        LocalTime timeInput = LocalTime.of(00,00);
        while(isInvalid){
            try {
                timeInput = LocalTime.of(Integer.parseInt(userInput),00);
                if (isInOpeningHours(Integer.parseInt(userInput))){
                    isInvalid = false;
                }
                else {
                    System.out.println("Den valgte time er ikke inden for butikkens åbningstid. Indtast venligst et andet tidspunkt:");
                    getInput();
                }
            }
            catch (Exception e){
                System.out.println("Invalid input: " + userInput + "\n Prøv igen (10-17): \n OBS: Det er kun muligt at booke på hele timer");
                getInput();
            }
        }
        return timeInput;
    }

    public static void main(String[] args) throws Exception {
        InputHandler iH = new InputHandler();
        System.out.println(iH.inputInt());
        System.out.println(iH.inputString("navn"));
        System.out.println(iH.inputDate());
        System.out.println(iH.inputTime());
    }
}
