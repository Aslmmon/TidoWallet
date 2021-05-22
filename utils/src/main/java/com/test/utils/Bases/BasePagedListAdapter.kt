package com.test.utils.Bases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.floriaapp.core.domain.models.products.ProductItem


//abstract class BasePagedListAdapter<T> : PagedListAdapter<T, BasePagedListAdapter.MyViewHolder>(USER_COMPARATOR) {
//
//    abstract fun getView(view: View?): View?
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        init {
//            itemview = itemView
//            getView(itemView)
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context)
//                .inflate(layoutNeeded(), parent, false)
//        return MyViewHolder(view)
//    }
//
//    abstract fun layoutNeeded(): Int
//
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val data = getItem(position)
//        holder.bind(it, click!!)
//    }
//
//    abstract fun <T : View?> bind(id: Int): T {
//        return itemview.findViewById(id)
//    }
//
//    companion object {
//        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<ProductItem>() {
//            override fun areItemsTheSame(oldItem: T, newItem: ProductItem): Boolean = oldItem.id == newItem.id
//            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean = newItem == oldItem
//        }
//    }
//}