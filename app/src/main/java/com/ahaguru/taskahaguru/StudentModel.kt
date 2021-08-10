package com.ahaguru.taskahaguru

import java.util.*

data class StudentModel(

    val Id: Int = getAutoId(),
    val Name: String ="",
    val Email: String ="",
    val Class: String ="",
    val Location: String ="",
    val Date: String =""
) {

    companion object {

        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)


        }

    }

}