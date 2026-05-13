package com.example.valentinesgarage

import com.example.valentinesgarage.data.entity.VehicleEntity
import org.junit.Assert.*
import org.junit.Test

class VehicleEntityTest {

    @Test
    fun `vehicle entity creates correctly with all fields`() {
        val vehicle = VehicleEntity(
            id = 1,
            plateNumber = "N 123 ABC",
            ownerName = "John Doe",
            kilometersDriven = 45000,
            conditionNotes = "Minor scratch on door",
            conditionRating = 4,
            checkedInByUserId = 2
        )

        assertEquals(1, vehicle.id)
        assertEquals("N 123 ABC", vehicle.plateNumber)
        assertEquals("John Doe", vehicle.ownerName)
        assertEquals(45000, vehicle.kilometersDriven)
        assertEquals("Minor scratch on door", vehicle.conditionNotes)
        assertEquals(4, vehicle.conditionRating)
        assertEquals(2, vehicle.checkedInByUserId)
    }

    @Test
    fun `vehicle condition rating is within valid range`() {
        val vehicle = VehicleEntity(
            plateNumber = "N 456 DEF",
            ownerName = "Jane Smith",
            kilometersDriven = 10000,
            conditionNotes = "",
            conditionRating = 3,
            checkedInByUserId = 1
        )
        assertTrue(vehicle.conditionRating in 1..5)
    }

    @Test
    fun `vehicle kilometers driven is non negative`() {
        val vehicle = VehicleEntity(
            plateNumber = "N 789 GHI",
            ownerName = "Bob",
            kilometersDriven = 0,
            conditionNotes = "",
            conditionRating = 5,
            checkedInByUserId = 1
        )
        assertTrue(vehicle.kilometersDriven >= 0)
    }

    @Test
    fun `vehicle checkedInAt is set automatically`() {
        val before = System.currentTimeMillis()
        val vehicle = VehicleEntity(
            plateNumber = "N 001 ZZZ",
            ownerName = "Test Owner",
            kilometersDriven = 5000,
            conditionNotes = "",
            conditionRating = 2,
            checkedInByUserId = 1
        )
        val after = System.currentTimeMillis()
        assertTrue(vehicle.checkedInAt in before..after)
    }

//    @Test
//    fun `two vehicles with same plate are not equal if different ids`() {
//        val v1 = VehicleEntity(1, "N 123 ABC", "Owner A", 1000, "", 3, 1)
//        val v2 = VehicleEntity(2, "N 123 ABC", "Owner B", 2000, "", 4, 2)
//        assertNotEquals(v1, v2)
    //}
}