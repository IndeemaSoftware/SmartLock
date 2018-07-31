package com.indeema.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:00
 */

class User {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("login")
    @Expose
    var login: String? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("created_on")
    @Expose
    var createdOn: String? = null
    @SerializedName("last_login_on")
    @Expose
    var lastLoginOn: String? = null
    @SerializedName("api_key")
    @Expose
    var apiKey: String? = null
}