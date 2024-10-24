import java.util.Collections;

public class BookingHandler extends MenuAndInterface {
    public static void main(String[] args) {
        /*BookingHandler b = new BookingHandler(); //test1
            //b.createBooking(2); //create in a specific timestamp
            b.nextDay(); //send to arraylist (in Menu and Inteface) to send in the timestamps so it can be changed in a specific way.
            //System.out.println(appointmentsArrayList.size()); //confirms that arraylist has increased
            //System.out.println(appointmentsArrayList.get(2)); //confirms that appointment is there (create a booking)
            b.createBookingToSpecificDay(1, 1);
            b.nextDay();
            b.createBookingToSpecificDay(2, 1);
            b.createBookingToSpecificDay(2, 1);
            // System.out.println(appointmentsArrayList.get(0)); //confirms that appointment is there (createBookingToSpecificDay)
            // b.deleteBookingToSpecificDay(1,1);
            //System.out.println(appointmentsArrayList.get(0)); //confirms that appointment is deleted (deleteBookingToSpecificDay)
            b.showBooking();*/
        BookingHandler b2 = new BookingHandler();
        b2.createAllAppointments();
        b2.changeSpecificBooking(1,1,"Jake Fucktart");
        b2.showBooking();
    }
    static  int index; //Index chosen depening on day and Timeslot
    static Appointment currentAppointment=null; //So you know which Appointment is the current appointment
    static Appointment[] timeSlotInADay= new Appointment[8]; //The eight timeslots Chosen
    static int bookingNr = 0;

    public void createAllAppointments(){ //Fill up an arraylist so it can be used later
        for (int i = 0; i < 366; i++) {
            nextDay();
            for (int j = 0; j < timeSlotInADay.length ; j++) {
                timeSlotInADay[j]= new Appointment();
            }
        }
        //some kind of bug with ekstra fields in arrayList but ti works now with the code
        appointmentsArrayList.remove(0);
        appointmentsArrayList.remove(1);
        appointmentsArrayList.remove(2);
        appointmentsArrayList.remove(3);
        appointmentsArrayList.remove(0);
        appointmentsArrayList.remove(1);
        appointmentsArrayList.remove(0);;
        appointmentsArrayList.removeFirst();
}

    public void nextDay(){ //this sends an array to arralist just to make it easier to determin that the n timeslots is added per day
        Collections.addAll(appointmentsArrayList,timeSlotInADay);
    }
    public void changeSpecificBooking(int dayNo, int posOneToEight, String newName) {//this is to change an orignal empty booking to add a new name depending on paramether
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < appointmentsArrayList.size()) {
            currentAppointment = appointmentsArrayList.get(index);
            try {
                currentAppointment.setName(newName);//you can add more of these setter methoeds
            }catch (NullPointerException e){}
        }
/*    public void createBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < appointmentsArrayList.size()) {appointmentsArrayList.set(index, new Appointment());  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}
    public void createBooking(int pos){
        //timeSlotInADay[pos]= new Appointment();}
    public void deleteBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < appointmentsArrayList.size()) {appointmentsArrayList.set(index, null);  // Replace existing appointment
        } else {System.out.println("Error: Invalid booking index.");}}*/
    }
    public void deleteBookingToSpecificDay(int dayNo, int posOneToEight){
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < appointmentsArrayList.size()) {
            currentAppointment = appointmentsArrayList.get(index);
            try {
                currentAppointment.setName("Empty");
            }catch (NullPointerException e){}
        }

    }
    public void showBooking(){//prints all booking in system out print
        for (int i = 0; i < appointmentsArrayList.size(); i++) {
            System.out.println(appointmentsArrayList.get(i));
        }
    }
    public Appointment selectBooking(int dayNo, int posOneToEight){ //custom methoed for selekting an specifik appointment
        index = (dayNo - 1) * 8 + (posOneToEight - 1);
        if (index < appointmentsArrayList.size()) {
            currentAppointment = appointmentsArrayList.get(index);
        }
        return currentAppointment;
    }
}
