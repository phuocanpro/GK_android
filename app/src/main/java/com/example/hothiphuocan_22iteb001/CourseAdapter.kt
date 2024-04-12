package com.example.hothiphuocan_22iteb001

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class CourseAdapter(private val context: Context, private val courseList: ArrayList<Course>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_course, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courseList[position]
        holder.tvMaHocPhan.text = course.mahocphan
        holder.tvTenHocPhan.text = course.tenhocphan
        holder.tvSoTinChi.text = course.sotinchi
        holder.tvHocKy.text = course.hocky

        holder.cardView.setOnClickListener {
            // Truyền dữ liệu từ TextView vào EditText tương ứng
            (context as AppCompatActivity).findViewById<EditText>(R.id.editMaHocPhan).setText(course.mahocphan)
            (context as AppCompatActivity).findViewById<EditText>(R.id.editTenHocPhan).setText(course.tenhocphan)
            (context as AppCompatActivity).findViewById<EditText>(R.id.editSoTinChi).setText(course.sotinchi)
            (context as AppCompatActivity).findViewById<EditText>(R.id.editHocKy).setText(course.hocky)
        }
        holder.cardView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa học phần này?")
                .setPositiveButton("Xóa") { _, _ ->
                    val mahocphan = course.mahocphan // Hoặc thông tin cần thiết khác

                    if (mahocphan != null) {
                        deleteCourseFromFirebase(mahocphan)
                    }
                }
                .setNegativeButton("Hủy", null)
                .show()

            true
        }
    }
    private fun deleteCourseFromFirebase(mahocphan: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("HocPhan")
        val query = databaseReference.orderByChild("mahocphan").equalTo(mahocphan)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (courseSnapshot in dataSnapshot.children) {
                        val courseKey = courseSnapshot.key
                        if (courseKey != null) {

                            databaseReference.child(courseKey).removeValue()
                                .addOnSuccessListener {
                                    Log.d("FirebaseDelete", "Xóa học phần với mã HP $mahocphan thành công")
                                }
                                .addOnFailureListener { exception ->
                                    Log.e("FirebaseDelete", "Lỗi khi xóa học phần với mã HP $mahocphan: ${exception.message}")
                                }
                        }
                    }
                } else {
                    Log.d("FirebaseDelete", "Không tìm thấy học phần với mã HP $mahocphan")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseDelete", "Lỗi khi truy vấn dữ liệu: ${databaseError.message}")
            }
        })
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMaHocPhan: TextView = itemView.findViewById(R.id.tvMaHocPhan)
        val tvTenHocPhan: TextView = itemView.findViewById(R.id.tvTenHocPhan)
        val tvSoTinChi: TextView = itemView.findViewById(R.id.tvSoTinChi)
        val tvHocKy: TextView = itemView.findViewById(R.id.tvHocKy)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}

