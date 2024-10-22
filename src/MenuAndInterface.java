import java.util.ArrayList;

public class MenuAndInterface {
    //konsolbaseret single-user bookingsystem.

    static protected ArrayList<Appointment> appointmentsArrayList = new ArrayList<>(365);//Forsøg på at skabe 365 dage
    public static void main(String[] args) {
        System.out.println("Hej Drenge, jeg er glad for at rapportere, at mit Git virker igen!");

        for (int i = 0; i < 365 ; i++) { //skaber det
            appointmentsArrayList.add(null);

        }
        System.out.println(appointmentsArrayList.size()); // tjekker at arraylist har den korekte size


    }

}

