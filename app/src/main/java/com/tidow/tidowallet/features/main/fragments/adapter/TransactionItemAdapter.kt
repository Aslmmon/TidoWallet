package com.tidow.tidowallet.features.main.fragments.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tidow.tidowallet.R
import com.tidow.tidowallet.features.main.fragments.QrCodeFragment
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class TransactionItemAdapter(private val onItemClickOfProduct: OnItemClickOfProduct? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QrCodeFragment.TransactionItem>() {

        override fun areItemsTheSame(
            oldItem: QrCodeFragment.TransactionItem,
            newItem: QrCodeFragment.TransactionItem
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: QrCodeFragment.TransactionItem,
            newItem: QrCodeFragment.TransactionItem
        ) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubjectViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.transaction_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubjectViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<QrCodeFragment.TransactionItem>) {
        differ.submitList(list)
    }

    class SubjectViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: QrCodeFragment.TransactionItem) = with(itemView) {
            findViewById<TextView>(R.id.tv_cost).text =
                data.pricePaid.toString() + " " + resources.getString(R.string.egp)
            findViewById<TextView>(R.id.tv_store_name).text = data.storeName.toString()
            if (data.date?.time != null) findViewById<TextView>(R.id.tv_date).text = SimpleDateFormat("yyyy.MM.dd  HH:mm").format(data.date?.time)


        }
    }


    interface OnItemClickOfProduct {
        fun onItemClicked(position: Int, item: QrCodeFragment.TransactionItem)
    }
}