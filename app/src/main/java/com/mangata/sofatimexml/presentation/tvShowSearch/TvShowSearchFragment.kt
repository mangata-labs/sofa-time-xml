package com.mangata.sofatimexml.presentation.tvShowSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mangata.core_ui.util.hideKeyboard
import com.mangata.sofatimexml.R
import com.mangata.core_ui.R as CoreUI
import com.mangata.sofatimexml.adapters.TvShowAdapter
import com.mangata.sofatimexml.databinding.FragmentTvShowSearchBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowSearchFragment : Fragment(R.layout.fragment_tv_show_search) {

    private lateinit var binding: FragmentTvShowSearchBinding
    private val viewModel: TvShowSearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowAdapter = TvShowAdapter { tvShowId -> openTvShowDetail(tvShowId) }
        binding.recyclerViewTvShowList.apply {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        binding.recyclerViewTvShowList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (position + 1 == viewModel.tvShowsState.value.tvShows.size) {
                    viewModel.loadNextTvShows()
                }
            }
        })

        binding.editTxtLayout.apply {
            endIconDrawable = null
            startIconDrawable =
                ResourcesCompat.getDrawable(
                    resources,
                    CoreUI.drawable.ic_search,
                    resources.newTheme()
                )
        }

        binding.editTxtLayout.setEndIconOnClickListener {
            viewModel.searchState.value = ""
            binding.editTxtSearch.text.clear()
        }

        binding.editTxtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(requireActivity())
            }
            false
        }

        binding.editTxtSearch.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (it.isNotEmpty()) {
                    binding.editTxtLayout.endIconDrawable =
                        ResourcesCompat.getDrawable(
                            resources,
                            CoreUI.drawable.ic_cancel,
                            resources.newTheme()
                        )
                    viewModel.searchState.value = it.toString()
                } else {
                    binding.editTxtLayout.endIconDrawable = null
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    handleSearch()
                }
                launch {
                    handleTvShowDisplay(tvShowAdapter)
                }
            }
        }
    }

    private suspend fun handleTvShowDisplay(tvShowAdapter: TvShowAdapter) {
        viewModel.tvShowsState
            .filter { !it.isLoading }
            .collectLatest {
                if (it.tvShows.isEmpty()) {
                    binding.emptyListMessage.root.visibility = View.VISIBLE
                    binding.recyclerViewTvShowList.visibility = View.GONE
                } else {
                    binding.emptyListMessage.root.visibility = View.GONE
                    binding.recyclerViewTvShowList.visibility = View.VISIBLE
                }
                tvShowAdapter.submitList(it.tvShows)
            }
    }

    private fun openTvShowDetail(tvShowId: Int) {
        val action =
            TvShowSearchFragmentDirections.actionTvShowSearchFragmentToTvShowDetailFragment(tvShowId)
        findNavController().navigate(action)
    }

    @OptIn(FlowPreview::class)
    private suspend fun handleSearch() {
        viewModel.searchState
            .drop(1)
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest {
                viewModel.onEvent(TvShowSearchEvent.FinishedSearch)
            }
    }
}