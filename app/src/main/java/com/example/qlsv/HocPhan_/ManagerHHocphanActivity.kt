package HocPhan_

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.Controller.HocPhanDAO
import com.example.qlsv.MainActivity
import com.example.qlsv.R

@Suppress("DEPRECATION")
class ManagerHHocphanActivity : AppCompatActivity() {

    private val them: Button by lazy { findViewById(R.id.btnthem_hp) }
    private val thoat: Button by lazy { findViewById(R.id.btnthoat_hp) }
    private val lview: ListView by lazy { findViewById(R.id.listview_hp) }
    private val timkiem: EditText by lazy { findViewById(R.id.edit_timkiem) }

    private lateinit var arrayAdapter_hp: ArrayAdapter<String>
    private lateinit var hocPhanDAO: HocPhanDAO
    private var list_hp: MutableList<String> = mutableListOf()
    private var textItem: String? = null
    private var subTextItem: String? = null
    private var m_position = 0

    private lateinit var updateLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val REQUEST_CODE_UPDATE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_hhocphan)

        initView()
        loadData()
        setupListeners()

        updateLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                loadData()
            }
        }
    }

    private fun initView() {
        hocPhanDAO = HocPhanDAO(this)
        list_hp = hocPhanDAO.allHocPhanToString.toMutableList()
        arrayAdapter_hp = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_hp)
        lview.adapter = arrayAdapter_hp
        registerForContextMenu(lview)
    }

    private fun loadData() {
        list_hp.clear()
        list_hp.addAll(hocPhanDAO.allHocPhanToString)
        arrayAdapter_hp.notifyDataSetChanged()
    }

    private fun setupListeners() {
        them.setOnClickListener {
            startActivity(Intent(this, AddHHocphanActivity::class.java))
        }

        thoat.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        lview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            textItem = lview.getItemAtPosition(position) as String
            subTextItem = textItem?.substring(0, textItem!!.indexOf(" "))
            m_position = position

            // Lấy thông tin học phần từ database
            val hocPhan = hocPhanDAO.getHocPhan(subTextItem!!)

            // Tạo Intent và truyền dữ liệu sang UpdateHocphanActivity
            val intent = Intent(this, UpdateHHocphanActivity::class.java).apply {
                putExtra("maHocPhan", hocPhan?.maHocPhan)
                putExtra("tenHocPhan", hocPhan?.tenHocPhan)
                putExtra("soTiet", hocPhan?.soTiet)
//                putExtra("maHocPhanVT", hocPhan?.maHocPhanVT)
            }
            startActivity(intent)
        }

        timkiem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                arrayAdapter_hp.filter.filter(s)
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
                    subTextItem?.let { hocPhanDAO.deleteHocPhan(it) }
                    list_hp.removeAt(menuInfo.position)
                    arrayAdapter_hp.notifyDataSetChanged()
                    Toast.makeText(this, "Xóa thành công $textItem", Toast.LENGTH_LONG).show()
                } catch (ex: Exception) {
                    Toast.makeText(this, "Xóa không thành công $textItem", Toast.LENGTH_LONG).show()
                }
                true
            }
            1 -> {
                startActivity(Intent(this, ManagerHHocphanActivity::class.java))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
