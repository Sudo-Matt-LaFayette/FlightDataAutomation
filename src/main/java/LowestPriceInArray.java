import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LowestPriceInArray {
    public static void main(String[] args) {

        // Initialize the arraylist
        List<Double> listOfPrices = new ArrayList<>(Arrays.asList(340.38, 290.56, 420.30, 799.99, 649.95));

        // Object for our project
        //

        // Initialize bestPrice with first element of array.
        Double bestPrice = listOfPrices.get(0);

        //loop through the arraylist
        for (int i = 0; i < listOfPrices.size(); i++) {
            //Compare elements of array with min
            if (listOfPrices.get(i) < bestPrice)
                bestPrice = listOfPrices.get(i);
        }

        System.out.println("The best price in the array is: " + bestPrice);

    }
}
// Adjustments to our code
// We are using objects instead of regular indeces
// List<nameOfClass> listOfPrices = new ArrayList<>();
// Double bestPrice.getPrice() = listOfPrices.getPrice(0);
// bestPrice = listOfPrices.get(i).getPrice();