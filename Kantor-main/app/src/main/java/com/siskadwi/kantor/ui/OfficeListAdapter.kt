package com.siskadwi.kantor.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siskadwi.kantor.R
import com.siskadwi.kantor.model.Office

class OfficeListAdapter(
    private val onItemClickListener: (Office) -> Unit
): ListAdapter<Office, OfficeListAdapter.OfficeViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeViewHolder{
        return OfficeViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: OfficeViewHolder,position: Int){
        val office = getItem(position)
        holder.bind(office)
        holder.itemView.setOnClickListener{
            onItemClickListener(office)
        }
    }
    class OfficeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val nameTextView : TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView : TextView = itemView.findViewById(R.id.addressTextView)
        private val kategoriTextView : TextView = itemView.findViewById(R.id.kategoriTextView)
        fun bind(office: Office?) {
            nameTextView.text = office?.name
            addressTextView.text = office?.address
            kategoriTextView.text = office?.category

        }

        companion object{
            fun create(parent: ViewGroup): OfficeListAdapter.OfficeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_office, parent, false)
                return OfficeViewHolder(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Office>(){
            override fun areItemsTheSame(oldItem: Office, newItem: Office): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Office, newItem: Office): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
private fun OfficeListAdapter.OfficeViewHolder.bind(office: Office?) {

}