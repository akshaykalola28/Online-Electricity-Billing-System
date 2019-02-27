package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForgotPassword extends Fragment {

    View mainView;

    EditText email_ET, coNo_ET;
    Button sendPasswordButton;

    String email, coNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_forgot_password,container,false);

        ((HomeActivity) getActivity()).setActionBarTitle("Forgot Password");

        email_ET = mainView.findViewById(R.id.fg_email);
        coNo_ET = mainView.findViewById(R.id.fg_number);
        sendPasswordButton = mainView.findViewById(R.id.send_password_button);
        sendPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                String yearString = "" + year;
                String monthString = "" + (month+1);
                if(month <= 8){
                    monthString = "0" + monthString;
                }
                String label = yearString + monthString;
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                Snackbar.make(v,"Label: " + label + ", Date: " + date,Snackbar.LENGTH_SHORT).show();
            }
        });

        return mainView;
    }
}
