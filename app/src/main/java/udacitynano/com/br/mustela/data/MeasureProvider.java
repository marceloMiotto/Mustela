package udacitynano.com.br.mustela.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MeasureProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private   MeasureDbHelper mOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;

    static final int USER = 100;
    static final int PROJECT = 101;
    static final int MEASURE_BY_USER_PROJECT = 102;
    static final int MEASURES = 103;

    private static final SQLiteQueryBuilder sMeasureByProjectUserQueryBuilder;
    private static final SQLiteQueryBuilder sUsersQueryBuilder;
    private static final SQLiteQueryBuilder sProjectsQueryBuilder;

    static{
        sMeasureByProjectUserQueryBuilder = new SQLiteQueryBuilder();
        sUsersQueryBuilder = new SQLiteQueryBuilder();
        sProjectsQueryBuilder = new SQLiteQueryBuilder();

        sMeasureByProjectUserQueryBuilder.setTables(
                MeasureContract.MeasureEntry.TABLE_NAME + " INNER JOIN " +
                        MeasureContract.UserEntry.TABLE_NAME +
                        " ON " + MeasureContract.MeasureEntry.TABLE_NAME +
                        "." + MeasureContract.MeasureEntry.COLUMN_USER_KEY +
                        " = " + MeasureContract.UserEntry.TABLE_NAME +
                        "." + MeasureContract.UserEntry._ID);

        sUsersQueryBuilder.setTables(MeasureContract.UserEntry.TABLE_NAME);

        sProjectsQueryBuilder.setTables(MeasureContract.ProjectEntry.TABLE_NAME);

    }


    private static final String sMeasureUserProjectSelection =
            MeasureContract.MeasureEntry.TABLE_NAME+
                    "." + MeasureContract.MeasureEntry.COLUMN_USER_KEY + " = ? AND " +
            MeasureContract.MeasureEntry.TABLE_NAME+
                    "." + MeasureContract.MeasureEntry.COLUMN_PROJECT_KEY + " = ? " ;


    private Cursor getMeasureByUserProject(Uri uri, String[] projection, String sortOrder) {
        String user    = MeasureContract.MeasureEntry.getUserFromUri(uri);
        String project = MeasureContract.MeasureEntry.getProjectFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sMeasureUserProjectSelection;
        selectionArgs = new String[]{user,project};


        Cursor cursor = sMeasureByProjectUserQueryBuilder.query(sqLiteDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    private Cursor getUsers(String[] projection, String selection,
                            String[] selectionArgs,String sortOrder) {



        Cursor userCursor = sUsersQueryBuilder.query(sqLiteDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        Log.e("Debug","Debug10 first result "+ userCursor.getCount());

        return userCursor;
    }


    private Cursor getProjects(String[] projection, String selection,
                               String[] selectionArgs,String sortOrder) {



        Cursor cursor = sProjectsQueryBuilder.query(sqLiteDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );


        return cursor;
    }



    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MeasureContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MeasureContract.PATH_USER, USER);
        matcher.addURI(authority, MeasureContract.PATH_PROJECT,PROJECT);
        matcher.addURI(authority, MeasureContract.PATH_MEASURE,MEASURES);
        matcher.addURI(authority, MeasureContract.PATH_MEASURE + "/*/*", MEASURE_BY_USER_PROJECT);

        return matcher;
    }

    public MeasureProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if ( null == selection ) selection = "1";
        switch (match) {
            case MEASURES:
                rowsDeleted = sqLiteDatabase.delete(
                        MeasureContract.MeasureEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case USER:
                rowsDeleted = sqLiteDatabase.delete(
                        MeasureContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case PROJECT:
                rowsDeleted = sqLiteDatabase.delete(
                        MeasureContract.ProjectEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        //if (rowsDeleted != 0) {
        //    getContext().getContentResolver().notifyChange(uri, null);
       // }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {

            final int match = sUriMatcher.match(uri);

            switch (match) {

                case USER:
                    return MeasureContract.UserEntry.CONTENT_TYPE;

                case PROJECT:
                    return MeasureContract.ProjectEntry.CONTENT_ITEM_TYPE;

                case MEASURE_BY_USER_PROJECT:
                    return MeasureContract.MeasureEntry.CONTENT_ITEM_TYPE;
                case MEASURES:
                    return MeasureContract.MeasureEntry.CONTENT_TYPE;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case USER: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(MeasureContract.UserEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MeasureContract.UserEntry.buildUserUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case PROJECT: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(MeasureContract.ProjectEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MeasureContract.ProjectEntry.buildProjectUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case MEASURES: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(MeasureContract.MeasureEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MeasureContract.MeasureEntry.buildMeasureUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert measure row into " + uri);
                break;
            }

            case MEASURE_BY_USER_PROJECT: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(MeasureContract.MeasureEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MeasureContract.MeasureEntry.buildMeasureUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MeasureDbHelper(getContext());
        if(sqLiteDatabase == null) {
            sqLiteDatabase = mOpenHelper.getWritableDatabase();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case USER: {
                Log.e("Debug","Debug07 "+projection.toString());

                retCursor = getUsers(projection, selection, selectionArgs, sortOrder);
                Log.e("Debug","Debug08 "+retCursor.getCount());
                break;
            }

            case PROJECT: {
                retCursor = getProjects(projection, selection, selectionArgs,sortOrder);
                break;
            }

            case MEASURE_BY_USER_PROJECT: {
                retCursor = getMeasureByUserProject(uri, projection, sortOrder);
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {

            case USER:
                //normalizeDate(values);
                rowsUpdated = sqLiteDatabase.update(MeasureContract.UserEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case PROJECT:
                //normalizeDate(values);
                rowsUpdated = sqLiteDatabase.update(MeasureContract.ProjectEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;


            case MEASURE_BY_USER_PROJECT:
                //normalizeDate(values);
                rowsUpdated = sqLiteDatabase.update(MeasureContract.MeasureEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {


            case USER:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = sqLiteDatabase.insert(MeasureContract.UserEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;



            case PROJECT:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = sqLiteDatabase.insert(MeasureContract.ProjectEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;


            case MEASURE_BY_USER_PROJECT:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = sqLiteDatabase.insert(MeasureContract.MeasureEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    /*
    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(MeasureContract.MeasureEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE);
            values.put(WeatherContract.WeatherEntry.COLUMN_DATE, WeatherContract.normalizeDate(dateValue));
        }
    }
    */
}




