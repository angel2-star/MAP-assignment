package com.example.valentinesgarage

import com.example.valentinesgarage.data.entity.TaskEntity
import org.junit.Assert.*
import org.junit.Test

class TaskEntityTest {

    @Test
    fun `task entity creates correctly with default values`() {
        val task = TaskEntity(
            vehicleId = 1,
            title = "Oil change",
            assignedToUserId = 2
        )

        assertEquals(1, task.vehicleId)
        assertEquals("Oil change", task.title)
        assertEquals(false, task.isCompleted)
        assertEquals("", task.notes)
        assertEquals(2, task.assignedToUserId)
        assertNull(task.completedAt)
    }

    @Test
    fun `task can be marked as completed`() {
        val task = TaskEntity(
            vehicleId = 1,
            title = "Brake check",
            assignedToUserId = 2,
            isCompleted = true,
            completedAt = System.currentTimeMillis()
        )

        assertTrue(task.isCompleted)
        assertNotNull(task.completedAt)
    }

    @Test
    fun `task copy updates completion status correctly`() {
        val original = TaskEntity(
            id = 1,
            vehicleId = 1,
            title = "Tyre rotation",
            assignedToUserId = 2
        )

        val completed = original.copy(
            isCompleted = true,
            notes = "All four tyres rotated",
            completedAt = System.currentTimeMillis()
        )

        assertFalse(original.isCompleted)
        assertTrue(completed.isCompleted)
        assertEquals("All four tyres rotated", completed.notes)
        assertNotNull(completed.completedAt)
    }

    @Test
    fun `task notes are empty by default`() {
        val task = TaskEntity(
            vehicleId = 1,
            title = "Filter replacement",
            assignedToUserId = 2
        )
        assertEquals("", task.notes)
    }

    @Test
    fun `task completedAt is null when not completed`() {
        val task = TaskEntity(
            vehicleId = 1,
            title = "Coolant check",
            assignedToUserId = 2,
            isCompleted = false
        )
        assertNull(task.completedAt)
    }
}