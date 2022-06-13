package sg.edu.np.mad.mad_assignment_cookverse;

import static java.lang.Boolean.parseBoolean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "accountsDB.db";
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_USERID = "Id";

    public static String RECIPES = "Recipes";
    public static String COLUMN_RECIPENAME = "Recipename";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_RECIPEID = "RecipeId";
    public static String COLUMN_RECIPEUSERID = "UserId";
    public static String COLUMN_IMAGEID = "ImageId";

    public static int DATABASE_VERSION = 1;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE1 = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_USERID + " TEXT" + ")";
        String CREATE_TABLE2 = "CREATE TABLE " + RECIPES + "(" + COLUMN_RECIPENAME
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_RECIPEID + " TEXT," +
                COLUMN_RECIPEUSERID + " TEXT," + COLUMN_IMAGEID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES);
        onCreate(db);
    }

    public User findUser(String username){
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setName(cursor.getString(0));
            queryData.setPassword(cursor.getString(1));
            queryData.setId(cursor.getInt(2));
            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public void addUser(User userData){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        values.put(COLUMN_USERID, userData.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public Recipe findRecipe(String recipeID){
        String query = "SELECT * FROM " + RECIPES +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        Recipe queryData = new Recipe();

        if (cursor.moveToFirst()){
            queryData.setName(cursor.getString(0));
            queryData.setDescription(cursor.getString(1));
            queryData.setRecipeId(cursor.getInt(2));
            queryData.setUserId(cursor.getInt(3));
            queryData.setImage(cursor.getInt(4));
            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public void addRecipe(Recipe recipeData){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipeData.getName());
        values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        values.put(COLUMN_RECIPEID, recipeData.getRecipeId());
        values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());
        values.put(COLUMN_IMAGEID, recipeData.getImage());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(RECIPES, null, values);
        db.close();
    }
}
