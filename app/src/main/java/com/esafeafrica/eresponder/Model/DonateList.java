
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DonateList {

    @SerializedName("donate")
    @Expose
    private ArrayList<Donate> donate = null;

    public ArrayList<Donate> getDonate() {
        return donate;
    }

    public void setDonate(ArrayList<Donate> donate) {
        this.donate = donate;
    }

}
