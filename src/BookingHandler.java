import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class BookingHandler {
    static ArrayList<Appointment> DontFuckTHisappointmentsArrayList = new ArrayList<>();
    public static void main(String[] args) {
        /*BookingHandler b = new BookingHandler(); //test1
            //b.createBooking(2); //create in a specific timestamp
            b.nextDay(); //send to arraylist (in Menu and Inteface) to send in the timestamps so it can be changed in a specific way.
            //System.out.println(DontFuckTHisappointmentsArrayList.size()); //confirms that arraylist has increased
            //System.out.println(DontFuckTHisappointmentsArrayList.get(2)); //confirms that appointment is there (create a booking)
            b.createBookingToSpecificDay(1, 1);
            b.nextDay();
            b.createBookingToSpecificDay(2, 1);
            b.createBookingToSpecificDay(2, 1);
            // System.out.println(DontFuckTHisappointmentsArrayList.get(0)); //confirms that appointment is there (createBookingToSpecificDay)
            // b.deleteBookingToSpecificDay(1,1);
            //System.out.println(DontFuckTHisappointmentsArrayList.get(0)); //confirms that appointment is deleted (deleteBookingToSpecificDay)
            b.showBooking();*/
        BookingHandler b2 = new BookingHandler();
       // b2.createAllAppointments();
        //b2.changeSpecificBooking(1,1,"Jake Fucktart");
        //b2.showBooking();



    }
    static  int index;
    static Appointment currentAppointment=null;
    static Appointment[] timeSlotInADay= new Appointment[8];
    static int bookingNr = 0;
    static InputHandler inputHandler = new InputHandler();


   /* public void createAllAppointments(){
        for (int i = 0; i < 366; i++) {
            nextDay();
            for (int j = 0; j < timeSlotInADay.length ; j++) {
                timeSlotInADay[j]= new Appointment(null,null,null,000000);
            }
        }
        //some kind of bug with ekstra fields in arrayList but ti works now with the code
        DontFuckTHisappointmentsArrayList.remove(0);
        DontFuckTHisappointmentsArrayList.remove(1);
        DontFuckTHisappointmentsArrayList.remove(2);
        DontFuckTHisappointmentsArrayList.remove(3);
        DontFuckTHisappointmentsArrayList.remove(0);
        DontFuckTHisappointmentsArrayList.remove(1);
        DontFuckTHisappointmentsArrayList.remove(0);;
        DontFuckTHisappointmentsArrayList.removeFirst();
}
    */



    /*public void nextDay(){
        Collections.addAll(DontFuckTHisappointmentsArrayList,timeSlotInADay);
    }

     */


    public void changeSpecificBooking(int dayNo, int posOneToEight, String newName) {
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < DontFuckTHisappointmentsArrayList.size()) {
            currentAppointment = DontFuckTHisappointmentsArrayList.get(index);
            try {
                currentAppointment.setName(newName);
            }catch (NullPointerException e){}
        }
/*    public void createBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < DontFuckTHisappointmentsArrayList.size()) {DontFuckTHisappointmentsArrayList.set(index, new Appointment());  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}
    public void createBooking(int pos){
        //timeSlotInADay[pos]= new Appointment();}
    public void deleteBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < DontFuckTHisappointmentsArrayList.size()) {DontFuckTHisappointmentsArrayList.set(index, null);  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}*/
    }

    public void showBooking(){
        for (int i = 0; i < DontFuckTHisappointmentsArrayList.size(); i++) {
            System.out.println(DontFuckTHisappointmentsArrayList.get(i));
        }
    }
    public Appointment selectBooking(int dayNo, int posOneToEight){
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < DontFuckTHisappointmentsArrayList.size()) {
            currentAppointment = DontFuckTHisappointmentsArrayList.get(index);
        }
        return currentAppointment;
    }


    public Appointment findAppointment_WithId(int idInput, FileHandler fileHandler){
        for (Appointment a : fileHandler.getList() ){
            if(idInput==a.getBookingId()){
                return a;
            }

        }
        System.out.println("Fandt ikke id");
        return null;
    }


    public Appointment findAppointment_WithNavn(String stringInput, FileHandler fileHandler){
        for (Appointment a : fileHandler.getList() ){
            if(Objects.equals(stringInput, a.getname())){
                return a;
            }

        }
        System.out.println("Fandt ikke navn");
        return null;
    }
    public Appointment findAppointment_WithDateAndTime(LocalDate dateInput, LocalTime timeInput, FileHandler fileHandler){
        Appointment x = null;
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isDate=false;

        for (Appointment a : fileHandler.getList() ){

            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){
                aTime[i]=a;
                isDate=true;

                if(i==7){
                    x=findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler);


                }


            }
            /*for (int j = 0; j < 8 ; j++) {
                String formaterTimeInput =timeInput.toString();
                String formaterATime =aTime[j].getTime().toString();
                if(Objects.equals(formaterTimeInput,formaterATime)){
                    x=aTime[j];
                }

            }*/
            i++;



            try {
                fileHandler.getList().set(Objects.requireNonNull(x).getBookingId(),x);
            }catch (NullPointerException e ){}
        }
        return x;
    }
    private Appointment findADaysSpecificAppointmentTimeslot_WithArray(Appointment[] inputArray, LocalTime timeInput, FileHandler fileHandler){
        Appointment x = null;
        for (Appointment a : inputArray ){
            String formaterTimeInput =timeInput.toString();
            String formaterAArray =a.getTime().toString();
            if(Objects.equals(formaterTimeInput, formaterAArray)){
                 x=a;
            }

        }


        return x;
    }
    public void createAppointment(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isWorking=false;

        LocalDate dateInput = inputHandler.inputDate();


        for (Appointment a : fileHandler.getList() ){

            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){
                aTime[i]=a;
                System.out.println(aTime[i]);
                isWorking=true;
                i++;










            }


        }

        /*System.out.println("Indtast tid x=time (XX:YY): ");
        inputHour = inputHandler.inputInt();
        System.out.println("Indtast tid y=Minutter (XX:YY): ");
        inputMinutes = inputHandler.inputInt();
        LocalTime timeInput = LocalTime.of(inputHour,inputMinutes);

         */
        LocalTime timeInput = inputHandler.inputTime();
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler);

        System.out.println("Hvad er dit navn: ");
        String name = inputHandler.input.next();
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setName(name);

        System.out.println("Hvad er dit Telefon Nummer : ");
        int tlf = (Integer)inputHandler.input.nextInt();
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setPhoneNumber(tlf);
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setBookingstatus();



        fileHandler.saveCalendar();

    }
    public void showAppointments(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i = 0;

        LocalDate dateInput = inputHandler.inputDate();

        for (Appointment a : fileHandler.getList()) {
            if (dateInput.equals(a.getDate())) {
                aTime[i] = a;
                System.out.println(aTime[i]);
                i++;
            }
        }

        if (i == 0) {
            System.out.println("No appointments found for the specified date.");
        } else {
            System.out.println("Total appointments on " + dateInput + ": " + i);
        }

    }
    public void deleteAppointment(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isWorking=false;

        LocalDate dateInput = inputHandler.inputDate();


        for (Appointment a : fileHandler.getList() ){

            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){
                aTime[i]=a;
                System.out.println(aTime[i]);
                isWorking=true;
                i++;










            }


        }

        /*System.out.println("Indtast tid x=time (XX:YY): ");
        inputHour = inputHandler.inputInt();
        System.out.println("Indtast tid y=Minutter (XX:YY): ");
        inputMinutes = inputHandler.inputInt();
        LocalTime timeInput = LocalTime.of(inputHour,inputMinutes);

         */
        LocalTime timeInput = inputHandler.inputTime();
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler);

        String name = "Empty";
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setName(name);


        int tlf = 99999999;
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setPhoneNumber(tlf);
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setBookingstatus();



        fileHandler.saveCalendar();

    }

    public void registerHoliday(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isWorking=false;

        LocalDate dateInput = inputHandler.inputDate();


        for (Appointment a : fileHandler.getList() ){

            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){
                aTime[i]=a;


                isWorking=true;
                i++;










            }


        }
        for (int j = 0; j < aTime.length; j++) {
            aTime[j].setName("Feriedag");


        }





        fileHandler.saveCalendar();

    }

    /*public Appointment findAppointment_WithTimeSlot(LocalDate timeInput, FileHandler fileHandler){
        Appointment x = null;
        for (Appointment a : fileHandler.getList() ){

            if(Objects.equals(formaterTimeInput,formaterA)){
                x=a;
            }

        }
        return x;
    }

     */


    




}
