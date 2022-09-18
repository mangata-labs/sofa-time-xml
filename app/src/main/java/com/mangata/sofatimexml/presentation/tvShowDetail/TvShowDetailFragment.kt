package com.mangata.sofatimexml.presentation.tvShowDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.mangata.core.extensions.round
import com.mangata.core_ui.util.UiEvent
import com.mangata.sofatimexml.R
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { handleTvShowDetailDisplay() }
                launch { handleLoadingContent() }
                launch { handleSnackbarEvent() }
            }
        }
    }

    private suspend fun handleTvShowDetailDisplay() {
        viewModel.headerState
            .collectLatest { tvShowDetails ->
                binding.tvShowTitle.text = tvShowDetails.title
                binding.txtStoryDescription.text = tvShowDetails.description
                binding.txtTvShowYear.text = tvShowDetails.displayDate()
                binding.txtTvShowSeason.text = " ● ${tvShowDetails.numberOfSeasons} Seasons"
                binding.txtTvShowGenres.text = tvShowDetails.displayGenres()
                binding.txtTvShowRuntime.text = if (tvShowDetails.runTime != null) "${tvShowDetails.runTime} min" else " - "
                binding.txtTvShowRating.text = tvShowDetails.score.round(1)
                binding.tvShowImage.load(tvShowDetails.imagePath)
            }
    }

    private suspend fun handleSimilarTvShowDisplay() {
        viewModel.similarTvShowState.collect { similarTvShows ->
            println("here similarTvSHows: $similarTvShows")
            //configureAdapter(tvShows)
        }
    }

    private suspend fun handleVideoDisplay() {
        viewModel.videoState.collect { trailer ->
            println("here trailer: ${trailer?.title}")
            //configureAdapter(tvShows)
        }
    }

    private suspend fun handlePosterDisplay() {
        viewModel.posterState.collect { posters ->
            println("here posters: $posters")
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
}
