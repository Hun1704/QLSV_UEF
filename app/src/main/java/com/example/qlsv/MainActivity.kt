package com.example.qlsv

import Gmail.SendGmailActivity
import HocPhan.ManagerCourseActivity
import HocPhan_.ManagerHHocphanActivity
import Hocvien.ManagerSinhvienActivity
import Login.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cardview.widget.CardView

import com.example.qlsv.R
import com.example.qlsv.ThongKe.ThongKeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gán sự kiện nhấn cho các CardView
        findViewById<CardView>(R.id.hocVienCard).setOnClickListener {
            val intent = Intent(this, ManagerSinhvienActivity::class.java)
            startActivity(intent)
        }

        findViewById<CardView>(R.id.hocPhanCard).setOnClickListener {
            val intent = Intent(this, ManagerCourseActivity::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.EmailCard).setOnClickListener {
            val intent = Intent(this, SendGmailActivity::class.java)
            startActivity(intent)
        }

        findViewById<CardView>(R.id.ThongKeCard).setOnClickListener {
            val intent = Intent(this, ThongKeActivity::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.HPCard).setOnClickListener {
            val intent = Intent(this, ManagerHHocphanActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
