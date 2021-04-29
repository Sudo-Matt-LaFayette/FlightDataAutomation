package WebObjects;

public class Flight {

    private String airline;
    private double flightPrice;
    private String date;

    public Flight(String airline, double flightPrice, String date) {
        this.airline = airline;
        this.flightPrice = flightPrice;
        this.date = date;
    }

    public Flight() {

    }

    public String getAirline() {
        return airline;
    }

    public double getFlightPrice() {
        return flightPrice;
    }

    public String getDate() {
        return date;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setFlightPrice(double flightPrice) {
        this.flightPrice = flightPrice;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
