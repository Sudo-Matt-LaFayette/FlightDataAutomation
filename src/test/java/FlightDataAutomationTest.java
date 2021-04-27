import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import java.awt.*;
import java.util.List;




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
        driver.get("https://hotwire.com");
    }


    @Test
    public void doSomething() throws InterruptedException, AWTException {

        // Array of places to go
        String[] destination = new String[]{
                "Cancun",
                "Las Vegas",
                "Denver",
                "Rome",
                "Milan",
                "Paris",
                "Madrid",
                "Amsterdam",
                "Singapore"
        };

        int startDay = 0;
        int endDay = 0;

        for (int x = 0; x < 1; x++) {//destination.length; x++) {
            // Get to the right place
            WebElement flightsTab = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/meso-native-marquee/div[1]/div/div/div/div/div[2]/div[3]"));


            flightsTab.click();
            Thread.sleep(500);

            WebElement flyFrom = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/meso-native-marquee/div[1]/div/div/div/div/div[3]/form/div[2]/div/div[1]/input"));

            flyFrom.clear();
            flyFrom.sendKeys("Atlanta, GA, United States of America (ATL-Hartsfield-Jackson Atlanta Intl.)");
            Thread.sleep(500);
            flyFrom.sendKeys(Keys.ARROW_DOWN);
            flyFrom.sendKeys(Keys.ENTER);

            WebElement flyTo = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/meso-native-marquee/div[1]/div/div/div/div/div[3]/form/div[3]/div/div/input"));

            // Send to each place in our list
            flyTo.sendKeys(destination[x]);
            Thread.sleep(2000);
            flyTo.sendKeys(Keys.ENTER);

            // DO WHATEVER FOR THE DATE THING
            WebElement departingDate = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/meso-native-marquee/div[1]/div/div/div/div/div[3]/form/div[4]/div/div/div/div/div[1]/div[2]"));
            departingDate.click();

            // do some condition to check what months show... if the right one isn't in the 2nd positon then click the next arrow...

            // I can only see the 2nd month in the calendar??
            List <WebElement> nextMonth = driver.findElements(By.cssSelector("div.month:nth-child(3)"));


            String month = nextMonth.get(0).findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/meso-native-marquee/div[1]/div/div/div/div/div[3]/form/div[4]/div/div/div/span/span[2]/div/div[2]/div[2]/div/div[1]/h4")).getText();

            System.out.println(month);

            List <WebElement> days = nextMonth.get(0).findElements(By.className("day-availability__content"));

            if (x == 0) {
                startDay = 1;
                // make the end day one less then you want it to be... werid bug?
                endDay = 6;
            }
            else {
                startDay += x;
                endDay += x;
            }

            if (month.equals("April 2021")) {
                System.out.println("You're on the right track!");

                for (WebElement dayContainer: days) {
                    // If we've found the first day... click on it along with the last day
                    if (dayContainer.getText().equals(String.valueOf(startDay))) {
                        dayContainer.click();
                        Thread.sleep(2000);
                        days.get(endDay).click();
                        break;
                    }
                }

                System.out.println("I need to make sure it gets here....");

            }

            Thread.sleep(5000);


            // Click find a flight
            WebElement findAFlightButton = driver.findElement(By.cssSelector(".hw-btn"));
            findAFlightButton.click();

            // need to wait a while for page to load
            // should be 10 seconds...
            Thread.sleep(10000);

            WebElement bestResultRow = new WebDriverWait(driver, Long.valueOf(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]")));


//            WebElement hotwireLogo = driver.findElement(By.xpath("/html/body/div[2]/div[1]/a"));
//            hotwireLogo.click();
        }
    }
}
