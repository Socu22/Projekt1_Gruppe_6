import java.time.LocalDate;
import java.time.LocalTime;


public class Appointment {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int bookingId =0;
    private String name="";
    private String phoneNumber ="";
    private LocalDate date;
    private LocalTime timeSlot;
    private double price;
    private boolean isBooked;


    Appointment(){

        this.bookingId =accountCounter++;
        this.date=LocalDate.of(2024,1,1);
        this.timeSlot=LocalTime.of(10,00);
        this.name="Empty";
        this.phoneNumber ="XXXXXXXX";
        this.price =700.00;
        this.isBooked=false;



    }

    Appointment(LocalDate dateForAppointment, LocalTime timeForAppointment){

        this.bookingId =accountCounter++;
        this.date=LocalDate.of(2024,1,1);
        this.timeSlot=LocalTime.of(10,00);
        this.name="Empty";
        this.phoneNumber ="XXXXXXXX";
        this.price = 700.00;
        this.isBooked = false;



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

    public String isItBooked(){
        if (this.isBooked = true){
            return "BOOKED";
        }
        else {
            return "AVAILABLE";
        }
    }

    @Override
    public String toString() {
//         return String.format("Appointment nr %d,Date: "+date+", TimeSlot(1-n): %d  ,Name: %s, Phone Number: %s, Mail: %s,", bookingId,timeSlot,name, phoneNumber, mail);
        return "Appointment nr: " + bookingId + "\nBooking status: " + isItBooked() + "\n \tName:" + name + "\n \tPhone number:" + phoneNumber + "\n \tDate:" + date + "\n \tTime:" + timeSlot;
    }
}
