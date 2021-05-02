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

import static WebObjects.FlightTabObjects.*;



public class FlightDataAutomationTest {

    public static WebDriver driver;

    // Dates in the Calendar
    public static List<WebElement> dateSelection;

    // Titles of the Month (May 2021, June 2021, etc)
    public static List<WebElement> calendarTitles;
    public static List<WebElement> monthBox;
    public static WebElement nextMonthCalendarArrow;
    public static Calendar calendar;



    @BeforeClass
    public static void setUpChrome() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");
        driver = new ChromeDriver();
        driver.get("https://www.cheaptickets.com/");
    }

    // uncomment when done... we don't want the window to close when debugging. We want to know where the script died
//    @AfterClass
//    public static void tearDown()
//    {
//        driver.quit();
//    }

    @Test
    public void doSomething() throws InterruptedException, AWTException, IOException {

        // Array of places to go
        String[] destination = new String[]{"Cancun", "Las Vegas", "Denver", "Rome", "Milan", "Paris", "Madrid", "Amsterdam", "Singapore"};
        WebElement flightsTab = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/ul/li[2]/a"));

        int startDay = 22;
        int endDay = 28;

        calendar = Calendar.getInstance();


        //calendar.add(Calendar.MONTH, 1); // for now... Dr im will be running this in may not april...
        flightsTab.click();

        for (int x = 0; x < destination.length; x++) {//{ 1; x++) {//

            System.out.println("\n\n\n\nThe valule of x is " + x);
            Thread.sleep(500);

            // Leaving from box
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
            System.out.println("I am typing " + destination[x] + " for the desination\n\n\n");
            flyTo.sendKeys(destination[x]);
            Thread.sleep(1000);
            a.sendKeys(Keys.ENTER).perform();

            // Begin date loop
            //for (int i = 0; i < (daysInCurrentMonth - 7); i++) {
            for  (int i = 21; i < (getNumTimesToRunForMonth() - 6); i++) { // we want it to run an extra time to hit the if endday + 1 > days in current block

                int daysInCurrentMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

                System.out.println("\nThe getNumTimesToRunForMonth(calendar) method returns " + (getNumTimesToRunForMonth()) + "for the month " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                System.out.println("I am running the for loop " + (getNumTimesToRunForMonth() - 21 - 6) + " times");

                System.out.println("\nValue of i is " + i + "\n");

                // If we are NOT running the loop for the first time....
                if (i != 21) { // != 0
                    startDay++;
                    endDay++;
                }

                WebElement departingButton;
                WebElement nextMonthButton;
                WebElement monthName;

                // If we are on the last iteration of [Whatever month's] date loop
                System.out.println("end day + 1 = " + (endDay + 1) + " & getNumTimesToRunForMonth(calendar) = " + getNumTimesToRunForMonth());

                if ((endDay + 1) > getNumTimesToRunForMonth()) {
                    System.out.println("I have made it to the last iteration of the loop");
                    // Add 2x extra (empty) lines to the file for easier readability
                    FileUtils.writeStringToFile(new File("flightresults.txt"), "\n\n", StandardCharsets.UTF_8, true);

                    // This will help formatting.. easier to see sections while debugging
                    System.out.println("\n\n" + "*************************************************************************************");
                    System.out.println("I'm all done iterating through the dates in " + (calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " so now I'm adding a month!"));
                    System.out.println("Previously the month was " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                    calendar.add(Calendar.MONTH, 1);
                    System.out.println("The month is now " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));

                    departingButton = driver.findElement(By.xpath("//*[@id=\"d1-btn\"]"));
                    departingButton.click();

                    // Need to get to the next month...
                    System.out.println("I've clicked the departing calendar, I should now be clicking the next month button....");
                    Thread.sleep(3000);

                    // Press button to shift calendar to next month
                    nextMonthButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[1]/button[2]"));
                    nextMonthButton.click();
                    System.out.println("I have clicked the next month button");
                    Thread.sleep(3000);

                    // Date formatting ("June 2021" "July 2021" etc)
                    String nameOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                    String valueOfYear = String.valueOf(calendar.get(Calendar.YEAR));
                    String fullMonthString = nameOfMonth + " " + valueOfYear;

                    // if it doesn't actually click...
                    monthName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/h2"));
                    if (!monthName.getText().equals(fullMonthString))
                    {
                        System.out.println("I wasn't able to click on the next month button... I am currently in month " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                        nextMonthButton.click();
                        Thread.sleep(3000);

                        // Try it again..
                        if (!monthName.getText().equals(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())))
                        {
                            System.out.println("I STILL wasn't able to click on the next month button!!! I am currently in month " + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                            nextMonthButton.click();
                            Thread.sleep(3000);
                        }

                        else {
                            System.out.println("I only had trouble pressing the month button once");
                        }
                    }

                    else {
                        System.out.println("CONFIRMED! We are in ===>" + monthName.getText() + "<=== which is the correct month");
                    }
                    // Set Starting Day back to 1 since its a new month and begin process again (It should be hitting the startDay++ above and getting incremented in the new month)
                    //startDay = 1;
                    startDay = 21;
                    System.out.println("After moving months, the Start day is now " + startDay);
                    //endDay = 7;
                    endDay = 28;
                    System.out.println("After moving months, the End day is now " + endDay);

                    // This will help formatting.. easier to see sections while debugging
                    System.out.println("*************************************************************************************\n\n");
                }
                else {
                    // This opens the calendar
                    departingButton = driver.findElement(By.xpath("//*[@id=\"d1-btn\"]"));
                    departingButton.click();
                    Thread.sleep(1000);
                }

                // Begin Date manipulation
                monthName = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/h2"));
                System.out.println(monthName.getText() + " is the current month we are iterating through");

                // Date formatting
                String nameOfMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                String valueOfYear = String.valueOf(calendar.get(Calendar.YEAR));
                String fullMonthString = nameOfMonth + " " + valueOfYear;
                //System.out.println(fullMonthString);

                // waits till element is visible
                WebDriverWait wait = new WebDriverWait(driver, 20);

                System.out.println("\nmonthName.getText() = " + monthName.getText());
                System.out.println("fullMonthString = " + fullMonthString + "\n");

                // "May 2021" "June 2021" "July 2021" "Aug 2021"
                if (monthName.getText().equals(fullMonthString)) {
                    System.out.println("The month text matches the month string");

                    if (monthName.getText().equals("June 2021")){
                        Thread.sleep(1000);
                        System.out.println("I have reached June date range");
                    }

                    if (monthName.getText().equals("July 2021")){
                        Thread.sleep(1000);
                        System.out.println("I have reached July date range");
                    }

                    Thread.sleep(1000);

                    if (monthName.getText().equals("September 2021")) {
                        // We're done here!
                        System.out.println("CONGRATS you're done with the city! (Hopefully)");
                        WebElement previousMonthButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[1]/div[1]/div/figure/div[3]/div/div/div/div[2]/div/form/div[2]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[1]/button[1]"));

                        // Press twice to get back to May
                        previousMonthButton.click();
                        previousMonthButton.click();

                        // set cal object to May
                        calendar.add(Calendar.MONTH, -2);
                        break;
                    }

                    // This runs regardless
                    List<WebElement> month = driver.findElements(By.className("uitk-date-picker-month"));
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("uitk-new-date-picker-day")));

                    // there might be disabled days? (like from previous month)
                    List<WebElement> days = month.get(0).findElements(By.className("uitk-new-date-picker-day"));

                    Thread.sleep(3000);
                    // Again, we're starting at 21 for debugging... switch the commented line when  everything is done and ready to fully test
                    //for (int y = 0; x < days.size(); y++) {
                    System.out.println("\nThe y loop will run " + (daysInCurrentMonth - 21 - 6) + " times");
                    for (int y = 21; y < (daysInCurrentMonth - 6); y++) {

                        // If we've found the first day... click on it along with the last day
                        System.out.println("The value of y is " + y);
                        System.out.println((days.get(y).getAttribute("data-day")) + " is the text and " + (startDay + 1) + " is the target #");
                        if (days.get(y).getAttribute("data-day").equals(String.valueOf((startDay + 1))) ) {
                            System.out.println(days.get(y).getAttribute("data-day") + " is the beginning day");
                            days.get(y).click();
                            Thread.sleep(2000);
                            System.out.println(days.get(endDay).getAttribute("data-day") + " is the end day");
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
                    //System.out.println("\nAirline exception reached...\n");
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span")));
                        airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span"));
                    }
                    catch (Exception runningOutOfVariables) {        //html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span
                        try {
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span")));
                            airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span"));
                        }
                        catch (Exception eeee) {
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[12]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span")));
                            airLineElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[12]/ul/li[1]/div[1]/div[2]/div[1]/div/div/div/div[1]/div[2]/span"));
                            System.out.println("WTF!!!");
                        }
                    }
                }

                String airline = airLineElement.getText();
                Thread.sleep(2000);

                // This sometimes changes based if there is "No Change fees" or other elements present...Dunno if the site does this on purpose to discourage bots?
                // Probably better to a by class name instead of xpath, css selector, or other positional locators
                WebElement priceElement;
                try {
                    //finds the price
                    //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span[2]")));
                    priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span[2]"));
                }
                // when it loads the page subsequent times the element changes? /html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[1]/div/div/div/div[1]/div[2]/span
                catch (Exception e) {
                    try {
                        //System.out.println("\nprice element exception 1\n");
                        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span")));
                        priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[1]/div[2]/div/div[1]/div[1]/span"));
                    }
                    catch (Exception ex) {
                        //System.out.println("\nprice (inner) element exception 2\n");

                        try {
                            //System.out.println("\nprice (inner) element exception 3\n");
                            priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[2]/div/div[1]/div[1]/span[2]"));
                        }

                        catch (Exception exc) {
                            //System.out.println("\nprice (inner) element exception 4\n");
                            try {
                                priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[13]/ul/li[1]/div[1]/div[2]/div[2]/div/div[1]/div[1]/span"));
                            }
                            catch (Exception ee) {
                                priceElement = driver.findElement(By.xpath("/html/body/div[2]/div[11]/section/div/div[12]/ul/li[1]/div[1]/div[2]/div[2]/div/div[1]/div[1]/span[2]"));
                            }
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
                //System.out.println(price);

                //Grabs the destination
                WebElement destinationElement = driver.findElement(By.cssSelector("#titleBar > h1 > div > span.title-city-text"));

                //gets text from title and removes words to only get destination
                String finalDestination = destinationElement.getText().replace("Select your departure to ", "");

                // Format results and print to file
                String line = startDay + " " + endDay + ", " + finalDestination + ", " + airline + ", " + price + "\n";
                FileUtils.writeStringToFile(new File("flightresults.txt"), line, StandardCharsets.UTF_8, true);
                //System.out.println("\n\nI wrote: \n" + line + "to a file\n\n");

                WebElement cheapTicketsButton = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[7]/div/a/img"));
                cheapTicketsButton.click();
            }
        }
    }

    public int getNumTimesToRunForMonth() {
        String currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int daysInMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("\n**The month is " + currentMonth + " and I am running the loop "  + daysInMonth + " times**\n\n");
        return daysInMonth;
    }
}
