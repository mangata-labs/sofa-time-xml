package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.core_ui.R
import com.mangata.sofatimexml.databinding.ItemTrailerPlaceholderBinding
import com.mangata.sofatimexml.databinding.ItemTvShowPosterBinding
import com.mangata.sofatimexml.presentation.tvShowDetail.state.PosterSectionState
import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.video.Video

class PostersAdapter(
    private val posterSectionState: PosterSectionState,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_TRAILER = 0
    private val TYPE_POSTERS = 1

    inner class PostersViewHolder(private val binding: ItemTvShowPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: Poster) {
            binding.apply {
                imageViewTvShowPoster.load(poster.filePath) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }
            }
        }
    }

    inner class TrailerViewHolder(private val binding: ItemTrailerPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trailer: Video?) {
            val url = trailer?.createUrl() ?: return
            itemView.setOnClickListener { clickListener(url) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == TYPE_TRAILER && posterSectionState.trailer != null) {
            val binding = ItemTrailerPlaceholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TrailerViewHolder(binding)
        } else {
            val binding =
                ItemTvShowPosterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return PostersViewHolder(binding)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is TrailerViewHolder) {
            holder.bind(posterSectionState.trailer)
        } else if (holder is PostersViewHolder) {
            holder.bind(posterSectionState.posters[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionTrailer(position)) TYPE_TRAILER
        else TYPE_POSTERS
    }

    override fun getItemCount(): Int {
        return posterSectionState.posters.size
    }

    private fun isPositionTrailer(position: Int): Boolean {
        return position == 0
    }
}