package Gmail

import DBHelper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseBooleanArray
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R

class SendGmailActivity : AppCompatActivity() {

    private lateinit var searchBar: EditText
    private lateinit var listView: ListView
    private lateinit var sendEmailButton: Button
    private lateinit var exitButton: Button
    private lateinit var adapter: SimpleAdapter
    private lateinit var displayList: MutableList<Map<String, String>>
    private lateinit var selectedEmails: MutableSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_gmail)

        searchBar = findViewById(R.id.edit_timkiem)
        listView = findViewById(R.id.listview_)
        sendEmailButton = findViewById(R.id.send_email)
        exitButton = findViewById(R.id.btnthoat_sv)
        selectedEmails = mutableSetOf()

        loadEmails()

        adapter = SimpleAdapter(
            this,
            displayList,
            R.layout.list_item_with_checkbox,
            arrayOf("MSSV", "HoTen", "EmailEdu"),
            intArrayOf(R.id.mssvTextView, R.id.nameTextView, R.id.emailTextView)
        )

        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        sendEmailButton.setOnClickListener {
            // Cập nhật selectedEmails khi nút gửi email được nhấn
            updateSelectedEmails()
            openEmailClient()
        }

        exitButton.setOnClickListener {
            finish()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val email = displayList[position]["EmailEdu"]
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            checkBox.isChecked = !checkBox.isChecked
            if (checkBox.isChecked) {
                email?.let { selectedEmails.add(it) }
            } else {
                email?.let { selectedEmails.remove(it) }
            }
            // Log để kiểm tra các email đã chọn
            println("Selected Emails: $selectedEmails")
        }
    }

    private fun loadEmails() {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        displayList = mutableListOf()

        val query = "SELECT MSSV, HoTen, EmailEdu FROM SINHVIEN"

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("EmailEdu"))
                val item = mapOf("MSSV" to mssv, "HoTen" to name, "EmailEdu" to email)
                displayList.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        adapter = SimpleAdapter(
            this,
            displayList,
            R.layout.list_item_with_checkbox,
            arrayOf("MSSV", "HoTen", "EmailEdu"),
            intArrayOf(R.id.mssvTextView, R.id.nameTextView, R.id.emailTextView)
        )
        listView.adapter = adapter
    }

    private fun filter(text: String) {
        val filteredList = displayList.filter {
            it["MSSV"]!!.contains(text, true) ||
                    it["HoTen"]!!.contains(text, true) ||
                    it["EmailEdu"]!!.contains(text, true)
        }
        adapter = SimpleAdapter(
            this,
            filteredList as MutableList<Map<String, String>>,
            R.layout.list_item_with_checkbox,
            arrayOf("MSSV", "HoTen", "EmailEdu"),
            intArrayOf(R.id.mssvTextView, R.id.nameTextView, R.id.emailTextView)
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun updateSelectedEmails() {
        val checkedItemPositions: SparseBooleanArray = listView.checkedItemPositions
        selectedEmails.clear()
        for (i in 0 until checkedItemPositions.size()) {
            val position = checkedItemPositions.keyAt(i)
            if (checkedItemPositions.valueAt(i)) {
                val email = displayList[position]["EmailEdu"]
                email?.let { selectedEmails.add(it) }
            }
        }
        println("Selected Emails: $selectedEmails")
    }

    private fun openEmailClient() {
        val recipientsList = arrayOf("a@gmail.com", "b@gmail.com", "c@gmail.com")
        val subject = "Thông báo"
        val body = "Xin chào, đây là nội dung thông báo."

        val sendIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Chỉ email apps mới handle được
            putExtra(Intent.EXTRA_EMAIL, recipientsList)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            startActivity(Intent.createChooser(sendIntent, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
