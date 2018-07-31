package com.indeema.data.serices

import com.indeema.data.entities.RedmineResponce
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 19:43
 */

interface RedmineApiService {

    @GET("/users/current.json")
    fun requestLogin(@Header("Authorization") data: String): Observable<RedmineResponce>
}