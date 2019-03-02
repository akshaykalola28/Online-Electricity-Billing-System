package example.akshay.onlinebillingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "login";

    EditText username_ET, password_ET;
    Button logInButton, registerButton;
    TextView forgot_TV;

    String username,password;

    DatabaseReference mUserData;
    DatabaseReference mChildRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserData = FirebaseDatabase.getInstance().getReference("Users/Unit Reader");

        username_ET = findViewById(R.id.login_username);
        password_ET = findViewById(R.id.login_password);
        forgot_TV = findViewById(R.id.forgot_password);

        getPreferences();

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        forgot_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllActivity.class);
                startActivity(intent);
            }
        });
        logInButton = findViewById(R.id.log_in);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getValidData()) {
                    //Check valid user
                    mUserData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(username)) {
                                mChildRef = mUserData.child(username);
                                //Retrieve data of specific user
                                mChildRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        if (user.getPassword().equals(password)) {
                                            Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();

                                            savePreferences();

                                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                            intent.putExtra("ORIGINAL_USER", user);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Wrong password!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, "User doesn't Exists.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    public void savePreferences(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("usernameKey", username);
        editor.putString("passKey", password);
        editor.apply();
    }

    public void getPreferences(){
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains("usernameKey")) {
            username_ET.setText(sharedpreferences.getString("usernameKey", ""));
            username = sharedpreferences.getString("usernameKey", "");
            mChildRef = mUserData.child(username);
            //Retrieve data of specific user
            mChildRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        intent.putExtra("ORIGINAL_USER",user);
                        startActivity(intent);
                        finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (sharedpreferences.contains("passKey")) {
            password_ET.setText(sharedpreferences.getString("passKey", ""));
        }
    }

    private boolean getValidData() {
        username = username_ET.getText().toString();
        password = password_ET.getText().toString();
        if (username.equals("") || username == null){
            username_ET.setError("Enter Username");
            return false;
        } else if (password.equals("") || password == null){
            password_ET.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    public void openRegister(View view){
        Intent register = new Intent(this,Register.class);
        startActivity(register);
    }
}
