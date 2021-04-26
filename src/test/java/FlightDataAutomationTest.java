import WebObjects.Flight;
import WebObjects.FlightTabObjects;
import WebObjects.TripResults;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
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
        driver.get("https://www.priceline.com/m/fly/search/ATL-CUN-20210508/CUN-ATL-20210529/?cabin-class=ECO&no-date-search=false&num-adults=1&sbsroute=slice1&search-type=0000&vrid=8f256b28ca97e70f3e23ba9c391e783e");

        // Visible on home page... so I'm declaring here
        //flightsTab = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div[1]/div/div[1]/div[3]/div[2]"));
    }


    @Test
    public void doSomething() throws InterruptedException {
        List<Flight> flightDetails = new ArrayList<Flight>();
        // Get to the right place
        flightsTab.click();

         //Now the Flight Tab objects are visible... so we declare this object
        FlightTabObjects p = new FlightTabObjects(driver);

        departingFromBox.sendKeys("Matt");
        departureDatePicker.click();

         //(dateSelection & calendarTitles) need to be declared inside the testcase...
        //they only become visible after the departureDatePicker is clicked...
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

        //makes it wait till page loads
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#root > div > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.ListingsPagestyles__ListingsBody-sc-14lhci9-3.eHXvgJ > div.ListingsPagestyles__Middle-sc-14lhci9-5.chVbqz > div > div > div > div:nth-child(4) > div > div.ContentRenderer__StyledListingsWrapper-sc-1wxjt68-1.dUPnlT > section > ul > li:nth-child(1) > div")));
       //gets the airline name
        WebElement airlineElement = driver.findElement(By.cssSelector("#root > div > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.ListingsPagestyles__ListingsBody-sc-14lhci9-3.eHXvgJ > div.ListingsPagestyles__Middle-sc-14lhci9-5.chVbqz > div > div > div > div:nth-child(4) > div > div.ContentRenderer__StyledListingsWrapper-sc-1wxjt68-1.dUPnlT > section > ul > li:nth-child(1) > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.RetailItinerary__RelativeFlex-sc-5exnm5-5.gXRUkZ > div.Box-sc-8h3cds-0.RetailItinerary__SliceDetailsWrapper-sc-5exnm5-3.jAkZXc > div > div > div.Box-sc-8h3cds-0.SliceDisplay__AirlineWrapper-sc-1r9j249-4.iUmPcq > div.Text-sc-1c7ae3w-0.SliceDisplay__AirlineText-sc-1r9j249-6.emTubu"));
        String airline = airlineElement.getText();
        System.out.println(airline);
        //gets the dollars of the ticket cost
        WebElement costElementDollars = driver.findElement(By.cssSelector("#root > div > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.ListingsPagestyles__ListingsBody-sc-14lhci9-3.eHXvgJ > div.ListingsPagestyles__Middle-sc-14lhci9-5.chVbqz > div > div > div > div:nth-child(4) > div > div.ContentRenderer__StyledListingsWrapper-sc-1wxjt68-1.dUPnlT > section > ul > li:nth-child(1) > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.RetailItinerary__RelativeFlex-sc-5exnm5-5.gXRUkZ > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.RetailItinerary__FareBrandBoxWrapper-sc-5exnm5-4.cyFmxQ > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.FareBrandBox__LargerCentered-sc-1kqgbid-2.ikvFGs > div > div > div > div.Text-sc-1c7ae3w-0.fKzJka"));
        String costDollars = costElementDollars.getText();
        //get the cents of the ticket cost
        WebElement costElementCents = driver.findElement(By.cssSelector("#root > div > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.ListingsPagestyles__ListingsBody-sc-14lhci9-3.eHXvgJ > div.ListingsPagestyles__Middle-sc-14lhci9-5.chVbqz > div > div > div > div:nth-child(4) > div > div.ContentRenderer__StyledListingsWrapper-sc-1wxjt68-1.dUPnlT > section > ul > li:nth-child(1) > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.RetailItinerary__RelativeFlex-sc-5exnm5-5.gXRUkZ > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.RetailItinerary__FareBrandBoxWrapper-sc-5exnm5-4.cyFmxQ > div > div.Box-sc-8h3cds-0.Flex-sc-1ydst80-0.FareBrandBox__LargerCentered-sc-1kqgbid-2.ikvFGs > div > div > div > div.Box-sc-8h3cds-0.Hide-sc-16m85mi-0.iXQlrW > div"));
        String costCents = costElementCents.getText();
        //concatinates the dollars and cents
        String cost = costDollars + costCents;
        //converts into double
        double costOfFlight = Double.parseDouble(cost);
        System.out.println(costOfFlight);
        //creates a new Flight object with date as temp empty string
        //Dont know how to grab the date yet maybe *Matt* cant help..
        flightDetails.add(new Flight(airline, costOfFlight,""));




         //After you click the submit button theses objects are visible..
        TripResults tr = new TripResults(driver);

    }

}
