package com.example.qlsv.DashSinhVien

import DBHelper
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private lateinit var mssvText: TextView
    private lateinit var hoTenText: TextView
    private lateinit var gioiTinhText: TextView
    private lateinit var sdtText: TextView
    private lateinit var emailText: TextView
    private lateinit var lopText: TextView
    private lateinit var nienKhoaText: TextView
    private lateinit var trinhTrangHocText: TextView
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        dbHelper = DBHelper(this)
        mssvText = findViewById(R.id.mssvText)
        hoTenText = findViewById(R.id.hoTenText)
        gioiTinhText = findViewById(R.id.gioiTinhText)
        sdtText = findViewById(R.id.sdtText)
        emailText = findViewById(R.id.emailText)
        lopText = findViewById(R.id.lopText)
        nienKhoaText = findViewById(R.id.nienKhoaText)
        trinhTrangHocText = findViewById(R.id.trinhTrangHocText)
        btnExit = findViewById(R.id.btnExit)

        val mssv = intent.getStringExtra("MSSV")
        if (mssv != null) {
            val studentProfile = dbHelper.getStudentProfile(mssv)
            studentProfile?.let {
                mssvText.text = it["MSSV"]
                hoTenText.text = it["HoTen"]
                gioiTinhText.text = it["GioiTinh"]
                sdtText.text = it["SDT"]
                emailText.text = it["EmailEdu"]
                lopText.text = it["Lop"]
                nienKhoaText.text = it["NienKhoa"]
                trinhTrangHocText.text = it["TrinhTrangHoc"]

            }
        }

        btnExit.setOnClickListener {
            finish()
        }
    }
}
