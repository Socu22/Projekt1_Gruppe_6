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
            b.showBooking();

         */
        BookingHandler b2 = new BookingHandler();
        b2.createAllAppointments();
        b2.showBooking();

    }

     static Appointment[] timeSlotInADay= new Appointment[8];
    static int bookingNr = 0;



    public void createAllAppointments(){
        for (int i = 0; i < 366; i++) { //det her er ikke en fejl den her metode laver præcist et års dage
            nextDay();
            for (int j = 0; j < timeSlotInADay.length ; j++) {
                timeSlotInADay[j]= new Appointment();


            }



        }
    }

    public void nextDay(){
        Collections.addAll(appointmentsArrayList,timeSlotInADay);

    }
    public void createBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < appointmentsArrayList.size()) {
            appointmentsArrayList.set(index, new Appointment());  // Replace existing appointment
        } else {
            System.out.println("Error: Invalid booking index.");
        }
    }
    public void createBooking(int pos){

        //timeSlotInADay[pos]= new Appointment();


    }
    public void deleteBookingToSpecificDay(int dayNo, int posOneToEight) {
        int index = (dayNo - 1) * 8 + (posOneToEight - 1);  // Calculate the correct index
        if (index < appointmentsArrayList.size()) {
            appointmentsArrayList.set(index, null);  // Replace existing appointment
        } else {
            System.out.println("Error: Invalid booking index.");
        }
    }
    public void showBooking(){
        for (int i = 0; i < appointmentsArrayList.size(); i++) {
            System.out.println(appointmentsArrayList.get(i));


        }
    }
    public void selectBooking(){}
}
