import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        //test 2
        BookingHandler b2 = new BookingHandler();
       // b2.createAllAppointments();
        //b2.changeSpecificBooking(1,1,"Jake Fucktart");
        //b2.showBooking();



    }
    static InputHandler inputHandler = new InputHandler(); //Input methods needed



    public Appointment findAppointment_WithId(int idInput, FileHandler fileHandler){ //in the event that would need to find an appoint with id.
        for (Appointment a : fileHandler.getList() ){ //goes through a list from filehandler it is an arrayList, it goes through every element that is an appointment in the list
            if(idInput==a.getBookingId()){ //compares the value of the id with the value of a appointment, and returns the appointment the id matches with
                return a;
            }

        }
        System.out.println("Fandt ikke id"); //response in case of a failure
        return null; //then nothing is returned
    }


    public Appointment findAppointment_WithNavn(String stringInput, FileHandler fileHandler){ //same concept as above
        for (Appointment a : fileHandler.getList() ){
            if(Objects.equals(stringInput, a.getname())){
                return a;
            }

        }
        System.out.println("Fandt ikke navn");
        return null;
    }


    //a bit more complicated, but same concept
    public Appointment findAppointment_WithDateAndTime(LocalDate dateInput, LocalTime timeInput, FileHandler fileHandler){
        Appointment x = null; // this one is null because there needn't be an instance of a class here /object
        Appointment[] aTime = new Appointment[8]; //this one says that there a 8 appointments per day
        int i =0; //crude method for making a similiar concept as a for loop
        Boolean isDate=false;// isnt used but can be used at a later development

        for (Appointment a : fileHandler.getList() ){ // same concept, and array taken from filehandler

            String formaterDateInput =dateInput.toString(); //To be able to compare a string of Local date inputs
            String formaterA =a.getDate().toString();//To be able to compare a string of Local Time inputs from appointment
            if(Objects.equals(formaterDateInput,formaterA)){ //compares them and returns true if the 2 string equal each other
                aTime[i]=a; //inputs appointments in a array that later gets sends back to the real arraylist from filehandler
                isDate=true; //isn't used but can be used at a later date

                if(i==7){ //still 8 elements it goes 0, 1 ,2 ,3 ,4,5,6,7 == 8 elements
                    x=findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler);//all that before confirms the right date, and now its the right timeslot


                }


            }

            i++; //this makes sure i have a atribute like a for loop

            try {//try catch to catch a nulpointerException that wants to make sure what wil be .set is not null
                fileHandler.getList().set(Objects.requireNonNull(x).getBookingId(),x);//brute forces the appointment not conceted to filehandler to replace the exact index with the chosen appointment
            }catch (NullPointerException e ){}
        }
        return x;
    }
    public Appointment findADaysSpecificAppointmentTimeslot_WithArray(Appointment[] inputArray, LocalTime timeInput, FileHandler fileHandler){
        Appointment x = null;
        for (Appointment a : inputArray ){//goes through array of timeslots
            //compares strings again
            String formaterTimeInput =timeInput.toString();
            String formaterAArray =a.getTime().toString();
            if(Objects.equals(formaterTimeInput, formaterAArray)){
                 x=a; // and if the condition is true it returns a appointment
            }

        }


        return x;
    }

    public Appointment searchforAppointment(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isWorking;

        //this is earlier explained
        LocalDate dateInput = inputHandler.inputDate(); //inputs a date

        for (Appointment a : fileHandler.getList() ){
            //compares string
            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){
                aTime[i]=a;
                System.out.println(aTime[i]);
                isWorking=true;
                i++;

            }

        }
        LocalTime timeInput = inputHandler.inputTime(); //inputs a time
        return findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler);//use a ealier method
    }
    public void createAppointment(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];// 8 elemnts
        int i =0;
        Boolean isWorking;

        LocalDate dateInput = inputHandler.inputDate(); //inputs date

        for (Appointment a : fileHandler.getList() ){ //gets elements from arraylist
            //compares string
            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){ //if equal to each other it is true
                aTime[i]=a; // input the appointments to an array
                System.out.println(aTime[i]);
                isWorking=true;
                i++;

            }

        }


        LocalTime timeInput = inputHandler.inputTime();//inputs time
        //checks that the timeslot is not booked with . getBookingstatus
        if (!findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).getBookingstatus()){
            findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler); //gets the specific timeslot

            //System.out.println("Hvad er dit navn: (Kun fornavn)");
            String name = inputHandler.inputString("navn"); //write your name
            findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setName(name); //sets name on appointment

            System.out.println("Hvad er dit Telefon Nummer : ");
            int tlf = (Integer)inputHandler.inputInt(); //it can recive int input from a scanner, it is cast to Integer because somehow it is able to belive it should recive a String which should be impossible
            findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setPhoneNumber(tlf); // set phone number
            findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setBookingstatus(); // adds true to bookingstatus so it can't get booked in the furture

            fileHandler.saveCalendar(); // method for saving in the arraylist before it is converrtet to json file and then to AppointmentConverted
        }
        else{
            System.out.println("Denne tid er allerede booket"); // says the appointment is already booked
        }


    }
    public void showAppointments(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8]; // 8 timeslots
        int i;

        LocalDate dateInput = inputHandler.inputDate(); // gets an Local date

        for (int x = 0; x<5; x++){// gets Dates from now to 5 days in the future
            i = 0;
            for (Appointment a : fileHandler.getList()) { // for each Appointment element in Arraylist
                if (dateInput.equals(a.getDate())) {
                    aTime[i] = a;//adds to arraylist
                    System.out.println(aTime[i]);
                    i++;
                }
            }

            if (i == 0) { //this is how we find out if there isn't a day in the future
                System.out.println("Ingen datoer fundet for denne dato");
            } else {
                System.out.println("Total mængde aftaler på dagen: " + dateInput + ": " + i); // Appointments in a day
            }
            dateInput = dateInput.plusDays(1); // ads a day to dateInput to ...?
            System.out.println(dateInput);
        }


    }
    public void deleteAppointment(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8]; // array
        int i =0;
        Boolean isWorking=false;

        LocalDate dateInput = inputHandler.inputDate();


        for (Appointment a : fileHandler.getList() ){ // goes through Appointments in an arraylist

            //compares string
            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){ //here
                aTime[i]=a;//puts in array
                System.out.println(aTime[i]);
                isWorking=true;
                i++;










            }


        }


        LocalTime timeInput = inputHandler.inputTime(); // needs  a Local time
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler); // uses method

        String name = "Empty"; // sets a 'Empty' name to the Appointment readymade in array list
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setName(name); // sets name


        int tlf = 0; //same
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setPhoneNumber(tlf); // same
        findADaysSpecificAppointmentTimeslot_WithArray(aTime,timeInput,fileHandler).setBookingstatus();



        fileHandler.saveCalendar();

    }

    public void registerHoliday(FileHandler fileHandler) throws Exception {
        Appointment[] aTime = new Appointment[8];
        int i =0;
        Boolean isWorking=false;

        LocalDate dateInput = inputHandler.inputDate(); //inputs date
        for (Appointment a : fileHandler.getList() ){
            //compares string
            String formaterDateInput =dateInput.toString();
            String formaterA =a.getDate().toString();
            if(Objects.equals(formaterDateInput,formaterA)){ //here
                aTime[i]=a;
                isWorking=true;
                i++;
            }
        }
        for (int j = 0; j < aTime.length; j++) {
            aTime[j].setName("Feriedag");//set the name 'Feriedag' to  every appointment in array
        }
        fileHandler.saveCalendar();// saves in the arraylist in filehandler
    }





    //this is for Lucas And some import progress that made it so that i knew the logic behind making above methods
/*
    static Appointment[] timeSlotInADay= new Appointment[8];
    static  int index; //Unneeded Value That is not used
        static Appointment currentAppointment=null;

 */
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




    /*public void nextDay(){
        Collections.addAll(DontFuckTHisappointmentsArrayList,timeSlotInADay);
    }




    public void changeSpecificBooking(int dayNo, int posOneToEight, String newName) {
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < DontFuckTHisappointmentsArrayList.size()) {
            currentAppointment = DontFuckTHisappointmentsArrayList.get(index);
            try {
                currentAppointment.setName(newName);
            }catch (NullPointerException e){}
        }
    public void createBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < DontFuckTHisappointmentsArrayList.size()) {DontFuckTHisappointmentsArrayList.set(index, new Appointment());  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}
    public void createBooking(int pos){
        //timeSlotInADay[pos]= new Appointment();}
    public void deleteBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < DontFuckTHisappointmentsArrayList.size()) {DontFuckTHisappointmentsArrayList.set(index, null);  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}
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
    }*/


    




}
