package DashSinhVien

import Controller.LopHocPhanDAO
import MODEL.LopHocPhan
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class XemLopHocphanActivity : AppCompatActivity() {

    private val btnThoat: Button by lazy { findViewById(R.id.btnthoat_hp) }
    private val listView: ListView by lazy { findViewById(R.id.listview_hp) }
    private val editTimKiem: EditText by lazy { findViewById(R.id.edit_timkiem) }

    private lateinit var lopHocPhanDAO: LopHocPhanDAO
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var listLopHocPhan: MutableList<LopHocPhan> = mutableListOf()

    private var mssv: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xem_lop_hocphan)

        lopHocPhanDAO = LopHocPhanDAO(this)

        // Lấy dữ liệu từ Intent
        val maHocPhan = intent.getIntExtra("maHocPhan", -1)
        mssv = intent.getStringExtra("MSSV")

        btnThoat.setOnClickListener {
            finish()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val course = listLopHocPhan[position]
            val intent = Intent(this, DangkiHocphanActivity::class.java).apply {
                putExtra("maLopHocPhan", course.maLopHocPhan)
                putExtra("ngayBatDau", course.ngayBatDau)
                putExtra("ngayKetThuc", course.ngayKetThuc)
                putExtra("soLuongMax", course.soLuongMax)
                putExtra("soLuongDangKi", course.soLuongDangKi)
                putExtra("hocKy", course.hocKy)
                putExtra("namHoc", course.namHoc)
                putExtra("thongTin", course.thongTin)
                putExtra("maHocPhan", course.maHocPhan)
                putExtra("MSSV", mssv)
            }
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val course = listLopHocPhan[position]
            lopHocPhanDAO.deleteLopHocPhan(course.maLopHocPhan)
            loadData(maHocPhan)
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show()
            true
        }

        // Thiết lập bộ lọc tìm kiếm
        editTimKiem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                arrayAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        loadData(maHocPhan)
    }

    private fun loadData(maHocPhan: Int) {
        listLopHocPhan.clear()
        listLopHocPhan.addAll(lopHocPhanDAO.getLopHocPhanByMaHocPhan(maHocPhan))
        val listString = listLopHocPhan.map {
            "${it.maLopHocPhan.toString().padEnd(30)}" +
                    "${it.soLuongMax.toString().padEnd(10)}" +
                    "${it.soLuongDangKi.toString().padEnd(10)}" +
                    "${it.thongTin.padEnd(30)}"
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listString)
        listView.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
    }
}
