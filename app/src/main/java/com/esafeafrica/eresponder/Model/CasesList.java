
package com.esafeafrica.eresponder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CasesList {

    @SerializedName("cases")
    @Expose
    private ArrayList<Cases> cases = null;

    public ArrayList<Cases> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Cases> cases) {
        this.cases = cases;
    }

}
