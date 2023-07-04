package com.siskadwi.kantor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.siskadwi.kantor.model.Office
import com.siskadwi.kantor.repository.OfficeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class OfficeViewModel(private val repository: OfficeRepository): ViewModel() {
    val allOffice: LiveData<List<Office>> = repository.allOffice.asLiveData()

    fun insert(office: Office) = viewModelScope.launch {
        repository.insertOffice(office)
    }
    fun delete(office: Office) = viewModelScope.launch {
        repository.deleteOffice(office)
    }
    fun update(office: Office) = viewModelScope.launch {
        repository.updateOffice(office)
    }
}
class OfficeViewModelFactory (private val repository: OfficeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((OfficeViewModel::class.java))){
            return OfficeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}