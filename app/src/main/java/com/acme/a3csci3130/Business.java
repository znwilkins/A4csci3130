package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class describes a business in the Seafood Marketplace.
 * It can be serialized, for storage in Firebase.
 */

public class Business implements Serializable {

    /** The unique ID of this business. */
    public String uid;
    /** A 9 digit number associated with this business. */
    public String businessNumber;
    /** The name of this business. */
    public String name;
    /** The type of business. */
    public String businessType;
    /** The address of the business. */
    public String address;
    /** The province or territory of this business. */
    public String provinceTerritory;

    /** Default constructor required for calls to DataSnapshot.getValue */
    public Business() {}

    /**
     * Instantiate a Business with the following parameters.
     * @param uid The unique ID of this business
     * @param businessNumber A 9 digit number associated with this business
     * @param name The name of this business
     * @param businessType The type of business
     * @param address The address of the business
     * @param provinceTerritory The province or territory of this business
     */
    public Business(String uid, String businessNumber, String name, String businessType,
                    String address, String provinceTerritory) {
        this.uid = uid;
        this.businessNumber = businessNumber;
        this.name = name;
        this.businessType = businessType;
        this.address = address;
        this.provinceTerritory = provinceTerritory;
    }

    /**
     * This method is used as part of the serialization process.
     * It creates a map with the values of this Business.
     * @return a map containing the values of this business
     */
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
