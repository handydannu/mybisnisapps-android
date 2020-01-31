package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.adapter.OrderAdapter
import com.wardwar.mybisnisindoneisa.network.request.MyOrder
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper


/**
 * A simple [Fragment] subclass.
 *
 */
class OrderFragment : Fragment() {
    var list: ListView? = null
    var adapter: OrderAdapter? = null
    lateinit var refresh:SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val navName = view.findViewById<TextView>(R.id.navName)
        refresh = view.findViewById(R.id.refresh) as SwipeRefreshLayout
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"
        val backButton = view.findViewById<Button>(R.id.btn_back)
        backButton.visibility = View.GONE

        list = view.findViewById(R.id.listOrder) as ListView

        fetchData()

        refresh.setOnRefreshListener {
            fetchData()
        }

    }
    private fun fetchData() {

        val preferencesHelper = PreferencesHelper(context!!)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail

        val order = MyOrder(token, email)

        Fuel.get(order.endpoint).responseObject(order.response) { _, _, result ->
            result.fold({ data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        adapter = OrderAdapter(context!!, data.data)
                        list!!.adapter = adapter
                        if(refresh.isRefreshing){
                            refresh.isRefreshing = false
                        }
                    }
                }
            }, { err ->
                println("REQUEST ERROR ${err.message}")
                activity?.let {
                    Toast.makeText(it, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                }
                if(refresh.isRefreshing){
                    refresh.isRefreshing = false
                }
            })
        }

    }


}
