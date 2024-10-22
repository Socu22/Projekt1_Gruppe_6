import java.util.Collection;
import java.util.Collections;

public class BookingHandler extends MenuAndInterface {
    public static void main(String[] args) {
        BookingHandler b = new BookingHandler(); //test

        //b.createBooking(2); //create in a specific timestamp
        b.sendTSIDArrayToArrayList(); //send to arraylist (in Menu and Inteface) to send in the timestamps so it can be changed in a specific way.
        //System.out.println(appointmentsArrayList.size()); //confirms that arraylist has increased
        //System.out.println(appointmentsArrayList.get(2)); //confirms that appointment is there (create a booking)
        b.createBookingToSpecificDay(1,1);

        b.sendTSIDArrayToArrayList();
        b.createBookingToSpecificDay(2,1);


        System.out.println(appointmentsArrayList.get(0)); //confirms that appointment is there (addAppointmentTOADay)

        System.out.println("test");
        for (Appointment a : appointmentsArrayList){
            System.out.println(a);

        }






    }

    final static Appointment[] timeSlotInADay= new Appointment[8];
    static int bookingNr = 0;

    public void sendTSIDArrayToArrayList(){
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
    public void deleteBooking(int delPos){
        //timeSlotInADay[bookingNr]==new Appointment();

    }
    public void showBooking(){}
    public void selectBooking(){}
}
