package com.dicoding.tourismapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tourismapp.core.ui.TourismAdapter
import com.dicoding.tourismapp.feature.home.databinding.FragmentHomeBinding
import com.dicoding.tourismapp.feature.home.visibleNoInternet
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val tourismAdapter = TourismAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        onItemClickListener()
        setUpRvAdapter()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.tourismData.collectLatest { uiState ->
                    if(uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }

                    uiState.data?.let {
                        tourismAdapter.setData(it)
                    }

                    uiState.message?.let {
                        Snackbar.make(
                            requireView(),
                            uiState.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        homeViewModel.messageShowed()
                    }

                    if(uiState.visibleNoInternet) {
                        binding.viewNointernet.root.visibility = View.VISIBLE
                    } else {
                        binding.viewNointernet.root.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun onItemClickListener() {
        tourismAdapter.onItemClick = { selectedData ->
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.dicoding.tourismapp/detail_screen/${selectedData.tourismId}".toUri())
                .build()
            findNavController().navigate(request)
        }

        binding.viewNointernet.btnTryAgain.setOnClickListener {
            homeViewModel.getTourismData()
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
        //Toast.makeText(requireActivity(), "Destroyed View", Toast.LENGTH_SHORT).show()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
