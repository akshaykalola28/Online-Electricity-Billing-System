package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ForgotPassword extends Fragment {

    View mainView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_forgot_password,container,false);

        ((HomeActivity) getActivity()).setActionBarTitle("Forgot Password");

        return mainView;
    }
}
