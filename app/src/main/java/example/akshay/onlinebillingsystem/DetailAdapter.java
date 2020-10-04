package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private Context context;
    private List<AddBill> billList;

    public DetailAdapter(Context context, List<AddBill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_card, parent, false);
        return new DetailViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        AddBill addBill = billList.get(position);

        try {
            Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(addBill.date);
            String date = new SimpleDateFormat("MMM, yyyy", Locale.getDefault()).format(d1);
            holder.textBillDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.textAmount.setText("₹ " + addBill.payable_amount);
        holder.textBillNo.setText("" + addBill.bill_no);
        holder.textUsedUnit.setText("" + addBill.used_unit);
        holder.textDueDate.setText("" + addBill.due_date);
        holder.textStatus.setText("" + addBill.status);
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        TextView textBillDate, textBillNo, textUsedUnit, textAmount, textDueDate, textStatus;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            textBillDate = itemView.findViewById(R.id.bill_date);
            textBillNo = itemView.findViewById(R.id.bill_no);
            textUsedUnit = itemView.findViewById(R.id.used_unit);
            textAmount = itemView.findViewById(R.id.bill_amount);
            textDueDate = itemView.findViewById(R.id.due_date);
            textStatus = itemView.findViewById(R.id.status);
        }
    }
}
