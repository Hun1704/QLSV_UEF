package com.example.qlsv.Controller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.qlsv.Model.HocPhan
import DBHelper
import android.util.Log

class HocPhanDAO(private val context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    // Thêm dữ liệu
    fun insertHocPhan(h: HocPhan): Int {
        val values = ContentValues().apply {
            put("MaHocPhan", h.maHocPhan)
            put("TenHocPhan", h.tenHocPhan)
            put("SoTiet", h.soTiet)
        }

        val result = db.insert("HOCPHAN", null, values)
        return if (result == -1L) {
            -1 // insert thất bại
        } else {
            1 // insert thành công
        }
    }
    // Hiển thị dữ liệu
    val allHocPhanToString: List<String>
        get() {
            val ls = mutableListOf<String>()
            val cursor = db.query("HOCPHAN", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val h = HocPhan(
                        maHocPhan = cursor.getInt(cursor.getColumnIndexOrThrow("MaHocPhan")),
                        tenHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("TenHocPhan")),
                        soTiet = cursor.getInt(cursor.getColumnIndexOrThrow("SoTiet")),
                    )
                    val chuoi = "${h.maHocPhan.toString().padEnd(15)} - ${h.tenHocPhan.toString().padEnd(15)} - ${h.soTiet.toString().padEnd(5)} "
                    ls.add(chuoi)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return ls
        }

    // Xóa
    fun deleteHocPhan(maHocPhan:String): Int {
        val result = db.delete("HOCPHAN", "MaHocPhan=?", arrayOf(maHocPhan))
        return if (result <= 0) {
            -1 // xóa thất bại
        } else {
            1 // xóa thành công
        }
    }

    // Sửa
    fun updateHocPhan(h: HocPhan): Int {
        val values = ContentValues().apply {
            put("TenHocPhan", h.tenHocPhan)
            put("SoTiet", h.soTiet)
        }
        val result = db.update("HOCPHAN", values, "MaHocPhan=?", arrayOf(h.maHocPhan.toString()))
        return if (result <= 0) {
            -1 // update thất bại
        } else {
            1 // update thành công
        }
    }

    // Lấy thông tin học phần theo MaHocPhan
    fun getHocPhan(maHocPhan: String): HocPhan? {
        val cursor = db.query("HOCPHAN", null, "MaHocPhan=?", arrayOf(maHocPhan), null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val hocPhan = HocPhan(
                maHocPhan = cursor.getInt(cursor.getColumnIndexOrThrow("MaHocPhan")),
                tenHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("TenHocPhan")),
                soTiet = cursor.getInt(cursor.getColumnIndexOrThrow("SoTiet")),
            )
            cursor.close()
            hocPhan
        } else {
            cursor?.close()
            null
        }
    }
}
