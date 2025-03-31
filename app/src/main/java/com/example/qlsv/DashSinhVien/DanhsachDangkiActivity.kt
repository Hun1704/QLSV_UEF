package DashSinhVien

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R
import DBHelper
import android.util.Log

@Suppress("DEPRECATION")
class DanhsachDangkiActivity : AppCompatActivity() {

    private val thoat: Button by lazy { findViewById(R.id.btnthoat_dk) }
    private val lview: ListView by lazy { findViewById(R.id.listview_dk) }

    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var dbHelper: DBHelper
    private var list_dk: MutableList<String> = mutableListOf()
    private var mssv: String? = null

    companion object {
        private const val REQUEST_CODE_UPDATE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danhsach_dangki)

        dbHelper = DBHelper(this)
        mssv = intent.getStringExtra("MSSV")

        initView()
        mssv?.let { loadData(it) }
        setupListeners()
    }

    private fun initView() {
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_dk)
        lview.adapter = arrayAdapter
    }

    private fun loadData(mssv: String) {
        list_dk.clear()
        list_dk.addAll(dbHelper.getAllDangKy(mssv))
        arrayAdapter.notifyDataSetChanged()
    }

    private fun setupListeners() {
        thoat.setOnClickListener {
            finish()
        }

        lview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = lview.getItemAtPosition(position) as String
            Log.d("DanhsachDangkiActivity", "SelectedItem: $selectedItem")

            // Tách các thông tin từ selectedItem để truyền vào SuaDanhsachDangkiActivity
            try {
                val parts = selectedItem.split(" - ")
                Log.d("DanhsachDangkiActivity", "Parts: $parts")

                if (parts.size == 4) {
                    val maLopHocPhan = parts[0].trim().toInt()
                    val maHocPhan = parts[1].trim()
                    val thongTin = parts[2].trim()
                    val ngayDangKi = parts[3].trim()

                    val intent = Intent(this, SuaDanhsachDangkiActivity::class.java).apply {
                        putExtra("maLopHocPhan", maLopHocPhan)
                        putExtra("maHocPhan", maHocPhan)
                        putExtra("thongTin", thongTin)
                        putExtra("ngayDangKi", ngayDangKi)
                        putExtra("MSSV", mssv)
                    }
                    startActivityForResult(intent, REQUEST_CODE_UPDATE)
                } else {
                    Log.e("DanhsachDangkiActivity", "Selected item format is incorrect: $selectedItem")
                }
            } catch (e: Exception) {
                Log.e("DanhsachDangkiActivity", "Error parsing selected item: $selectedItem", e)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK) {
            mssv?.let { loadData(it) }
        }
    }
}
