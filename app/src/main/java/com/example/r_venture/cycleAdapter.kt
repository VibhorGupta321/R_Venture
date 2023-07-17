package com.example.r_venture
//Rventure
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class cycleAdapter(private val cycleList: ArrayList<cycle_data>) :
    RecyclerView.Adapter<cycleAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cycle_data, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentcycle = cycleList[position]
        holder.companyName.text = currentcycle.company
        holder.location.text = currentcycle.pickupaddress
        holder.rate.text = currentcycle.price
    }

    override fun getItemCount(): Int {
        return cycleList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val companyName : TextView = itemView.findViewById(R.id.company_name_text)
        val location : TextView = itemView.findViewById(R.id.location_name_text)
        val rate : TextView = itemView.findViewById(R.id.price_value_text)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}
