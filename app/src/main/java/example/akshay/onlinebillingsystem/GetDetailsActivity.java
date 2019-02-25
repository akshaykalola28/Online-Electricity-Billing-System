package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class GetDetailsActivity extends AppCompatActivity {

    LinearLayout detailsLayout;
    EditText meterNoEditText, currentUnitEditText;
    Button getDetailButton, submitUnitButton, resetButton;
    TextView name, cno, mno, lastUnit;

    String meterNo;
    int intMeterNo, currentUnit;

    FirebaseDatabase database;
    DatabaseReference mCustomerRef, mParticularRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);

        meterNoEditText = findViewById(R.id.get_meter_no);
        name = findViewById(R.id.dis_name);
        cno = findViewById(R.id.dis_cno);
        mno = findViewById(R.id.dis_mno);
        lastUnit = findViewById(R.id.dis_last_unit);
        currentUnitEditText = findViewById(R.id.enter_current_unit);

        database = FirebaseDatabase.getInstance();
        mCustomerRef = database.getReference("Users/Customer");

        detailsLayout = findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);

        getDetailButton = findViewById(R.id.get_details_button);
        getDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterNo = meterNoEditText.getText().toString();
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
                            Toast.makeText(GetDetailsActivity.this, "Invalid Meter Number", Toast.LENGTH_SHORT).show();
                            //meterNoEditText.setText("");
                            detailsLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        submitUnitButton = findViewById(R.id.submit_unit_data);
        submitUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentUnitEditText.getText().toString().equals("")) {
                    currentUnit = Integer.parseInt(currentUnitEditText.getText().toString());
                    int amount = Calculation.calculation(currentUnit);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GetDetailsActivity.this);
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

        resetButton = findViewById(R.id.reset_unit_data);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUnitEditText.setText("");
            }
        });
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
                    Toast.makeText(GetDetailsActivity.this, "Invalid example.akshay.onlinebillingsystem.AllActivity.Customer no.", Toast.LENGTH_SHORT).show();
                    detailsLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
