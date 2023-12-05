package com.example.kotlinshoppingapp.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinshoppingapp.databinding.RowListBinding

class AdapterAddAct(private var list:MutableList<String>):RecyclerView.Adapter<AdapterAddAct.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RowListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item =list[position]
        holder.binding.tvItemNameRowList.text = item
        holder.binding.ivDeleteButtonRowList.setOnClickListener {
            list.removeAt(position)
            refreshData(list)
        }
    }

    fun refreshData(newList :MutableList<String>){
        list = newList
        notifyDataSetChanged()
    }

    class VH(val binding: RowListBinding) :RecyclerView.ViewHolder(binding.root){}
}