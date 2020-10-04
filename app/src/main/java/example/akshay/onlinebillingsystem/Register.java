package example.akshay.onlinebillingsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText customerNo_ET;
    EditText customerName_ET;
    EditText meterNo_ET;
    EditText mobileNo_ET;
    EditText email_ET;
    EditText password_ET;
    EditText cPass_ET;
    EditText username_ET;
    Button dataSubmit;
    TextInputLayout cnoTIL, mnoTIL;

    String user_type;
    String name;
    String email;
    String username;
    String password;
    String mo_no;
    String cpass;
    int c_no, meter_no;

    FirebaseDatabase database;
    DatabaseReference mRef;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("dummy");

        //Fetch all edit text fields
        customerNo_ET = (EditText) findViewById(R.id.enter_cno);
        customerName_ET = (EditText) findViewById(R.id.enter_cname);
        meterNo_ET = (EditText) findViewById(R.id.enter_meter_no);
        email_ET = (EditText) findViewById(R.id.enter_email);
        mobileNo_ET = (EditText) findViewById(R.id.enter_mo_no);
        username_ET = (EditText) findViewById(R.id.enter_username);
        password_ET = (EditText) findViewById(R.id.enter_pass);
        cPass_ET = (EditText) findViewById(R.id.enter_cpass);
        dataSubmit = (Button) findViewById(R.id.submit_data);
        cnoTIL = findViewById(R.id.cno_text);
        mnoTIL = findViewById(R.id.mno_text);

        //Hide Customer and meter number
        cnoTIL.setVisibility(View.GONE);
        mnoTIL.setVisibility(View.GONE);

        final Spinner spinner = (Spinner) findViewById(R.id.user_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    user_type = null;
                    cnoTIL.setVisibility(View.GONE);
                    mnoTIL.setVisibility(View.GONE);
                }else if(position == 1){
                    user_type = "Users/Customer";
                    cnoTIL.setVisibility(View.VISIBLE);
                    mnoTIL.setVisibility(View.VISIBLE);
                    mRef = database.getReference(user_type);
                } else if(position == 2){
                    user_type = "Users/Unit Reader";
                    cnoTIL.setVisibility(View.GONE);
                    mnoTIL.setVisibility(View.GONE);
                    mRef = database.getReference(user_type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = customerName_ET.getText().toString();
                mo_no = mobileNo_ET.getText().toString();
                email = email_ET.getText().toString();
                username = username_ET.getText().toString();
                password = password_ET.getText().toString();
                cpass = cPass_ET.getText().toString();

                if(user_type == null) {
                    Toast.makeText(Register.this,"Please Select User",Toast.LENGTH_SHORT).show();
                } else if(user_type.equals("Users/Customer")){
                    if (!cpass.equals(password)) {

                        Snackbar.make(v, "Password are not same! Try Again!!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        password_ET.setText("");
                                        cPass_ET.setText("");
                                    }
                                }).show();
                    } else {
                        c_no = Integer.parseInt(customerNo_ET.getText().toString());
                        meter_no = Integer.parseInt(meterNo_ET.getText().toString());

                        mDialog = ProgressDialog.show(Register.this, "Loading", "Please wait...", true);
                        addCustomer();
                    }
                } else if(user_type.equals("Users/Unit Reader")){
                    if (!cpass.equals(password)) {
                        Snackbar.make(v, "Password are not same! Try Again!!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        password_ET.setText("");
                                        cPass_ET.setText("");
                                    }
                                }).show();
                    } else {
                        mDialog = ProgressDialog.show(Register.this, "Loading", "Please wait...", true);
                        addUnitReader();
                    }
                }
            }
        });
    }

    //Adding Customer in Firebase Database;
    private void addCustomer() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    alertDialog("Alert","Username already exists.");
                } else {
                    Customer customer = new Customer(name, email, username, password, mo_no, c_no, meter_no);
                    mRef.child(username).setValue(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDialog.dismiss();
                            alertDialog("Congratulation","Registration Successful.");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //Adding Unit Reader in Firebase Database
    private void addUnitReader() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    alertDialog("Alert","Username already exists.");
                } else {
                    User user = new User(name, email, username, password, mo_no);
                    mRef.child(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mDialog.dismiss();
                                alertDialog("Congratulation","Registration Successful.");
                            } else {
                                alertDialog("Error","Registration not completed.");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void alertDialog(String title, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Register.this);
        alertDialogBuilder.setTitle(title).setMessage(message)
                .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }
}
