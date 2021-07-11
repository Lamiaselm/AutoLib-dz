package com.clovertech.autolibdz.ui.promo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.clovertech.autolibdz.APIs.PromoApi
import com.clovertech.autolibdz.Adapters.PromoAdapter
import com.clovertech.autolibdz.DataClasses.Locataire
import com.clovertech.autolibdz.DataClasses.SubStateResponse
import com.clovertech.autolibdz.R
import com.clovertech.autolibdz.ViewModel.*
import com.clovertech.autolibdz.repository.PromoRepository
import com.clovertech.autolibdz.utils.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_card.*
import kotlinx.android.synthetic.main.fragment_promo.*
import kotlinx.android.synthetic.main.fragment_promo.close
import kotlinx.android.synthetic.main.tarification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PromoFragment : BottomSheetDialogFragment() {

    private lateinit var promoViewModel: PromoViewModel
    private lateinit var promoCodeViewModel: PromoCodeViewModel
    private lateinit var promoViewModelFactory: PromoViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        promoViewModel =
            ViewModelProvider(this).get(PromoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_promo, container, false)

        promoViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences: SharedPreferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val idUser=preferences.getInt("IDUSER",0)
        Log.d("idUSER",idUser.toString())

        close.setOnClickListener{
            this.dismiss()
        }



        val call = RetrofitInstance.apiUser.getPointByUser(idUser)
                    call.enqueue(object:Callback<Locataire>{
                        override fun onFailure(call: Call<Locataire>, t: Throwable) {
                            Log.d("fail",t.toString())
                        }

                        override fun onResponse(
                            call: Call<Locataire>,
                            response: Response<Locataire>
                        ) {
                            Log.d("push", response.raw().toString())
                            val mesPoints=response.body()?.points
                            points.text= mesPoints.toString() + " Points"



                        }

                    })
        var priceReduHelper=price
        priceReduHelper.setText("0 DA")
        var totalprice= arguments?.getInt("totalprice")
        if (pointListner){
            points.text= mesPointsHelper.toString()
        }
        val api= PromoApi()
        val repository= PromoRepository(api)
        val promo= PromoViewModelFactory(repository)
        promoCodeViewModel=ViewModelProvider(this,promo).get(PromoCodeViewModel::class.java)
        promoCodeViewModel.getPromo()
        promoCodeViewModel.promo.observe(viewLifecycleOwner, Observer { promoList->
          list_promo.also {
                it.layoutManager=LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter= totalprice?.let { it1 ->
                    PromoAdapter(requireContext(),promoList,priceReduHelper,
                        it1
                    )
                }
            }

        })
        confirm.setOnClickListener {
            val newprice= pricetotarif
            listner=true
            requireActivity().total.setText(newprice.toString()+" DA")
            this.dismiss()

        }



    }


}

