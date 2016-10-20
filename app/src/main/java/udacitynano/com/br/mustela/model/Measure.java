package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import udacitynano.com.br.mustela.data.MeasureContract;

public class Measure {

    private Context mContext;

    private long measureProjectId;
    private long measureUserId;
    private String measureDateTime;
    private double measureWeight;
    private double measureFatPercentage;
    private long measureId;

    public Measure() {
    }

    public Measure(Context context){
        mContext = context;
    }



    public Measure(long measureProjectId, long measureUserId, String measureDateTime, double measureWeight, double measureFatPercentage, long measureId) {
        this.measureProjectId = measureProjectId;
        this.measureUserId = measureUserId;
        this.measureDateTime = measureDateTime;
        this.measureWeight = measureWeight;
        this.measureFatPercentage = measureFatPercentage;
        this.measureId            = measureId;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(long measureId) {
        this.measureId = measureId;
    }

    public long getMeasureProjectId() {
        return measureProjectId;
    }

    public void setMeasureProjectId(long measureProjectId) {
        this.measureProjectId = measureProjectId;
    }

    public long getMeasureUserId() {
        return measureUserId;
    }

    public void setMeasureUserId(long measureUserId) {
        this.measureUserId = measureUserId;
    }

    public String getMeasureDateTime() {
        return measureDateTime;
    }

    public void setMeasureDateTime(String measureDateTime) {
        this.measureDateTime = measureDateTime;
    }

    public double getMeasureWeight() {
        return measureWeight;
    }

    public void setMeasureWeight(double measureWeight) {
        this.measureWeight = measureWeight;
    }

    public double getMeasureFatPercentage() {
        return measureFatPercentage;
    }

    public void setMeasureFatPercentage(double measureFatPercentage) {
        this.measureFatPercentage = measureFatPercentage;
    }

    public long addMeasure(Context context) {
        long measureId;


        ContentValues measureValues = new ContentValues();

        measureValues.put(MeasureContract.MeasureEntry.COLUMN_PROJECT_KEY, measureProjectId);
        measureValues.put(MeasureContract.MeasureEntry.COLUMN_USER_KEY, measureUserId);
        measureValues.put(MeasureContract.MeasureEntry.COLUMN_DATE_TIME, measureDateTime);
        measureValues.put(MeasureContract.MeasureEntry.COLUMN_WEIGHT, measureWeight);
        measureValues.put(MeasureContract.MeasureEntry.COLUMN_FAT_PERCENTAGE, measureFatPercentage);

        Uri insertedUri = context.getContentResolver().insert(
                MeasureContract.MeasureEntry.CONTENT_URI,
                measureValues
        );


        measureId = ContentUris.parseId(insertedUri);


        return measureId;
    }


    public ArrayList<Measure> getMeasures(long projectId, long userId){
        String dateTime = "test";
        double weight = 0;
        double fatPercentage = 0;
        long id = 0;

        ArrayList<Measure> measures = new ArrayList<>();

        Log.e("Debug","uri: "+MeasureContract.MeasureEntry.CONTENT_URI);

        Cursor measureCursor = mContext.getContentResolver().query(
                Uri.parse(MeasureContract.MeasureEntry.CONTENT_URI+"/"+userId+"/"+projectId),
                new String[]{MeasureContract.MeasureEntry.COLUMN_DATE_TIME, MeasureContract.MeasureEntry.COLUMN_WEIGHT
                            ,MeasureContract.MeasureEntry.COLUMN_FAT_PERCENTAGE,MeasureContract.MeasureEntry.TABLE_NAME+"."+MeasureContract.MeasureEntry._ID},
                null,
                null,
                null);

        Log.e("Debug","Debug03");
        measureCursor.moveToFirst();
        Log.e("Debug","cursor size "+measureCursor.getCount());

        if (measureCursor.getCount() != 0) {


            do {
                dateTime = measureCursor.getString(0);
                weight = measureCursor.getDouble(1);
                fatPercentage = measureCursor.getDouble(2);
                id = measureCursor.getLong(3);

                measures.add(new Measure(projectId, userId, dateTime, weight, fatPercentage, id));

                Log.e("Debug", "Debug06 datetime " + dateTime + "weight " + weight + " fatPercentage " + fatPercentage + "id " + id);

            } while (measureCursor.moveToNext());

        }

        measureCursor.close();

        return measures;

    }


}
