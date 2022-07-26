package sg.edu.np.mad.mad_assignment_cookverse;

import static java.lang.Boolean.parseBoolean;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "CookverseDB.db";

    //Accounts table storing User data
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "Username";
    //public static String COLUMN_PASSWORD = "Password"; Don't store password in local database
    public static String COLUMN_BIO= "Bio";
    public static String COLUMN_USERIMAGE = "UserImage";

    //Liked Recipes table storing User's liked recipes list
    public static String LIKEDRECIPES = "LikedRecipes";
    //Use COLUMN_USERNAME and COLUMN_RECIPEID as composite key

    //Created Recipes table storing User's created recipes list
    public static String CREATEDRECIPES = "CreatedRecipes";
    //Use COLUMN_USERNAME and COLUMN_RECIPEID as composite key


    //Recipes table storing Recipe data
    public static String RECIPES = "Recipes";
    public static String COLUMN_RECIPEID = "RecipeId";
    public static String COLUMN_RECIPENAME = "RecipeName";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_DURATION = "Duration";
    //Use COLUMN_USERNAME as Recipe's uid
    public static String COLUMN_LIKES = "NoOfLikes";
    public static String COLUMN_RECIPEIMAGE = "RecipeImage";
    public static String COLUMN_SERVINGS = "Servings";

    //Cuisines table storing Recipe's cuisine list
    public static String CUISINE = "Cuisine";
    public static String COLUMN_RECIPECUISINE = "RecipeCuisine";
    //Use COLUMN_RECIPEID in conjunction as composite key

    //Ingredients table storing Recipe's cuisine list
    public static String INGREDIENT = "Ingredient";
    public static String COLUMN_RECIPEINGREDIENT = "RecipeIngredient";
    //Use COLUMN_RECIPEID in conjunction as composite key

    //Steps table storing Recipe's cuisine list
    public static String STEPS = "Steps";
    public static String COLUMN_RECIPESTEPS = "RecipeSteps";
    //Use COLUMN_RECIPEID in conjunction as composite key

    Context context;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE1 = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERNAME + " TEXT," /*+ COLUMN_PASSWORD + " TEXT,"*/
                + COLUMN_BIO + " TEXT," + COLUMN_USERIMAGE + " TEXT)";
        String CREATE_TABLE2 = "CREATE TABLE " + LIKEDRECIPES + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_RECIPEID + " TEXT)";
        String CREATE_TABLE3 = "CREATE TABLE " + CREATEDRECIPES + "(" + COLUMN_USERNAME + " TEXT," + COLUMN_RECIPEID + " TEXT)";
        String CREATE_TABLE4 = "CREATE TABLE " + RECIPES + "(" + COLUMN_RECIPEID + " TEXT,"
                + COLUMN_RECIPENAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_DURATION
                + " INTEGER," + COLUMN_USERNAME  + " TEXT," + COLUMN_LIKES  + " INTEGER," + COLUMN_RECIPEIMAGE +
                " TEXT," + COLUMN_SERVINGS + " INTEGER)";
        String CREATE_TABLE5 = "CREATE TABLE " + CUISINE + "(" + COLUMN_RECIPECUISINE + " TEXT," + COLUMN_RECIPEID + " TEXT)";
        String CREATE_TABLE6 = "CREATE TABLE " + INGREDIENT + "(" + COLUMN_RECIPEINGREDIENT + " TEXT," + COLUMN_RECIPEID + " TEXT)";
        String CREATE_TABLE7 = "CREATE TABLE " + STEPS + "(" + COLUMN_RECIPESTEPS + " TEXT," + COLUMN_RECIPEID + " TEXT)";

        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        db.execSQL(CREATE_TABLE5);
        db.execSQL(CREATE_TABLE6);
        db.execSQL(CREATE_TABLE7);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Used when updating DB version
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + LIKEDRECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATEDRECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + CUISINE);
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + STEPS);
        onCreate(db);
    }

    public User findUser(String username){ //Returns User object with specified username
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setName(cursor.getString(0));
            queryData.setBio(cursor.getString(1));
            queryData.setUserImage(cursor.getString(2));

            cursor.close();
        }
        else{
            queryData = null;
        }

        query = "SELECT * FROM " + LIKEDRECIPES +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        cursor = db.rawQuery(query, null);
        ArrayList<String> lList = new ArrayList<>();
        String likedRecipeID;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                likedRecipeID = cursor.getString(1);
                lList.add(likedRecipeID);

                cursor.moveToNext();
            }
        }
        else{
            lList = null;
        }
        cursor.close();
        queryData.setLikedList(lList);

        query = "SELECT * FROM " + CREATEDRECIPES +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        cursor = db.rawQuery(query, null);
        ArrayList<String> cList = new ArrayList<>();
        String createdRecipeID;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                createdRecipeID = cursor.getString(1);
                cList.add(createdRecipeID);

                cursor.moveToNext();
            }
        }
        else{
            cList = null;
        }
        cursor.close();
        queryData.setCreatedList(cList);

        db.close();

        return queryData;
    }

    public void addUser(User userData) { //Adds user data to database
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_USERNAME, userData.getName());
        Values.put(COLUMN_BIO, userData.getBio());
        Values.put(COLUMN_USERIMAGE, userData.getUserImage());

        if (userData.getLikedList() != null){
            for (String rid : userData.getLikedList()){
                addLikedRecipes(rid, userData.getName());
            }
        }

        if (userData.getCreatedList() != null){
            for (String rid : userData.getCreatedList()){
                addCreatedRecipes(rid, userData.getName());
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, Values);
        db.close();
    }

    public void addLikedRecipes(String rid, String username){
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_USERNAME, username);
        Values.put(COLUMN_RECIPEID, rid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(LIKEDRECIPES, null, Values);
        db.close();
    }

    public void addCreatedRecipes(String rid, String username){
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_USERNAME, username);
        Values.put(COLUMN_RECIPEID, rid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(CREATEDRECIPES, null, Values);
        db.close();
    }

    /*public void updateUser(User userData){ //Replaces user database info with new user info

        deleteUser(userData.getId()); //Deletes user from database

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        values.put(COLUMN_USERID, userData.getId()); //necessary to avoid auto incrementation
        if (userData.getUserImage() != null){
            values.put(COLUMN_USERIMAGE, DbBitmapUtility.getBytes(userData.getUserImage()));
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public void deleteUser(int id) { //Deletes user from database with specified ID

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
    }*/

    public ArrayList<User> listUser(){ //Returns a list with all users from the database
        String query = "SELECT * FROM " + ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User queryData;
        String username;
        String query2;
        String query3;
        Cursor cursor2;
        Cursor cursor3;
        String createdRecipeID;
        ArrayList<String> cList;
        String likedRecipeID;
        ArrayList<String> lList;
        ArrayList<User> uList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do { //iterates through every row the query returned
                queryData = new User();

                username = cursor.getString(0);
                queryData.setName(username);
                queryData.setBio(cursor.getString(1));
                queryData.setUserImage(cursor.getString(2));


                query2 = "SELECT * FROM " + LIKEDRECIPES +
                        " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
                cursor2 = db.rawQuery(query2, null);
                lList = new ArrayList<>();
                if (cursor2.moveToFirst()) {
                    while (!cursor2.isAfterLast()) {
                        likedRecipeID = cursor2.getString(1);
                        lList.add(likedRecipeID);

                        cursor2.moveToNext();
                    }
                }
                else{
                    lList = null;
                }
                cursor2.close();
                queryData.setLikedList(lList);

                query3 = "SELECT * FROM " + CREATEDRECIPES +
                        " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
                cursor3 = db.rawQuery(query3, null);
                cList = new ArrayList<>();
                if (cursor3.moveToFirst()) {
                    while (!cursor3.isAfterLast()) {
                        createdRecipeID = cursor3.getString(1);
                        cList.add(createdRecipeID);

                        cursor3.moveToNext();
                    }
                }
                else{
                    cList = null;
                }
                cursor3.close();
                queryData.setCreatedList(cList);
                uList.add(queryData);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return uList;
    }

    public Recipe findRecipe(String recipeID){ //Returns recipe with specified recipe ID
        String query = "SELECT * FROM " + RECIPES +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        Recipe queryData = new Recipe();

        if (cursor.moveToFirst()){
            queryData.setRid(cursor.getString(0));
            queryData.setName(cursor.getString(1));
            queryData.setDescription(cursor.getString(2));
            queryData.setDuration(cursor.getInt(3));
            queryData.setUid(cursor.getString(4));
            queryData.setNooflikes(cursor.getInt(5));
            queryData.setRecipeimage(cursor.getString(6));
            queryData.setServings(cursor.getInt(7));

            cursor.close();
        }
        else{
            queryData = null;
        }

        query = "SELECT * FROM " + CUISINE +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        cursor = db.rawQuery(query, null);
        ArrayList<String> cList = new ArrayList<>();
        String cuisine;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                cuisine = cursor.getString(0);
                cList.add(cuisine);

                cursor.moveToNext();
            }
        }
        else{
            cList = null;
        }
        cursor.close();
        queryData.setCuisineList(cList);

        query = "SELECT * FROM " + INGREDIENT +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        cursor = db.rawQuery(query, null);
        ArrayList<String> iList = new ArrayList<>();
        String ingredient;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ingredient = cursor.getString(0);
                iList.add(ingredient);

                cursor.moveToNext();
            }
        }
        else{
            iList = null;
        }
        cursor.close();
        queryData.setIngredientsList(iList);

        query = "SELECT * FROM " + STEPS +
                " WHERE " + COLUMN_RECIPEID + "=\"" + recipeID + "\"";
        cursor = db.rawQuery(query, null);
        ArrayList<String> sList = new ArrayList<>();
        String step;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                step = cursor.getString(0);
                sList.add(step);

                cursor.moveToNext();
            }
        }
        else{
            sList = null;
        }
        cursor.close();
        queryData.setStepsList(sList);

        db.close();
        return queryData;
    }

    public void addRecipe(Recipe recipeData){ //Adds recipe to database
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPEID, recipeData.getRid());
        values.put(COLUMN_RECIPENAME, recipeData.getName());
        values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        values.put(COLUMN_DURATION, recipeData.getDuration());
        values.put(COLUMN_USERNAME, recipeData.getUid());
        values.put(COLUMN_LIKES, recipeData.getNooflikes());
        values.put(COLUMN_RECIPEIMAGE, recipeData.getRecipeimage());
        values.put(COLUMN_SERVINGS, recipeData.getServings());

        if (recipeData.getCuisineList() != null){
            for (String cuisi : recipeData.getCuisineList()){
                addCuisines(recipeData.getRid(), cuisi);
            }
        }

        if(recipeData.getIngredientsList() != null){
            for (String ingred : recipeData.getIngredientsList()){
                addIngredients(recipeData.getRid(), ingred);
            }
        }

        if(recipeData.getStepsList() != null){
            for (String step : recipeData.getStepsList()){
                addSteps(recipeData.getRid(), step);
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(RECIPES, null, values);
        db.close();
    }

    public void addCuisines(String rid, String cuisine){
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_RECIPECUISINE, cuisine);
        Values.put(COLUMN_RECIPEID, rid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(CUISINE, null, Values);
        db.close();
    }

    public void addIngredients(String rid, String ingredient){
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_RECIPEINGREDIENT, ingredient);
        Values.put(COLUMN_RECIPEID, rid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(INGREDIENT, null, Values);
        db.close();
    }

    public void addSteps(String rid, String step){
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_RECIPESTEPS, step);
        Values.put(COLUMN_RECIPEID, rid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(STEPS, null, Values);
        db.close();
    }

    /*public void updateRecipe(Recipe recipeData){ //Replaces recipe database info with new recipe info

        deleteRecipe(recipeData.getRecipeId());

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipeData.getName());
        values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); //Avoid incrementation
        values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());
        values.put(COLUMN_LIKES, recipeData.getNoOfLikes());
        values.put(COLUMN_STEPS, recipeData.getSteps());
        values.put(COLUMN_INGREDIENTS, recipeData.getIngredients());
        if (recipeData.getRecipeImage() != null){
            values.put(COLUMN_RECIPEIMAGE, DbBitmapUtility.getBytes(recipeData.getRecipeImage()));
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(RECIPES, null, values);
        db.close();
    }

    public void deleteRecipe(int id) { //Deletes recipe with specified ID

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
    }*/
    public ArrayList<Recipe> listAllRecipe(){ //Returns list of all Recipes from database
        String query = "SELECT * FROM " + RECIPES;

        return baseListRecipe(query);
    }
    public ArrayList<Recipe> listUserRecipe(){ //Returns list of User created Recipes from database
        String query = "SELECT * FROM " +RECIPES+" WHERE " +COLUMN_USERNAME+" is not null";

        return baseListRecipe(query);
    }
    public ArrayList<Recipe> listOnlineRecipe(){ //Returns list of Default online Recipes from database
        String query = "SELECT * FROM " +RECIPES+" WHERE " +COLUMN_USERNAME+" is null";

        return baseListRecipe(query);
    }

    public ArrayList<Recipe> baseListRecipe(String inputquery){ //Returns list of Recipes from database with query input
        String query = inputquery;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Recipe queryData;
        String rid;
        String query2;
        Cursor cursor2;
        String cuisine;
        ArrayList<String> cList;
        String query3;
        Cursor cursor3;
        String ingredient;
        ArrayList<String> iList;
        String query4;
        Cursor cursor4;
        String step;
        ArrayList<String> sList;

        ArrayList<Recipe> rList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                queryData = new Recipe();

                rid = cursor.getString(0);
                queryData.setRid(rid);
                queryData.setName(cursor.getString(1));
                queryData.setDescription(cursor.getString(2));
                queryData.setDuration(cursor.getInt(3));
                queryData.setUid(cursor.getString(4));
                queryData.setNooflikes(cursor.getInt(5));
                queryData.setRecipeimage(cursor.getString(6));
                queryData.setServings(cursor.getInt(7));


                query2 = "SELECT * FROM " + CUISINE +
                        " WHERE " + COLUMN_RECIPEID + "=\"" + rid + "\"";
                cursor2 = db.rawQuery(query2, null);
                cList = new ArrayList<>();
                if (cursor2.moveToFirst()) {
                    while (!cursor2.isAfterLast()) {
                        cuisine = cursor2.getString(0);
                        cList.add(cuisine);

                        cursor2.moveToNext();
                    }
                }
                else{
                    cList = null;
                }
                cursor2.close();
                queryData.setCuisineList(cList);

                query3 = "SELECT * FROM " + INGREDIENT +
                        " WHERE " + COLUMN_RECIPEID + "=\"" + rid + "\"";
                cursor3 = db.rawQuery(query3, null);
                iList = new ArrayList<>();
                if (cursor3.moveToFirst()) {
                    while (!cursor3.isAfterLast()) {
                        ingredient = cursor3.getString(0);
                        iList.add(ingredient);

                        cursor3.moveToNext();
                    }
                }
                else{
                    iList = null;
                }
                cursor3.close();
                queryData.setIngredientsList(iList);

                query4 = "SELECT * FROM " + STEPS +
                        " WHERE " + COLUMN_RECIPEID + "=\"" + rid + "\"";
                cursor4 = db.rawQuery(query4, null);
                sList = new ArrayList<>();
                if (cursor4.moveToFirst()) {
                    while (!cursor4.isAfterLast()) {
                        step = cursor4.getString(0);
                        sList.add(step);

                        cursor4.moveToNext();
                    }
                }
                else{
                    sList = null;
                }
                cursor4.close();
                queryData.setStepsList(sList);

                rList.add(queryData);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return rList;
    }
}