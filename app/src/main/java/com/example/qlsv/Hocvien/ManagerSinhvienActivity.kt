package Hocvien


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.SinhVienDAO
import com.example.qlsv.MainActivity
import com.example.qlsv.R


@Suppress("DEPRECATION")
class ManagerSinhvienActivity : AppCompatActivity() {

    private val them: Button by lazy { findViewById(R.id.btnthem_sv) }

    private val thoat: Button by lazy { findViewById(R.id.btnthoat_sv) }
    private val lview: ListView by lazy { findViewById(R.id.listview_sv) }
    private val timkiem: EditText by lazy { findViewById(R.id.edit_timkiem) }

    private lateinit var arrayAdapter_sv: ArrayAdapter<String>
    private lateinit var sinhVienDAO: SinhVienDAO
    private var list_sv: MutableList<String> = mutableListOf()
    private var textItem: String? = null
    private var subTextItem: String? = null
    private var m_position = 0

    companion object {
        private const val REQUEST_CODE_UPDATE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_sinhvien)

        initView()
        loadData()
        setupListeners()
    }

    private fun initView() {
        sinhVienDAO = SinhVienDAO(this)
        list_sv = sinhVienDAO.allSinhVienToString.toMutableList()
        arrayAdapter_sv = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_sv)
        lview.adapter = arrayAdapter_sv
        registerForContextMenu(lview)
    }

    private fun loadData() {
        list_sv.clear()
        list_sv.addAll(sinhVienDAO.allSinhVienToString)
        arrayAdapter_sv.notifyDataSetChanged()
    }

    private fun setupListeners() {
        them.setOnClickListener {
            startActivity(Intent(this, AddSinhvienActivity::class.java))
        }

        thoat.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        lview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            textItem = lview.getItemAtPosition(position) as String
            subTextItem = textItem?.substring(0, textItem!!.indexOf(" "))
            m_position = position

            // Lấy thông tin sinh viên từ database
            val sinhVien = sinhVienDAO.getSinhVien(subTextItem!!)

            // Tạo Intent và truyền dữ liệu sang UpdateStudentActivity
            val intent = Intent(this, UpdateSinhvienActivity::class.java).apply {
                putExtra("mssv", sinhVien?.mssv)
                putExtra("hoten", sinhVien?.hoten)
                putExtra("gioitinh", sinhVien?.gioitinh)
                putExtra("sdt", sinhVien?.sdt)
                putExtra("emailedu", sinhVien?.emailedu)
                putExtra("lop", sinhVien?.lop)
                putExtra("nienkhoa", sinhVien?.nienkhoa)
                putExtra("trinhtranghoc", sinhVien?.trinhtranghoc)
            }
            startActivityForResult(intent, REQUEST_CODE_UPDATE)
        }

        timkiem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                arrayAdapter_sv.filter.filter(s)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, 0, 0, "Xóa dữ liệu")
        menu.add(Menu.NONE, 1, 0, "Gửi email")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            0 -> {
                try {
                    subTextItem?.let { sinhVienDAO.deleteSinhVien(it) }
                    list_sv.removeAt(menuInfo.position)
                    arrayAdapter_sv.notifyDataSetChanged()
                    Toast.makeText(this, "Xóa thành công $textItem", Toast.LENGTH_LONG).show()
                } catch (ex: Exception) {
                    Toast.makeText(this, "Xóa không thành công $textItem", Toast.LENGTH_LONG).show()
                }
                true
            }
            1 -> {
                startActivity(Intent(this, ManagerSinhvienActivity::class.java))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK) {
            loadData()
        }
    }


}
