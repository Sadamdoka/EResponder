package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmnestySingle {

    @SerializedName("amnesty")
    @Expose
    private Amnesty amnesty;

    public Amnesty getAmnesty() {
        return amnesty;
    }

    public void setAmnesty(Amnesty amnesty) {
        this.amnesty = amnesty;
    }

}
