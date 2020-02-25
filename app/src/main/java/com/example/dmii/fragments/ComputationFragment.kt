package com.example.dmii.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dmii.R
import com.example.dmii.extensions.toDouble
import com.example.dmii.viewmodels.ComputeViewModel
import kotlinx.android.synthetic.main.computation_fragment.*

class ComputationFragment : Fragment() {
    private val operation: Operation by lazy {
        arguments?.getParcelable(ARGS_OPERATION) ?: Operation.SUM
    }

    private lateinit var viewModel: ComputeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ComputeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.computation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        submit.setOnClickListener {
            val nb1 = number1.toDouble() ?: return@setOnClickListener
            val nb2 = number2.toDouble() ?: return@setOnClickListener

            val operationString = when (operation) {
                Operation.SUM -> getString(R.string.sum)
                Operation.DIVISION -> getString(R.string.division)
                Operation.PRODUCT -> getString(R.string.product)
                Operation.MINUS -> getString(R.string.minus)
            }
            viewModel.resultLiveData.observe(this, Observer {
                val resultNumber = it

                result.text = getString(R.string.result, operationString, nb1, nb2, resultNumber)
            })

            viewModel.compute(operation = operation, value1 = nb1, value2 = nb2)
        }
    }

    companion object {
        private const val ARGS_OPERATION = "ARGS_OPERATION"
        fun newInstance(operation: Operation): ComputationFragment {
            return ComputationFragment().apply {
                arguments = bundleOf((ARGS_OPERATION to operation))
            }
        }
    }
}