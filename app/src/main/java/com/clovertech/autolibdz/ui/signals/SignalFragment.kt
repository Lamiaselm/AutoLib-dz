package com.clovertech.autolibdz.ui.signals

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.clovertech.autolibdz.R
import com.clovertech.autolibdz.ViewModel.MainViewModel
import com.clovertech.autolibdz.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_signal.*
import model.Signal
import repository.Repository


class SignalFragment:Fragment() {

    private lateinit var signalViewModel: SignalViewModel
    private lateinit var viewModel : MainViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        signalViewModel=
                ViewModelProvider(this).get(SignalViewModel::class.java)

        val root = inflater.inflate(R.layout.activity_signal, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     /*   val types = arrayOf("theft","panne")
        reporting_type.setOnClickListener {
            val mbuilder =
                    AlertDialog.Builder(this@SignalAct)
            mbuilder.setTitle("What's the type of the alert ?")
            mbuilder.setSingleChoiceItems(types, -1,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        reporting_type.text = types[i]
                        dialogInterface.dismiss()
                    })
            mbuilder.show()
        }*/
        val repository = Repository()
    val viewModelFactory = MainViewModelFactory(repository)
    viewModel = ViewModelProvider(this,viewModelFactory)
        .get(MainViewModel::class.java)
    val types = arrayOf("theft","panne")
    reporting_type.setOnClickListener {
        val mbuilder =
            AlertDialog.Builder(requireContext())
        mbuilder.setTitle("What's the type of the alert ?")
        mbuilder.setSingleChoiceItems(types, -1,
            DialogInterface.OnClickListener { dialogInterface, i ->
                reporting_type.text = types[i]
                dialogInterface.dismiss()
            })
        mbuilder.show()
    }
    report_btn.setOnClickListener {
        if (msg.text.toString().isEmpty()){
            msg.error = "Please set your alert message"
        }else {
            val mbuilder =
                AlertDialog.Builder(requireContext())
            mbuilder.setTitle("Do you really want to confirm this report ?")
            mbuilder.setPositiveButton(
                "Yes"
            ) { dialog, which ->

                val signal = Signal(0,reporting_type.text.toString(),3,""
                    ,"Tenant",msg.text.toString(),14)

                viewModel.pushSignal(signal)
                viewModel.signalResponse.observe(requireActivity(), Observer { response ->
                    if (response.isSuccessful){
                        Log.e("Push",response.body().toString())
                        Log.e("Push",response.code().toString())
                        Log.e("Push",response.message())
                        msg.setText("")
                        reporting_type.setText("")
                        Toast.makeText(requireContext(),"Alert sent sucessfully", Toast.LENGTH_SHORT).show()


                    }
                })
            }
            mbuilder.setNegativeButton(
                "No"
            ) { dialog, which ->

                dialog.dismiss()
            }
            mbuilder.show()
        }
    }
    }
}