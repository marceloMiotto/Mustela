package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import udacitynano.com.br.mustela.data.MeasureContract;

public class User {

    private Context mContext;
    private String userName;
    private double userHigh;
    private int userAge;
    private String userPhotoPath;

    public User() {
    }

    public User(Context context){

        mContext = context;
    }


    public User(String userName, double userHigh, int userAge, String userPhotoPath) {
        this.userName = userName;
        this.userHigh = userHigh;
        this.userAge = userAge;
        this.userPhotoPath = userPhotoPath;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserHigh() {
        return userHigh;
    }

    public void setUserHigh(double userHigh) {
        this.userHigh = userHigh;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }

    public void setUserPhotoPath(String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }

    public ArrayList<User> getUsers(){
        String name = "test";
        ArrayList<User> users = new ArrayList<>();

        Log.e("Debug","uri: "+MeasureContract.UserEntry.CONTENT_URI);

        Cursor usersCursor = mContext.getContentResolver().query(
                MeasureContract.UserEntry.CONTENT_URI,
                new String[]{MeasureContract.UserEntry.COLUMN_NAME},
                null,
                null,
                null);

        Log.e("Debug","Debug03");
        usersCursor.moveToFirst();
        Log.e("Debug","cursor size "+usersCursor.getCount());

        do{
            name = usersCursor.getString(0);
            users.add(new User(name,0.0,0,""));
            Log.e("Debug","Debug06 cursor "+name);
        }while(usersCursor.moveToNext());


        usersCursor.close();

        return users;

    }

    public long addUser(Context context) {
        long userId;


        Cursor userCursor = context.getContentResolver().query(
                MeasureContract.UserEntry.CONTENT_URI,
                new String[]{MeasureContract.UserEntry._ID},
                MeasureContract.UserEntry.COLUMN_NAME + " = ?",
                new String[]{userName},
                null);

        if (userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(MeasureContract.UserEntry._ID);
            userId = userCursor.getLong(userIdIndex);
            Log.e("Debug","Debug5 already exists "+userName);

        } else {

            Log.e("Debug","Debug04 "+userName);

            ContentValues userValues = new ContentValues();

            userValues.put(MeasureContract.UserEntry.COLUMN_NAME, userName);
            userValues.put(MeasureContract.UserEntry.COLUMN_HIGH, userHigh);
            userValues.put(MeasureContract.UserEntry.COLUMN_AGE, userAge);
            userValues.put(MeasureContract.UserEntry.COLUMN_PHOTO_PATH, userPhotoPath);


            Uri insertedUri = context.getContentResolver().insert(
                    MeasureContract.UserEntry.CONTENT_URI,
                    userValues
            );


            userId = ContentUris.parseId(insertedUri);
        }

        userCursor.close();

        return userId;
    }

}
