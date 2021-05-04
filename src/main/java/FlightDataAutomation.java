public class FlightDataAutomation {

    private String location;
    private String airline;
    private String date;
    private int price;

    public FlightDataAutomation(String location, String airline, int price) {
        this.location = location;
        this.airline = airline;
        this.price = price;
    }

    public FlightDataAutomation() {
    }

    public String getLocation() {
        return location;
    }

    public String getAirline() {
        return airline;
    }

    public int getPrice() {
        return price;
    }
}
