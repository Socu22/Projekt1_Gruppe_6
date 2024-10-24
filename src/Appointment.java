import java.time.LocalDate;
import java.time.LocalTime;


public class Appointment {
    public static void main(String[] args) {

    }

    static int accountCounter=1;
    private int bookingId = 0;
    private String name;
    private int phoneNumber;
    private LocalDate date;
    private LocalTime timeSlot;
    private double price;
    private boolean isBooked = false;

    Appointment(AppointmentConverted aC){

        this.bookingId =aC.getBookingId();
        this.date=LocalDate.parse(aC.getDate());
        this.timeSlot=LocalTime.parse(aC.getTime());
        this.name=aC.getname();
        this.phoneNumber = aC.getPhoneNumer();
        this.price =aC.getPrice();
        this.isBooked=aC.isBooked();
    }

    Appointment(LocalDate dateForAppointment, LocalTime timeForAppointment, String name, int phonenumber){

        this.bookingId =accountCounter++;
        this.date=dateForAppointment;
        this.timeSlot=timeForAppointment;
        this.name=name;
        this.phoneNumber =phonenumber;
        this.price = 0.0;
        this.isBooked = false;
    }

    Boolean setBooked(){
        isBooked = true;
        return true;
    }

    public static void setAccountCounter(int accountCounter) {
        Appointment.accountCounter = accountCounter;
    }

    String getname(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    LocalDate getDate(){
        return date;
    }

    LocalTime getTime(){
        return timeSlot;
    }

    int getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(int recievedPhonenumber){
        this.phoneNumber=recievedPhonenumber;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double recievedPrice){
        this.price=recievedPrice;
    }

    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int recievedBookingid){
        this.bookingId=recievedBookingid;
    }


    public String isItBooked(){
        if (this.isBooked == true){
            return "BOOKED";
        }
        else {
            return "AVAILABLE";
        }
    }

    public boolean getBookingstatus(){
        return isBooked;
    }
    public void setBookingstatus(boolean inputs){
     isBooked=inputs;
    }
    /*public boolean inputs (){
        if (phoneNumber ){
            return
        }
    }*/

    @Override
    public String toString() {
//         return String.format("Appointment nr %d,Date: "+date+", TimeSlot(1-n): %d  ,Name: %s, Phone Number: %s, Mail: %s,", bookingId,timeSlot,name, phoneNumber, mail);
        return "Appointment nr: " + bookingId + "\nBooking status: " + isItBooked() + "\n \tName:" + name + "\n \tPhone number:" + phoneNumber + "\n \tDate:" + date + "\n \tTime:" + timeSlot;
    }
}
