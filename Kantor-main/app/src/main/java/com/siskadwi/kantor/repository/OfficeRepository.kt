package com.siskadwi.kantor.repository

import com.siskadwi.kantor.dao.OfficeDao
import com.siskadwi.kantor.model.Office
import kotlinx.coroutines.flow.Flow

class OfficeRepository(private val officeDao: OfficeDao) {
    val allOffice: Flow<List<Office>> = officeDao.getAllOffice()

    suspend fun insertOffice(office: Office) {
        officeDao.insertOfiice(office)
    }

    suspend fun deleteOffice(office: Office) {
        officeDao.deleteOffice(office)
    }

    suspend fun updateOffice(office: Office) {
        officeDao.updateOffice(office)
    }
}