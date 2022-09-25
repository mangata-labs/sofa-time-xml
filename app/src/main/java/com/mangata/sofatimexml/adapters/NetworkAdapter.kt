package com.mangata.sofatimexml.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mangata.sofatimexml.databinding.ItemTvShowNetworkBinding
import com.mangata.tvshow_domain.model.tvShowDetail.Network

class NetworkAdapter(
    private val networks: List<Network>,
) : RecyclerView.Adapter<NetworkAdapter.NetworkViewHolder>() {

    inner class NetworkViewHolder(private val binding: ItemTvShowNetworkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(network: Network) {
            binding.apply {
                imageViewTvShowNetwork.load(network.logo_path)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NetworkViewHolder {
        val binding =
            ItemTvShowNetworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NetworkViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NetworkViewHolder,
        position: Int
    ) {
        holder.bind(networks[position])
    }

    override fun getItemCount(): Int = networks.size
}