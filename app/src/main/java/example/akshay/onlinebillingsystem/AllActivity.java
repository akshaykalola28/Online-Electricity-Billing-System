package example.akshay.onlinebillingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
    }

    public void openRegister(View view){
        Intent register = new Intent(this,Register.class);
        startActivity(register);
    }

    public void getDetails(View view){
        Intent get = new Intent(this,GetDetailsActivity.class);
        startActivity(get);
    }

    public void mainActivity(View view){
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
    }

    public void forgotPassword(View view){
        Intent forgot = new Intent(this,ForgotPassword.class);
        startActivity(forgot);
    }

    public void totalAmount(View view){
        Intent total = new Intent(this,TotalAmount.class);
        startActivity(total);
    }

    public static class Customer{
    }
}
