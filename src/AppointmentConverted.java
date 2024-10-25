import java.time.LocalDate;
import java.time.LocalTime;


public class AppointmentConverted {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int bookingId = 0;
    private String name;
    private int phoneNumber;
    private String date;
    private String timeSlot;
    private double price;
    private double credit;
    private boolean isBooked;


    AppointmentConverted(Appointment appointmentForConvert){

        this.bookingId =accountCounter++;
        this.date=(appointmentForConvert.getDate().toString());
        this.timeSlot=appointmentForConvert.getTime().toString();
        this.name=appointmentForConvert.getname();
        this.phoneNumber = appointmentForConvert.getPhoneNumber();
        this.price = appointmentForConvert.getPrice();
        this.isBooked = false;
        this.credit = appointmentForConvert.getCredit();
    }

    public static void setAccountCounter(int accountCounter) {
        AppointmentConverted.accountCounter = accountCounter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookingId() {
        return bookingId;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public double getPrice() {
        return price;
    }

    public double getCredit(){
        return credit;
    }


    String getname(){
        return name;
    }

    String getDate(){
        return date;
    }

    String getTime(){
        return timeSlot;
    }

    int getPhoneNumer(){
        return phoneNumber;
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
