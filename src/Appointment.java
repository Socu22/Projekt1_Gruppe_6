public class Appointment {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int id=0;
    private String name="";



    Appointment(){
        this.id=accountCounter++;
        this.name="test";


    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setAccountCounter(int accountCounter) {
        Appointment.accountCounter = accountCounter;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Appointment_Id:"+id+"\n\t"+name;
    }
}
