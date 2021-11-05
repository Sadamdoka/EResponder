
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoronaList {

    @SerializedName("corona")
    @Expose
    private ArrayList<Corona> corona = null;

    public ArrayList<Corona> getCorona() {
        return corona;
    }

    public void setCorona(ArrayList<Corona> corona) {
        this.corona = corona;
    }

}
