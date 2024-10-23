public class Appointment {

    static int accountCounter=1;
    private int id=0;



    Appointment(){
        this.id=accountCounter++;


    }


    @Override
    public String toString() {
        return "Appointment id: "+id;
    }
}
