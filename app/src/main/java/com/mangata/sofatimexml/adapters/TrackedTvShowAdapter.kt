package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.core_ui.R
import com.mangata.sofatimexml.databinding.ItemTvShowTrackedBinding
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow

class TrackedTvShowAdapter(
    private val tvShowList: List<TvShow>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<TrackedTvShowAdapter.TrackedTvShowViewHolder>() {

    inner class TrackedTvShowViewHolder(private val binding: ItemTvShowTrackedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow, clickListener: (Int) -> Unit) {
            binding.apply {
                imageViewTvShowPoster.load(tvShow.posterPath) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }
                itemView.setOnClickListener { clickListener(tvShow.id) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackedTvShowViewHolder {
        val binding =
            ItemTvShowTrackedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackedTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrackedTvShowViewHolder,
        position: Int
    ) {
        holder.bind(tvShowList[position], clickListener)
    }

    override fun getItemCount(): Int = tvShowList.size
}