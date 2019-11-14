package com.wolfsoft.firebasenotification;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {

    private static MySharedPref yourPreference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private static final String MOBILE_NO = "mobileNo";
    private static final String FULL_NAME = "fullName";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String UID = "uid";
    private static final String IMAGE = "image";
    private static final String PASSWORD = "password";
    private static final String key = "key";
    private static final String storeName = "storeName";
    private static final String storeAddress = "storeAddress";
    private static final String storeLandmark = "storeLandmark";
    private static final String storeCategorie = "storeCategorie";
    private static final String storeImage = "storeImage";

    public static MySharedPref getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new MySharedPref(context);
        }
        return yourPreference;
    }

    private MySharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

   public void clear(String key){
        prefsEditor = sharedPreferences.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
   }

    public void clearAll() {
        prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

}
