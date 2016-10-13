package udacitynano.com.br.mustela.model;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import udacitynano.com.br.mustela.data.MeasureContract;

public class Project {

    private String projectName;
    private String projectStartDate;
    private String projectEndDate;


    public Project() {
    }

    public Project(String projectName, String projectStartDate, String projectEndDate) {
        this.projectName = projectName;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public long addProject(Context context) {
        long userId;

        // First, check if the location with this city name exists in the db
        Cursor userCursor = context.getContentResolver().query(
                MeasureContract.ProjectEntry.CONTENT_URI,
                new String[]{MeasureContract.ProjectEntry._ID},
                MeasureContract.ProjectEntry.COLUMN_NAME + " = ?",
                new String[]{projectName},
                null);

        if (userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(MeasureContract.ProjectEntry._ID);
            userId = userCursor.getLong(userIdIndex);
        } else {

            ContentValues userValues = new ContentValues();

            userValues.put(MeasureContract.ProjectEntry.COLUMN_NAME, projectName);
            userValues.put(MeasureContract.ProjectEntry.COLUMN_START_DATE, projectStartDate);
            userValues.put(MeasureContract.ProjectEntry.COLUMN_END_DATE, projectEndDate);


            Uri insertedUri = context.getContentResolver().insert(
                    MeasureContract.ProjectEntry.CONTENT_URI,
                    userValues
            );


            userId = ContentUris.parseId(insertedUri);
        }

        userCursor.close();

        return userId;
    }
}
