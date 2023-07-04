package com.siskadwi.kantor.application

import android.app.Application
import com.siskadwi.kantor.repository.OfficeRepository

class OfficeApp : Application(){
val database by lazy { OfficeDatabase.getDatabase(this) }
    val repository by lazy { OfficeRepository(database.officeDao()) }
}