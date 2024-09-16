package com.dicoding.tourismapp.feature.favorite

import com.dicoding.tourismapp.core.domain.model.Tourism

data class FavoriteUiState(
    val data: List<Tourism>? = null
)
