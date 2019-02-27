package example.akshay.onlinebillingsystem;

public class Calculation {

    public static int calculation(int used_unit, int pending_amount){
        int amount, totalAmount, penalty = 0;
        int amountPerUnit = 6;

        amount = used_unit * amountPerUnit;

        if (pending_amount > 0){
            penalty = 20;
        }
        totalAmount = amount + pending_amount + penalty;
        return totalAmount;
    }
}
