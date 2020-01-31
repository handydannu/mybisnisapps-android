package com.wardwar.mybisnisindoneisa.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso
import com.wardwar.mybisnisindoneisa.KonfirmasiActivity

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.Order
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductEpeaperFragment : Fragment() {
    internal val KONFIRMASI_RESULT:Int = 1223


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_epeaper, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = view.findViewById(R.id.btn_back) as Button


        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val context = context!!.applicationContext

        val preferencesHelper = PreferencesHelper(context)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 14


        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        val orderBasic = view.findViewById(R.id.order_product_basic) as ImageButton
        val orderSilver = view.findViewById(R.id.order_product_silver) as ImageButton
        val orderGold = view.findViewById(R.id.order_product_gold) as ImageButton

        orderBasic.setOnClickListener {
            orderDialog(idProduct)
        }

        orderSilver.setOnClickListener {
            orderDialog(idProduct)
        }

        orderGold.setOnClickListener {
            orderDialog(idProduct)
        }

        val imageBasic = view.findViewById(R.id.image_basic) as ImageView
        Picasso.get().load("https://image.ibb.co/k92zwd/basic.png").into(imageBasic)

        val imageSilver = view.findViewById(R.id.image_silver) as ImageView
        Picasso.get().load("https://image.ibb.co/mWW3NJ/45_rp_600_000.png").into(imageSilver)

        val imageGold = view.findViewById(R.id.image_gold) as ImageView
        Picasso.get().load("https://image.ibb.co/eTz19y/75_rp_1_000_000.png").into(imageGold)



    }

    fun orderDialog(idProduct: Int){
        val intent = Intent(context!!, KonfirmasiActivity::class.java)
        intent.putExtra("id",idProduct)
        startActivityForResult(intent,KONFIRMASI_RESULT)
    }

    fun showCreateCategoryDialog(idProduct:Int,token:String,email:String) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Pesan produk ini?")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_order, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText

        builder.setView(view)


        // set up the ok button
        builder.setPositiveButton("Order") { dialog, p1 ->
            val description = categoryEditText.text.toString()
            val order = Order(idProduct, token, email,description)
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->

                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context, "Successfully order product", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })

            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }



}
