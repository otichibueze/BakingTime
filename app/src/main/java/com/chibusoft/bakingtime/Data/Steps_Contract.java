package com.chibusoft.bakingtime.Data;

/**
 * Created by EBELE PC on 6/23/2018.
 */


import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DataType.Type;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

public class Steps_Contract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.ABORT)
    @AutoIncrement
    public static final String COLUMN_ID = "_id";

    @DataType(Type.INTEGER)
    @NotNull
    public static final String COLUMN_RECIPE_ID = "recipe_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_SHORT_DESCRIPTION = "short_description";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_VIDEO_URL = "video_url";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";

}
