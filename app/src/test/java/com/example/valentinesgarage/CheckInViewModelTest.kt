package com.example.valentinesgarage

import org.junit.Assert.*
import org.junit.Test

class CheckInViewModelTest {

    private fun isValidCheckIn(
        plateNumber: String,
        ownerName: String,
        kilometersStr: String
    ): Boolean {
        if (plateNumber.isBlank() || ownerName.isBlank() || kilometersStr.isBlank()) return false
        if (kilometersStr.toIntOrNull() == null) return false
        return true
    }
    @Test
    fun `blank plate number fails validation`() {
        assertFalse(isValidCheckIn("", "John", "5000"))
    }
    @Test
    fun `blank owner name fails validation`() {
        assertFalse(isValidCheckIn("N 123 ABC", "", "5000"))
    }
    @Test
    fun `blank kilometers fails validation`() {
        assertFalse(isValidCheckIn("N 123 ABC", "John", ""))
    }
}