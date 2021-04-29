import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FlightDataAutomationTest {

    public static WebDriver driver;

    // Dates in the Calendar
    public static List<WebElement> dateSelection;

    // Titles of the Month (May 2021, June 2021, etc)
    public static List<WebElement> calendarTitles;
    public static List<WebElement> monthBox;
    public static WebElement nextMonthCalendarArrow;

    @BeforeClass
    public static void setUpChrome() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");
        driver = new ChromeDriver();
        driver.get("https://www.cheaptickets.com/");
    }

    @Test
    public void doSomething() throws InterruptedException, AWTException, IOException {

        // Array of places to go
        String[] destination = new String[]{ "Cancun", "Las Vegas", "Denver", "Rome", "Milan", "Paris", "Madrid", "Amsterdam", "Singapore" };

        int startDay = 0;
        int endDay = 0;

        for (int x = 0; x < 1; x++) {//destination.length; x++) {
            // Get to the right place
            WebElement flightsTab = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/ul/li[2]/a"));

            flightsTab.click();
            Thread.sleep(500);

            WebElement leavingFrom = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[1]/div/div[1]/div/div/div/div/div[1]/button"));
            leavingFrom.click();

            // Weird bug where it doesn't take the first input?
            leavingFrom.sendKeys("A");
            Thread.sleep(1000);
            leavingFrom.sendKeys("Atlanta (ATL - Hartsfield-Jackson Atlanta Intl.)");
            Actions a = new Actions(driver);
            a.sendKeys(Keys.ENTER).perform();

            // Don't know why... but there's a button you need to press before the fly to element becomes visible?
            WebElement goingToButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[1]/div/div[2]/div/div/div/div/div[1]/button"));
            goingToButton.click();

            // Send to each place in our list
            WebElement flyTo = driver.findElement(By.xpath("//*[@id=\"location-field-leg1-destination\"]"));
            flyTo.sendKeys(destination[x]);
            Thread.sleep(1000);
            a.sendKeys(Keys.ENTER).perform();

            // This opens the calendar
            WebElement departingButton = driver.findElement(By.xpath("//*[@id=\"d1-btn\"]"));
            departingButton.click();

            // DO WHATEVER FOR THE DATE THING
            List <WebElement> month = driver.findElements(By.className("uitk-new-date-picker-month"));
            WebElement monthName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/h2"));
            List <WebElement> days = month.get(0).findElements(By.className("uitk-new-date-picker-day"));

            if (x == 0) {
                startDay = 1;
                // make the end day one less then you want it to be... werid bug?
                endDay = 6;
            }
            else {
                startDay += x;
                endDay += x;
            }

            if (monthName.getText().equals("May 2021")) {
                System.out.println("You're on the right track!");

                for (WebElement dayContainer: days) {
                    // If we've found the first day... click on it along with the last day
                    if (dayContainer.getAttribute("data-day").equals(String.valueOf(startDay))) {
                        dayContainer.click();
                        Thread.sleep(2000);
                        days.get(endDay).click();
                        break;
                    }
                }

                // Closes the calendar
                WebElement doneButton = driver.findElement(By.cssSelector("#wizard-flight-tab-roundtrip > div.uitk-layout-grid.uitk-layout-grid-gap-three.uitk-layout-grid-columns-small-4.uitk-layout-grid-columns-medium-6.uitk-layout-grid-columns-large-12.uitk-spacing.uitk-spacing-padding-block-three > div.uitk-layout-grid-item.uitk-layout-grid-item-columnspan-small-4.uitk-layout-grid-item-columnspan-medium-6.uitk-layout-grid-item-columnspan-large-4 > div > div > div:nth-child(1) > div > div.uitk-date-picker-menu-container.uitk-date-picker-menu-container-double.uitk-menu-container.uitk-menu-open.uitk-menu-pos-left.uitk-menu-container-autoposition.uitk-menu-container-text-nowrap > div > div.uitk-flex.uitk-date-picker-menu-footer > button"));
                doneButton.click();

                System.out.println("I need to make sure it gets here....");
            }

            // Click search button and go to next page
            WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[3]/div[2]/button"));
            searchButton.click();

            // Sleep for 15 seconds
            Thread.sleep(15000);

            //waits till page loads
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //find the airline
            WebElement airLineElement = driver.findElement(By.xpath("//*[@id=\"flight-module-2021-05-01t15:26:00-04:00-coach-atl-clt-aa-5690-coach-clt-cun-aa-2713_2021-05-07t15:41:00-05:00-coach-cun-mco-f9-30-coach-mco-atl-f9-1011_\"]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span"));
            //gets the text from the element
            String airline = airLineElement.getText();
            //prints out airline name
            System.out.println(airline);
            //finds the price
            WebElement priceElement = driver.findElement(By.cssSelector("#flight-module-2021-05-01t15\\:26\\:00-04\\:00-coach-atl-clt-aa-5690-coach-clt-cun-aa-2713_2021-05-07t15\\:41\\:00-05\\:00-coach-cun-mco-f9-30-coach-mco-atl-f9-1011_ > div.grid-container.standard-padding > div.uitk-grid.all-grid-fallback-alt > div.uitk-col.all-col-shrink > div > div.uitk-col.price-details-container.all-col-fill > div.primary-content.urgency.custom-primary-padding > span.full-bold.no-wrap"));
            //gets text and removes $
            String priceString = priceElement.getText().replace("$", "");
            //converts to int
            int price = Integer.parseInt(priceString);
            //prints price
            System.out.println(price);
            //Grabs the destination
            WebElement destinationElement = driver.findElement(By.cssSelector("#titleBar > h1 > div > span.title-city-text"));
            //gets text from title and removes words to only get destination
            String finalDestination = destinationElement.getText().replace("Select your departure to ", "");
            //prints
            System.out.println(finalDestination);
            String line = finalDestination + "," + airline + "," + price;
            FileUtils.writeStringToFile(new File("flightresults.txt"),line, StandardCharsets.UTF_8);
        }
    }
}