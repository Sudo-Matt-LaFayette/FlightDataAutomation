import WebObjects.FlightTabObjects;
import WebObjects.TripResults;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static WebObjects.FlightTabObjects.*;


public class FlightDataAutomationTest {

    public static WebDriver driver;
    public static WebElement flightsTab;

    // Dates in the Calendar
    public static List<WebElement> dateSelection;

    // Titles of the Month (May 2021, June 2021, etc)
    public static List<WebElement> calendarTitles;


    @BeforeClass
    public static void setUpChrome() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.priceline.com");

        // Visible on home page... so I'm declaring here
        flightsTab = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div[1]/div/div[1]/div[3]/div[2]"));
    }


    @Test
    public void doSomething() throws InterruptedException {
        // Get to the right place
        flightsTab.click();

        // Now the Flight Tab objects are visible... so we declare this object
        FlightTabObjects p = new FlightTabObjects(driver);

        departingFromBox.sendKeys("Matt");
        departureDatePicker.click();

        // (dateSelection & calendarTitles) need to be declared inside the testcase...
        // they only become visible after the departureDatePicker is clicked...
        dateSelection = driver.findElements(By.className("bpiqYf"));
        calendarTitles = driver.findElements(By.className("ffqzcs"));

        // Need to interate through these, find the approiate child elements and pick the dates you want....
        for (WebElement x: calendarTitles) {
            System.out.println(x.getText());
            if (x.getText().equals("May 2021")) {
                // This doesn't work... still prints all dates in the calendar...
                for (WebElement e: dateSelection) {
                    System.out.println(e.getText());
                }
            }
        }

        // This takes you to the next page
        findYourFlightButton.click();

        // After you click the submit button theses objects are visible..
        TripResults tr = new TripResults(driver);
    }

}
