package com.dicoding.tourismapp.feature.detail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.tourismapp.feature.detail.databinding.FragmentDetailTourismBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class DetailTourismFragment : Fragment() {
    private val detailTourismViewModel: DetailTourismViewModel by viewModels()
    private var _binding: FragmentDetailTourismBinding? = null
    private val binding get() = _binding!!

    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTourismBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("dataTourismId")?.let {
            detailTourismViewModel.getTourismById(it)
//            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        observer()
        handleTopAppBar()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailTourismViewModel.detailTourism.collectLatest { data ->
                    data?.let {
                        binding.content.tvDetailDescription.text = it.description

                        binding.topAppBar.setTitle(it.name)

                        Glide.with(requireActivity())
                            .load(it.image)
                            .into(binding.ivTourism)

                        isFavorite = data.isFavorite
                        updateFavoriteIcon(isFavorite)
                    }
                }
            }
        }
    }

    private fun handleTopAppBar() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    if(!isFavorite) addToFavoriteTourism(true) else addToFavoriteTourism(false)
                    true
                }
                else -> false
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val icon = if(isFavorite) R.drawable.ic_favorite_white else R.drawable.ic_not_favorite
        binding.topAppBar.menu.findItem(R.id.favorite).setIcon(icon)
    }

    private fun addToFavoriteTourism(newState: Boolean) {
        detailTourismViewModel.setFavoriteTourism(newState)
        isFavorite = newState
        val message = if(newState) {
            getString(R.string.successfully_added_to_favorite)
        } else {
            getString(R.string.successfully_removed_from_favorite)
        }
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}