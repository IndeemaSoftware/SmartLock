package com.indeema.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 0:20
 */

class DoorResponce {

    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("status")
    @Expose
    var status: Boolean? = null;
}