
package com.esafeafrica.eresponder.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("organ")
    @Expose
    private String organ;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("resid")
    @Expose
    private String resid;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("type")
    @Expose
    private String type;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String datereg, String id, String status, String email, String name, String organ, String password, String phone, String resid, String role, String type) {
        this.datereg = datereg;
        this.id = id;
        this.status = status;
        this.email = email;
        this.name = name;
        this.organ = organ;
        this.password = password;
        this.phone = phone;
        this.resid = resid;
        this.role = role;
        this.type = type;
    }

    protected User(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        status = in.readString();
        email = in.readString();
        name = in.readString();
        organ = in.readString();
        password = in.readString();
        phone = in.readString();
        resid = in.readString();
        role = in.readString();
        type = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getDatereg() {
        return datereg;
    }

    public void setDatereg(String datereg) {
        this.datereg = datereg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(datereg);
        parcel.writeString(id);
        parcel.writeString(status);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(organ);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(resid);
        parcel.writeString(role);
        parcel.writeString(type);
    }
}
