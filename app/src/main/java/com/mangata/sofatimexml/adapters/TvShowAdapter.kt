package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.sofatimexml.R
import com.mangata.sofatimexml.databinding.ItemTvShowBinding
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow

class TvShowAdapter(
    private val clickListener: (Int) -> Unit
) : ListAdapter<TvShow, TvShowAdapter.TvShowViewHolder>(DiffCallBack()) {

    inner class TvShowViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow, clickListener: (Int) -> Unit) {
            binding.apply {
                textViewTvShowTitle.text = tvShow.name
                imageViewTvShow.load(tvShow.backdropPath) {
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
    ): TvShowViewHolder {
        val binding =
            ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TvShowViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)
        holder.bind(currentItem, clickListener)
    }

    class DiffCallBack : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
            oldItem == newItem
    }
}