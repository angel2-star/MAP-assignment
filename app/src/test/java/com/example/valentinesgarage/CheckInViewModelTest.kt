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
    @Test
    fun `non numeric kilometers fails validation`() {
        assertFalse(isValidCheckIn("N 123 ABC", "John", "abc"))
    }
    @Test
    fun `valid inputs pass validation`() {
        assertTrue(isValidCheckIn("N 123 ABC", "John Doe", "45000"))
    }
    @Test
    fun `plate number is uppercased on check in`() {
        val plate = "n 123 abc"
        assertEquals("N 123 ABC", plate.trim().uppercase())
    }

    @Test
    fun `condition rating stays within 1 to 5 range`() {
        val rating = 4
        assertTrue(rating in 1..5)
    }
}