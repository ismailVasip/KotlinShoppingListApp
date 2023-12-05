package com.example.kotlinshoppingapp.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinshoppingapp.Model.ShoppingList
import com.example.kotlinshoppingapp.Views.DetailsActivity
import com.example.kotlinshoppingapp.databinding.RowItemBinding


class AdapterMainAct(private var list: MutableList<ShoppingList>) :RecyclerView.Adapter<AdapterMainAct.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = list[position]
        holder.binding.tvTittleRowItem.text = item.tittle
        holder.binding.tvDescriptionRowItem.text = item.description
        holder.binding.ivUpdateButtonRowItem.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("selectedList", item)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            holder.itemView.context.startActivity(intent)
        }


    }

    class VH(val binding: RowItemBinding):RecyclerView.ViewHolder(binding.root){}
}