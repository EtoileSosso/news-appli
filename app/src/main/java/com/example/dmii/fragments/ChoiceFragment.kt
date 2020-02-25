package com.example.dmii.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmii.R
import com.example.dmii.viewmodels.ComputeViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.choice_fragment.*

class ChoiceFragment : Fragment() {
    private lateinit var viewModel: ComputeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProvider(this).get(ComputeViewModel::class.java)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choice_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plusButton.setOnClickListener {
            selectOperation(Operation.SUM)
        }
        minusButton.setOnClickListener {
            selectOperation(Operation.MINUS)
        }
        timesButton.setOnClickListener {
            selectOperation(Operation.PRODUCT)
        }
        divButton.setOnClickListener {
            selectOperation(Operation.DIVISION)
        }

        viewModel.resultLiveData.observe(this, Observer {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
            // previousResult.text = it.toString()
        })
    }

    private fun selectOperation(operation: Operation) {
        val computationFragment = ComputationFragment.newInstance(operation)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.compute_fragment, computationFragment)
            addToBackStack(null)
        }.commit()
    }
}

@Parcelize
enum class Operation : Parcelable {
    SUM, MINUS, PRODUCT, DIVISION
}