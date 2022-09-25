package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.core.extensions.round
import com.mangata.core_ui.R
import com.mangata.sofatimexml.databinding.ItemTrendingTvShowBinding
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow

class TrendingTvShowAdapter(
    private val tvShowList: List<TvShow>,
    private val clickListener: (TvShow) -> Unit
) :
    RecyclerView.Adapter<TrendingTvShowAdapter.TrendingTvShowViewHolder>() {

    inner class TrendingTvShowViewHolder(private val binding: ItemTrendingTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow, clickListener: (TvShow) -> Unit) {
            binding.apply {
                textViewTvShowTitle.text = tvShow.name
                viewItemRating.txtTvShowRating.text = tvShow.voteAverage.round(1)
                imageViewTrendingTvShow.load(tvShow.backdropPath) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }
                itemView.setOnClickListener { clickListener(tvShow) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingTvShowViewHolder {
        val binding =
            ItemTrendingTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrendingTvShowViewHolder,
        position: Int
    ) {
        holder.bind(tvShowList[position], clickListener)
    }

    override fun getItemCount(): Int = tvShowList.size
}