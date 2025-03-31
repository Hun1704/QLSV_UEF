package Hocvien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.SinhVienDAO
import com.example.qlsv.Model.SinhVien
import com.example.qlsv.R

class AddSinhvienActivity : AppCompatActivity() {

    private val mssv: EditText by lazy { findViewById(R.id.mssv_them) }
    private val hoten: EditText by lazy { findViewById(R.id.hoten_them) }
    private val gioitinh: EditText by lazy { findViewById(R.id.gioitinh_them) }
    private val sdt: EditText by lazy { findViewById(R.id.sdt_them) }
    private val emailedu: EditText by lazy { findViewById(R.id.emailedu_them) }
    private val lop: EditText by lazy { findViewById(R.id.lop_them) }
    private val nienkhoa: EditText by lazy { findViewById(R.id.nienkhoa_them) }
    private val trinhtranghoc: EditText by lazy { findViewById(R.id.trinhtranghoc_them) }
    private val them: Button by lazy { findViewById(R.id.btn_them) }
    private val back: Button by lazy { findViewById(R.id.btnback) }
    private lateinit var sinhVienDAO: SinhVienDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sinhvien)

        // Khởi tạo DAO
        sinhVienDAO = SinhVienDAO(this)

        // Đặt giá trị mặt định của EditText về ""
        setDefaultValues()

        // Thêm sinh viên mới
        them.setOnClickListener {
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

            val result = sinhVienDAO.insertSinhVien(sinhVien)
            if (result == 1) {
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
                clearFields()
            } else {
                Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý sinh viên
        back.setOnClickListener {
            finish()
        }
    }

    private fun setDefaultValues() {
        mssv.setText("")
        hoten.setText("")
        gioitinh.setText("")
        sdt.setText("")
        emailedu.setText("")
        lop.setText("")
        nienkhoa.setText("")
        trinhtranghoc.setText("")
    }

    private fun clearFields() {
        mssv.text.clear()
        hoten.text.clear()
        gioitinh.text.clear()
        sdt.text.clear()
        emailedu.text.clear()
        lop.text.clear()
        nienkhoa.text.clear()
        trinhtranghoc.text.clear()
    }
}
