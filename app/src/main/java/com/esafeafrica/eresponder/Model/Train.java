package com.esafeafrica.eresponder.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Train implements Parcelable {

    public static final Creator<Train> CREATOR = new Creator<Train>() {
        @Override
        public Train createFromParcel(Parcel in) {
            return new Train(in);
        }

        @Override
        public Train[] newArray(int size) {
            return new Train[size];
        }
    };
    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("agency")
    @Expose
    private String agency;
    @SerializedName("ccepdo")
    @Expose
    private String ccepdo;
    @SerializedName("centerID")
    @Expose
    private String centerID;
    @SerializedName("centerName")
    @Expose
    private String centerName;
    @SerializedName("cso")
    @Expose
    private String cso;
    @SerializedName("fao")
    @Expose
    private String fao;
    @SerializedName("fho")
    @Expose
    private String fho;
    @SerializedName("fict")
    @Expose
    private String fict;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("groupID")
    @Expose
    private String groupID;
    @SerializedName("hko")
    @Expose
    private String hko;
    @SerializedName("irco")
    @Expose
    private String irco;
    @SerializedName("llo")
    @Expose
    private String llo;
    @SerializedName("names")
    @Expose
    private String names;
    @SerializedName("nin")
    @Expose
    private String nin;
    @SerializedName("ohso")
    @Expose
    private String ohso;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("phpo")
    @Expose
    private String phpo;
    @SerializedName("po")
    @Expose
    private String po;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("trainID")
    @Expose
    private String trainID;
    @SerializedName("userid")
    @Expose
    private String userid;

    protected Train(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        status = in.readString();
        agency = in.readString();
        ccepdo = in.readString();
        centerID = in.readString();
        centerName = in.readString();
        cso = in.readString();
        fao = in.readString();
        fho = in.readString();
        fict = in.readString();
        grade = in.readString();
        groupID = in.readString();
        hko = in.readString();
        irco = in.readString();
        llo = in.readString();
        names = in.readString();
        nin = in.readString();
        ohso = in.readString();
        passport = in.readString();
        phpo = in.readString();
        po = in.readString();
        total = in.readString();
        trainID = in.readString();
        userid = in.readString();
    }

    public Train() {
    }

    public Train(String datereg, String id, String status, String agency, String ccepdo, String centerID, String centerName, String cso, String fao, String fho, String fict, String grade, String groupID, String hko, String irco, String llo, String names, String nin, String ohso, String passport, String phpo, String po, String total, String trainID, String userid) {
        this.datereg = datereg;
        this.id = id;
        this.status = status;
        this.agency = agency;
        this.ccepdo = ccepdo;
        this.centerID = centerID;
        this.centerName = centerName;
        this.cso = cso;
        this.fao = fao;
        this.fho = fho;
        this.fict = fict;
        this.grade = grade;
        this.groupID = groupID;
        this.hko = hko;
        this.irco = irco;
        this.llo = llo;
        this.names = names;
        this.nin = nin;
        this.ohso = ohso;
        this.passport = passport;
        this.phpo = phpo;
        this.po = po;
        this.total = total;
        this.trainID = trainID;
        this.userid = userid;
    }

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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getCcepdo() {
        return ccepdo;
    }

    public void setCcepdo(String ccepdo) {
        this.ccepdo = ccepdo;
    }

    public String getCenterID() {
        return centerID;
    }

    public void setCenterID(String centerID) {
        this.centerID = centerID;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCso() {
        return cso;
    }

    public void setCso(String cso) {
        this.cso = cso;
    }

    public String getFao() {
        return fao;
    }

    public void setFao(String fao) {
        this.fao = fao;
    }

    public String getFho() {
        return fho;
    }

    public void setFho(String fho) {
        this.fho = fho;
    }

    public String getFict() {
        return fict;
    }

    public void setFict(String fict) {
        this.fict = fict;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getHko() {
        return hko;
    }

    public void setHko(String hko) {
        this.hko = hko;
    }

    public String getIrco() {
        return irco;
    }

    public void setIrco(String irco) {
        this.irco = irco;
    }

    public String getLlo() {
        return llo;
    }

    public void setLlo(String llo) {
        this.llo = llo;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getOhso() {
        return ohso;
    }

    public void setOhso(String ohso) {
        this.ohso = ohso;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhpo() {
        return phpo;
    }

    public void setPhpo(String phpo) {
        this.phpo = phpo;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(agency);
        dest.writeString(ccepdo);
        dest.writeString(centerID);
        dest.writeString(centerName);
        dest.writeString(cso);
        dest.writeString(fao);
        dest.writeString(fho);
        dest.writeString(fict);
        dest.writeString(grade);
        dest.writeString(groupID);
        dest.writeString(hko);
        dest.writeString(irco);
        dest.writeString(llo);
        dest.writeString(names);
        dest.writeString(nin);
        dest.writeString(ohso);
        dest.writeString(passport);
        dest.writeString(phpo);
        dest.writeString(po);
        dest.writeString(total);
        dest.writeString(trainID);
        dest.writeString(userid);
    }
}
