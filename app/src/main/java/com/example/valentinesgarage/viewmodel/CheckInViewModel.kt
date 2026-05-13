package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valentinesgarage.data.AppDatabase
import com.example.valentinesgarage.data.entity.VehicleEntity
import kotlinx.coroutines.launch

class CheckInViewModel(application: Application) : AndroidViewModel(application) {

    private val vehicleDao = AppDatabase.getInstance(application).vehicleDao()

    val allVehicles = vehicleDao.getAllVehicles()

    private val _checkInSuccess = MutableLiveData<Int>() // returns new vehicle id
    val checkInSuccess: LiveData<Int> = _checkInSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun checkInVehicle(
        plateNumber: String,
        ownerName: String,
        kilometersStr: String,
        conditionNotes: String,
        conditionRating: Int,
        checkedInByUserId: Int
    ) {
        if (plateNumber.isBlank() || ownerName.isBlank() || kilometersStr.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            return
        }

        val kilometers = kilometersStr.toIntOrNull()
        if (kilometers == null) {
            _errorMessage.value = "Kilometers must be a valid number"
            return
        }

        viewModelScope.launch {
            val vehicle = VehicleEntity(
                plateNumber = plateNumber.trim().uppercase(),
                ownerName = ownerName.trim(),
                kilometersDriven = kilometers,
                conditionNotes = conditionNotes.trim(),
                conditionRating = conditionRating,
                checkedInByUserId = checkedInByUserId
            )
            vehicleDao.insert(vehicle)

            // Get the just-inserted vehicle to return its ID
            val inserted = vehicleDao.getByPlateNumber(plateNumber.trim().uppercase())
            _checkInSuccess.postValue(inserted?.id ?: -1)
        }
    }
}