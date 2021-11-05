
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PressList {

    @SerializedName("press")
    @Expose
    private ArrayList<Press> press = null;

    public ArrayList<Press> getPress() {
        return press;
    }

    public void setPress(ArrayList<Press> press) {
        this.press = press;
    }

}
