import java.util.ArrayList;

public class MenuAndInterface {
    //konsolbaseret single-user bookingsystem.

    static protected ArrayList<Appointment2> appointmentsArrayList = new ArrayList<>(365);//Bruges i Bookinghandler
    static protected ArrayList<Appointment> DontFuckTHisappointmentsArrayList = new ArrayList<>(365);//Bruges i Bookinghandler
    public static void main(String[] args) {

        for (int i = 0; i < 365 ; i++) { //skaber det
            appointmentsArrayList.add(null);

        }
        System.out.println(appointmentsArrayList.size()); // tjekker at arraylist har den korekte size


    }


}

