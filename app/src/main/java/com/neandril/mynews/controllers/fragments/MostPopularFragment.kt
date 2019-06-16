package com.neandril.mynews.controllers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.neandril.mynews.R

class MostPopularFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_mostpopular, container, false)
    }

}// Required empty public constructor   Required empty public constructor