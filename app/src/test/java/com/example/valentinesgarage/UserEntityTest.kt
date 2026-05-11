package com.example.valentinesgarage

import com.example.valentinesgarage.data.entity.UserEntity
import org.junit.Assert.*
import org.junit.Test

class UserEntityTest {

    @Test
    fun `user entity creates correctly with all fields`() {
        val user = UserEntity(
            id = 1,
            name = "Valentine",
            email = "manager@garage.com",
            password = "admin123",
            role = "manager"
        )

        assertEquals(1, user.id)
        assertEquals("Valentine", user.name)
        assertEquals("manager@garage.com", user.email)
        assertEquals("admin123", user.password)
        assertEquals("manager", user.role)
    }

    @Test
    fun `user role is manager returns true for manager`() {
        val user = UserEntity(
            name = "Valentine",
            email = "manager@garage.com",
            password = "admin123",
            role = "manager"
        )
        assertEquals("manager", user.role)
    }

    @Test
    fun `user role is mechanic returns true for mechanic`() {
        val user = UserEntity(
            name = "John",
            email = "john@garage.com",
            password = "mech123",
            role = "mechanic"
        )
        assertEquals("mechanic", user.role)
    }

    @Test
    fun `two users with same data are equal`() {
        val user1 = UserEntity(1, "John", "john@garage.com", "mech123", "mechanic")
        val user2 = UserEntity(1, "John", "john@garage.com", "mech123", "mechanic")
        assertEquals(user1, user2)
    }

    @Test
    fun `two users with different emails are not equal`() {
        val user1 = UserEntity(1, "John", "john@garage.com", "mech123", "mechanic")
        val user2 = UserEntity(2, "Sarah", "sarah@garage.com", "mech123", "mechanic")
        assertNotEquals(user1, user2)
    }
}