package com.mangata.sofatimexml.presentation.tvShowTracked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mangata.core_ui.util.GridSpacingItemDecoration
import com.mangata.core_ui.util.SpacingItemDecoration
import com.mangata.sofatimexml.R
import com.mangata.sofatimexml.adapters.TrackedTvShowAdapter
import com.mangata.sofatimexml.databinding.FragmentTvShowTrackedBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowTrackedFragment : Fragment(R.layout.fragment_tv_show_tracked) {

    private lateinit var binding: FragmentTvShowTrackedBinding
    private val viewModel: TvShowTrackedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowTrackedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getTrackedTvShow()

        binding.recyclerViewTrackedTvShowList.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                space = 8,
                context = requireContext()
            )
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { handleTrackedTvShowDisplay() }
            }
        }
    }

    private suspend fun handleTrackedTvShowDisplay() {
        viewModel.state
            .filter { !it.isLoading }
            .collect { state ->
                if (state.tvShows.isEmpty()) {
                    binding.recyclerViewTrackedTvShowList.visibility = View.GONE
                    binding.emptyListMessage.root.visibility = View.VISIBLE
                } else {
                    binding.recyclerViewTrackedTvShowList.visibility = View.VISIBLE
                    binding.emptyListMessage.root.visibility = View.GONE
                    val trackedTvShowAdapter =
                        TrackedTvShowAdapter(state.tvShows) { tvShowId -> openTvShowDetail(tvShowId) }
                    binding.recyclerViewTrackedTvShowList.apply {
                        adapter = trackedTvShowAdapter
                        layoutManager = GridLayoutManager(requireContext(), 2)
                    }
                }
            }
    }

    private fun openTvShowDetail(tvShowId: Int) {
        val action =
            TvShowTrackedFragmentDirections.actionTvShowTrackedFragmentToTvShowDetailFragment(
                tvShowId
            )
        findNavController().navigate(action)
    }
}