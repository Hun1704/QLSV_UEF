package com.example.qlsv.Controller

import DBHelper
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.qlsv.Model.SinhVien

class SinhVienDAO(private val context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    // 1. Thêm dữ liệu
    fun insertSinhVien(s: SinhVien): Int {
        val values = ContentValues().apply {
            put("MSSV", s.mssv)
            put("HoTen", s.hoten)
            put("GioiTinh", s.gioitinh)
            put("SDT", s.sdt)
            put("EmailEdu", s.emailedu)
            put("Lop", s.lop)
            put("NienKhoa", s.nienkhoa)
            put("TrinhTrangHoc", s.trinhtranghoc)
        }
        val result = db.insert("SINHVIEN", null, values)
        return if (result == -1L) {
            -1 // insert thất bại
        } else {
            1 // insert thành công
        }
    }


    // 2. Hiển thị dữ liệu
    val allSinhVienToString: List<String>
        get() {
            val ls = mutableListOf<String>()
            val cursor = db.query("SINHVIEN", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val s = SinhVien(
                        mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV")),
                        hoten = cursor.getString(cursor.getColumnIndexOrThrow("HoTen")),
                        gioitinh = cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh")),
                        sdt = "",
                        emailedu = "",
                        lop = cursor.getString(cursor.getColumnIndexOrThrow("Lop")),
                        nienkhoa = "",
                        trinhtranghoc = cursor.getString(cursor.getColumnIndexOrThrow("TrinhTrangHoc"))
                    )
                    val chuoi = "${s.mssv} - ${s.hoten} - ${s.gioitinh} - ${s.sdt} - ${s.emailedu} - ${s.lop} - ${s.nienkhoa} - ${s.trinhtranghoc}"
                    ls.add(chuoi)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return ls
        }

    // Xóa
    fun deleteSinhVien(mssv: String): Int {
        val result = db.delete("SINHVIEN", "MSSV=?", arrayOf(mssv))
        return if (result <= 0) {
            -1 // xóa thất bại
        } else {
            1 // xóa thành công
        }
    }

    // Sửa
    fun updateSinhVien(s: SinhVien): Int {
        val values = ContentValues().apply {
            put("HoTen", s.hoten)
            put("GioiTinh", s.gioitinh)
            put("SDT", s.sdt)
            put("EmailEdu", s.emailedu)
            put("Lop", s.lop)
            put("NienKhoa", s.nienkhoa)
            put("TrinhTrangHoc", s.trinhtranghoc)
        }
        val result = db.update("SINHVIEN", values, "MSSV=?", arrayOf(s.mssv))
        return if (result <= 0) {
            -1 // update thất bại
        } else {
            1 // update thành công
        }
    }

    // Lấy thông tin sinh viên theo MSSV
    fun getSinhVien(mssv: String): SinhVien? {
        val cursor = db.query("SINHVIEN", null, "MSSV=?", arrayOf(mssv), null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val sinhVien = SinhVien(
                mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV")),
                hoten = cursor.getString(cursor.getColumnIndexOrThrow("HoTen")),
                gioitinh = cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh")),
                sdt = cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                emailedu = cursor.getString(cursor.getColumnIndexOrThrow("EmailEdu")),
                lop = cursor.getString(cursor.getColumnIndexOrThrow("Lop")),
                nienkhoa = cursor.getString(cursor.getColumnIndexOrThrow("NienKhoa")),
                trinhtranghoc = cursor.getString(cursor.getColumnIndexOrThrow("TrinhTrangHoc"))
            )
            cursor.close()
            sinhVien
        } else {
            cursor?.close()
            null
        }
    }
}
