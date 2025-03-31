package Controller

import DBHelper
import MODEL.LopHocPhan
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class LopHocPhanDAO(private val context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun insertLopHocPhan(lopHocPhan: LopHocPhan): Long {
        val values = ContentValues().apply {
            put("MaLopHocPhan", lopHocPhan.maLopHocPhan)
            put("NgayBatDau", lopHocPhan.ngayBatDau)
            put("NgayKetThuc", lopHocPhan.ngayKetThuc)
            put("SoLuong_Max", lopHocPhan.soLuongMax)
            put("SoLuongDangKi", lopHocPhan.soLuongDangKi)
            put("HocKy", lopHocPhan.hocKy)
            put("NamHoc", lopHocPhan.namHoc)
            put("ThongTin", lopHocPhan.thongTin)
            put("MaHocPhan", lopHocPhan.maHocPhan)
        }
        return db.insert("LOPHOCPHAN", null, values)
    }

    fun getAllLopHocPhan(namHoc: String, hocKy: Int): List<LopHocPhan> {
        val list = mutableListOf<LopHocPhan>()
        val cursor = db.query(
            "LOPHOCPHAN", null, "NamHoc=? AND HocKy=?", arrayOf(namHoc, hocKy.toString()),
            null, null, null
        )
        with(cursor) {
            while (moveToNext()) {
                val lopHocPhan = LopHocPhan(
                    getInt(getColumnIndexOrThrow("MaLopHocPhan")),
                    getString(getColumnIndexOrThrow("NgayBatDau")),
                    getString(getColumnIndexOrThrow("NgayKetThuc")),
                    getInt(getColumnIndexOrThrow("SoLuong_Max")),
                    getInt(getColumnIndexOrThrow("SoLuongDangKi")),
                    getInt(getColumnIndexOrThrow("HocKy")),
                    getString(getColumnIndexOrThrow("NamHoc")),
                    getString(getColumnIndexOrThrow("ThongTin")),
                    getString(getColumnIndexOrThrow("MaHocPhan"))
                )
                list.add(lopHocPhan)
            }
        }
        cursor.close()
        return list
    }

    fun updateLopHocPhan(lopHocPhan: LopHocPhan): Int {
        val values = ContentValues().apply {
            put("NgayBatDau", lopHocPhan.ngayBatDau)
            put("NgayKetThuc", lopHocPhan.ngayKetThuc)
            put("SoLuong_Max", lopHocPhan.soLuongMax)
            put("SoLuongDangKi", lopHocPhan.soLuongDangKi)
            put("HocKy", lopHocPhan.hocKy)
            put("NamHoc", lopHocPhan.namHoc)
            put("ThongTin", lopHocPhan.thongTin)
            put("MaHocPhan", lopHocPhan.maHocPhan)
        }
        return db.update("LOPHOCPHAN", values, "MaLopHocPhan=?", arrayOf(lopHocPhan.maLopHocPhan.toString()))
    }

    fun getLopHocPhanByMaHocPhan(maHocPhan: Int): List<LopHocPhan> {
        val ls = mutableListOf<LopHocPhan>()
        val cursor = db.query("LOPHOCPHAN", null, "MaHocPhan=?", arrayOf(maHocPhan.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val lopHocPhan = LopHocPhan(
                    cursor.getInt(cursor.getColumnIndexOrThrow("MaLopHocPhan")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NgayBatDau")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NgayKetThuc")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong_Max")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("SoLuongDangKi")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("HocKy")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NamHoc")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ThongTin")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("MaHocPhan")).toString()
                )
                ls.add(lopHocPhan)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ls
    }

    fun deleteLopHocPhan(maLopHocPhan: Int): Int {
        return db.delete("LOPHOCPHAN", "MaLopHocPhan=?", arrayOf(maLopHocPhan.toString()))
    }
}
