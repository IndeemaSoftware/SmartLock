package com.indeema.domain.interactor.interfaces

import com.indeema.data.entities.DoorRequest
import com.indeema.data.entities.DoorResponce
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:20
 */

interface DoorInteractor : BaseInteractor {

    fun openDoor(next: Next<DoorResponce>, error: Error, doorRequest: DoorRequest)
}