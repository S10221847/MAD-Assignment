package sg.edu.np.mad.mad_assignment_cookverse;

import static java.lang.Boolean.parseBoolean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "CookverseDB.db";
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_USERID = "Id";
    /*public static String COLUMN_LIKEDRECIPES = "LikedList";
    public static String COLUMN_CREATEDRECIPES = "CreatedList";*/

    public static String RECIPES = "Recipes";
    public static String COLUMN_RECIPENAME = "RecipeName";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_RECIPEID = "RecipeId";
    public static String COLUMN_RECIPEUSERID = "UserId";
    /*public static String COLUMN_CUISINES = "CuisinesList";
    public static String COLUMN_INGREDIENTS = "IngredientsList";*/
    public static String COLUMN_LIKES = "NoOfLikes";
    /*public static String COLUMN_IMAGEID = "ImageId";*/

    public static int DATABASE_VERSION = 1;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE1 = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD +  " TEXT)";

        db.execSQL(CREATE_TABLE1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        onCreate(db);
    }

    public User findUserByName(String username){ //either username or Id is fine but both are necessary before the User class is substantiated
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setPassword(cursor.getString(2));

            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    /*public User findUserById(int id){
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERID + "=\"" + id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setPassword(cursor.getString(2));

            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }*/

    public void addUser(User userData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }
}
