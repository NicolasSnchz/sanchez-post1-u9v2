package com.nicolassnchz.carttddu9.ui.cart

import androidx.lifecycle.ViewModel
import com.nicolassnchz.carttddu9.domain.analytics.AnalyticsService
import com.nicolassnchz.carttddu9.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        // RED: implementación incompleta a propósito.
        // Los tests deben fallar en este commit.
    }
}
