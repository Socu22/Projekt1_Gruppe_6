import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MenuAndInterface {
    //konsolbaseret single-user bookingsystem.

    static protected ArrayList<Appointment> appointmentsArrayList = new ArrayList<>(365);//Bruges i Bookinghandler
    static FileHandler f;
    public static void main(String[] args) throws FileNotFoundException {

      BookingHandler b = new BookingHandler();
      f=new FileHandler();
        System.out.println(b.findAppointment_WithId(1,f));
        System.out.println(b.findAppointment_WithNavn("Lucas",f));

    }


}

