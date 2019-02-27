package example.akshay.onlinebillingsystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddBill {
    public int bill_no;
    public String date;
    public int used_unit;
    public int payable_amount;

    AddBill(){}

    AddBill(int used_unit, int payable_amount){
        this.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        this.used_unit = used_unit;
        this.payable_amount = payable_amount;
    }
}
