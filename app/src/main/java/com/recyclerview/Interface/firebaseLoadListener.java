package com.recyclerview.Interface;

import com.recyclerview.model.ItemGroup;

import java.util.List;

public interface firebaseLoadListener {

    void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);
    void onFirebaseLoadFailed(String message);

}
