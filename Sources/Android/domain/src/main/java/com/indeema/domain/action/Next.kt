package com.indeema.domain.action


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 17:12
 */

interface Next<T> {
    fun onNext(data: T)
}