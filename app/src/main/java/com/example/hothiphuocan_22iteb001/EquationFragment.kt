package com.example.hothiphuocan_22iteb001

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EquationFragment : Fragment() {

    lateinit var edit_a: EditText
    lateinit var edit_b: EditText
    lateinit var edit_result: EditText
    lateinit var nghiem: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_equation, container, false)
        edit_a = view.findViewById(R.id.edit_a)
        edit_b = view.findViewById(R.id.edit_b)
        edit_result = view.findViewById(R.id.edit_result)
        nghiem = view.findViewById(R.id.nghiem)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nghiem.setOnClickListener {
            // Lấy giá trị nhập vào từ EditText
            val a = edit_a.text.toString().toDoubleOrNull()
            val b = edit_b.text.toString().toDoubleOrNull()

            if (a != null && b != null) {
                if (a != 0.0) {
                    // Tính giá trị của x
                    val x = -b / a
                    edit_result.setText("X = $x")
                } else {
                    if (b == 0.0) {
                        edit_result.setText("Phương trình có vô số nghiệm")
                    } else {
                        edit_result.setText("Phương trình không có nghiệm")
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập các số hợp lệ.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}