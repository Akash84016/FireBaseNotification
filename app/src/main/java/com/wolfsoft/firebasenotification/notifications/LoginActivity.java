package com.wolfsoft.firebasenotification.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolfsoft.firebasenotification.MySharedPref;
import com.wolfsoft.firebasenotification.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnSubmit;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strEmail = edtEmail.getText().toString().trim();
                final String strPassword = edtPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)){
                    firebaseAuth.signInWithEmailAndPassword(strEmail,strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            MySharedPref.getInstance(LoginActivity.this).saveData("Current_USERID",firebaseAuth.getCurrentUser().getUid());
                            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                            startActivity(intent);
                            UserDetails userDetails = new UserDetails(firebaseAuth.getCurrentUser().getUid(),strEmail);
                            MyDatabaseRef.usersRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(userDetails);
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            MySharedPref.getInstance(LoginActivity.this).saveData("Current_USERID",firebaseAuth.getCurrentUser().getUid());
            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(intent);
        }
    }
}
