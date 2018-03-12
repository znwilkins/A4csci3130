package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase database. This is converted to a JSON format
 */

public class Business implements Serializable {

    public String uid;
    public String businessNumber;
    public String name;
    public String businessType;
    public String address;
    public String provinceTerritory;

    /** Default constructor required for calls to DataSnapshot.getValue */
    public Business() {}

    public Business(String uid, String businessNumber, String name, String businessType,
                    String address, String provinceTerritory) {
        this.uid = uid;
        this.businessNumber = businessNumber;
        this.name = name;
        this.businessType = businessType;
        this.address = address;
        this.provinceTerritory = provinceTerritory;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("businessNumber", businessNumber);
        result.put("name", name);
        result.put("businessType", businessType);
        result.put("address", address);
        result.put("provinceTerritory", provinceTerritory);

        return result;
    }
}
