package HocPhan

import Controller.LopHocPhanDAO
import MODEL.LopHocPhan
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class AddCourseActivity : AppCompatActivity() {

    private val maLopHocPhan: EditText by lazy { findViewById(R.id.mahp_sua) }
    private val ngayBatDau: EditText by lazy { findViewById(R.id.ngaybd_sua) }
    private val ngayKetThuc: EditText by lazy { findViewById(R.id.ngaykt_sua) }
    private val soLuongMax: EditText by lazy { findViewById(R.id.slmax_sua) }
    private val soLuongDangKi: EditText by lazy { findViewById(R.id.sldk_sua) }
    private val hocKy: EditText by lazy { findViewById(R.id.hocky_sua) }
    private val namHoc: EditText by lazy { findViewById(R.id.namhoc_sua) }
    private val thongTin: EditText by lazy { findViewById(R.id.thongtin_sua) }
    private val maHocPhan: EditText by lazy { findViewById(R.id.mahocphan_sua) }
    private val them: Button by lazy { findViewById(R.id.btn_them) }
    private val back: Button by lazy { findViewById(R.id.btnback) }
    private lateinit var lopHocPhanDAO: LopHocPhanDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        // Khởi tạo DAO
        lopHocPhanDAO = LopHocPhanDAO(this)

        // Đặt giá trị mặc định của EditText về ""
        setDefaultValues()

        // Thêm khóa học phần mới
        them.setOnClickListener {
            val maLopHocPhanText = maLopHocPhan.text.toString()
            val ngayBatDauText = ngayBatDau.text.toString()
            val ngayKetThucText = ngayKetThuc.text.toString()
            val soLuongMaxText = soLuongMax.text.toString()
            val soLuongDangKiText = soLuongDangKi.text.toString()
            val hocKyText = hocKy.text.toString()
            val namHocText = namHoc.text.toString()
            val thongTinText = thongTin.text.toString()
            val maHocPhanText = maHocPhan.text.toString()

            if (maLopHocPhanText.isEmpty() || ngayBatDauText.isEmpty() || ngayKetThucText.isEmpty() || soLuongMaxText.isEmpty() || soLuongDangKiText.isEmpty() || hocKyText.isEmpty() || namHocText.isEmpty() || thongTinText.isEmpty() || maHocPhanText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                val maLopHocPhanInt = maLopHocPhanText.toInt()
                val soLuongMaxInt = soLuongMaxText.toInt()
                val soLuongDangKiInt = soLuongDangKiText.toInt()
                val hocKyInt = hocKyText.toInt()
                if (hocKyInt !in 1..3) {
                    Toast.makeText(this, "Học kỳ phải nằm trong khoảng từ 1 đến 3", Toast.LENGTH_LONG).show()
                } else {
                    val lopHocPhan = LopHocPhan(maLopHocPhanInt, ngayBatDauText, ngayKetThucText, soLuongMaxInt, soLuongDangKiInt, hocKyInt, namHocText, thongTinText, maHocPhanText)
                    val result = lopHocPhanDAO.insertLopHocPhan(lopHocPhan)
                    if (result != -1L) {
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
                        clearFields()
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Số lượng tối đa, số lượng đăng ký, học kỳ phải là số nguyên", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý khóa học phần
        back.setOnClickListener {
            finish()
        }
    }

    private fun setDefaultValues() {
        maLopHocPhan.setText("")
        ngayBatDau.setText("")
        ngayKetThuc.setText("")
        soLuongMax.setText("")
        soLuongDangKi.setText("")
        hocKy.setText("")
        namHoc.setText("")
        thongTin.setText("")
        maHocPhan.setText("")
    }

    private fun clearFields() {
        maLopHocPhan.text.clear()
        ngayBatDau.text.clear()
        ngayKetThuc.text.clear()
        soLuongMax.text.clear()
        soLuongDangKi.text.clear()
        hocKy.text.clear()
        namHoc.text.clear()
        thongTin.text.clear()
        maHocPhan.text.clear()
    }
}
