import java.time.LocalDate;


public class Appointment {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int bookingId =0;
    private String name="";
    private String phoneNumber ="";
    private String mail="";
    private LocalDate date;
    private int timeSlot;
    private LocalDate appointmentDateData;



    Appointment(){

        this.bookingId =accountCounter++;
        this.date=LocalDate.of(2024,1,1);
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
        return String.format("Appointment nr %d,Date: "+date+", TimeSlot(1-n): %d  ,Name: %s, Phone Number: %s, Mail: %s,", bookingId,timeSlot,name, phoneNumber, mail);
    }
}
