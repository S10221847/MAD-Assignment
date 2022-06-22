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
                 + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + /*" TEXT," + COLUMN_LIKEDRECIPES +
                " TEXT," + COLUMN_CREATEDRECIPES +*/ " TEXT)";
        String CREATE_TABLE2 = "CREATE TABLE " + RECIPES + "(" + COLUMN_RECIPEID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RECIPENAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_RECIPEUSERID
                + " TEXT," /*+ COLUMN_CUISINES  + " TEXT," + COLUMN_INGREDIENTS  + " TEXT,"*/ + COLUMN_LIKES
                + /*" TEXT," + COLUMN_IMAGEID +*/ " TEXT)";
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

        addDefaultRecipes(db);
    }

    public void addDefaultRecipes(SQLiteDatabase db) {
        // create default Recipes
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, "Farro Salad with Asparagus and Parmesan");
        //values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        // values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); Primary Key Autoincrement
        //values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());
        values.put(COLUMN_LIKES, 0);

        db.insert(RECIPES, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES);
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

            /*JSONObject ljson = new JSONObject(cursor.getString(3));
            JSONArray ljArray = ljson.optJSONArray("unique");
            ArrayList<String> lList = new ArrayList<String>();
            for (int i =0;i<ljArray.length();i++){
                String str_value =ljArray.optString(i);
                lList.add(str_value);
            }
            queryData.setLikedList(lList);

            JSONObject cjson = new JSONObject(cursor.getString(4));
            JSONArray cjArray = cjson.optJSONArray("unique");
            ArrayList<String> cList = new ArrayList<String>();
            for (int i =0;i<cjArray.length();i++){
                String str_value =cjArray.optString(i);
                cList.add(str_value);
            }*/

            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public User findUserById(int id){
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERID + "=\"" + id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setPassword(cursor.getString(2));

            /*JSONObject ljson = new JSONObject(cursor.getString(3));
            JSONArray ljArray = ljson.optJSONArray("unique");
            ArrayList<String> lList = new ArrayList<String>();
            for (int i =0;i<ljArray.length();i++){
                String str_value =ljArray.optString(i);
                lList.add(str_value);
            }
            queryData.setLikedList(lList);

            JSONObject cjson = new JSONObject(cursor.getString(4));
            JSONArray cjArray = cjson.optJSONArray("unique");
            ArrayList<String> cList = new ArrayList<String>();
            for (int i =0;i<cjArray.length();i++){
                String str_value =cjArray.optString(i);
                cList.add(str_value);
            }*/

            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public void addUser(User userData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        //values.put(COLUMN_USERID, userData.getId()); not necessary due to Integer Primary Key Autoincrementation

        /*JSONObject ljson = new JSONObject();
        ljson.put("uniqueArrays", new JSONArray(userData.getLikedList()));
        String lList = ljson.toString();
        values.put(COLUMN_LIKEDRECIPES, lList);

        JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(userData.getCreatedList()));
        String cList = cjson.toString();
        values.put(COLUMN_CREATEDRECIPES, cList);*/

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public void updateUser(User userData){

        deleteUser(userData.getId());

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        values.put(COLUMN_USERID, userData.getId()); //necessary to avoid auto incrementation

        /*JSONObject ljson = new JSONObject();
        ljson.put("uniqueArrays", new JSONArray(userData.getLikedList()));
        String lList = ljson.toString();
        values.put(COLUMN_LIKEDRECIPES, lList);

        JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(userData.getCreatedList()));
        String cList = cjson.toString();
        values.put(COLUMN_CREATEDRECIPES, cList);*/

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public void deleteUser(int id) {

        String query = "SELECT * FROM " + ACCOUNTS + " WHERE "
                + COLUMN_USERID + " = \""
                + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(ACCOUNTS, COLUMN_USERID + " = ?",
                    new String[] { String.valueOf(user.getId()) });
            cursor.close();
        }
        db.close();
    }
    public Recipe findRecipe(int recipeID){
        String query = "SELECT * FROM " + RECIPES +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        Recipe queryData = new Recipe();

        if (cursor.moveToFirst()){
            queryData.setRecipeId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setDescription(cursor.getString(2));
            queryData.setUserId(cursor.getInt(3));

            /*JSONObject cjson = new JSONObject(cursor.getString(4));
            JSONArray cjArray = cjson.optJSONArray("unique");
            ArrayList<String> cList = new ArrayList<String>();
            for (int i =0;i<cjArray.length();i++){
                String str_value =cjArray.optString(i);
                cList.add(str_value);
            }
            queryData.setCuisineList(cList);

            JSONObject ijson = new JSONObject(cursor.getString(5));
            JSONArray ijArray = ijson.optJSONArray("unique");
            ArrayList<String> iList = new ArrayList<String>();
            for (int i =0;i<ijArray.length();i++){
                String str_value =ijArray.optString(i);
                iList.add(str_value);
            }
            queryData.setIngredientList(iList);

            queryData.setImage(cursor.getInt(6));*/
            queryData.setNoOfLikes(cursor.getInt(4));
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
        // values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); Primary Key Autoincrement
        values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());

        /*JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(recipeData.getCuisineList()));
        String cList = cjson.toString();
        values.put(COLUMN_CUISINES, cList);

        JSONObject ijson = new JSONObject();
        ijson.put("uniqueArrays", new JSONArray(recipeData.getIngredientList()));
        String iList = ijson.toString();
        values.put(COLUMN_INGREDIENTS, iList);

        values.put(COLUMN_IMAGEID, recipeData.getImage());*/
        values.put(COLUMN_LIKES, recipeData.getNoOfLikes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(RECIPES, null, values);
        db.close();
    }

    public void updateRecipe(Recipe recipeData){

        deleteRecipe(recipeData.getRecipeId());

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipeData.getName());
        values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); //Avoid incrementation
        values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());

        /*JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(recipeData.getCuisineList()));
        String cList = cjson.toString();
        values.put(COLUMN_CUISINES, cList);

        JSONObject ijson = new JSONObject();
        ijson.put("uniqueArrays", new JSONArray(recipeData.getIngredientList()));
        String iList = ijson.toString();
        values.put(COLUMN_INGREDIENTS, iList);

        values.put(COLUMN_IMAGEID, recipeData.getImage());*/
        values.put(COLUMN_LIKES, recipeData.getNoOfLikes());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(RECIPES, null, values);
        db.close();
    }

    public void deleteRecipe(int id) {

        String query = "SELECT * FROM " + RECIPES + " WHERE "
                + COLUMN_RECIPEID + " = \""
                + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Recipe recipe = new Recipe();
        if (cursor.moveToFirst()) {
            recipe.setRecipeId(Integer.parseInt(cursor.getString(0)));
            db.delete(RECIPES, COLUMN_RECIPEID + " = ?",
                    new String[] { String.valueOf(recipe.getRecipeId()) });
            cursor.close();
        }
        db.close();
    }
}
