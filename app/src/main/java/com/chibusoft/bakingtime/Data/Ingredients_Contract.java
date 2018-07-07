package com.chibusoft.bakingtime.Data;

/**
 * Created by EBELE PC on 6/23/2018.
 */
import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class Ingredients_Contract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.ABORT)
    @AutoIncrement
    public static final String COLUMN_ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String COLUMN_RECIPE_ID = "recipe_id";

    @DataType(DataType.Type.REAL)
    @NotNull
    public static final String COLUMN_QUANTITY = "quantity";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_MEASURE = "measure";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_INGREDIENT = "ingredient";

}
