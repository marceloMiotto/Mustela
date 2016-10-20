package udacitynano.com.br.mustela.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MeasureContract {

    public static final String CONTENT_AUTHORITY = "udacitynano.com.br.mustela.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "user";
    public static final String PATH_PROJECT = "project";
    public static final String PATH_MEASURE = "measure";


    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";


        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HIGH = "high";
        public static final String COLUMN_AGE  = "age";
        public static final String COLUMN_PHOTO_PATH  = "photo_pat";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "projects";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE   = "end_date";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;

        public static Uri buildProjectUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class MeasureEntry implements BaseColumns {

        public static final String TABLE_NAME = "measures";

        public static final String COLUMN_PROJECT_KEY = "project_id";
        public static final String COLUMN_USER_KEY = "user_id";
        public static final String COLUMN_DATE_TIME = "date_time";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_FAT_PERCENTAGE = "fat_percentage";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEASURE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEASURE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEASURE+"/*/*";


        public static Uri buildMeasureUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMeasureUserProject(String userId, String projectId) {
            return CONTENT_URI.buildUpon().appendPath(userId).appendPath(projectId).build();
        }


        public static String getUserFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getProjectFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }


    }



}
