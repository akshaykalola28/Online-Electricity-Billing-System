package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GetDetailsActivity extends Fragment {

    View mainView;

    LinearLayout detailsLayout;
    EditText meterNoEditText, currentUnitEditText;
    Button getDetailButton, submitUnitButton, resetButton;
    TextView name, cno, mno, lastUnit;

    String meterNo;
    int intMeterNo, currentUnit;

    FirebaseDatabase database;
    DatabaseReference mCustomerRef, mParticularRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_get_details,container,false);

        //Toolbar toolbar = (Toolbar) mainView.findViewById(R.id.toolbar);
        ((HomeActivity) getActivity()).setActionBarTitle("Add Bill");

        meterNoEditText =  mainView.findViewById(R.id.get_meter_no);
        name =  mainView.findViewById(R.id.dis_name);
        cno =  mainView.findViewById(R.id.dis_cno);
        mno =  mainView.findViewById(R.id.dis_mno);
        lastUnit =  mainView.findViewById(R.id.dis_last_unit);
        currentUnitEditText =  mainView.findViewById(R.id.enter_current_unit);

        database = FirebaseDatabase.getInstance();
        mCustomerRef = database.getReference("Users/Customer");

        detailsLayout =  mainView.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);

        getDetailButton =  mainView.findViewById(R.id.get_details_button);
        getDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterNo = meterNoEditText.getText().toString();
                if (!meterNo.equals("")) {
                    intMeterNo = Integer.parseInt(meterNo);
                    //searchCustomer();  //Without query Object

                    Query query = FirebaseDatabase.getInstance().getReference("Users/Customer")
                            .orderByChild("meter_no").equalTo(intMeterNo);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                detailsLayout.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Customer customer = snapshot.getValue(Customer.class);
                                    name.setText(customer.name);
                                    cno.setText("" + customer.c_no);
                                    mno.setText("" + customer.meter_no);
                                    //lastUnit.setText("" + user.last_unit);
                                }
                            } else {
                                meterNoEditText.setError("Wrong Meter Number");
                                //meterNoEditText.setText("");
                                detailsLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else {
                    meterNoEditText.setError("Enter Valid meter Number");
                }
            }
        });

        submitUnitButton =  mainView.findViewById(R.id.submit_unit_data);
        submitUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentUnitEditText.getText().toString().equals("")) {
                    currentUnit = Integer.parseInt(currentUnitEditText.getText().toString());
                    int amount = Calculation.calculation(currentUnit);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Confirm Dialog").setMessage("Total Amount is: " + amount)
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            currentUnitEditText.setText("");
                        }
                    }).show();
                } else {
                    currentUnitEditText.setError("Enter Valid Unit");
                }
            }
        });

        resetButton =  mainView.findViewById(R.id.reset_unit_data);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUnitEditText.setText("");
            }
        });


        return mainView;
    }

    private void searchCustomer() {
        mCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(meterNo)) {
                    mParticularRef = mCustomerRef.child(meterNo);
                    mParticularRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            detailsLayout.setVisibility(View.VISIBLE);
                            String getName = dataSnapshot.child("name").getValue(String.class);
                            name.setText(dataSnapshot.child("name").getValue(String.class));
                            cno.setText(dataSnapshot.child("c_no").getValue(String.class));
                            mno.setText(dataSnapshot.child("meter_no").getValue(String.class));
                            lastUnit.setText(dataSnapshot.child("last_unit").getValue(String.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Invalid Customer no.", Toast.LENGTH_SHORT).show();
                    detailsLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
