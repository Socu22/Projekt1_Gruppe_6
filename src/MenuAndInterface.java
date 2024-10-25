import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class MenuAndInterface {
    //konsolbaseret single-user bookingsystem.

    static protected ArrayList<Appointment> appointmentsArrayList = new ArrayList<>(365);//Bruges i Bookinghandler
    static FileHandler f;
    public static void main(String[] args) throws IOException {

      BookingHandler b = new BookingHandler();
        f=new FileHandler();
        System.out.println(b.findAppointment_WithId(1,f));
        b.findAppointment_WithId(1,f).setName("Lucas");
        System.out.println(b.findAppointment_WithId(1,f));
        System.out.println(b.findAppointment_WithNavn("Empty",f));
        b.findAppointment_WithDateAndTime(LocalDate.of(2024,1,1), LocalTime.of(15, 0),f).setName("Gentle_mand");
        System.out.println(b.findAppointment_WithDateAndTime(LocalDate.of(2024,1,1), LocalTime.of(15, 0),f));
        System.out.println(b.findAppointment_WithId(6,f));

    }


}

