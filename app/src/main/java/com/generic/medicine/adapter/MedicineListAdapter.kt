package com.generic.medicine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.generic.medicine.R
import com.generic.medicine.com.generic.medicine.utils.Utils
import com.generic.medicine.model.Medicine

class MedicineListAdapter(private val context: Context,  private val medicineList: ArrayList<Medicine>): RecyclerView.Adapter<MedicineListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.medicine_list_view, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val medicine:Medicine = medicineList[position]

        medicine.images?.let { if (it.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(Utils.getBitmapFromAssets(context, it[0]))
                .apply(RequestOptions().override(55,55))
                .into(holder.medicineImage)
        }}

        holder.medicineName.text = medicine.name
        holder.medicineDetails.text = medicine.description

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(medicine) }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Medicine)
    }

    inner class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var medicineName: TextView = itemView.findViewById(R.id.medicine_name)
        var medicineDetails: TextView = itemView.findViewById(R.id.medicine_detail)
        var medicineImage: ImageView = itemView.findViewById(R.id.medicine_image)
    }
}
