package DashSinhVien

import DBHelper
import Controller.LopHocPhanDAO
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R
import java.text.SimpleDateFormat
import java.util.Date

class DangkiHocphanActivity : AppCompatActivity() {

    private val maLopHocPhan: TextView by lazy { findViewById(R.id.mahp_sua) }
    private val ngayBatDau: TextView by lazy { findViewById(R.id.ngaybd_sua) }
    private val ngayKetThuc: TextView by lazy { findViewById(R.id.ngaykt_sua) }
    private val soLuongMax: TextView by lazy { findViewById(R.id.slmax_sua) }
    private val soLuongDangKi: TextView by lazy { findViewById(R.id.sldk_sua) }
    private val hocKy: TextView by lazy { findViewById(R.id.hocky_sua) }
    private val namHoc: TextView by lazy { findViewById(R.id.namhoc_sua) }
    private val thongTin: TextView by lazy { findViewById(R.id.thongtin_sua) }
    private val maHocPhan: TextView by lazy { findViewById(R.id.mahocphan_sua) }
    private val btndkhp: Button by lazy { findViewById(R.id.dk_hp) }
    private val back: Button by lazy { findViewById(R.id.back3) }
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangki_hocphan)

        dbHelper = DBHelper(this)
        // Khởi tạo DAO
        var lopHocPhanDAO = LopHocPhanDAO(this)

        // Lấy dữ liệu từ Intent
        val intent = intent
        maLopHocPhan.text = intent.getIntExtra("maLopHocPhan", 0).toString()
        ngayBatDau.text = intent.getStringExtra("ngayBatDau")
        ngayKetThuc.text = intent.getStringExtra("ngayKetThuc")
        soLuongMax.text = intent.getIntExtra("soLuongMax", 0).toString()
        soLuongDangKi.text = intent.getIntExtra("soLuongDangKi", 0).toString()
        hocKy.text = intent.getIntExtra("hocKy", 0).toString()
        namHoc.text = intent.getStringExtra("namHoc")
        thongTin.text = intent.getStringExtra("thongTin")
        maHocPhan.text = intent.getStringExtra("maHocPhan")

        // Đăng kí học phần click
        btndkhp.setOnClickListener {
            val mssv = intent.getStringExtra("MSSV")
            val maLopHocPhan = maLopHocPhan.text.toString().toInt()
            val ngayDangKi = SimpleDateFormat("yyyy-MM-dd").format(Date())

            if (mssv != null) {
                val result = dbHelper.dangKiHocPhan(mssv.toString(), maLopHocPhan, ngayDangKi)
                if (result) {
                    Toast.makeText(this, "Đăng kí học phần thành công", Toast.LENGTH_LONG).show()
                    finish() // Quay lại màn hình trước đó
                } else {
                    Toast.makeText(this, "Đăng kí học phần thất bại", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Không lấy được MSSV", Toast.LENGTH_LONG).show()
            }
        }

        // Quay lại màn hình quản lý lớp học phần
        back.setOnClickListener {
            finish()
        }
    }
}
