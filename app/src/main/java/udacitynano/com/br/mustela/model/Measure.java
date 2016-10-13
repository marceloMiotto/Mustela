package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import udacitynano.com.br.mustela.data.MeasureContract;

public class Measure {

    private long measureProjectId;
    private long measureUserId;
    private String measureDateTime;
    private double measureWeight;
    private double measureFatPercentage;

    public Measure() {
    }

    public Measure(long measureProjectId, long measureUserId, String measureDateTime, double measureWeight, double measureFatPercentage) {
        this.measureProjectId = measureProjectId;
        this.measureUserId = measureUserId;
        this.measureDateTime = measureDateTime;
        this.measureWeight = measureWeight;
        this.measureFatPercentage = measureFatPercentage;
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
}
