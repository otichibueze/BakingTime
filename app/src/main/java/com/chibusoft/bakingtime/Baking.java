package com.chibusoft.bakingtime;

import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by EBELE PC on 6/4/2018.
 */

public class Baking implements Parcelable{

    @SerializedName("id")
   public int id;
    @SerializedName("name")
   public String name;
    @SerializedName("ingredients")
   public ingredients[] myIngredients;
    @SerializedName("steps")
   public steps[] mySteps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ingredients[] getMyIngredients() {
        return myIngredients;
    }

    public void setMyIngredients(ingredients[] myIngredients) {
        this.myIngredients = myIngredients;
    }

    public steps[] getMySteps() {
        return mySteps;
    }

    public void setMySteps(steps[] mySteps) {
        this.mySteps = mySteps;
    }

    private class ingredients{
        @SerializedName("quantity")
        Double quantity;
        @SerializedName("measure")
        String measure;
        @SerializedName("ingredient")
        String ingredient;
    }


    private class steps {
        @SerializedName("id")
        int id;
        @SerializedName("shortDescription")
        String shortDescription;
        @SerializedName("description")
        String description;
        @SerializedName("videoURL")
        String videoURL;
        @SerializedName("thumbnailURL")
        String thumbnailURL;
    }

    private Baking(Parcel in){

        id = in.readInt();
        name = in.readString();


        for(int i = 0; i < getMyIngredients().length; i++)
        {
            myIngredients[i].quantity = in.readDouble();
            myIngredients[i].measure = in.readString();
            myIngredients[i].ingredient = in.readString();
        }

        for(int i = 0; i < getMySteps().length; i++)
        {
            mySteps[i].id = in.readInt();
            mySteps[i].shortDescription = in.readString();
            mySteps[i].description = in.readString();
            mySteps[i].videoURL = in.readString();
            mySteps[i].thumbnailURL = in.readString();
        }

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);

        for(int j = 0; j < getMyIngredients().length; j++)
        {
            parcel.writeDouble(myIngredients[j].quantity);
            parcel.writeString(myIngredients[j].measure);
            parcel.writeString(myIngredients[j].ingredient);
        }

        for(int j = 0; j < getMySteps().length; j++)
        {
            parcel.writeInt(mySteps[j].id);
            parcel.writeString(mySteps[i].shortDescription);
            parcel.writeString(mySteps[i].description);
            parcel.writeString(mySteps[i].videoURL);
            parcel.writeString(mySteps[i].thumbnailURL);

        }
    }

    public final Parcelable.Creator<Baking> CREATOR = new Parcelable.Creator<Baking>() {
        @Override
        public Baking createFromParcel(Parcel parcel) {
            return new Baking(parcel);
        }

        @Override
        public Baking[] newArray(int i) {
            return new Baking[i];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }
}
