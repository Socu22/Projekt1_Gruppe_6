public class Appointment {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int bookingId =0;
    private String name="";
    private String phoneNumber ="";
    private String mail="";
    private String date="";
    private int timeSlot;



    Appointment(){

        this.bookingId =accountCounter++;
        this.date="XX/XX/XX";
        this.timeSlot=999999;
        this.name="Empty";
        this.phoneNumber ="XXXXXXXX";
        this.mail="XXX@XX.dk";



    }



    public static void setAccountCounter(int accountCounter) {
        Appointment.accountCounter = accountCounter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookingId() {
        return bookingId;
    }

    @Override
    public String toString() {
        return String.format("Appointment nr %d,Date: %s, TimeSlot(1-n): %d  ,Name: %s, Phone Number: %s, Mail: %s,", bookingId,date,timeSlot,name, phoneNumber, mail);
    }
}
