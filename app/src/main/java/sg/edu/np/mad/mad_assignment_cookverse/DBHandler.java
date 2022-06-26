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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "CookverseDB.db";

    //Accounts table storing User data
    public static String ACCOUNTS = "Accounts";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_USERID = "Id";
    public static String COLUMN_USERIMAGE = "UserImage";
    /*public static String COLUMN_LIKEDRECIPES = "LikedList";
    public static String COLUMN_CREATEDRECIPES = "CreatedList";*/

    //Recipes table storing Recipe data
    public static String RECIPES = "Recipes";
    public static String COLUMN_RECIPENAME = "RecipeName";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_STEPS = "Steps";
    public static String COLUMN_INGREDIENTS = "Ingredients";
    public static String COLUMN_RECIPEID = "RecipeId";
    public static String COLUMN_RECIPEUSERID = "UserId";
    /*public static String COLUMN_CUISINES = "CuisinesList";*/
    public static String COLUMN_LIKES = "NoOfLikes";
    public static String COLUMN_RECIPEIMAGE = "RecipeImage";

    public static int DATABASE_VERSION = 1;
    Context context;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE1 = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_USERIMAGE +
                /*" TEXT," + COLUMN_CREATEDRECIPES +*/ " BLOB)";
        String CREATE_TABLE2 = "CREATE TABLE " + RECIPES + "(" + COLUMN_RECIPEID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RECIPENAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_RECIPEUSERID
                + " TEXT," + COLUMN_LIKES  + " TEXT," + COLUMN_STEPS  + " TEXT," + COLUMN_INGREDIENTS
                + " TEXT," + COLUMN_RECIPEIMAGE + " BLOB)";
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

        //Creating initial Accounts and Recipes data
        addDefaultAccounts(db);
        addDefaultRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Used when updating DB version
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES);
        onCreate(db);
    }

    public User findUserByName(String username){ //Returns User object with specified username
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setPassword(cursor.getString(2));
            if (cursor.getBlob(3) != null){ //checking if stored User has an image
                queryData.setUserImage(DbBitmapUtility.getImage(cursor.getBlob(3))); //changing Blob from database to Bitmap
            }

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

    public User findUserById(int id){ //Returns User object with specific ID
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERID + "=\"" + id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setId(cursor.getInt(0));
            queryData.setName(cursor.getString(1));
            queryData.setPassword(cursor.getString(2));
            if (cursor.getBlob(3) != null){
                queryData.setUserImage(DbBitmapUtility.getImage(cursor.getBlob(3)));
            }

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

    public void addUser(User userData) { //Adds user data to database
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        if (userData.getUserImage() != null){
            values.put(COLUMN_USERIMAGE, DbBitmapUtility.getBytes(userData.getUserImage())); //Changing Bitmap to Bytes to store inside Database
        }
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

    public void updateUser(User userData){ //Replaces user database info with new user info

        deleteUser(userData.getId()); //Deletes user from database

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getName());
        values.put(COLUMN_PASSWORD, userData.getPassword());
        values.put(COLUMN_USERID, userData.getId()); //necessary to avoid auto incrementation
        if (userData.getUserImage() != null){
            values.put(COLUMN_USERIMAGE, DbBitmapUtility.getBytes(userData.getUserImage()));
        }

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
    }

    public ArrayList<User> listUser(){ //Returns a list with all users from the database
        String query = "SELECT * FROM " + RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User queryData = null;
        ArrayList<User> uList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do { //iterates through every row the query returned
                queryData = new User();
                queryData.setId(cursor.getInt(0));
                queryData.setName(cursor.getString(1));
                queryData.setPassword(cursor.getString(2));
                if (cursor.getBlob(3) != null){
                    queryData.setUserImage(DbBitmapUtility.getImage(cursor.getBlob(3)));
                }
                uList.add(queryData);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return uList;
    }

    public Recipe findRecipe(int recipeID){ //Returns recipe with specified recipe ID
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
            queryData.setNoOfLikes(cursor.getInt(4));
            queryData.setSteps(cursor.getString(5));
            queryData.setIngredients(cursor.getString(6));
            if (cursor.getBlob(7) != null){
                queryData.setRecipeImage(DbBitmapUtility.getImage(cursor.getBlob(7)));

            }

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
            queryData.setIngredientList(iList);*/

            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }

    public void addRecipe(Recipe recipeData){ //Adds recipe to database
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipeData.getName());
        values.put(COLUMN_DESCRIPTION, recipeData.getDescription());
        // values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); Primary Key Autoincrement

        if (recipeData.getUserId() != 0){
            values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());}

        /*JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(recipeData.getCuisineList()));
        String cList = cjson.toString();
        values.put(COLUMN_CUISINES, cList);

        JSONObject ijson = new JSONObject();
        ijson.put("uniqueArrays", new JSONArray(recipeData.getIngredientList()));
        String iList = ijson.toString();
        values.put(COLUMN_INGREDIENTS, iList);*/

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

    public void updateRecipe(Recipe recipeData){ //Replaces recipe database info with new recipe info

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

        /*JSONObject cjson = new JSONObject();
        cjson.put("uniqueArrays", new JSONArray(recipeData.getCuisineList()));
        String cList = cjson.toString();
        values.put(COLUMN_CUISINES, cList);

        JSONObject ijson = new JSONObject();
        ijson.put("uniqueArrays", new JSONArray(recipeData.getIngredientList()));
        String iList = ijson.toString();
        values.put(COLUMN_INGREDIENTS, iList);*/

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
    }

    public ArrayList<Recipe> listRecipe(){ //Returns list of Recipes from database
        String query = "SELECT * FROM " + RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Recipe queryData = null;
        ArrayList<Recipe> rList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                queryData = new Recipe();
                queryData.setRecipeId(cursor.getInt(0));
                queryData.setName(cursor.getString(1));
                queryData.setDescription(cursor.getString(2));
                queryData.setUserId(cursor.getInt(3));
                queryData.setNoOfLikes(cursor.getInt(4));
                queryData.setSteps(cursor.getString(5));
                queryData.setIngredients(cursor.getString(6));
                if (cursor.getBlob(7) != null){
                    queryData.setRecipeImage(DbBitmapUtility.getImage(cursor.getBlob(7)));
                }
                rList.add(queryData);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return rList;
    }

    public void addDefaultRecipes(SQLiteDatabase db) { //Method housing all default recipes to be added to database
        // create default Recipes
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, "Farro Salad with Asparagus and Parmesan");
        values.put(COLUMN_DESCRIPTION, "A light and delicious way to get your whole grains. Perfect salad for picnics, baby or bridal showers or just because!");
        values.put(COLUMN_STEPS, "Step 1: Soak farro in a large bowl of water for at least 12 hours. Drain.\n" +
                "Step 2: Fill a large pot with lightly salted water and bring to a rolling boil over high heat. Once the water is boiling, stir in the drained farro, and return to a boil. Reduce heat to medium, then cook the farro uncovered, stirring occasionally for 20 minutes. Reduce heat to low, cover, and continue simmering until tender, about 30 more minutes. Drain and allow to cool.\n" +
                "Step 3: Bring a large pot of lightly salted water to a boil. Add the asparagus, and cook uncovered until tender, about 3 minutes. Drain in a colander, then immediately immerse in ice water for several minutes until cold to stop the cooking process. Once the asparagus is cold, drain well, and chop. Set aside.\n" +
                "Step 4: Place farro, asparagus, tomatoes, walnuts, cranberries, parsley, and chives in a large bowl. Drizzle the balsamic vinaigrette over and sprinkle about 3/4 cups Parmesan cheese, then toss. Top with the remaining 1/4 cup of Parmesan cheese. Serve at room temperature.\n");
        values.put(COLUMN_INGREDIENTS, "2 cups farro\n" +
                "¾ pound fresh asparagus, trimmed\n" +
                "1 cup red and yellow cherry tomatoes, halved\n" +
                "¾ cup chopped walnuts\n" +
                "¾ cup dried cranberries\n" +
                "½ cup chopped fresh parsley\n" +
                "⅓ cup chopped fresh chives\n" +
                "¼ cup balsamic vinaigrette, or to taste\n" +
                "1 cup shaved Parmesan cheese, divided ");
        //values.put(COLUMN_RECIPEID, recipeData.getRecipeId()); Primary Key Autoincrement
        //values.put(COLUMN_RECIPEUSERID, recipeData.getUserId());
        values.put(COLUMN_LIKES, 0);
        //Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.random_pic);
        //values.put(COLUMN_RECIPEIMAGE, DbBitmapUtility.getBytes(bm));
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Avocado Deviled Eggs");
        values.put(COLUMN_DESCRIPTION, "This is a twist on the traditional deviled egg. I usually use 1 or 2 fewer yolks for the filling. ");
        values.put(COLUMN_STEPS, "Step 1: Scoop egg yolks into a bowl; add avocado, 2/3 of chopped turkey bacon, mayonnaise, lime juice, garlic, cayenne pepper, and salt. Mash egg yolk mixture until filling is evenly combined.\n" +
                "Step 2: Spoon filling into a piping bag or plastic bag with a snipped corner. Pipe filling into each egg white; top with a turkey bacon piece, jalapeno slice, and dash hot sauce.");
        values.put(COLUMN_INGREDIENTS, "6 hard-boiled eggs, peeled and halved\n" +
                "1 avocado - peeled, pitted, and diced\n" +
                "3 slices cooked turkey bacon, chopped, divided\n" +
                "2½ tablespoons mayonnaise\n" +
                "2 teaspoons lime juice\n" +
                "1 clove garlic, crushed\n" +
                "⅛ teaspoon cayenne pepper\n" +
                "sea salt to taste\n" +
                "1 jalapeno pepper, sliced (Optional)\n" +
                "1 dash hot sauce, or to taste (Optional)");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Spicy Sweet Glazed Salmon");
        values.put(COLUMN_DESCRIPTION, "I looked for a recipe for glazed salmon and didn't find one that used the ingredients I had on hand. So I threw together what I had and came up with this tangy, spicy sweet marinade that adds flavor, but doesn't mask the flavor of the salmon. My kids and sister loved it. They said it was better than 'restaurant salmon' It's great served with wild rice and stir fried veggies. ");
        values.put(COLUMN_STEPS, "Step 1: Place salmon in a shallow, flat dish, and set aside. Combine the vinegar, olive oil, soy sauce, water, lemon juice, red pepper flakes, onion powder, garlic powder, cilantro, and brown sugar in a blender. Blend until brown sugar dissolves. Pour the marinade over the salmon to cover evenly. Cover the dish, and refrigerate at least 2 hours.\n" +
                "Step 2: Line a broiling pan with foil. Remove salmon from marinade, and place on prepared broiling pan; season to taste with salt and pepper. Transfer remaining marinade to a saucepan.\n" +
                "Step 3: Turn on broiler to low.\n" +
                "Step 4: Broil salmon about 6 inches from the heat for 5 minutes; brush with remaining marinade. Broil an additional 5 to 10 minutes, brushing 2 or 3 more times with additional marinade. Salmon is done when fish is no longer bright red and can be flaked with a fork.\n" +
                "Step 5: Meanwhile, cook the remaining marinade over low heat until it thickens and reduces by one-third, 5 to 10 minutes. Use cooked marinade as a dipping sauce or drizzle over salmon just before serving.\n");
        values.put(COLUMN_INGREDIENTS, "1½ pounds fresh salmon fillet with skin removed\n" +
                "¼ cup red wine vinegar\n" +
                "¼ cup olive oil\n" +
                "¼ cup soy sauce\n" +
                "¼ cup water\n" +
                "1 tablespoon lemon juice\n" +
                "½ teaspoon red pepper flakes, or to taste\n" +
                "1 teaspoon onion powder\n" +
                "1 teaspoon garlic powder\n" +
                "2 teaspoons chopped fresh cilantro\n" +
                "⅓ cup brown sugar, packed\n" +
                "salt and ground black pepper to taste ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Good Old Fashioned Pancakes");
        values.put(COLUMN_DESCRIPTION, "This is a great recipe that I found in my Grandma's recipe book. Judging from the weathered look of this recipe card, this was a family favorite. ");
        values.put(COLUMN_STEPS, "Step 1: In a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; mix until smooth.\n" +
                "Step 2: Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.\n");
        values.put(COLUMN_INGREDIENTS, "1½ cups all-purpose flour\n" +
                "3½ teaspoons baking powder\n" +
                "¼ teaspoon salt, or more to taste\n" +
                "1 tablespoon white sugar\n" +
                "1¼ cups milk\n" +
                "1 egg\n" +
                "3 tablespoons butter, melted ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Rhubarb Muffin");
        values.put(COLUMN_DESCRIPTION, "This is a great way to use up that frozen rhubarb in your freezer, but you can also use fresh rhubarb. It makes a moist and tangy muffin. ");
        values.put(COLUMN_STEPS, "Step 1: Preheat the oven to 350 degrees F (175 degrees C). Grease a 12 cup muffin tin, or line with paper liners.\n" +
                "Step 2: In a medium bowl, stir together the yogurt, 2 tablespoons of melted butter, oil and egg. In a large bowl, stir together the flour, 3/4 cup of brown sugar, baking soda and salt. Pour the wet ingredients into the dry, and mix until just blended. Fold in rhubarb. Spoon into the prepared muffin tin, filling cups at least 2/3 full.\n" +
                "Step 3: In a small bowl, stir together 1/4 cup of brown sugar, cinnamon, nutmeg, almonds, and 2 teaspoons of melted butter. Spoon over the tops of the muffins, and press down lightly.\n" +
                "Step 4: Bake for 25 minutes in the preheated oven, or until the tops spring back when lightly pressed. Cool in the pan for about 15 minutes before removing.\n");
        values.put(COLUMN_INGREDIENTS, "½ cup vanilla yogurt\n" +
                "2 tablespoons butter, melted\n" +
                "2 tablespoons vegetable oil\n" +
                "1 egg\n" +
                "1⅓ cups all-purpose flour\n" +
                "¾ cup brown sugar\n" +
                "½ teaspoon baking soda\n" +
                "¼ teaspoon salt\n" +
                "1 cup diced rhubarb\n" +
                "¼ cup brown sugar\n" +
                "½ teaspoon ground cinnamon\n" +
                "¼ teaspoon ground nutmeg\n" +
                "¼ cup crushed sliced almonds\n" +
                "2 teaspoons melted butter");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Perfect Summer Fruit Salad");
        values.put(COLUMN_DESCRIPTION, "The perfect fruit salad for a backyard bbq or any occasion. There are never leftovers! This is one of my favorite fruit salad recipes, as I think the sauce really makes it. This salad is tastier the longer you can let it soak in its juices. I prefer 3 to 4 hours in the refrigerator before I serve it. Enjoy. ");
        values.put(COLUMN_STEPS, "Step 1: Bring orange juice, lemon juice, brown sugar, orange zest, and lemon zest to a boil in a saucepan over medium-high heat. Reduce heat to medium-low, and simmer until slightly thickened, about 5 minutes. Remove from heat, and stir in vanilla extract. Set aside to cool.\n" +
                "Step 2: Layer the fruit in a large, clear glass bowl in this order: pineapple, strawberries, kiwi fruit, bananas, oranges, grapes, and blueberries. Pour the cooled sauce over the fruit. Cover and refrigerate for 3 to 4 hours before serving.\n");
        values.put(COLUMN_INGREDIENTS, "⅔ cup fresh orange juice\n" +
                "⅓ cup fresh lemon juice\n" +
                "⅓ cup packed brown sugar\n" +
                "½ teaspoon grated orange zest\n" +
                "½ teaspoon grated lemon zest\n" +
                "1 teaspoon vanilla extract\n" +
                "2 cups cubed fresh pineapple\n" +
                "2 cups strawberries, hulled and sliced\n" +
                "3 kiwi fruit, peeled and sliced\n" +
                "3 bananas, sliced\n" +
                "2 oranges, peeled and sectioned\n" +
                "1 cup seedless grapes\n" +
                "2 cups blueberries ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Scrambled Eggs Done Right");
        values.put(COLUMN_DESCRIPTION, "The right way to scramble eggs. There is more to just mixing eggs and cooking! This will make a believer out of you. ");
        values.put(COLUMN_STEPS, "Step 1: In a cup or small bowl, whisk together the eggs, mayonnaise and water using a fork. Melt margarine in a skillet over low heat. Pour in the eggs, and stir constantly as they cook. Remove the eggs to a plate when they are set, but still moist. Do not over cook. Never add salt or pepper until eggs are on plate, but these are also good without.\n");
        values.put(COLUMN_INGREDIENTS, "2 eggs\n" +
                "1 teaspoon mayonnaise or salad dressing\n" +
                "1 teaspoon water (Optional)\n" +
                "1 teaspoon margarine or butter\n" +
                "1 pinch salt and pepper to taste ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Oven-Baked Bacon");
        values.put(COLUMN_DESCRIPTION, "I always bake bacon in the oven! You just need to plan a bit ahead as the bacon takes longer. With baking, the bacon grease stays nice and clean and you can reuse it to saute vegetables or eggs for extra flavor. ");
        values.put(COLUMN_STEPS, "Step 1: Preheat the oven to 350 degrees F (175 degrees C). Line a baking sheet with parchment paper.\n" +
                "Step 2: Place bacon slices one next to the other on the prepared baking sheet.\n" +
                "Step 3: Bake in the preheated oven for 15 to 20 minutes. Remove from oven. Flip bacon slices with kitchen tongs and return to oven. Bake until crispy, 15 to 20 minutes more. Thinner slices will need less time, about 20 minutes total. Drain on a plate lined with paper towels.\n");
        values.put(COLUMN_INGREDIENTS, "1 (16 ounce) package bacon ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Breakfast Sausage");
        values.put(COLUMN_DESCRIPTION, "Homemade breakfast sausage patties made with ground pork, brown sugar, and sage deliver sweet and savory flavor in every bite. These are better than store-bought since you can flavor them as you like! Serve a patty alongside scrambled eggs and hash brown potatoes or layer one with a fried egg and a slice of cheese to make a tasty breakfast sandwich. ");
        values.put(COLUMN_STEPS, "Step 1: Mix together brown sugar, sage, salt, black pepper, marjoram, red pepper flakes, and cloves in a small bowl until well combined.\n" +
                "Step 2: Place pork in a large bowl. Add spice mixture and mix with your hands until well combined. Form mixture into 6 patties.\n" +
                "Step 3: Heat a large skillet over medium-high heat. Add patties and saute until browned and crispy, about 5 minutes per side. An instant-read thermometer inserted into the center should read at least 160 degrees F (71 degrees C).\n");
        values.put(COLUMN_INGREDIENTS, "1 tablespoon brown sugar\n" +
                "2 teaspoons dried sage\n" +
                "2 teaspoons salt\n" +
                "1 teaspoon ground black pepper\n" +
                "¼ teaspoon dried marjoram\n" +
                "⅛ teaspoon crushed red pepper flakes\n" +
                "1 pinch ground cloves\n" +
                "2 pounds ground pork ");
        db.insert(RECIPES, null, values);

        values.put(COLUMN_RECIPENAME, "Egg Bites");
        values.put(COLUMN_DESCRIPTION, "Easy baked version of those egg bites made popular by that famous chain coffeehouse. I like them with hot sauce! ");
        values.put(COLUMN_STEPS, "Step 1: Preheat the oven to 350 degrees F (175 degrees C). Spray a 12-cup muffin tin with nonstick cooking spray.\n" +
                "Step 2: Place a thin layer of potato slices in the bottom of each muffin cup. Add a little butter on top.\n" +
                "Step 3: Bake in the preheated oven for 5 minutes.\n" +
                "Step 4: Mix eggs, tomato, bell pepper, spinach, ham, and onion together in a large bowl. Remove muffin tin from oven and ladle the egg mixture over the potatoes. Top each muffin cup with a mozzarella cube.\n" +
                "Step 5: Continue baking until egg bites are set, about 20 minutes.\n");
        values.put(COLUMN_INGREDIENTS, "cooking spray\n" +
                "5 small tri-color baby potatoes, thinly sliced\n" +
                "¼ stick butter\n" +
                "10 small eggs\n" +
                "1 small tomato, finely chopped\n" +
                "1 small yellow bell pepper, finely chopped\n" +
                "½ cup fresh spinach\n" +
                "¼ cup chopped ham\n" +
                "¼ cup chopped white onion\n" +
                "1 slice mozzarella cheese, cut into 12 cubes ");
        db.insert(RECIPES, null, values);

    }

    public void addDefaultAccounts(SQLiteDatabase db){ //Method housing all default accounts to be added to Database
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "test");
        values.put(COLUMN_PASSWORD, "password");

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.random_pic);
        values.put(COLUMN_USERIMAGE, DbBitmapUtility.getBytes(bm));
        db.insert(ACCOUNTS, null, values);
    }
}
