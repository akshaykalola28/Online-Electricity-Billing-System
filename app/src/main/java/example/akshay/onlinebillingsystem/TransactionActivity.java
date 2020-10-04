package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends Fragment {

    View mainView;
    Customer mCustomer;

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private List<AddBill> billList;

    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_transaction, container, false);

        ((CustomerActivity) getActivity()).setActionBarTitle("Bill Details");
        mCustomer = ((CustomerActivity) getActivity()).getCustomer();

        recyclerView = mainView.findViewById(R.id.transactionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        billList = new ArrayList<>();
        adapter = new DetailAdapter(getActivity().getApplicationContext(), billList);
        recyclerView.setAdapter(adapter);

        mRef = FirebaseDatabase.getInstance().getReference("/Bill Info/" + mCustomer.meter_no);
        Query query = mRef.orderByChild("bill_no");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                billList.clear();
                Log.d("Demo ", dataSnapshot.toString());
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {
                            AddBill addBill = snapshot.getValue(AddBill.class);
                            billList.add(addBill);
                        } catch (Exception e) {

                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mainView;
    }

}
