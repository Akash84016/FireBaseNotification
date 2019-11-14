package com.wolfsoft.firebasenotification.notifications;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class MyDatabaseRef {

    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("NotificationApp");
    static final DatabaseReference tokensRef = databaseReference.child("Tokens");
    static final DatabaseReference usersRef = databaseReference.child("ChatUsers");
}
