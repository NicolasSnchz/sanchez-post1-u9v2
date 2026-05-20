package com.nicolassnchz.carttddu9.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolassnchz.carttddu9.domain.analytics.AnalyticsService
import com.nicolassnchz.carttddu9.domain.model.CartItem
import com.nicolassnchz.carttddu9.domain.repository.CartRepository
import java.io.IOException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            _uiState.value = CartUiState.Loading

            val result = runCatching {
                repository.getItems()
            }

            _uiState.value = result.fold(
                onSuccess = { items ->
                    analytics.track("cart_loaded")
                    CartUiState.Success(
                        items = items,
                        total = calculateTotal(items)
                    )
                },
                onFailure = { error ->
                    analytics.track("cart_error")
                    CartUiState.Error(errorMessage(error))
                }
            )
        }
    }

    internal fun calculateTotal(items: List<CartItem>): Double {
        return items.sumOf { item -> item.price * item.qty }
    }

    private fun errorMessage(error: Throwable): String {
        return when (error) {
            is IOException -> "Sin conexión. Verificar red."
            else -> error.message ?: "Error inesperado"
        }
    }
}
