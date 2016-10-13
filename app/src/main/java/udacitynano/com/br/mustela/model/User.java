package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import udacitynano.com.br.mustela.data.MeasureContract;

public class User {

    private String userName;
    private double userHigh;
    private int userAge;

    public User() {
    }

    public User(String userName, double userHigh, int userAge) {
        this.userName = userName;
        this.userHigh = userHigh;
        this.userAge = userAge;
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

    public long addUser(Context context) {
        long userId;

        // First, check if the location with this city name exists in the db
        Cursor userCursor = context.getContentResolver().query(
                MeasureContract.UserEntry.CONTENT_URI,
                new String[]{MeasureContract.UserEntry._ID},
                MeasureContract.UserEntry.COLUMN_NAME + " = ?",
                new String[]{userName},
                null);

        if (userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(MeasureContract.UserEntry._ID);
            userId = userCursor.getLong(userIdIndex);
        } else {

            ContentValues userValues = new ContentValues();

            userValues.put(MeasureContract.UserEntry.COLUMN_NAME, userName);
            userValues.put(MeasureContract.UserEntry.COLUMN_HIGH, userHigh);
            userValues.put(MeasureContract.UserEntry.COLUMN_AGE, userAge);


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
