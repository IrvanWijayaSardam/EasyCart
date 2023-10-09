package com.aminivan.easycart

import androidx.recyclerview.widget.DiffUtil


class CartDiffCallback(private val mOldCartList: List<Cart?>, private val mNewCartList: List<Cart?>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldCartList.size
    }

    override fun getNewListSize(): Int {
        return mNewCartList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldCartList[oldItemPosition]!!.id == mNewCartList[newItemPosition]!!.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = mOldCartList[oldItemPosition]
        val newMovie = mNewCartList[newItemPosition]
        return oldMovie!!.id == newMovie!!.id && oldMovie!!.idbarang == newMovie!!.idbarang
    }
}