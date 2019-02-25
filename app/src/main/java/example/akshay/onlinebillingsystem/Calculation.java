package example.akshay.onlinebillingsystem;

public class Calculation {

    public static int calculation(int unit){
        int totalAmount = 0;
        int amountPerUnit = 6;

        totalAmount = unit * amountPerUnit;

        return totalAmount;
    }
}
