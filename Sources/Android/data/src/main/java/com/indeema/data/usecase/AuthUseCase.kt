package com.indeema.data.usecase

import com.indeema.data.entities.RedmineResponce
import com.indeema.data.serices.RedmineApiService
import io.reactivex.Observable


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 19:44
 */

class AuthUseCase(var mRedmineApiService: RedmineApiService) : BaseUseCase() {

    fun login(data: String): Observable<RedmineResponce> {
        return mRedmineApiService.requestLogin(data)
    }
}