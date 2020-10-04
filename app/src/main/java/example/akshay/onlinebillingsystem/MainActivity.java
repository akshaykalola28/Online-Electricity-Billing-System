package example.akshay.onlinebillingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ViewGroup viewGroup;

    SharedPreferences sharedpreferences;
    public static final String MYPREFERENCE = "login";

    EditText username_ET, password_ET;
    Button logInButton;
    TextView forgot_TV;

    String username, password;

    DatabaseReference mUserData;
    DatabaseReference mChildRef;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        mUserData = FirebaseDatabase.getInstance().getReference("Users");

        username_ET = findViewById(R.id.login_username);
        password_ET = findViewById(R.id.login_password);
        forgot_TV = findViewById(R.id.forgot_password);

        getPreferences();
        sharedpreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        forgot_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassActivity.class));
            }
        });

        logInButton = findViewById(R.id.log_in);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValidData()) {
                    mDialog = ProgressDialog.show(MainActivity.this, "Please Wait", "We are logging you...", true);
                    userLogin(username);
                }
            }
        });
    }

    private void userLogin(String userLogIn) {
        username = userLogIn;
        Query query = mUserData.child("Unit Reader").orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.child(username).getValue(User.class);
                    if (user.password.equals(password)) {
                        Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();

                        savePreferences();
                        mDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("ORIGINAL_USER", user);
                        startActivity(intent);
                        finish();
                    } else {
                        mDialog.dismiss();
                        Snackbar.make(viewGroup, "Wrong Password!", Snackbar.LENGTH_LONG)
                                .setAction("ACTION", null).show();
                    }
                } else {
                    //This user is customer
                    customerLogin(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void customerLogin(String userLogIn) {
        username = userLogIn;
        Query query = mUserData.child("Customer").orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Customer user = dataSnapshot.child(username).getValue(Customer.class);
                    if (user.password.equals(password)) {
                        Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();

                        savePreferences();
                        mDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                        intent.putExtra("ORIGINAL_USER", user);
                        startActivity(intent);
                        finish();
                    } else {
                        mDialog.dismiss();
                        Snackbar.make(viewGroup, "Wrong Password!", Snackbar.LENGTH_LONG)
                                .setAction("ACTION", null).show();
                    }
                } else {
                    mDialog.dismiss();
                    Snackbar.make(viewGroup, "User Doesn't Exists", Snackbar.LENGTH_LONG)
                            .setAction("ACTION", null).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void savePreferences() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("usernameKey", username);
        editor.apply();
    }

    public void getPreferences() {
        sharedpreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("usernameKey")) {
            username_ET.setText(sharedpreferences.getString("usernameKey", ""));
            username = sharedpreferences.getString("usernameKey", "");
            mDialog = ProgressDialog.show(MainActivity.this, "Please Wait", "We are logging you...", true);

            //Retrieve data of specific user
            Query query = mUserData.child("Unit Reader").orderByChild("username").equalTo(username);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.child(username).getValue(User.class);
                        mDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("ORIGINAL_USER", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Query query1 = mUserData.child("Customer").orderByChild("username").equalTo(username);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Customer user = dataSnapshot.child(username).getValue(Customer.class);
                                mDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                                intent.putExtra("ORIGINAL_USER", user);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private boolean getValidData() {
        username = username_ET.getText().toString();
        password = password_ET.getText().toString();
        if (username.equals("") || username == null) {
            username_ET.setError("Enter Username");
            return false;
        } else if (password.equals("") || password == null) {
            password_ET.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    public void openRegister(View view) {
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }
}
