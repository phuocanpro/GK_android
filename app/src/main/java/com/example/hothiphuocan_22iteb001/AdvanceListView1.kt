package com.example.hothiphuocan_22iteb001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hothiphuocan_22iteb001.databinding.AdvanceListViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdvanceListView1 : AppCompatActivity() {
    lateinit var dbref: DatabaseReference
    lateinit var courseRecyclerView: RecyclerView
    lateinit var courseArrayList: ArrayList<Course>
    lateinit var binding: AdvanceListViewBinding
    lateinit var courseAdapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdvanceListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        courseRecyclerView = binding.courseList
        courseRecyclerView.layoutManager = LinearLayoutManager(this)
        courseRecyclerView.setHasFixedSize(true)

        courseArrayList = ArrayList()


        courseAdapter = CourseAdapter(this, courseArrayList)
        courseRecyclerView.adapter = courseAdapter


        getCourseData()
    }

    private fun getCourseData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (childSnapshot in dataSnapshot.children) {
                        val mahocphan = childSnapshot.child("mahocphan").getValue(String::class.java)
                        val tenhocphan = childSnapshot.child("tenhocphan").getValue(String::class.java)
                        val sotinchi = childSnapshot.child("sotinchi").getValue(String::class.java)
                        val hocky = childSnapshot.child("hocky").getValue(String::class.java)
                        Log.d("FirebaseData", "mahocphan: $mahocphan, tenhocphan: $tenhocphan,sotinchi:$sotinchi, hocky: $hocky")
                        courseArrayList.add(Course(mahocphan, tenhocphan,sotinchi, hocky))
                    }
                    courseAdapter.notifyDataSetChanged()
                } else {
                    Log.d("FirebaseData", "Không có dữ liệu trong nút HocPhan")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.e("FirebaseData", "Lỗi khi truy vấn dữ liệu: ${databaseError.message}")
            }
        })
    }
}
