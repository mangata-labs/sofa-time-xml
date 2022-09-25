package com.mangata.sofatimexml.presentation.tvShowDetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.mangata.core.extensions.round
import com.mangata.core_ui.util.SpacingItemDecoration
import com.mangata.core_ui.util.UiEvent
import com.mangata.sofatimexml.R
import com.mangata.core_ui.R as CoreUI
import com.mangata.sofatimexml.adapters.NetworkAdapter
import com.mangata.sofatimexml.adapters.PostersAdapter
import com.mangata.sofatimexml.adapters.SimilarTvShowAdapter
import com.mangata.sofatimexml.databinding.FragmentTvShowDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvShowDetailFragment : Fragment(R.layout.fragment_tv_show_detail) {

    private val args: TvShowDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentTvShowDetailBinding
    private val viewModel: TvShowDetailViewModel by viewModel(parameters = { parametersOf(args.tvShowId) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerViewSimilarTvShows.addItemDecoration(
            SpacingItemDecoration(
                context = requireContext(),
                space = 12,
                addEndSpacing = true
            )
        )
        binding.recyclerViewNetwork.addItemDecoration(
            SpacingItemDecoration(
                context = requireContext(),
                space = 8,
                addEndSpacing = true
            )
        )
        binding.recyclerViewPosterSection.addItemDecoration(
            SpacingItemDecoration(
                context = requireContext(),
                space = 12,
                addEndSpacing = true
            )
        )

        binding.imageButtonAddToWatchilst.setOnClickListener {
            if (viewModel.isAddedToWatchList.value) {
                viewModel.onEvent(TvShowDetailEvent.RemoveFromWatchlist)
            } else {
                viewModel.onEvent(TvShowDetailEvent.AddedToWatchList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { handleTvShowHeaderSectionDisplay() }
                launch { handleLoadingContent() }
                launch { handleNetworkDisplay() }
                launch { handlePosterSectionDisplay() }
                launch { handleStorySectionDisplay() }
                launch { handleSimilarTvShowDisplay() }
                launch { handleWatchlistToggle() }
                launch { handleSnackbarEvent() }
            }
        }
    }

    private suspend fun handleWatchlistToggle() {
        viewModel.isAddedToWatchList.collect { isAdded ->
            if(isAdded) {
                binding.imageButtonAddToWatchilst.setImageResource(CoreUI.drawable.ic_circle_filled_add)
                binding.imageButtonAddToWatchilst.setColorFilter(MaterialColors.getColor(requireContext(), androidx.appcompat.R.attr.colorPrimary, Color.BLACK))
            } else {
                binding.imageButtonAddToWatchilst.setImageResource(CoreUI.drawable.ic_circle_outlined_add)
                binding.imageButtonAddToWatchilst.setColorFilter(ContextCompat.getColor(requireContext(), CoreUI.color.black))
            }
        }
    }

    private suspend fun handleStorySectionDisplay() {
        viewModel.story.collect {
            if (it.isEmpty()) {
                binding.txtStoryDescription.visibility = View.GONE
                binding.txtStoryTitle.visibility = View.GONE
            } else {
                binding.txtStoryDescription.visibility = View.VISIBLE
                binding.txtStoryTitle.visibility = View.VISIBLE
                binding.txtStoryDescription.text = it
            }
        }
    }

    private suspend fun handleTvShowHeaderSectionDisplay() {
        viewModel.headerState
            .collectLatest { tvShowDetails ->
                binding.tvShowTitle.text = tvShowDetails?.title
                binding.txtTvShowYear.text = tvShowDetails?.displayDate()
                binding.txtTvShowSeason.text = " ● ${tvShowDetails?.numberOfSeasons} Seasons"
                binding.txtTvShowGenres.text = tvShowDetails?.displayGenres()
                binding.txtTvShowRuntime.text =
                    if (tvShowDetails?.runTime != null) "${tvShowDetails.runTime} min" else " - "
                binding.txtTvShowRating.text = tvShowDetails?.score?.round(1)
                binding.tvShowImage.load(tvShowDetails?.imagePath)
            }
    }

    private suspend fun handleSimilarTvShowDisplay() {
        viewModel.similarTvShowState.collectLatest { similarTvShows ->
            val similarTvShowAdapter =
                SimilarTvShowAdapter(similarTvShows) { tvShowId -> openTvShowDetail(tvShowId) }
            binding.recyclerViewSimilarTvShows.apply {
                adapter = similarTvShowAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    private suspend fun handlePosterSectionDisplay() {
        viewModel.posterSectionState.collect { posterSection ->
            val postersAdapter = PostersAdapter(posterSection) { url -> openTrailerInWebView(url) }
            binding.recyclerViewPosterSection.apply {
                adapter = postersAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }
        }
    }

    private suspend fun handleNetworkDisplay() {
        viewModel.networks
            .collect { networks ->
                if (networks.isEmpty()) {
                    binding.recyclerViewNetwork.visibility = View.GONE
                } else {
                    binding.recyclerViewNetwork.visibility = View.VISIBLE
                    val networkAdapter = NetworkAdapter(networks)
                    binding.recyclerViewNetwork.apply {
                        adapter = networkAdapter
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        setHasFixedSize(true)
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
                            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
                        )
                        .setListener(null)
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun handleSnackbarEvent() {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SnackbarEvent -> {
                    Snackbar.make(binding.root, event.uiText, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun openTvShowDetail(tvShowId: Int) {
        val action = TvShowDetailFragmentDirections.actionTvShowDetailFragmentSelf(tvShowId)
        findNavController().navigate(action)
    }

    private fun openTrailerInWebView(url: String) {
        val action = TvShowDetailFragmentDirections.actionTvShowDetailFragmentToWebViewFragment(url)
        findNavController().navigate(action)
    }
}
