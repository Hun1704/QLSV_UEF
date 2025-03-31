package Hocvien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.SinhVienDAO
import com.example.qlsv.Model.SinhVien
import com.example.qlsv.R

class UpdateSinhvienActivity : AppCompatActivity() {

    private val mssv: TextView by lazy { findViewById(R.id.mssv_sua) }
    private val hoten: TextView by lazy { findViewById(R.id.tensv_sua) }
    private val gioitinh: TextView by lazy { findViewById(R.id.gioitinh_sua) }
    private val sdt: TextView by lazy { findViewById(R.id.sdt_sua) }
    private val emailedu: TextView by lazy { findViewById(R.id.emailedu_sua) }
    private val lop: TextView by lazy { findViewById(R.id.lop_sv) }
    private val nienkhoa: TextView by lazy { findViewById(R.id.nienkhoa_sua) }
    private val trinhtranghoc: TextView by lazy { findViewById(R.id.trinhtranghoc_sua) }
    private val sua: Button by lazy { findViewById(R.id.sua_sv) }
    private val btnxoa: Button by lazy { findViewById(R.id.xoa_sv) }
    private val back: Button by lazy { findViewById(R.id.back3) }
    private lateinit var sinhVienDAO: SinhVienDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_sinhvien)

        // Khởi tạo DAO
        sinhVienDAO = SinhVienDAO(this)

        // Lấy dữ liệu từ Intent
        val intent = intent
        mssv.text = intent.getStringExtra("mssv")
        hoten.text = intent.getStringExtra("hoten")
        gioitinh.text = intent.getStringExtra("gioitinh")
        sdt.text = intent.getStringExtra("sdt")
        emailedu.text = intent.getStringExtra("emailedu")
        lop.text = intent.getStringExtra("lop")
        nienkhoa.text = intent.getStringExtra("nienkhoa")
        trinhtranghoc.text = intent.getStringExtra("trinhtranghoc")

        // Thiết lập mssv không thể chỉnh sửa
        mssv.isEnabled = false

        // Cập nhật dữ liệu sinh viên
        sua.setOnClickListener {
            val mssvText = mssv.text.toString()
            val hotenText = hoten.text.toString()
            val gioitinhText = gioitinh.text.toString()
            val sdtText = sdt.text.toString()
            val emaileduText = emailedu.text.toString()
            val lopText = lop.text.toString()
            val nienkhoaText = nienkhoa.text.toString()
            val trinhtranghocText = trinhtranghoc.text.toString()

            if (mssvText.isEmpty() || hotenText.isEmpty() || gioitinhText.isEmpty() || sdtText.isEmpty() || emaileduText.isEmpty() || lopText.isEmpty() || nienkhoaText.isEmpty() || trinhtranghocText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val sinhVien = SinhVien(
                mssv = mssvText,
                hoten = hotenText,
                gioitinh = gioitinhText,
                sdt = sdtText,
                emailedu = emaileduText,
                lop = lopText,
                nienkhoa = nienkhoaText,
                trinhtranghoc = trinhtranghocText
            )

            val result = sinhVienDAO.updateSinhVien(sinhVien)
            if (result == 1) {
                Toast.makeText(this, "Sửa dữ liệu thành công", Toast.LENGTH_LONG).show()
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            } else {
                Toast.makeText(this, "Sửa dữ liệu thất bại", Toast.LENGTH_LONG).show()
            }
        }

        btnxoa.setOnClickListener {
            val maSVToDelete = mssv.text.toString().trim()
            if (maSVToDelete.isNotEmpty()) {
                val result = sinhVienDAO.deleteSinhVien(maSVToDelete)
                if (result > 0) {
                    Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Xóa sinh viên thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không thể xóa sinh viên", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý sinh viên
        back.setOnClickListener {
            finish()
        }
    }
}
