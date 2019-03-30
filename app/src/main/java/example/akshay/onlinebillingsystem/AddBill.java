package example.akshay.onlinebillingsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBill {
    public int bill_no;
    public String date;
    public String due_date;
    public int used_unit;
    public int payable_amount;
    public String status;

    AddBill(){}

    AddBill(int bill_no, int used_unit, int payable_amount, String status){
        this.bill_no = bill_no;
        this.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        this.used_unit = used_unit;
        this.payable_amount = payable_amount;
        this.status = status;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,10);
        this.due_date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(c.getTime());
    }
}
