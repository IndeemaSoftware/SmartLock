package com.indeema.data.usecase

import com.indeema.data.entities.DoorRequest
import com.indeema.data.entities.DoorResponce
import com.indeema.data.serices.DoorApiService
import io.reactivex.Observable


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:19
 */

class DoorUseCase(var mDoorApiService: DoorApiService) : BaseUseCase() {

    fun openDoor(doorRequest: DoorRequest): Observable<DoorResponce> {
        return mDoorApiService.openDoor(doorRequest)
    }
}