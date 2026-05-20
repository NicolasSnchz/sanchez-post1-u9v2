package com.nicolassnchz.carttddu9.ui.cart

import app.cash.turbine.test
import com.nicolassnchz.carttddu9.domain.analytics.AnalyticsService
import com.nicolassnchz.carttddu9.domain.model.CartItem
import com.nicolassnchz.carttddu9.domain.repository.CartRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class CartViewModelTest {

    @MockK
    lateinit var repository: CartRepository

    @MockK(relaxed = true)
    lateinit var analyticsService: AnalyticsService

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel(repository, analyticsService)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCart emits Success state with items and total`() = runTest {
        val items = listOf(
            CartItem(id = "1", name = "Libro", price = 25.0, qty = 2),
            CartItem(id = "2", name = "Pen", price = 5.0, qty = 1)
        )

        coEvery { repository.getItems() } returns items

        viewModel.loadCart()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CartUiState.Success)
        state as CartUiState.Success
        assertEquals(2, state.items.size)
        assertEquals(55.0, state.total, 0.001)
    }

    @Test
    fun `loadCart emits Error when repository throws IOException`() = runTest {
        coEvery { repository.getItems() } throws IOException("sin red")

        viewModel.loadCart()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CartUiState.Error)
        state as CartUiState.Error
        assertEquals("Sin conexión. Verificar red.", state.message)
    }

    @Test
    fun `loadCart emits Loading before Success`() = runTest {
        coEvery { repository.getItems() } returns emptyList()

        viewModel.uiState.test {
            assertEquals(CartUiState.Loading, awaitItem())

            viewModel.loadCart()
            advanceUntilIdle()

            assertTrue(awaitItem() is CartUiState.Success)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `calculateTotal returns zero when cart is empty`() {
        val total = viewModel.calculateTotal(emptyList())

        assertEquals(0.0, total, 0.001)
    }
}
