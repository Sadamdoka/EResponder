
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrganList {

    @SerializedName("organ")
    @Expose
    private ArrayList<Organ> organ = null;

    public ArrayList<Organ> getOrgan() {
        return organ;
    }

    public void setOrgan(ArrayList<Organ> organ) {
        this.organ = organ;
    }

}
