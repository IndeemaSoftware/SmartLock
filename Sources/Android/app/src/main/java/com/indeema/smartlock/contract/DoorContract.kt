package com.indeema.smartlock.contract


/**
 * @author Ruslan Stosyk
 * Date: May, 19, 2018
 * Time: 23:01
 */

class DoorContract : BaseActivityContract() {

    interface Actions : BaseActivityActions {
        fun openDoor()
    }

    interface Views : BaseActivityViews {
        fun switchViews(isOpen: Boolean)
        fun successOpeningDoor()
        fun errorOpenDoor(throwable: Throwable)
    }
}