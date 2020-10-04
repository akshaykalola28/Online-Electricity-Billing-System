package example.akshay.onlinebillingsystem;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {

    TextInputEditText usernameT;
    String username;
    Button button;

    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        mFunctions = FirebaseFunctions.getInstance();

        usernameT = findViewById(R.id.username);

        button = findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameT.getText().toString();
                Task task = sendPassword(username);
            }
        });
    }

    private Task<String> sendPassword(String user) {

        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("username", user);

        return mFunctions
                .getHttpsCallable("forgotPassword")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Toast.makeText(ForgotPassActivity.this,"Password Send to Email",Toast.LENGTH_SHORT).show();
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }
}
