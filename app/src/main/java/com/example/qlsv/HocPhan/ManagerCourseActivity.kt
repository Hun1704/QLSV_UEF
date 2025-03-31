package HocPhan

import Controller.LopHocPhanDAO
import MODEL.LopHocPhan
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class ManagerCourseActivity : AppCompatActivity() {

    private val btnThem: Button by lazy { findViewById(R.id.btn_them_hp) }
    private val btnThoat: Button by lazy { findViewById(R.id.btnthoat_hp) }
    private val listView: ListView by lazy { findViewById(R.id.listview_hp) }
    private val spinnerHocki: Spinner by lazy { findViewById(R.id.rdhk3) }
    private val spinnerNamhoc: Spinner by lazy { findViewById(R.id.rdhk1) }
    private val editTimKiem: EditText by lazy { findViewById(R.id.edit_timkiem) }

    private lateinit var lopHocPhanDAO: LopHocPhanDAO
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var listLopHocPhan: MutableList<LopHocPhan> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_course)

        lopHocPhanDAO = LopHocPhanDAO(this)

        val years = arrayOf("23-24", "24-25", "25-26", "26-27")
        val hk = arrayOf(1, 2, 3)

        val adapterYear = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNamhoc.adapter = adapterYear

        val adapterHK = ArrayAdapter(this, android.R.layout.simple_spinner_item, hk)
        adapterHK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHocki.adapter = adapterHK

        spinnerNamhoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                loadData()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerHocki.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                loadData()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnThem.setOnClickListener {
            startActivity(Intent(this, AddCourseActivity::class.java))
        }

        btnThoat.setOnClickListener {
            finish()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val course = listLopHocPhan[position]
            val intent = Intent(this, UpdateCourseActivity::class.java).apply {
                putExtra("maLopHocPhan", course.maLopHocPhan)
                putExtra("ngayBatDau", course.ngayBatDau)
                putExtra("ngayKetThuc", course.ngayKetThuc)
                putExtra("soLuongMax", course.soLuongMax)
                putExtra("soLuongDangKi", course.soLuongDangKi)
                putExtra("hocKy", course.hocKy)
                putExtra("namHoc", course.namHoc)
                putExtra("thongTin", course.thongTin)
                putExtra("maHocPhan", course.maHocPhan)
            }
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val course = listLopHocPhan[position]
            lopHocPhanDAO.deleteLopHocPhan(course.maLopHocPhan)
            loadData()
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

        loadData()
    }

    private fun loadData() {
        val namhoc = spinnerNamhoc.selectedItem as String
        val hocky = spinnerHocki.selectedItem as Int

        listLopHocPhan.clear()
        listLopHocPhan.addAll(lopHocPhanDAO.getAllLopHocPhan(namhoc, hocky))
        val listString = listLopHocPhan.map { "${it.maLopHocPhan.toString().padEnd(30)}" +
                "${it.soLuongMax.toString().padEnd(10)}" +
                "${it.soLuongDangKi.toString().padEnd(10)}" +
                "${it.thongTin.padEnd(30)}" }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listString)
        listView.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
    }
}
