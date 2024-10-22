public class Appointment {

    int id=0;

    Appointment( ){
        id=id+1;
    }


    @Override
    public String toString() {
        return ""+id;
    }
}
