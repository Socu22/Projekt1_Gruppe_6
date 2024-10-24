import java.time.LocalDate;
import java.util.Collections;

public class BookingHandler extends MenuAndInterface {
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
        //b2.createAllAppointments();
        b2.changeSpecificBooking(1,1,"Jake Fucktart");
        b2.showBooking();
    }
    static  int index;
    static Appointment currentAppointment=null;
    static Appointment[] timeSlotInADay= new Appointment[8];
    static int bookingNr = 0;

    /*public void createAllAppointments(){
        for (int i = 0; i < 366; i++) {
            nextDay();
            for (int j = 0; j < timeSlotInADay.length ; j++) {
                timeSlotInADay[j]= new Appointment();
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
    
    public LocalDate getAppointmentDatoAndTid(LocalDate date, LocalDate tid){
        
        for(Appointment a : DontFuckTHisappointmentsArrayList){
            if (date==a.getDate()){
                return a.getDate();
            }
            
        }
    }
    
}
