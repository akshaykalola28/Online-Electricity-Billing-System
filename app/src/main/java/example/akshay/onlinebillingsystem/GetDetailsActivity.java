package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class GetDetailsActivity extends Fragment {

    View mainView;

    LinearLayout detailsLayout;
    EditText meterNoEditText, currentUnitEditText;
    Button getDetailButton, submitUnitButton, resetButton;
    TextView nameTextView, cnoTextView, mnoTextView, lastUnitTextView, pendingAmountTextView;

    String meterNo;
    int intMeterNo, currentUnit, lastUnit, pendingAmount;

    FirebaseDatabase database;
    DatabaseReference mCustomerRef, mBillInfo;
    ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_get_details, container, false);

        ((HomeActivity) getActivity()).setActionBarTitle("Add Bill");

        meterNoEditText = mainView.findViewById(R.id.get_meter_no);
        nameTextView = mainView.findViewById(R.id.dis_name);
        cnoTextView = mainView.findViewById(R.id.dis_cno);
        mnoTextView = mainView.findViewById(R.id.dis_mno);
        pendingAmountTextView = mainView.findViewById(R.id.dis_pending_amount);
        lastUnitTextView = mainView.findViewById(R.id.dis_last_unit);
        currentUnitEditText = mainView.findViewById(R.id.enter_current_unit);

        database = FirebaseDatabase.getInstance();
        mCustomerRef = database.getReference("Users/Customer");

        detailsLayout = mainView.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);

        getDetailButton = mainView.findViewById(R.id.get_details_button);
        getDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterNo = meterNoEditText.getText().toString();
                if (!meterNo.equals("")) {
                    mDialog = ProgressDialog.show(mainView.getContext(), "Please Wait", "Searching Customer...", true);
                    intMeterNo = Integer.parseInt(meterNo);
                    mBillInfo = database.getReference("Bill Info/" + meterNo);
                    fetchOldData();

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
                                    nameTextView.setText(customer.name);
                                    cnoTextView.setText("" + customer.c_no);
                                    mnoTextView.setText("" + customer.meter_no);
                                    pendingAmountTextView.setText("" + pendingAmount);
                                    lastUnitTextView.setText("" + lastUnit);
                                    mDialog.dismiss();
                                }
                            } else {
                                mDialog.dismiss();
                                meterNoEditText.requestFocus();
                                meterNoEditText.setError("Customer Not Found");
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

        submitUnitButton = mainView.findViewById(R.id.submit_unit_data);
        submitUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentUnitEditText.getText().toString().equals("")) {
                    currentUnit = Integer.parseInt(currentUnitEditText.getText().toString());
                    if (isValidCurrentUnit(currentUnit)) {
                        final int used_unit = currentUnit - lastUnit;
                        final int final_amount = Calculation.calculation(used_unit, pendingAmount);

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Confirm Dialog").setMessage("Current Unit: " + currentUnit +
                                "\nUsed Unit: " + used_unit + "\nTotal Amount is: " + final_amount)
                                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDialog = ProgressDialog.show(mainView.getContext(), "Loading", "Please Wait...", true);
                                        //getBillNo();

                                        final DatabaseReference billNoRef = database.getReference("/Data");
                                        billNoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                try {
                                                    int billNo = dataSnapshot.child("bill_no").getValue(Integer.class);
                                                    billNo++;
                                                    billNoRef.child("bill_no").setValue(billNo);

                                                    AddBill addBill = new AddBill(billNo, used_unit, final_amount, "pending");
                                                    mBillInfo.child(labelOfBillInfo()).setValue(addBill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                mDialog.dismiss();
                                                                alertDialog("Success", "Bill Added");
                                                            } else {
                                                                mDialog.dismiss();
                                                                alertDialog("Error", "Please add bill again");
                                                            }
                                                        }
                                                    });
                                                    mBillInfo.child("last_unit").setValue(currentUnit);
                                                    mBillInfo.child("pending_amount").setValue(final_amount);
                                                } catch (NullPointerException e) {
                                                    Log.d("Bill No: ", "null pointer exception");
                                                    mDialog.dismiss();
                                                    alertDialog("Error", "Please add bill again");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                currentUnitEditText.setText("");
                            }
                        }).show();
                    } else {
                        currentUnitEditText.setError("Enter Valid Unit");
                    }
                } else {
                    currentUnitEditText.setError("Enter Valid Unit");
                }
            }
        });

        resetButton = mainView.findViewById(R.id.reset_unit_data);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUnitEditText.setText("");
            }
        });


        return mainView;
    }

    private boolean isValidCurrentUnit(int unit) {
        return unit >= lastUnit;
    }

    private void fetchOldData() {
        mBillInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    lastUnit = dataSnapshot.child("last_unit").getValue(Integer.class);
                    pendingAmount = dataSnapshot.child("pending_amount").getValue(Integer.class);
                } catch (NullPointerException e) {
                    lastUnit = 0;
                    pendingAmount = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String labelOfBillInfo() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String yearString = "" + year;
        String monthString = "" + (month + 1);
        if (month <= 8) {
            monthString = "0" + monthString;
        }
        return yearString + monthString;
    }

    private void alertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainView.getContext());
        alertDialogBuilder.setTitle(title).setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        detailsLayout.setVisibility(View.GONE);
                        currentUnitEditText.setText("");
                        meterNoEditText.requestFocus();
                        //meterNoEditText.setText("");
                    }
                }).show();
    }
}
