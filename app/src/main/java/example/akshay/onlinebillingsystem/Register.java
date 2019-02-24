package example.akshay.onlinebillingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

        //Hide example.akshay.onlinebillingsystem.AllActivity.Customer and meter number
        customerNo_ET.setVisibility(View.GONE);
        meterNo_ET.setVisibility(View.GONE);

        final Spinner spinner = (Spinner) findViewById(R.id.user_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    user_type = "Users/Customer";
                    customerNo_ET.setVisibility(View.VISIBLE);
                    meterNo_ET.setVisibility(View.VISIBLE);
                    mRef = database.getReference(user_type);
                } else if(position == 2){
                    user_type = "Users/Unit Reader";
                    customerNo_ET.setVisibility(View.GONE);
                    meterNo_ET.setVisibility(View.GONE);
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
                        c_no = Integer.parseInt(customerNo_ET.getText().toString());
                        meter_no = Integer.parseInt(meterNo_ET.getText().toString());

                        Snackbar.make(v, "Password are not same! Try Again!!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        password_ET.setText("");
                                        cPass_ET.setText("");
                                    }
                                }).show();
                    } else {
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
                    mRef.child(username).setValue(customer);
                    alertDialog("Congratulation","Registration Successful.");
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
                    mRef.child(username).setValue(user);
                    alertDialog("Congratulation","Registration Successful.");
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
