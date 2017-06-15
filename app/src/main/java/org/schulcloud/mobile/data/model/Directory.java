package org.schulcloud.mobile.data.model;

import org.parceler.Transient;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Directory implements RealmModel {
    @PrimaryKey
    public String name;
    public String path;
}
