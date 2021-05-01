import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
        String[] destination = new String[]{"Cancun", "Las Vegas", "Denver", "Rome", "Milan", "Paris", "Madrid", "Amsterdam", "Singapore"};

        int startDay = 0;
        int endDay = 0;

        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH, 1); // for now... Dr im will be running this in may not april...

        for (int x = 0; x < destination.length; x++) {//{ 1; x++) {//
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

            // Begin date loop
            int timesToRun = calendar.getMaximum(Calendar.DAY_OF_MONTH);

            for (int i = 0; i < timesToRun; i++) {

                // Chosing start/end day logic
                if (i == 0) {
                    startDay = 1;
                    // make the end day one less then you want it to be... werid bug?
                    endDay = 7;
                } else {
                    startDay++;
                    endDay++;
                }

                WebElement departingButton;
                WebElement nextMonthButton;

                // Werid bug where it's one less then the actual value...
                if ((endDay + 1) > timesToRun) {
                    FileUtils.writeStringToFile(new File("flightresults.txt"), "\n\n", StandardCharsets.UTF_8, true);
                    // Hmm.... will this work?
                    System.out.println("I'm adding a month!");
                    calendar.add(Calendar.MONTH, 1);

                    System.out.println("The month is now " + calendar.get(Calendar.MONTH));

                    departingButton = driver.findElement(By.xpath("//*[@id=\"d1-btn\"]"));
                    departingButton.click();
                    System.out.println("I've clicked the departing calendar, I should now be clicking the next month button....");

                    // Need to know this After the calendar is opened
                    nextMonthButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[1]/button[2]"));
                    nextMonthButton.click();
                    Thread.sleep(1000);
                    // Set back to 1 since its a new month and begin process again (It should be hitting the startDay++ above and getting incremented in the new month
                    System.out.println("Value of i is: " + i);
                    // I know this math/technique is probably funny, but hopefully this will work... It should hopefully hit a new month...
                    startDay = 1;
                    endDay = 7;
                }
                else {
                    // This opens the calendar
                    departingButton = driver.findElement(By.xpath("//*[@id=\"d1-btn\"]"));
                    departingButton.click();
                    Thread.sleep(1000);
                }

                // Begin Date manipulation
                WebElement monthName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/h2"));

                // Date formatting
                String nameOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                String valueOfYear = String.valueOf(calendar.get(Calendar.YEAR));
                String fullMonthString = nameOfMonth + " " + valueOfYear;
                System.out.println(fullMonthString);

                // waits till element is visible
                WebDriverWait wait = new WebDriverWait(driver, 20);

                // "May 2021" "June 2021" "July 2021" "Aug 2021"
                if (monthName.getText().equals(fullMonthString)) {

                    if (monthName.getText().equals("June 2021")){
                        Thread.sleep(1000);
                        System.out.println("I have reached June dates");
                    }

                    Thread.sleep(1000);

                    if (monthName.getText().equals("September 2021")) {
                        // We're done here!
                        System.out.println("CONGRATS you're done! (Hopefully)");
                        break;
                    }

                    // This runs regardless
                    List<WebElement> month = driver.findElements(By.className("uitk-date-picker-month"));

                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("uitk-new-date-picker-day")));

                    List<WebElement> days = month.get(0).findElements(By.className("uitk-new-date-picker-day"));

                    Thread.sleep(3000);
                    for (WebElement dayContainer : days) {
                        // If we've found the first day... click on it along with the last day
                        if (dayContainer.getAttribute("data-day").equals(String.valueOf(startDay))) {
                            dayContainer.click();
                            Thread.sleep(2000);
                            days.get(endDay).click();
                            break;
                        }
                    }

                    // Closes the calendar
                    WebElement doneButton;
                    //wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("#wizard-flight-tab-roundtrip > div.uitk-layout-grid.uitk-layout-grid-gap-three.uitk-layout-grid-columns-small-4.uitk-layout-grid-columns-medium-6.uitk-layout-grid-columns-large-12.uitk-spacing.uitk-spacing-padding-block-three > div.uitk-layout-grid-item.uitk-layout-grid-item-columnspan-small-4.uitk-layout-grid-item-columnspan-medium-6.uitk-layout-grid-item-columnspan-large-4 > div > div > div:nth-child(1) > div > div.uitk-date-picker-menu-container.uitk-date-picker-menu-container-double.uitk-menu-container.uitk-menu-open.uitk-menu-pos-left.uitk-menu-container-autoposition.uitk-menu-container-text-nowrap > div > div.uitk-flex.uitk-date-picker-menu-footer > button"))));
                    try {
                        Thread.sleep(3000);
                        doneButton = driver.findElement(By.cssSelector("#wizard-flight-tab-roundtrip > div.uitk-layout-grid.uitk-layout-grid-gap-three.uitk-layout-grid-columns-small-4.uitk-layout-grid-columns-medium-6.uitk-layout-grid-columns-large-12.uitk-spacing.uitk-spacing-padding-block-three > div.uitk-layout-grid-item.uitk-layout-grid-item-columnspan-small-4.uitk-layout-grid-item-columnspan-medium-6.uitk-layout-grid-item-columnspan-large-4 > div > div > div:nth-child(1) > div > div.uitk-date-picker-menu-container.uitk-date-picker-menu-container-double.uitk-menu-container.uitk-menu-open.uitk-menu-pos-left.uitk-menu-container-autoposition.uitk-menu-container-text-nowrap > div > div.uitk-flex.uitk-date-picker-menu-footer > button"));
                    }
                    catch (Exception excep) {
                        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[3]/button"))));
                        doneButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[3]/button"));
                    }
                    doneButton.click();
                }


                // Click search button and go to next page
                wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[3]/div[2]/button"))));
                WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[3]/div[2]/button"));
                searchButton.click();

                // This sometimes changes?....
                WebElement airLineElement;
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span"))); //
                    Thread.sleep(3000);
                    airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span"));
                }
                catch (Exception e) {
                    System.out.println("\nAirline exception reached...\n");
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span")));
                        airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span"));
                    }
                    catch (Exception runningOutOfVariables) {        //html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span")));
                        airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span"));
                    }
                }

                String airline = airLineElement.getText();

                // Unsure if needed... comment out later to test
                Thread.sleep(2000);

                // This sometimes changes...
                WebElement priceElement;
                try {
                    //finds the price
                    //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span[2]")));
                    priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span[2]"));
                }
                // when it loads the page subsequent times the element changes? /html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span
                catch (Exception e) {
                    try {
                        System.out.println("\nprice element exception 1\n");
                        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span")));
                        priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span"));
                    }
                    catch (Exception ex) {
                        System.out.println("\nprice (inner) element exception 2\n");

                        try {
                            System.out.println("\nprice (inner) element exception 3\n");
                            priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[2]/div/div[1]/div[1]/span[2]"));
                        }

                        catch (Exception exc) {
                            System.out.println("\nprice (inner) element exception 4\n");
                            priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[2]/div/div[1]/div[1]/span"));
                        }
                    }
                }

                //gets text and removes $
                String priceString = priceElement.getText().replace("$", "");

                //converts to int
                int price;
                try {
                    price = Integer.parseInt(priceString);
                }
                catch (Exception e) {
                    price = Integer.parseInt(priceString.replace("4 left at", ""));
                }
                System.out.println(price);

                //Grabs the destination
                WebElement destinationElement = driver.findElement(By.cssSelector("#titleBar > h1 > div > span.title-city-text"));

                //gets text from title and removes words to only get destination
                String finalDestination = destinationElement.getText().replace("Select your departure to ", "");

                // Format results and print to file
                String line = startDay + " " + endDay + ", " + finalDestination + ", " + airline + ", " + price + "\n";
                FileUtils.writeStringToFile(new File("flightresults.txt"), line, StandardCharsets.UTF_8, true);
                System.out.println("I wrote: \n" + line + "\nto a file\n\n\n");

                WebElement cheapTicketsButton = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[7]/div/a/img"));
                cheapTicketsButton.click();
            }
        }
    }
}
