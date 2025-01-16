package com.dicoding.tourismapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tourismapp.core.ui.TourismAdapter
import com.dicoding.tourismapp.feature.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val tourismAdapter = TourismAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListener()
        setUpRvAdapter()
        observer()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.favoriteTourismData.collectLatest { uiState ->
                    if(!uiState.data.isNullOrEmpty()) {
                        tourismAdapter.setData(uiState.data)
                        binding.viewEmpty.root.visibility = View.GONE
                    } else {
                        tourismAdapter.setData(emptyList())
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun onClickListener() {
        tourismAdapter.onItemClick = { selectedData ->
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.dicoding.tourismapp/detail_screen/${selectedData.tourismId}".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

    private fun setUpRvAdapter() {
        with(binding.rvTourism) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tourismAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
