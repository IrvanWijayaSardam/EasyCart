package com.aminivan.easycart

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminivan.easycart.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list = listOf<Cart>(
            Cart(1,"2","Kopi Susu",1,10000,20000,""),
            Cart(2,"3","Kopi Hitam",2,10000,20000,""),
            Cart(3,"4","Kopi Putih",4,10000,20000,""),
        )


        var adapterCart = CartAdapter(object: CartAdapter.CartItemClickListener{
            override fun onItemIncrease(cart: Cart) {

            }

            override fun onItemDecrease(cart: Cart) {

            }

            override fun onItemDelete(cart: Cart) {
                Toast.makeText(this@MainActivity, "${cart.name} Dihapus dari Cart", Toast.LENGTH_SHORT).show()

            }
        })

        list.forEach { cart ->
            adapterCart.addNotesQty()
        }

        adapterCart.setListCart(list)
        binding.rvCart.adapter = adapterCart
        binding.rvCart.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        binding.btnSubmit.setOnClickListener {
            Log.d(TAG, "onCreate: listQty ${adapterCart.getProductQty()}")
        }

    }
}