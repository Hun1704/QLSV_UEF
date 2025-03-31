package com.example.qlsv.ThongKe

import DBHelper
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.qlsv.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

@Suppress("DEPRECATION")
class ThongKeActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var barChartHocPhan: BarChart
    private lateinit var barChartSinhVien: BarChart
    private lateinit var btnExit: Button

    // Biến toàn cục để giữ nhãn trục x
    private var xLabels = mutableListOf<String>()
    private var classLabels = mutableListOf<String>()
    private var studentLabels = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_ke)

        lineChart = findViewById(R.id.lineChart)
        barChartHocPhan = findViewById(R.id.barChartHocPhan)
        barChartSinhVien = findViewById(R.id.barChartSinhVien)
        btnExit = findViewById(R.id.btnExit)

        setupLineChart()
        setupBarChartHocPhan()
        setupBarChartSinhVien()

        btnExit.setOnClickListener {
            finish()
        }
    }

    private fun setupLineChart() {
        val entries = getCourseDataPerSemester()
        val dataSet = LineDataSet(entries, "Số môn học theo học kỳ")
        val lineData = LineData(dataSet)

        lineChart.data = lineData
        lineChart.description.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.axisRight.isEnabled = false

        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index in xLabels.indices) xLabels[index] else value.toString()
            }
        }

        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.labelRotationAngle = -45f // Xoay nhãn trục x để dễ đọc hơn
        lineChart.invalidate() // refresh
    }

    private fun setupBarChartHocPhan() {
        val entries = getThongKeHocPhanData()

        val dataSet = BarDataSet(entries, "Số học viên theo học phần")
        dataSet.color = resources.getColor(R.color.colorPrimary) // Màu sắc cho tập dữ liệu đầu tiên

        val barData = BarData(dataSet)

        barChartHocPhan.data = barData
        barChartHocPhan.description.isEnabled = false
        barChartHocPhan.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartHocPhan.axisRight.isEnabled = false

        // Sử dụng ValueFormatter để hiển thị tên lớp trên trục x
        barChartHocPhan.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index in classLabels.indices) classLabels[index] else value.toString()
            }
        }

        barChartHocPhan.xAxis.granularity = 1f
        barChartHocPhan.xAxis.labelRotationAngle = -45f // Xoay nhãn trục x để dễ đọc hơn
        barChartHocPhan.invalidate() // refresh
    }

    private fun setupBarChartSinhVien() {
        val entries = getThongKeSinhVienData()

        val dataSet = BarDataSet(entries, "Số học phần theo sinh viên")
        dataSet.color = resources.getColor(R.color.colorAccent) // Màu sắc cho tập dữ liệu thứ hai

        val barData = BarData(dataSet)

        barChartSinhVien.data = barData
        barChartSinhVien.description.isEnabled = false
        barChartSinhVien.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartSinhVien.axisRight.isEnabled = false

        // Sử dụng ValueFormatter để hiển thị tên lớp trên trục x
        barChartSinhVien.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index in studentLabels.indices) studentLabels[index] else value.toString()
            }
        }

        barChartSinhVien.xAxis.granularity = 1f
        barChartSinhVien.xAxis.labelRotationAngle = -45f // Xoay nhãn trục x để dễ đọc hơn
        barChartSinhVien.invalidate() // refresh
    }

    private fun getCourseDataPerSemester(): List<Entry> {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase
        val entries = mutableListOf<Entry>()

        val cursor = db.rawQuery("SELECT NamHoc, HocKy, COUNT(*) as count FROM LOPHOCPHAN GROUP BY NamHoc, HocKy", null)
        xLabels.clear()
        if (cursor.moveToFirst()) {
            do {
                val namhoc = cursor.getInt(cursor.getColumnIndexOrThrow("NamHoc"))
                val hocky = cursor.getInt(cursor.getColumnIndexOrThrow("HocKy"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count")).toFloat()
                val label = "$namhoc.$hocky"
                xLabels.add(label)
                entries.add(Entry((xLabels.size - 1).toFloat(), count))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return entries
    }

    private fun getThongKeHocPhanData(): List<BarEntry> {
        val dbHelper = DBHelper(this)
        val thongKeHocPhan = dbHelper.getThongKeHocPhan()
        val entries = mutableListOf<BarEntry>()
        classLabels.clear()

        thongKeHocPhan.forEachIndexed { index, item ->
            val soLuongDangKy = item["SoLuongDangKy"]?.toFloat() ?: 0f
            entries.add(BarEntry(index.toFloat(), soLuongDangKy))
            classLabels.add(item["TenHocPhan"].orEmpty())
        }

        return entries
    }

    private fun getThongKeSinhVienData(): List<BarEntry> {
        val dbHelper = DBHelper(this)
        val thongKeSinhVien = dbHelper.getThongKeSinhVien()
        val entries = mutableListOf<BarEntry>()
        studentLabels.clear()

        thongKeSinhVien.forEachIndexed { index, item ->
            val count = thongKeSinhVien.count { it["MSSV"] == item["MSSV"] }.toFloat()
            entries.add(BarEntry(index.toFloat(), count))
            studentLabels.add(item["HoTen"].orEmpty())
        }

        return entries
    }
}
