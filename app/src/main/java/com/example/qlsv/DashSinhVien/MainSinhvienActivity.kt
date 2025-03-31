package DashSinhVien
import HocPhan.ManagerCourseActivity
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
import com.example.qlsv.DashSinhVien.ProfileActivity
import com.example.qlsv.R
import com.example.qlsv.ThongKe.ThongKeActivity

class MainSinhvienActivity : AppCompatActivity() {
    private lateinit var mssv: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_sinhvien)
        // Nhận MSSV từ Intent
        mssv = intent.getStringExtra("MSSV") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gán sự kiện nhấn cho các CardView
        findViewById<CardView>(R.id.ProfileCard).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("MSSV", mssv)
            startActivity(intent)
        }

        findViewById<CardView>(R.id.hocPhanCard).setOnClickListener {
            val intent = Intent(this, XemHocphanActivity::class.java)
            intent.putExtra("MSSV", mssv)
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
