package example.akshay.onlinebillingsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        AddBill addBill = billList.get(position);
        holder.textBillDate.setText("Date: " + addBill.date);
        holder.textBillNo.setText("Bill No.: " + addBill.bill_no);
        holder.textUsedUnit.setText("Used Unit: " + addBill.used_unit);
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        TextView textBillDate, textBillNo, textUsedUnit;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            textBillDate = itemView.findViewById(R.id.bill_date);
            textBillNo = itemView.findViewById(R.id.bill_no);
            textUsedUnit = itemView.findViewById(R.id.used_unit);
        }
    }
}
