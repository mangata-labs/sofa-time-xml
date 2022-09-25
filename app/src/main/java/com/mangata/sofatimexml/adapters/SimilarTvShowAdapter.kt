package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.core_ui.R
import com.mangata.sofatimexml.databinding.ItemTvShowSimilarBinding
import com.mangata.tvshow_domain.model.tvShowGeneral.TvShow

class SimilarTvShowAdapter(
    private val tvShowList: List<TvShow>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<SimilarTvShowAdapter.SimilarTvShowViewHolder>() {

    inner class SimilarTvShowViewHolder(private val binding: ItemTvShowSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow, clickListener: (Int) -> Unit) {
            binding.apply {
                imageViewTvShowPoster.load(tvShow.posterPath) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }
                txtTvShowTitle.text = tvShow.name
                itemView.setOnClickListener { clickListener(tvShow.id) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarTvShowViewHolder {
        val binding =
            ItemTvShowSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarTvShowViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SimilarTvShowViewHolder,
        position: Int
    ) {
        holder.bind(tvShowList[position], clickListener)
    }

    override fun getItemCount(): Int = tvShowList.size
}