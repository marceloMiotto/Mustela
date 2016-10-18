package udacitynano.com.br.mustela.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MeasureDbHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "measure.db";

    public MeasureDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + MeasureContract.UserEntry.TABLE_NAME + " (" +
                MeasureContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MeasureContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MeasureContract.UserEntry.COLUMN_HIGH + " REAL NOT NULL, " +
                MeasureContract.UserEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                MeasureContract.UserEntry.COLUMN_PHOTO_PATH + " TEXT " +
                " );";


        final String SQL_CREATE_PROJECT_TABLE = "CREATE TABLE " + MeasureContract.ProjectEntry.TABLE_NAME + " (" +
                MeasureContract.ProjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MeasureContract.ProjectEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MeasureContract.ProjectEntry.COLUMN_START_DATE + " TEXT NOT NULL, " +
                MeasureContract.ProjectEntry.COLUMN_END_DATE + " TEXT NOT NULL " +
                " );";


        final String SQL_CREATE_MEASURE_TABLE = "CREATE TABLE " + MeasureContract.MeasureEntry.TABLE_NAME + " (" +


                MeasureContract.MeasureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MeasureContract.MeasureEntry.COLUMN_PROJECT_KEY + " INTEGER NOT NULL, " +
                MeasureContract.MeasureEntry.COLUMN_USER_KEY + " INTEGER NOT NULL, " +
                MeasureContract.MeasureEntry.COLUMN_DATE_TIME + " TEXT NOT NULL, " +
                MeasureContract.MeasureEntry.COLUMN_WEIGHT + " REAL NOT NULL," +
                MeasureContract.MeasureEntry.COLUMN_FAT_PERCENTAGE + " REAL NOT NULL, " +
                " FOREIGN KEY (" + MeasureContract.MeasureEntry.COLUMN_PROJECT_KEY + ") REFERENCES " +
                MeasureContract.ProjectEntry.TABLE_NAME + " (" + MeasureContract.ProjectEntry._ID + "), " +
                " FOREIGN KEY (" + MeasureContract.MeasureEntry.COLUMN_USER_KEY + ") REFERENCES " +
                MeasureContract.UserEntry.TABLE_NAME + " (" + MeasureContract.UserEntry._ID + ") " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_PROJECT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MEASURE_TABLE);

        Log.e("Debug","Debug02");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //TODO implement the onUpgrade

    }
}
