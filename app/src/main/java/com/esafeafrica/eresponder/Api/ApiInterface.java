package com.esafeafrica.eresponder.Api;

import com.esafeafrica.eresponder.Model.AmnestyList;
import com.esafeafrica.eresponder.Model.AnounceList;
import com.esafeafrica.eresponder.Model.CasesList;
import com.esafeafrica.eresponder.Model.CasesSingle;
import com.esafeafrica.eresponder.Model.ContactList;
import com.esafeafrica.eresponder.Model.CoronaList;
import com.esafeafrica.eresponder.Model.CoronaSingle;
import com.esafeafrica.eresponder.Model.EmergencyList;
import com.esafeafrica.eresponder.Model.Feedback;
import com.esafeafrica.eresponder.Model.NumbersList;
import com.esafeafrica.eresponder.Model.OrganList;
import com.esafeafrica.eresponder.Model.OrganSingle;
import com.esafeafrica.eresponder.Model.PressList;
import com.esafeafrica.eresponder.Model.UserSingle;
import com.esafeafrica.eresponder.Model.UserWorkerList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by sadam on 08/08/18.
 * Copyright 2018
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface ApiInterface {

    @Multipart
    @POST("conditions/user")
    Call<Feedback> Authenticate(@Part("email") RequestBody email, @Part("password") RequestBody password);

    @Multipart
    @POST("create/responder")
    Call<Feedback> createAccount(@Part("organ") RequestBody organ, @Part("type") RequestBody type, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel, @Part("password") RequestBody password, @Part("role") RequestBody role);

    @Multipart
    @POST("create/cases")
    Call<Feedback> createCase(@Part("case") RequestBody _case, @Part("recovery") RequestBody recovery, @Part("dead") RequestBody dead, @Part("last") RequestBody last);

    @Multipart
    @POST("create/corona")
    Call<Feedback> createCorona(@Part("name") RequestBody name, @Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("tel") RequestBody tel, @Part("travel") RequestBody travel, @Part("were") RequestBody were, @Part("pain") RequestBody pain, @Part("fever") RequestBody fever, @Part("cough") RequestBody cough, @Part("sore") RequestBody sore, @Part("nose") RequestBody nose, @Part("tired") RequestBody tired, @Part("breath") RequestBody breath);


    @Multipart
    @POST("create/emergency")
    Call<Feedback> createEmergency(@Part("name") RequestBody name, @Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("brief") RequestBody brief, @Part("event") RequestBody event, @Part("extras") RequestBody extras);

    @Multipart
    @POST("create/contact")
    Call<Feedback> createContact(@Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel);

    @Multipart
    @POST("update/emergency")
    Call<Feedback> updateEmergency(@Part("id") RequestBody id, @Part("name") RequestBody name, @Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("brief") RequestBody brief, @Part("event") RequestBody event, @Part("extras") RequestBody extras);

    @Multipart
    @POST("update/account")
    Call<Feedback> updateAccount(@Part("id") RequestBody id, @Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel, @Part("password") RequestBody password);

    @Multipart
    @POST("update/contact")
    Call<Feedback> updateContact(@Part("id") RequestBody id, @Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel);

    @GET("conditions/emergency/{id}")
    Call<Feedback> clearActive(@Path("id") String id);

    @GET("fetch/responder/{id}")
    Call<UserSingle> getAccount(@Path("id") String id);

    @GET("fetch/responder/{con}")
    Call<UserSingle> getAccountbyEmail(@Path("con") String con);

    @GET("fetch/responder/{organ}")
    Call<UserSingle> getAccountbyOrgan(@Path("organ") String organ);

    @GET("fetch/amnesty")
    Call<AmnestyList> allAmnesty();

    @GET("fetch/emergency")
    Call<EmergencyList> allEmergency();

    @GET("fetch/emergency/{id}/{emerid}/{userid}/{passport}/{organ}")
    Call<EmergencyList> selectEmergency(@Path("id") String id, @Path("emerid") String emerid, @Path("userid") String userid, @Path("passport") String passport, @Path("organ") String organ);

    @GET("fetch/numbers")
    Call<NumbersList> allNumbers();

    @GET("fetch/contact")
    Call<ContactList> allContacts();

    @GET("fetch/anounce")
    Call<AnounceList> allAnounce();

    @GET("fetch/account")
    Call<UserWorkerList> allAccount();

    @GET("fetch/corona")
    Call<CoronaList> allCorona();

    @GET("fetch/corona/{id}")
    Call<CoronaSingle> getCorona(@Path("id") String id);

    @GET("fetch/organ")
    Call<OrganList> allorgan();


    @GET("fetch/organ/{id}/{organid}/{type}/{name}")
    Call<OrganList> organ(@Path("id") String id, @Path("organid") String organid, @Path("type") String type, @Path("name") String name);

    @GET("fetch/organ/{id}/{organid}/{type}/{name}")
    Call<OrganSingle> organSingle(@Path("id") String id, @Path("organid") String organid, @Path("type") String type, @Path("name") String name);

    @GET("fetch/cases")
    Call<CasesList> allCases();

    @GET("fetch/case/{id}")
    Call<CasesSingle> getCases(@Path("id") String id);

    @GET("fetch/press")
    Call<PressList> allPress();

    @GET("fetch/press/{id}")
    Call<PressList> getPress(@Path("id") String id);
}
