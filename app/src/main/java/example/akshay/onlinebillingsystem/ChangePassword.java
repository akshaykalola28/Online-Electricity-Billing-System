package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends Fragment {

    View mainView;
    public static final int CUSTOMER = 1001;
    private static final int UNITREADER = 1002;


    EditText pass_ET, cpass_ET;
    Button sendPasswordButton;

    Customer customer;
    User unitReader;
    int type;
    String pass, cpass;

    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_change_password, container, false);

        try {
            ((HomeActivity) getActivity()).setActionBarTitle("Change Password");
            unitReader = ((HomeActivity) getActivity()).getUnitReader();
            type = UNITREADER;
            mRef = FirebaseDatabase.getInstance().getReference("Users/Unit Reader/" + unitReader.username);
        } catch (Exception e) {
            ((CustomerActivity) getActivity()).setActionBarTitle("Change Password");
            customer = ((CustomerActivity) getActivity()).getCustomer();
            type = CUSTOMER;
            mRef = FirebaseDatabase.getInstance().getReference("Users/Customer/" + customer.username);
        }


        pass_ET = mainView.findViewById(R.id.cp_pass);
        cpass_ET = mainView.findViewById(R.id.cp_cpass);
        sendPasswordButton = mainView.findViewById(R.id.send_password_button);
        sendPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValidData()) {
                    pass = pass_ET.getText().toString();
                    cpass = cpass_ET.getText().toString();
                    if (pass.equals(cpass)) {
                        mRef.child("password").setValue(pass);
                        Toast.makeText(getActivity().getApplicationContext(), "Password Changed!", Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mainView, "Password are not same! Try Again!!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        pass_ET.setText("");
                                        cpass_ET.setText("");
                                    }
                                }).show();
                    }
                }
            }
        });
        return mainView;
    }

    private boolean getValidData() {
        pass = pass_ET.getText().toString();
        cpass = cpass_ET.getText().toString();
        if (pass.equals("") || pass == null) {
            pass_ET.setError("Enter Password");
            pass_ET.requestFocus();
            return false;
        } else if (cpass.equals("") || cpass == null) {
            cpass_ET.setError("Enter Confirm Password");
            pass_ET.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
