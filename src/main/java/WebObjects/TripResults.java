package WebObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TripResults {

    public WebElement nonStopCheckBox;
    public static List <WebElement> returnFlightRows;

    public TripResults(WebDriver driver) {
        nonStopCheckBox = driver.findElement(By.cssSelector("#stops-0-checkbox-0"));

        // Cost of flights will be a child div inside of... Still need to find the individual cost of flights...
        returnFlightRows = driver.findElements(By.className("gXRUkZ"));

    }
}
