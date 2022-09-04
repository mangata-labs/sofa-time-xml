package com.mangata.sofatimexml.presentation.tvShowHome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.mangata.core_ui.util.UiEvent
import com.mangata.sofatimexml.R
import com.mangata.sofatimexml.adapters.TrendingTvShowAdapter
import com.mangata.sofatimexml.databinding.FragmentTvShowHomeBinding
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

interface SearchTvShowListener{
    fun onSearchCLick(didRequestTabChange: Boolean, destinationId: Int)
}

class TvShowHomeFragment : Fragment(R.layout.fragment_tv_show_home) {

    private lateinit var binding: FragmentTvShowHomeBinding
    private val viewModel: TvShowHomeViewModel by viewModel()
    private var searchTvShowListener: SearchTvShowListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SearchTvShowListener) {
            searchTvShowListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        binding.cardViewSearch.setOnClickListener {
            searchTvShowListener?.onSearchCLick(didRequestTabChange = true, destinationId = R.id.tvShowSearchFragment)
        }
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    handleLoadingContent()
                }
                launch {
                    handleHomeFeedDisplay()
                }
                launch {
                    handleSnackbarEvent()
                }
            }
        }
    }

    private suspend fun handleLoadingContent() {
        viewModel.isLoading.collect {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.layoutStackView.visibility = View.INVISIBLE
            } else {
                binding.layoutStackView.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate()
                        .alpha(1f)
                        .setDuration(
                            resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                        )
                        .setListener(null)
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun handleSnackbarEvent() {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    Snackbar.make(binding.root, event.uiText, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private suspend fun handleHomeFeedDisplay() {
        viewModel.tvShowsState.collect { tvShows ->
            configureAdapter(tvShows)
        }
    }

    private fun configureAdapter(tvShows: List<TvShow>) {
        val adapter =
            TrendingTvShowAdapter(tvShows) { tvShow -> openTvShowDetail(tvShowId = tvShow.id) }
        binding.viewPagerTvShowCarousel.adapter = adapter
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPagerTvShowCarousel) { _, _ -> }
        tabLayoutMediator.attach()
    }

    private fun openTvShowDetail(tvShowId: Int) {
        val action =
            TvShowHomeFragmentDirections.actionTvShowHomeFragmentToTvShowDetailFragment(tvShowId)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchTvShowListener = null
    }
}