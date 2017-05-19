package org.schulcloud.mobile.data.model;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.schulcloud.mobile.data.model.jsonApi.Included;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Event implements RealmModel {
    public String _id;
    public String type;
    public String title;
    public Boolean allDay;
    public String start;
    public String end;
    public String summary;
    public String location;
    public RealmList<Included> included;

    @SerializedName("x-sc-courseId")
    public String xScCourseId;

    @SerializedName("x-sc-courseTimeId")
    public String xScCourseTimeId;
}
