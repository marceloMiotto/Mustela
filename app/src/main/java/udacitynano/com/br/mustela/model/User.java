package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import udacitynano.com.br.mustela.data.MeasureContract;

public class User implements Parcelable {

    private Context mContext;
    private String userName;
    private double userHigh;
    private int userAge;
    private String userPhotoPath;
    private long userId;
    private String colorName;

    public User() {
    }

    public User(Context context){

        mContext = context;
    }


    private User(Parcel in) {
        this.userName = in.readString();
        this.userHigh = in.readDouble();
        this.userAge = in.readInt();
        this.userPhotoPath = in.readString();
        this.userId = in.readLong();
        this.colorName = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(userName);
        dest.writeDouble(userHigh);
        dest.writeInt(userAge);
        dest.writeString(userPhotoPath);
        dest.writeLong(userId);
        dest.writeString(colorName);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };



    public User(String userName, double userHigh, int userAge, String userPhotoPath, long userId, String colorName) {
        this.userName = userName;
        this.userHigh = userHigh;
        this.userAge = userAge;
        this.userPhotoPath = userPhotoPath;
        this.userId = userId;
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        String photoPath = "test";
        long id = 0;

        ArrayList<User> users = new ArrayList<>();

        Log.e("Debug","uri: "+MeasureContract.UserEntry.CONTENT_URI);

        Cursor usersCursor = mContext.getContentResolver().query(
                MeasureContract.UserEntry.CONTENT_URI,
                new String[]{MeasureContract.UserEntry.COLUMN_NAME,MeasureContract.UserEntry.COLUMN_PHOTO_PATH,
                             MeasureContract.UserEntry._ID},
                null,
                null,
                null);

        Log.e("Debug","Debug03");
        usersCursor.moveToFirst();
        Log.e("Debug","cursor size "+usersCursor.getCount());

        do{
            name = usersCursor.getString(0);
            photoPath = usersCursor.getString(1);
            id        = usersCursor.getLong(2);
            users.add(new User(name,0.0,0,photoPath,id,""));
            Log.e("Debug","Debug06 cursor "+name + "photo "+photoPath + " id "+id);
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

            Log.e("Debug","Debug12 "+userPhotoPath);
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
