package HocPhan_

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.HocPhanDAO
import com.example.qlsv.Model.HocPhan
import com.example.qlsv.R

class UpdateHHocphanActivity : AppCompatActivity() {

    private val mahocphan: EditText by lazy { findViewById(R.id.mahocphan_sua) }
    private val tenhocphan: EditText by lazy { findViewById(R.id.tenhocphan_sua) }
    private val sotiet: EditText by lazy { findViewById(R.id.sotiet_sua) }
    private val sua: Button by lazy { findViewById(R.id.sua_hp) }
    private val btnxoa: Button by lazy { findViewById(R.id.xoa_hp) }
    private val back: Button by lazy { findViewById(R.id.back3) }
    private lateinit var hocPhanDAO: HocPhanDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_hhocphan)

        // Khởi tạo DAO
        hocPhanDAO = HocPhanDAO(this)

        // Lấy dữ liệu từ Intent
        val intent = intent
        val maHocPhan = intent.getIntExtra("maHocPhan", -1)
        val tenHocPhan = intent.getStringExtra("tenHocPhan")
        val soTiet = intent.getIntExtra("soTiet", -1)



        // Gán giá trị vào các trường EditText
        mahocphan.setText(maHocPhan.toString())
        tenhocphan.setText(tenHocPhan)
        sotiet.setText(soTiet.toString())

        // Thiết lập mahocphan không thể chỉnh sửa
        mahocphan.isEnabled = false

        // Cập nhật dữ liệu học phần
        sua.setOnClickListener {
            val mahocphanText = mahocphan.text.toString()
            val tenhocphanText = tenhocphan.text.toString()
            val sotietText = sotiet.text.toString()

            if (mahocphanText.isEmpty() || tenhocphanText.isEmpty() || sotietText.isEmpty() ) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                val hocPhan = HocPhan(
                    maHocPhan = mahocphanText.toInt(),
                    tenHocPhan = tenhocphanText,
                    soTiet = sotietText.toInt(),
                )

                val result = hocPhanDAO.updateHocPhan(hocPhan)
                if (result == 1) {
                    Toast.makeText(this, "Sửa dữ liệu thành công", Toast.LENGTH_LONG).show()
                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Sửa dữ liệu thất bại", Toast.LENGTH_LONG).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Các trường dữ liệu phải là số nguyên hợp lệ", Toast.LENGTH_LONG).show()
            }
        }

        btnxoa.setOnClickListener {
            val maHPToDelete = mahocphan.text.toString().trim()
            if (maHPToDelete.isNotEmpty()) {
                val result = hocPhanDAO.deleteHocPhan(maHPToDelete)
                if (result > 0) {
                    Toast.makeText(this, "Xóa học phần thành công", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Xóa học phần thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không thể xóa học phần", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý học phần
        back.setOnClickListener {
            finish()
        }
    }
}
