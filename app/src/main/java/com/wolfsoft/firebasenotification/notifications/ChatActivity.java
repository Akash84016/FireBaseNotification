package com.wolfsoft.firebasenotification.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wolfsoft.firebasenotification.MySharedPref;
import com.wolfsoft.firebasenotification.R;

import retrofit2.Call;
import retrofit2.Callback;

public class ChatActivity extends AppCompatActivity {

    APIService apiService;
    boolean notify = false;
    EditText editText;
    String hisUid, name;
    String savedCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_notification);

        savedCurrentUser = MySharedPref.getInstance(this).getData("Current_USERID");
        hisUid = MySharedPref.getInstance(getApplicationContext()).getData("hisUid");
        name = MySharedPref.getInstance(getApplicationContext()).getData("name");

        editText = findViewById(R.id.edittext);
        apiService = Client.getRetrofit().create(APIService.class);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                final String message = editText.getText().toString();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("ChatUsers").child(savedCurrentUser);
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (notify) {
                            sendNotification(hisUid, message);
                        }
                        notify = false;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void sendNotification(final String hisUid, final String message) {
        MyDatabaseRef.tokensRef.orderByKey().equalTo(hisUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(savedCurrentUser, name + " : " + message, "new Message", hisUid, R.drawable.ic_launcher_background);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    Toast.makeText(ChatActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {
                                    Log.w("ChatActivity", t);
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
