package com.example.hothiphuocan_22iteb001
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hothiphuocan_22iteb001.databinding.FragmentStudentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class CourseFragment : Fragment() {
    lateinit var courseRecyclerView: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: FragmentStudentBinding
    lateinit var editMaHocPhan: EditText
    lateinit var editTenHocPhan: EditText
    lateinit var editSearch: EditText
    lateinit var editSoTinChi: EditText
    lateinit var editHocKy: EditText
    lateinit var btnAdd: Button
    lateinit var btnUpdate: Button
    lateinit var btnAdvanceListView: Button
    lateinit var btnSearch: Button
    lateinit var courseAdapter: CourseAdapter
    lateinit var courseArrayList: ArrayList<Course>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getCourseData()
        binding = FragmentStudentBinding.inflate(inflater, container, false)

        courseRecyclerView = binding.listcoursenew
        courseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        courseRecyclerView.setHasFixedSize(true)

        courseArrayList = ArrayList()
        courseAdapter = CourseAdapter(requireContext(), courseArrayList)

        courseRecyclerView.adapter = courseAdapter


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("HocPhan")

        editMaHocPhan = binding.editMaHocPhan
        editTenHocPhan = binding.editTenHocPhan
        editSoTinChi = binding.editHocKy
        editHocKy = binding.editHocKy
        btnAdd = binding.btnAdd
        btnUpdate = binding.btnUpdate
        courseRecyclerView = binding.listcoursenew
        courseAdapter = CourseAdapter(requireContext(),ArrayList())


        binding.btnAdd.setOnClickListener {
            val mahocphan = binding.editMaHocPhan.text.toString()
            val tenhocphan = binding.editTenHocPhan.text.toString()
            val sotinchi = binding.editSoTinChi.text.toString()
            val hocky = binding.editHocKy.text.toString()
            if (mahocphan.isNotEmpty() && tenhocphan.isNotEmpty() && sotinchi.isNotEmpty() && hocky.isNotEmpty()) {
                addHocPhan(mahocphan, tenhocphan, sotinchi ,hocky)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Dữ liệu rỗng không thêm được !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnAdvanceListView.setOnClickListener {
            val intent = Intent(requireContext(), AdvanceListView1::class.java)
            startActivity(intent)
        }
        binding.btnUpdate.setOnClickListener {
            val mahocphan = binding.editMaHocPhan.text.toString()
            val tenhocphan = binding.editTenHocPhan.text.toString()
            val sotinchi = binding.editSoTinChi.text.toString()
            val hocky = binding.editHocKy.text.toString()
            if (mahocphan.isNotEmpty() && tenhocphan.isNotEmpty() && sotinchi.isNotEmpty() && hocky.isNotEmpty()) {
                update(mahocphan, tenhocphan, sotinchi ,hocky)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Dữ liệu rỗng không thêm được !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnSearch.setOnClickListener {
            val mahocphanp=binding.editSearch.text.toString()
            if(mahocphanp.isNotEmpty()){
search(mahocphanp)
            }else {
                Toast.makeText(
                    requireContext(),
                    "Dữ liệu rỗng không thêm được !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }
    private fun clearForm() {
        editMaHocPhan.text.clear()
        editTenHocPhan.text.clear()
        editHocKy.text.clear()
        editHocKy.text.clear()
    }
    private fun addHocPhan( mahocphan: String, tenhocphan: String, sotinchi: String, hocky: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        val query = databaseReference.orderByChild("mahocphan").equalTo(mahocphan)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key
                    val mahocphanData = Course(mahocphan, tenhocphan, sotinchi, hocky)
                    id?.let {
                        databaseReference.child(it).setValue(mahocphanData)
                        Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Lớp đã tồn tại", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(context, "Lỗi không xác định", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getCourseData() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                courseArrayList.clear()
                // Kiểm tra xem có dữ liệu không
                if (dataSnapshot.exists()) {
                    // Lặp qua tất cả các children trong nút "HocPhan"
                    for (childSnapshot in dataSnapshot.children) {
                        // Lấy giá trị của từng child và thêm vào ArrayList
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
    private fun update(mahocphan: String, tenhocphan: String, sotinchi: String, hocky: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        val query = databaseReference.orderByChild("mahocphan").equalTo(mahocphan)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val key = snapshot.key
                            val updateData = HashMap<String, Any?>()
                            updateData["mahocphan"] = mahocphan
                            updateData["tenhocphan"] = tenhocphan
                            updateData["sotinchi"] = sotinchi
                            updateData["hocky"] = hocky
                            // Thực hiện cập nhật dữ liệu
                            databaseReference.child(key!!).updateChildren(updateData)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Cập nhật thành công !",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    clearForm()
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Lỗi khi cập nhật dữ liệu: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Dữ liệu không tồn tại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "Lỗi khi truy cập cơ sở dữ liệu: ${databaseError.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    private fun search(mahocphan: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        val query = databaseReference.orderByChild("mahocphan").equalTo(mahocphan)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (courseSnapshot in dataSnapshot.children) {
                        val course = courseSnapshot.getValue(Course::class.java)
                        if (course != null) {
                            showCourseInfoDialog(course)
                            Log.d("FirebaseData", "Học phần được tìm thấy: ${course.toString()}")
                        }
                    }
                } else {
                    Log.d("FirebaseData", "Không tìm thấy học phần với mã học phần là $mahocphan")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseData", "Lỗi khi truy vấn dữ liệu: ${databaseError.message}")
            }
        })
    }
    private fun showCourseInfoDialog(course: Course) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Thông tin học phần")
            setMessage("Mã học phần: ${course.mahocphan}\nTên học phần: ${course.tenhocphan}\nSố tín chỉ: ${course.sotinchi}\nHọc Kỳ: ${course.hocky}")
            setPositiveButton("Đóng") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
