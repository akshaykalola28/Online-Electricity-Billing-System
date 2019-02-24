package example.akshay.onlinebillingsystem;

public class AddBill {
    public String customer_no;
    public int last_unit;
    public int current_unit;
    public int total_amount;

    AddBill(){}

    AddBill(String customer_no, int last_unit, int current_unit, int total_amount){
        this.customer_no = customer_no;
        this.last_unit = last_unit;
        this.current_unit = current_unit;
        this.total_amount = total_amount;
    }
}
