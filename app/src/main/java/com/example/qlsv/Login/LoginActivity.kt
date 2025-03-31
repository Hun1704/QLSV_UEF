package Login

import DBHelper
import DashSinhVien.MainSinhvienActivity
import Hocvien.ManagerSinhvienActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R
import com.example.qlsv.MainActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var dangnhap: Button
    private lateinit var user: TextInputEditText
    private lateinit var pass: TextInputEditText
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(this)
        user = findViewById(R.id.editUser)
        pass = findViewById(R.id.password)
        dangnhap = findViewById(R.id.btnlogin)

        dangnhap.setOnClickListener {
            val username = user.text.toString()
            val password = pass.text.toString()

            Log.d("LoginActivity", "Username: $username, Password: $password")

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (username == "ql" && password == "123") {
                    Toast.makeText(this, "Bạn đã đăng nhập thành công", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else if (dbHelper.checkUser(username, password)) {
                    Toast.makeText(this, "Bạn đã đăng nhập thành công", Toast.LENGTH_LONG).show()

                    // Lấy MSSV từ cơ sở dữ liệu
                    val mssv = dbHelper.getMssvByUsername(username)

                    // Tạo intent và truyền MSSV qua ProfileActivity
                    val intent = Intent(this, MainSinhvienActivity::class.java)
                    intent.putExtra("MSSV", mssv)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Mật khẩu hoặc tài khoản không đúng", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Mời bạn nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
            }
        }
    }
}
