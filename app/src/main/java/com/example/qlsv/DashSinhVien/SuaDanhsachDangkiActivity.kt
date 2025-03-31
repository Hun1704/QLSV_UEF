package DashSinhVien

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R
import DBHelper

class SuaDanhsachDangkiActivity : AppCompatActivity() {

    private val spinnerMaLopHocPhan: Spinner by lazy { findViewById(R.id.spinner_ma_lop_hoc_phan) }
    private val thongTin: TextView by lazy { findViewById(R.id.thong_tin) }
    private val ngayDangKi: EditText by lazy { findViewById(R.id.ngay_dang_ki) }
    private val btnUpdate: Button by lazy { findViewById(R.id.btn_update) }
    private val btnDelete: Button by lazy { findViewById(R.id.btn_delete) }
    private val btnback: Button by lazy { findViewById(R.id.btn_back) }
    private lateinit var dbHelper: DBHelper
    private var mssv: String? = null
    private var originalMaLopHocPhan: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sua_danhsach_dangki)

        dbHelper = DBHelper(this)

        mssv = intent.getStringExtra("MSSV")
        originalMaLopHocPhan = intent.getIntExtra("maLopHocPhan", -1)
        val maHocPhanValue = intent.getStringExtra("maHocPhan")
        val thongTinValue = intent.getStringExtra("thongTin")
        val ngayDangKiValue = intent.getStringExtra("ngayDangKi")

        // Thiết lập dữ liệu ban đầu
        thongTin.text = thongTinValue
        ngayDangKi.setText(ngayDangKiValue)

        // Thiết lập Spinner
        if (maHocPhanValue != null) {
            val lopHocPhanList = dbHelper.getAllLopHocPhanForHocPhan(maHocPhanValue)
            val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lopHocPhanList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMaLopHocPhan.adapter = spinnerAdapter

            // Chọn giá trị ban đầu cho Spinner
            val initialPosition = lopHocPhanList.indexOf(originalMaLopHocPhan)
            spinnerMaLopHocPhan.setSelection(initialPosition)
        }

        btnUpdate.setOnClickListener {
            val selectedItem = spinnerMaLopHocPhan.selectedItem.toString().toInt()
            val newNgayDangKi = ngayDangKi.text.toString()

            if (mssv != null && originalMaLopHocPhan != null) {
                val result = dbHelper.updateDangKy(mssv!!, originalMaLopHocPhan!!, selectedItem, newNgayDangKi)
                if (result) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không tìm thấy MSSV hoặc mã lớp học phần gốc", Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            if (mssv != null && originalMaLopHocPhan != null) {
                val result = dbHelper.deleteDangKy(mssv!!, originalMaLopHocPhan!!)
                if (result) {
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không tìm thấy MSSV hoặc mã lớp học phần gốc", Toast.LENGTH_LONG).show()
            }
        }

        btnback.setOnClickListener {
            finish()
        }
    }
}
