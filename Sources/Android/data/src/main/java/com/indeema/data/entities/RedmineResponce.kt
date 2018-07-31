package com.indeema.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:00
 */

class RedmineResponce {

    @SerializedName("user")
    @Expose
    var user: User? = null

}