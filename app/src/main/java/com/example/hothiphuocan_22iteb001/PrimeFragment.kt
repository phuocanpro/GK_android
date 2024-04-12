package com.example.hothiphuocan_22iteb001

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PrimeFragment : Fragment() {
    lateinit var number_equal: EditText
    lateinit var result_equal: EditText
    lateinit var check1: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prime, container, false)
        number_equal = view.findViewById(R.id.number_equal)
        result_equal = view.findViewById(R.id.result_equal)
        check1 = view.findViewById(R.id.checkequaion)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check1.setOnClickListener {
            val number = number_equal.text.toString().toIntOrNull()
            if (number !=null) {
                val isPrime = isPrime(number)
                if (isPrime) {
                    result_equal.setText("Là số nguyên tố!")
                } else {
                    result_equal.setText("Không phải số nguyên tố!")
                }
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập một số nguyên.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun isPrime(number: Int): Boolean {
        if (number <= 1) {
            return false
        }
        for (i in 2 until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }
}