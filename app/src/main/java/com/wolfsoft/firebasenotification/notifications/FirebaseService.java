package com.wolfsoft.firebasenotification.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.w("FirebaseService", "getInstanceId token " + instanceIdResult.getToken());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Get new Instance ID token
                String tokenRefresh = instanceIdResult.getToken();
                if (user != null) {
                    updateToken(tokenRefresh);
                }
            }
        });
    }

    private void updateToken(String tokenRefresh) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Token token = new Token(tokenRefresh);
        MyDatabaseRef.tokensRef.child(user.getUid()).setValue(token);
    }
}
