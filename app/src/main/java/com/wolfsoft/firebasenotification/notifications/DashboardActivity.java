package com.wolfsoft.firebasenotification.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wolfsoft.firebasenotification.MySharedPref;
import com.wolfsoft.firebasenotification.R;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    String mUID;
    FirebaseAuth firebaseAuth;

    private UsersListAdapter adapter;
    private ArrayList<UserDetails> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mUID = firebaseUser != null ? firebaseUser.getUid() : null;
        MySharedPref.getInstance(this).saveData("Current_USERID",mUID);

        updateToken();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ChatUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDetails userDetails = snapshot.getValue(UserDetails.class);
                    if (userDetails != null && !userDetails.getUid().equals(mUID)) {
                        arrayList.add(userDetails);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new UsersListAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);

    }

    public void updateToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
                Token mToken = new Token(instanceIdResult.getToken());
                ref.child(mUID).setValue(mToken);
            }
        });

    }
}
