package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends Fragment {

    View mainView;

    Customer customer;
    User unitReader;

    EditText subjectT, descT, emailT;
    Button button;
    String email, subject, desc;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_contact_us, container, false);

        try {
            ((HomeActivity) getActivity()).setActionBarTitle("Contact Us");
            unitReader = ((HomeActivity) getActivity()).getUnitReader();
            email = unitReader.email;
        } catch (Exception e) {
            ((CustomerActivity) getActivity()).setActionBarTitle("Contact Us");
            customer = ((CustomerActivity) getActivity()).getCustomer();
            email = customer.email;
        }

        mRef = FirebaseDatabase.getInstance().getReference("/Contact Query");

        emailT = mainView.findViewById(R.id.email);
        emailT.setText(email);
        subjectT = mainView.findViewById(R.id.subject);
        descT = mainView.findViewById(R.id.description);
        button = mainView.findViewById(R.id.contact_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject = subjectT.getText().toString().trim();
                desc = descT.getText().toString().trim();

                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("subject", subject);
                map.put("description", desc);

                mRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity().getApplicationContext(),"Query Added",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return mainView;
    }
}
