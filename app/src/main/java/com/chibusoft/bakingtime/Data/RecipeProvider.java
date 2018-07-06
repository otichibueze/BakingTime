package com.chibusoft.bakingtime.Data;

/**
 * Created by EBELE PC on 6/23/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.NotifyBulkInsert;
import net.simonvt.schematic.annotation.NotifyDelete;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.NotifyUpdate;
import net.simonvt.schematic.annotation.TableEndpoint;


@ContentProvider(
        authority = RecipeProvider.AUTHORITY,
        database = RecipeDatabase.class,
        packageName = "com.chibusoft.bakingtime.Data.provider")
public final class RecipeProvider {

    //Note all classes must have a constructor
    private RecipeProvider() {
    }


    public static final String AUTHORITY = "com.chibusoft.bakingtime.Data.provider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //here we create interface paths to our tables to make code more readable
    //The values must the same with your table name
    interface Path {
        String Recipes = "recipes";
        String Ingredients = "ingredients";
        String Steps = "steps";
    }


    //Here we create url to put paths in different tables in oour tables
    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }


    @TableEndpoint(table = RecipeDatabase.RECIPES)
    public static class Recipes {

        //Here we define uri to path messages
        //Like get all messages
        @ContentUri(
                path = Path.Recipes,
                type = "vnd.android.cursor.dir/recipes",
                defaultSort = Recipe_Contract.COLUMN_RECIPE_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");

        //Get content with id from table
        @InexactContentUri(
                path = Path.Recipes + "/#",
                name = "Recipe_ID",
                type = "vnd.android.cursor.item/recipes",
                whereColumn = Recipe_Contract.COLUMN_RECIPE_ID,
                pathSegment = 1)
        public static Uri recipe_withid (long id) {
            return buildUri(Path.Recipes, String.valueOf(id));
        }

        //Insert code for recipe code
        @NotifyInsert(paths = Path.Recipes) public static Uri[] onInsert(ContentValues values) {
            final long recipeId = values.getAsLong(Recipe_Contract.COLUMN_RECIPE_ID);
            //Note these must be an array else will throw an error
            return new Uri[] {
                    recipe_withid(recipeId),
            };
        }

        @NotifyBulkInsert(paths = Path.Recipes)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        //Not properly implemented
        //(Uri uri, ContentValues values, String where, String[] whereArgs)
        @NotifyUpdate(paths = Path.Recipes + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long recipe_Id = Long.valueOf(uri.getPathSegments().get(1));

            //Note these must be an array else will throw an error
            return new Uri[] {
                    recipe_withid(recipe_Id),
            };
        }

        @NotifyDelete(paths = Path.Recipes + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long recipe_Id = Long.valueOf(uri.getPathSegments().get(1));

            //Note these must be an array else will throw an error
            return new Uri[] {
                    recipe_withid(recipe_Id),
            };
        }

    }



    @TableEndpoint(table = RecipeDatabase.STEPS)
    public static class Steps {

        //Here we define uri to path messages
        //Like get all messages
        @ContentUri(
                path = Path.Steps,
                type = "vnd.android.cursor.dir/steps",
                defaultSort = Steps_Contract.COLUMN_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/steps");

        //Get content with id from table
        @InexactContentUri(
                path = Path.Steps + "/#",
                name = "Steps_ID",
                type = "vnd.android.cursor.item/Steps",
                whereColumn = Steps_Contract.COLUMN_ID,
                pathSegment = 1)
        public static Uri step_withid (long id) {
            return buildUri(Path.Steps, String.valueOf(id));
        }

        //Insert code for Steps code
        @NotifyInsert(paths = Path.Steps) public static Uri[] onInsert(ContentValues values) {
            final long step_Id = values.getAsLong(Steps_Contract.COLUMN_ID);
            //Note these must be an array else will throw an error
            return new Uri[] {
                    step_withid(step_Id),
            };
        }

        @NotifyBulkInsert(paths = Path.Steps)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.Steps + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long step_Id = Long.valueOf(uri.getPathSegments().get(1));
            //Cursor c = context.getContentResolver().query(uri, new String[] {
            //        Steps_Contract.COLUMN_ID,
            //}, null, null, null);
            //c.moveToFirst();
            //final long listId = c.getLong(c.getColumnIndex(Steps_Contract.COLUMN_ID));
            //c.close();

            return new Uri[] {
                    step_withid(step_Id),
            };
        }

        @NotifyDelete(paths = Path.Steps + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long step_Id = Long.valueOf(uri.getPathSegments().get(1));

            return new Uri[] {
                    step_withid(step_Id),
            };
        }

    }


    @TableEndpoint(table = RecipeDatabase.INGREDIENTS)
    public static class Ingredients {

        //Here we define uri to path messages
        //Like get all messages
        @ContentUri(
                path = Path.Ingredients,
                type = "vnd.android.cursor.dir/ingredients",
                defaultSort = Ingredients_Contract.COLUMN_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");

        //Get content with id from table
        @InexactContentUri(
                path = Path.Ingredients + "/#",
                name = "Ingredient_ID",
                type = "vnd.android.cursor.item/ingredients",
                whereColumn = Ingredients_Contract.COLUMN_RECIPE_ID,
                pathSegment = 1)
        public static Uri ingredient_withid (long id) {
            return buildUri(Path.Ingredients, String.valueOf(id));
        }

        //Insert code for Ingredients code
        @NotifyInsert(paths = Path.Ingredients) public static Uri[] onInsert(ContentValues values) {
            final long ingredientsId = values.getAsLong(Ingredients_Contract.COLUMN_ID);
            //Note these must be an array else will throw an error
            return new Uri[] {
                    ingredient_withid(ingredientsId),
            };
        }

        @NotifyBulkInsert(paths = Path.Ingredients)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.Ingredients + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long ingredients_Id = Long.valueOf(uri.getPathSegments().get(1));

            //Note these must be an array else will throw an error
            return new Uri[] {
                    ingredient_withid(ingredients_Id),
            };
        }

        @NotifyDelete(paths = Path.Ingredients + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long ingredientsId = Long.valueOf(uri.getPathSegments().get(1));
            //Note these must be an array else will throw an error
            return new Uri[] {
                    ingredient_withid(ingredientsId),
            };
        }

    }


}
