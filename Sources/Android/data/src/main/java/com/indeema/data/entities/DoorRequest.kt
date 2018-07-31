package com.indeema.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 0:14
 */

class DoorRequest {

    @SerializedName("api_key")
    @Expose
    var apiKey: String? = null
}