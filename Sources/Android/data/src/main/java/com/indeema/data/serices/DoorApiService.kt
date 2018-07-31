package com.indeema.data.serices

import com.indeema.data.entities.DoorRequest
import com.indeema.data.entities.DoorResponce
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Ruslan Stosyk
 * Date: May, 20, 2018
 * Time: 0:03
 */

interface DoorApiService {
    @POST("/open_door")
    fun openDoor(@Body() data: DoorRequest): Observable<DoorResponce>
}