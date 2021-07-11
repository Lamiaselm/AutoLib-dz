package com.clovertech.autolibdz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter


class SliderAdapter(var context: Context) : PagerAdapter() {
    var layoutInflater: LayoutInflater? = null
    var slider_first_headers = arrayOf(
            "AutoLib Dz",
            "AutoLib Dz",
            "AutoLib Dz",
            "AutoLib Dz"
    )
    var slider_second_headers = arrayOf(
            "Choisissez une borne",
            "Choisisez un véhicule",
            "Accedez au paiement",
            "Débloquez votre véhicule !"
    )
    var slider_descriptions = arrayOf(
            "Localisez-vous à la borne la plus proche",
            "Parmis la liste des véhicules disponibles",
            "Avec carte de crédit ou carte d'abonnement",
            "Associez-vous avec votre véhicule"
    )

    override fun getCount(): Int {
        return slider_first_headers.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View? = layoutInflater?.inflate(R.layout.view_pager_item, container, false)
        val service_first_header = view?.findViewById<TextView>(R.id.title1)
        val service_second_header = view?.findViewById<TextView>(R.id.title2)
        val service_description = view?.findViewById<TextView>(R.id.desc_txt_view)
        service_first_header?.text = slider_first_headers[position]
        service_second_header?.text = slider_second_headers[position]
        service_description?.text = slider_descriptions[position]
        container.addView(view)
        return view!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as RelativeLayout)
    }

}
