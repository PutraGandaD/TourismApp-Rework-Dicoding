package com.dicoding.tourismapp.feature.home

import com.dicoding.tourismapp.core.domain.model.Tourism

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<Tourism>? = null,
    val message: String? = null
)
