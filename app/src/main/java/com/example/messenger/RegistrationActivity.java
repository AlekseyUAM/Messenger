package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignUp;
    private RegistrationViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        setupClickListeners();
        observeViewModel();
    }

    private void setupClickListeners(){
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getTrimmedValue(editTextEmail);
                String password = getTrimmedValue(editTextPassword);
                String name = getTrimmedValue(editTextName);
                String last_name = getTrimmedValue(editTextLastName);
                int age = Integer.parseInt(getTrimmedValue(editTextAge));
                viewModel.signUp(email, password, name, last_name, age);
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null){
                    Toast.makeText(RegistrationActivity.this,
                            errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }
    private void initViews(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }
}