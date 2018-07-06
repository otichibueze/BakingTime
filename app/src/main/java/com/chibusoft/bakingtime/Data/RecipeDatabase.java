package com.chibusoft.bakingtime.Data;

/**
 * Created by EBELE PC on 6/23/2018.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

@Database(version = RecipeDatabase.VERSION, packageName = "com.chibusoft.bakingtime.Data.provider")
public class RecipeDatabase {

    private RecipeDatabase(){}

    public static final int VERSION = 1;

    //Name of the table
    @Table(Recipe_Contract.class)
    public static final String RECIPES = "recipes";

    //Name of the table
    @Table(Ingredients_Contract.class)
    public static final String INGREDIENTS = "ingredients";

    //Name of table
    @Table(Steps_Contract.class)
    public static final String STEPS = "steps";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {
    }

    //@ExecOnCreate public static final String EXEC_ON_CREATE = "SELECT * FROM " + Recipes;

}
