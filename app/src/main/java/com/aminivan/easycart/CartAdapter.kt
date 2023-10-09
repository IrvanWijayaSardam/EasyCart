package com.aminivan.easycart

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aminivan.easycart.databinding.ItemCartBinding

private const val TAG = "CartAdapter"
class CartAdapter(private val listener: CartItemClickListener): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val listCart = ArrayList<Cart?>()
    private var productQty: MutableList<Int> = mutableListOf()

    fun getProductQty(): List<Int> = productQty

    interface CartItemClickListener {
        fun onItemIncrease(cart : Cart)
        fun onItemDecrease(cart : Cart)
        fun onItemDelete(cart : Cart)

    }

    fun setListCart(listCart: List<Cart?>) {
        val diffCallback = CartDiffCallback(this.listCart, listCart)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listCart.clear()
        this.listCart.addAll(listCart)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ItemCartBinding, private val listener: CartItemClickListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart){
            val textWatcher = object : TextWatcher{
                override fun beforeTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(str: Editable?) {
                    str?.toString()?.let {
                        if (it.isNotEmpty()) {
                            val newQty = it.toInt()
                            productQty[adapterPosition] = newQty // Update productQty immediately
                        }
                    }
                }
            }
            with(binding){
                tvProductName.text = cart.name
                tvQty.text = "Qty : " + cart.qty.toString()
                edtCart.setText(cart.qty.toString())
                tvProductPrice.text = "Price : Rp " + cart.price.toString()
                tvSubtotal.text = "Subtotal : Rp "+ cart.subtotal.toString()


                edtCart.addTextChangedListener(textWatcher)
                edtCart.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        var str = binding.edtCart.text.toString()
                        if (str.isEmpty()) {
                            str = "1"
                            binding.edtCart.setText(str)
                        }
                        binding.edtCart.clearFocus()
                        if (str.isNotEmpty()) {
                            productQty[adapterPosition] = str.toInt() // newQty
                        }
                    }
                }

                icAddCart.setOnClickListener {
                    cart.qty++
                    cart.subtotal = cart.qty * cart.price
                    tvQty.text = "Qty : " + cart.qty
                    edtCart.setText(cart.qty.toString())
                    tvSubtotal.text = "Subtotal : Rp "+ (cart.price * cart.qty).toString()
                    listener.onItemIncrease(cart)
                }
                icMinusCart.setOnClickListener {
                    // Decrease the amount and update views if it's greater than 0
                    if (cart.qty > 1) {
                        cart.qty--
                        cart.subtotal = cart.qty * cart.price
                        tvQty.text = "Qty : " + cart.qty
                        edtCart.setText(cart.qty.toString())
                        tvSubtotal.text = "Subtotal : Rp "+ (cart.price * cart.qty).toString()

                        listener.onItemDecrease(cart)
                    } else {
                        listener.onItemDelete(cart)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding,listener)
    }

    fun addNotesQty(){
        productQty.add(1)
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCart[position]!!)
        val qtyEditText: EditText = holder.binding.edtCart
    }
}