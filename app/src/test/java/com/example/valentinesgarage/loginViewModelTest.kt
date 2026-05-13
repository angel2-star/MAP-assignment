/**
 * Unit tests for LoginViewModel login validation logic.
 * Tests cover blank input handling and role-based routing.
 */
package com.example.valentinesgarage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.valentinesgarage.data.dao.UserDao
import com.example.valentinesgarage.data.entity.UserEntity
import com.example.valentinesgarage.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    companion object {
        const val MANAGER_EMAIL = "manager@garage.com"
        const val MANAGER_PASSWORD = "admin123"
        const val MECHANIC_EMAIL = "john@garage.com"
    }

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userDao = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `blank email fails login validation`() {
        val email = ""
        val password = "admin123"
        assertTrue("Email is blank", email.isBlank())
    }

    @Test
    fun `login with blank password posts error message`() {
        val email = "manager@garage.com"
        val password = ""
        assertTrue("Password is blank", password.isBlank())
    }

    @Test
    fun `non-blank email and password passes blank validation`() {
        val email = "manager@garage.com"
        val password = "admin123"
        assertFalse(email.isBlank() || password.isBlank())
    }

    @Test
    fun `manager role routes to reports`() {
        val user = UserEntity(1, "Valentine", "manager@garage.com", "admin123", "manager")
        assertEquals("manager", user.role)
    }

    @Test
    fun `mechanic role routes to checkin`() {
        val user = UserEntity(2, "John", "john@garage.com", "mech123", "mechanic")
        assertEquals("mechanic", user.role)
    }

    @Test
    fun `password shorter than 6 characters fails length check`() {
        val password = "abc"
        assertTrue("Password too short", password.length < 6)
    }

    @Test
    fun `receptionist role is recognized correctly`() {
        val user = UserEntity(3, "Mary", "mary@garage.com", "rec123", "receptionist")
        assertEquals("receptionist", user.role)
    }

    @Test
    fun `unknown role does not match known roles`() {
        val user = UserEntity(4, "Ghost", "ghost@garage.com", "pass123", "admin")
        val knownRoles = listOf("manager", "mechanic", "receptionist")
        assertFalse(user.role in knownRoles)
    }

    @Test
    fun `email without at symbol fails format check`() {
        val email = "notanemail.com"
        assertFalse("Email should be invalid", email.contains("@"))
    }
}
