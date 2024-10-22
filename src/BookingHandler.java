public class BookingHandler extends MenuAndInterface {
    public static void main(String[] args) {

    }

    static Appointment[] timeSlotInADay= new Appointment[8];
    static int bookingNr = 0;


    public void createBooking(){

        timeSlotInADay[bookingNr]= new Appointment();
        bookingNr++;

    }
    public void deleteBooking(int bookingNr){
        timeSlotInADay[bookingNr]==new Appointment().n;

    }
    public void showBooking(){}
    public void selectBooking(){}
}
