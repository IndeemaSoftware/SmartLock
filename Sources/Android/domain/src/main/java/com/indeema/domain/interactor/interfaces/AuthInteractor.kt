package com.indeema.domain.interactor.interfaces

import com.indeema.data.entities.RedmineResponce
import com.indeema.domain.action.Error
import com.indeema.domain.action.Next


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 20:42
 */

interface AuthInteractor : BaseInteractor {

    fun login(next: Next<RedmineResponce>, error: Error, credential: String)
}