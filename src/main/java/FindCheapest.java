import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FindCheapest {
    //"Cancun", "Las Vegas", "Denver", "Rome", "Milan", "Paris", "Madrid", "Amsterdam", "Singapore"
    List<FlightDataAutomation> flights = new ArrayList<>();





    public void readIn() throws IOException {
        File file = new File("flightresults.txt");
        List<String> lines = new ArrayList<>();
        lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

        System.out.println(lines);

        for (int i = 0; i < lines.size(); i++) {

            String[] splitComma = lines.get(i).split(",");

            String dates = splitComma[0];
            String location = splitComma[1];
            String airline = splitComma[2];
            String priceString = splitComma[3].replace(" ", "");
            int price = Integer.parseInt(priceString);

            System.out.println(location + " " + airline + " " + priceString);

            FlightDataAutomation f1 = new FlightDataAutomation(location, airline, price);
            flights.add(f1);


        }
    }

    /**
     * method: getCheapest()
     * this method returns the cheapest flight when comparing all of the flights together
     * @return String with destination, location and cost
     */
    public String getCheapestOverall() {
        int cheapest = 10000000;
        String airline = "";
        String location = "";

        for(int i = 0; i < flights.size(); i++) {
            if(cheapest > flights.get(i).getPrice()) {
                cheapest = flights.get(i).getPrice();
                airline = flights.get(i).getAirline();
                location = flights.get(i).getLocation();

            }
        }

        return "The cheapest is to " + location + " costs $" + cheapest + " on " + airline;
    }


}
