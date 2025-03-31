package HocPhan

import Controller.LopHocPhanDAO
import MODEL.LopHocPhan
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class UpdateCourseActivity : AppCompatActivity() {

    private val maLopHocPhan: EditText by lazy { findViewById(R.id.mahp_sua) }
    private val ngayBatDau: EditText by lazy { findViewById(R.id.ngaybd_sua) }
    private val ngayKetThuc: EditText by lazy { findViewById(R.id.ngaykt_sua) }
    private val soLuongMax: EditText by lazy { findViewById(R.id.slmax_sua) }
    private val soLuongDangKi: EditText by lazy { findViewById(R.id.sldk_sua) }
    private val hocKy: EditText by lazy { findViewById(R.id.hocky_sua) }
    private val namHoc: EditText by lazy { findViewById(R.id.namhoc_sua) }
    private val thongTin: EditText by lazy { findViewById(R.id.thongtin_sua) }
    private val maHocPhan: EditText by lazy { findViewById(R.id.mahocphan_sua) }
    private val sua: Button by lazy { findViewById(R.id.sua_hp) }
    private val btnXoa: Button by lazy { findViewById(R.id.xoa_hp) }
    private val back: Button by lazy { findViewById(R.id.back3) }
    private lateinit var lopHocPhanDAO: LopHocPhanDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_course)

        // Khởi tạo DAO
        lopHocPhanDAO = LopHocPhanDAO(this)

        // Lấy dữ liệu từ Intent
        val intent = intent
        maLopHocPhan.setText(intent.getIntExtra("maLopHocPhan", 0).toString())
        ngayBatDau.setText(intent.getStringExtra("ngayBatDau"))
        ngayKetThuc.setText(intent.getStringExtra("ngayKetThuc"))
        soLuongMax.setText(intent.getIntExtra("soLuongMax", 0).toString())
        soLuongDangKi.setText(intent.getIntExtra("soLuongDangKi", 0).toString())
        hocKy.setText(intent.getIntExtra("hocKy", 0).toString())
        namHoc.setText(intent.getStringExtra("namHoc"))
        thongTin.setText(intent.getStringExtra("thongTin"))
        maHocPhan.setText(intent.getStringExtra("maHocPhan"))

        // Thiết lập maLopHocPhan không thể chỉnh sửa
        maLopHocPhan.isEnabled = false

        // Cập nhật dữ liệu lớp học phần
        sua.setOnClickListener {
            val maLopHocPhanText = maLopHocPhan.text.toString()
            val ngayBatDauText = ngayBatDau.text.toString()
            val ngayKetThucText = ngayKetThuc.text.toString()
            val soLuongMaxText = soLuongMax.text.toString()
            val soLuongDangKiText = soLuongDangKi.text.toString()
            val hocKyText = hocKy.text.toString()
            val namHocText = namHoc.text.toString()
            val thongTinText = thongTin.text.toString()
            val maHocPhanText = maHocPhan.text.toString()

            try {
                val maLopHocPhanInt = maLopHocPhanText.toInt()
                val soLuongMaxInt = soLuongMaxText.toInt()
                val soLuongDangKiInt = soLuongDangKiText.toInt()
                val hocKyInt = hocKyText.toInt()

                if (hocKyInt !in 1..3) {
                    Toast.makeText(this, "Học kỳ phải nằm trong khoảng từ 1 đến 3", Toast.LENGTH_LONG).show()
                } else {
                    val lopHocPhan = LopHocPhan(maLopHocPhanInt, ngayBatDauText, ngayKetThucText, soLuongMaxInt, soLuongDangKiInt, hocKyInt, namHocText, thongTinText, maHocPhanText)
                    val result = lopHocPhanDAO.updateLopHocPhan(lopHocPhan)
                    if (result == 1) {
                        Toast.makeText(this, "Sửa dữ liệu thành công", Toast.LENGTH_LONG).show()
                        val returnIntent = Intent()
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Sửa dữ liệu thất bại", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Các trường dữ liệu phải là số nguyên hợp lệ", Toast.LENGTH_LONG).show()
            }
        }

        btnXoa.setOnClickListener {
            val maLopHocPhanToDelete = maLopHocPhan.text.toString().trim()
            if (maLopHocPhanToDelete.isNotEmpty()) {
                val result = lopHocPhanDAO.deleteLopHocPhan(maLopHocPhanToDelete.toInt())
                if (result > 0) {
                    Toast.makeText(this, "Xóa lớp học phần thành công", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Xóa lớp học phần thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không thể xóa lớp học phần", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý lớp học phần
        back.setOnClickListener {
            finish()
        }
    }
}
