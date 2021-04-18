package WebObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FlightTabObjects {

    public static WebElement departingFromBox;
    public static WebElement roundTripRadioButton;
    public static WebElement oneWayRadioButton;
    public static WebElement goingToInputBox;
    public static WebElement departureDatePicker;
    public static WebElement findYourFlightButton;


    public FlightTabObjects(WebDriver driver) {

        // Departing from box
        departingFromBox = driver.findElement(By.cssSelector("#flights\\.0\\.startLocation-typeahead-input"));

        // Round trip radio
        roundTripRadioButton = driver.findElement(By.cssSelector("#flight-type-round-trip"));

        // One way radio button
        oneWayRadioButton = driver.findElement(By.cssSelector("#flight-type-one-way"));

        // Going to box
        goingToInputBox = driver.findElement(By.cssSelector("#flights\\.0\\.endLocation-typeahead-input"));

        // Calendar Departure Date Picker
        departureDatePicker = driver.findElement(By.cssSelector("#flight-date-range-0"));

        // Find your flight button
        findYourFlightButton = driver.findElement(By.cssSelector(".sc-chPdSV"));
    }

}
