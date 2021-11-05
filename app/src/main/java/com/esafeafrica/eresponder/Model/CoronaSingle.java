
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoronaSingle {

    @SerializedName("corona")
    @Expose
    private Corona corona;

    public Corona getCorona() {
        return corona;
    }

    public void setCorona(Corona corona) {
        this.corona = corona;
    }

}
