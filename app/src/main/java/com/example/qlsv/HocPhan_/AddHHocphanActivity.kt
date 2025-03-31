package HocPhan_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.HocPhanDAO
import com.example.qlsv.Model.HocPhan
import com.example.qlsv.R

class AddHHocphanActivity : AppCompatActivity() {

    private val mahocphan: EditText by lazy { findViewById(R.id.mahocphan_them) }
    private val tenhocphan: EditText by lazy { findViewById(R.id.tenhocphan_them) }
    private val sotiet: EditText by lazy { findViewById(R.id.sotiet_them) }
    private val them: Button by lazy { findViewById(R.id.btn_them) }
    private val back: Button by lazy { findViewById(R.id.btnback) }
    private lateinit var hocPhanDAO: HocPhanDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hhocphan)

        // Khởi tạo DAO
        hocPhanDAO = HocPhanDAO(this)

        // Thêm học phần mới
        them.setOnClickListener {
            val mahocphanText = mahocphan.text.toString()
            val tenhocphanText = tenhocphan.text.toString()
            val sotietText = sotiet.text.toString()


            if (mahocphanText.isEmpty() || tenhocphanText.isEmpty() || sotietText.isEmpty() ) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val hocPhan = HocPhan(
                maHocPhan = mahocphanText.toInt(),
                tenHocPhan = tenhocphanText,
                soTiet = sotietText.toInt()

            )

            val result = hocPhanDAO.insertHocPhan(hocPhan)
            if (result == 1) {
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
                clearFields()
            } else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý học phần
        back.setOnClickListener {
            finish()
        }
    }

    private fun clearFields() {
        mahocphan.text.clear()
        tenhocphan.text.clear()
        sotiet.text.clear()
    }
}
