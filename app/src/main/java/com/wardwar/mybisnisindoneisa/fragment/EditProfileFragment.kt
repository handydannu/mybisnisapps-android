package com.wardwar.mybisnisindoneisa.fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.EditProfile
import com.wardwar.mybisnisindoneisa.network.request.Profile
import com.wardwar.mybisnisindoneisa.utils.Constats
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.order_item.view.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.net.Uri
import android.R.attr.bitmap
import android.graphics.Matrix
import android.util.Base64
import com.wardwar.mybisnisindoneisa.network.request.ChangePicture
import com.wardwar.mybisnisindoneisa.network.request.Order
import java.io.ByteArrayOutputStream


class EditProfileFragment : Fragment() {

    lateinit var image:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val profile = Profile(token, email)

        val navName = view.findViewById<TextView>(R.id.navName)
        val firstName = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${firstName}!"

        val agencyMap = mapOf(1 to "Bandung", 2 to "Jakarta", 3 to "Palembang", 4 to "Medan", 5 to "Pekanbaru", 6 to "Semarang", 7 to "Surabaya", 8 to "Malang", 9 to "Bali",
                10 to "Balikpapan", 11 to  "Manado", 12 to "Makassar")

        val idSelected = 0

        val agencyId = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val cityList = arrayOf("Bandung","Jakarta","Palembang","Medan","Pekanbaru", "Semarang","Surabaya","Malang","Balikpapan","Manado","Makasar")

        val nameForm = view.findViewById(R.id.edit_profile_name_form) as EditText
        val emailForm = view.findViewById(R.id.edit_profile_email_form) as EditText
        val addressForm = view.findViewById(R.id.edit_profile_address_form)  as EditText
        val phoneForm = view.findViewById(R.id.edit_profile_phone_form)  as EditText
        val cityForm = view.findViewById(R.id.edit_profile_city_form) as Spinner
        val companyForm = view.findViewById(R.id.edit_profile_company_form) as TextView
        val btnEditProfile = view.findViewById(R.id.edit_profile_btn) as Button
        image = view.findViewById(R.id.user_pic) as ImageView
        val changeProfileButton = view.findViewById(R.id.changeProfileBtn) as Button

        changeProfileButton.setOnClickListener {
            pickImageFromGallery()
        }


        cityForm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                println("nothing")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println(agencyMap.get(p2))
            }
        }

        cityForm.adapter = ArrayAdapter<String>(context!!,android.R.layout.simple_spinner_item,cityList)



        Fuel.get(profile.endpoint, profile.request).responseObject(profile.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        println(data.data)
                        nameForm.setText(data.data.name)
                        companyForm.setText(data.data.company)
                        emailForm.setText(data.data.email)
                        addressForm.setText(data.data.address)
                        phoneForm.setText(data.data.phone)
                        Picasso.get().load( "${data.data.picture}?type=normal").placeholder(R.drawable.user_profile).into(image)
//                        Picasso.get().load("${Constats.BASE_IMAGE}${data.data.picture}").placeholder(R.drawable.user_profile).into(image)
                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(context!!.applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }


        btnEditProfile.setOnClickListener {
            val editProfile = EditProfile(nameForm.text.toString(), email.toString(), addressForm.text.toString(), phoneForm.text.toString(), agencyId[cityForm.selectedItemPosition], companyForm.text.toString(), token)
            Fuel.post(editProfile.endpoint, editProfile.request).responseObject(editProfile.response) { _, _, result ->
                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context!!.applicationContext, "Success edit profil.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(context!!.applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })
            }
        }

    }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 9090)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode === RESULT_OK) {
            when (requestCode) {
                9090 -> manageImageFromUri(data!!.getData())
            }
        } else {
            // manage result not ok !
        }
    }

    private fun manageImageFromUri(imageUri: Uri) {
        var bitmap:Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    context!!.getContentResolver(), imageUri)

            val resize = Bitmap.createScaledBitmap(bitmap,300,300,false)

            val byteArrayOutputStream = ByteArrayOutputStream()
            resize.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

            image.setImageBitmap(resize)

            val preferencesHelper = PreferencesHelper(context!!)
            val token = preferencesHelper.deviceToken
            val email = preferencesHelper.deviceEmail
            val order = ChangePicture(token, email,encoded)
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->

                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context, "Successfully save image", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })

            }

        } catch (e: Exception) {
            // Manage exception ...
        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
        }
    }



}
