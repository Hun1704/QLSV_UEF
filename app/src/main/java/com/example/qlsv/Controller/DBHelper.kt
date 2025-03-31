import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL("""
                CREATE TABLE KHOA (
                    MaKhoa INTEGER PRIMARY KEY,
                    TenKhoa TEXT,
                    MaKhoaVT TEXT
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE NGANH (
                    MaNganh INTEGER PRIMARY KEY,
                    TenNganh TEXT,
                    MaKhoa INTEGER,
                    FOREIGN KEY (MaKhoa) REFERENCES KHOA(MaKhoa)
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE SINHVIEN (
                    MSSV INTEGER PRIMARY KEY,
                    HoTen TEXT,
                    GioiTinh TEXT,
                    SDT TEXT,
                    EmailEdu TEXT,
                    Lop TEXT,
                    NienKhoa TEXT,
                    TrinhTrangHoc TEXT
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE HOCPHAN (
                    MaHocPhan INTEGER PRIMARY KEY,
                    TenHocPhan TEXT,
                    SoTiet INTEGER
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE LOPHOCPHAN (
                    MaLopHocPhan INTEGER PRIMARY KEY,
                    NgayBatDau DATE,
                    NgayKetThuc DATE,
                    SoLuong_Max INTEGER,
                    SoLuongDangKi INTEGER,
                    HocKy INTEGER,
                    NamHoc TEXT,
                    ThongTin TEXT,
                    MaHocPhan INTEGER,
                    FOREIGN KEY (MaHocPhan) REFERENCES HOCPHAN(MaHocPhan)
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE DANGKY (
                    MSSV INTEGER,
                    MaLopHocPhan INTEGER,
                    NgayDangKi DATE,
                    PRIMARY KEY (MSSV, MaLopHocPhan),
                    FOREIGN KEY (MSSV) REFERENCES SINHVIEN(MSSV),
                    FOREIGN KEY (MaLopHocPhan) REFERENCES LOPHOCPHAN(MaLopHocPhan)
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE HOCPHANTIENQUYET (
                    MaHocPhan INTEGER,
                    MaHocPhanTienQuyet INTEGER,
                    PRIMARY KEY (MaHocPhan, MaHocPhanTienQuyet),
                    FOREIGN KEY (MaHocPhan) REFERENCES HOCPHAN(MaHocPhan),
                    FOREIGN KEY (MaHocPhanTienQuyet) REFERENCES HOCPHAN(MaHocPhan)
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE HOCPHANTRUOC (
                    MaHocPhan INTEGER,
                    MaHocPhanTruoc INTEGER,
                    PRIMARY KEY (MaHocPhan, MaHocPhanTruoc),
                    FOREIGN KEY (MaHocPhan) REFERENCES HOCPHAN(MaHocPhan),
                    FOREIGN KEY (MaHocPhanTruoc) REFERENCES HOCPHAN(MaHocPhan)
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE CHUONGTRINHDAOTAO (
                    MaNganh INTEGER,
                    MaHocPhan INTEGER,
                    PRIMARY KEY (MaNganh, MaHocPhan),
                    FOREIGN KEY (MaNganh) REFERENCES NGANH(MaNganh),
                    FOREIGN KEY (MaHocPhan) REFERENCES HOCPHAN(MaHocPhan)
                )
            """.trimIndent())

            // Tạo bảng đăng nhập dựa vào thông tin sinh viên
            db.execSQL("""
                CREATE TABLE DANGNHAP (
                    MSSV INTEGER PRIMARY KEY,
                    User TEXT,
                    MatKhau TEXT,
                    FOREIGN KEY (MSSV) REFERENCES SINHVIEN(MSSV)
                )
            """.trimIndent())

            // Thêm dữ liệu mẫu
            db.execSQL("""
                INSERT INTO KHOA (MaKhoa, TenKhoa, MaKhoaVT) VALUES
                (1, 'Khoa Công Nghệ Thông Tin', 'CNTT'),
                (2, 'Khoa Kinh Tế', 'KT'),
                (3, 'Khoa Kỹ Thuật', 'KT'),
                (4, 'Khoa Y', 'Y'),
                (5, 'Khoa Luật', 'L'),
                (6, 'Khoa Xã Hội', 'XH'),
                (7, 'Khoa Nhân Văn', 'NV'),
                (8, 'Khoa Ngoại Ngữ', 'NN'),
                (9, 'Khoa Giáo Dục', 'GD'),
                (10, 'Khoa Thể Dục Thể Thao', 'TDTT'),
                (11, 'Khoa Môi Trường', 'MT'),
                (12, 'Khoa Kiến Trúc', 'KT'),
                (13, 'Khoa Dược', 'D'),
                (14, 'Khoa Âm Nhạc', 'AN'),
                (15, 'Khoa Nghệ Thuật', 'NT')
            """.trimIndent())

            db.execSQL("""
                INSERT INTO NGANH (MaNganh, TenNganh, MaKhoa) VALUES
                (1, 'Công Nghệ Phần Mềm', 1),
                (2, 'Kinh Tế Đối Ngoại', 2),
                (3, 'Cơ Điện Tử', 3),
                (4, 'Y Đa Khoa', 4),
                (5, 'Luật Kinh Tế', 5),
                (6, 'Xã Hội Học', 6),
                (7, 'Ngôn Ngữ Anh', 8),
                (8, 'Giáo Dục Tiểu Học', 9),
                (9, 'Quản Trị Kinh Doanh', 2),
                (10, 'Kỹ Thuật Xây Dựng', 3),
                (11, 'Điện Tử - Viễn Thông', 3),
                (12, 'Thể Dục Thể Thao', 10),
                (13, 'Quản Lý Môi Trường', 11),
                (14, 'Kiến Trúc Công Trình', 12),
                (15, 'Dược Học', 13)
            """.trimIndent())

            db.execSQL("""
                INSERT INTO SINHVIEN (MSSV, HoTen, GioiTinh, SDT, EmailEdu, Lop, NienKhoa, TrinhTrangHoc) VALUES
                (1, 'Nguyen Van A', 'Nam', '0123456789', 'a@gmail.com', 'K61', '2018-2022', 'Dang Hoc'),
                (2, 'Tran Thi B', 'Nu', '0123456790', 'b@gmail.com', 'K61', '2018-2022', 'Dang Hoc'),
                (3, 'Le Van C', 'Nam', '0123456791', 'c@gmail.com', 'K62', '2019-2023', 'Dang Hoc'),
                (4, 'Hoang Thi D', 'Nu', '0123456792', 'd@gmail.com', 'K62', '2019-2023', 'Dang Hoc'),
                (5, 'Pham Van E', 'Nam', '0123456793', 'e@gmail.com', 'K63', '2020-2024', 'Dang Hoc'),
                (6, 'Do Thi F', 'Nu', '0123456794', 'f@gmail.com', 'K63', '2020-2024', 'Dang Hoc'),
                (7, 'Nguyen Van G', 'Nam', '0123456795', 'g@gmail.com', 'K64', '2021-2025', 'Dang Hoc'),
                (8, 'Tran Thi H', 'Nu', '0123456796', 'h@gmail.com', 'K64', '2021-2025', 'Dang Hoc'),
                (9, 'Le Van I', 'Nam', '0123456797', 'i@gmail.com', 'K65', '2022-2026', 'Dang Hoc'),
                (10, 'Hoang Thi J', 'Nu', '0123456798', 'j@gmail.com', 'K65', '2022-2026', 'Dang Hoc'),
                (11, 'Pham Van K', 'Nam', '0123456799', 'k@gmail.com', 'K66', '2023-2027', 'Dang Hoc'),
                (12, 'Do Thi L', 'Nu', '0123456800', 'l@gmail.com', 'K66', '2023-2027', 'Dang Hoc'),
                (13, 'Nguyen Van M', 'Nam', '0123456801', 'm@gmail.com', 'K67', '2024-2028', 'Dang Hoc'),
                (14, 'Tran Thi N', 'Nu', '0123456802', 'n@gmail.com', 'K67', '2024-2028', 'Dang Hoc'),
                (15, 'Le Van O', 'Nam', '0123456803', 'o@gmail.com', 'K68', '2025-2029', 'Dang Hoc')
            """.trimIndent())

            db.execSQL("""
                INSERT INTO HOCPHAN (MaHocPhan, TenHocPhan, SoTiet) VALUES
                (1, 'Toán Cao Cấp', 45),
                (2, 'Vật Lý Đại Cương', 30),
                (3, 'Hóa Học Đại Cương', 30),
                (4, 'Lập Trình Cơ Bản', 60),
                (5, 'Cơ Sở Dữ Liệu', 45),
                (6, 'Lập Trình Hướng Đối Tượng', 60),
                (7, 'Mạng Máy Tính', 45),
                (8, 'Hệ Điều Hành', 45),
                (9, 'Trí Tuệ Nhân Tạo', 60),
                (10, 'Xử Lý Ảnh', 45),
                (11, 'Phân Tích Dữ Liệu', 60),
                (12, 'Kỹ Thuật Lập Trình', 60),
                (13, 'Cấu Trúc Dữ Liệu', 45),
                (14, 'Thiết Kế Web', 60),
                (15, 'Công Nghệ Phần Mềm', 60)
            """.trimIndent())

            db.execSQL("""
                INSERT INTO LOPHOCPHAN (MaLopHocPhan, NgayBatDau, NgayKetThuc, SoLuong_Max, SoLuongDangKi, HocKy, NamHoc, ThongTin, MaHocPhan) VALUES
                (1, '2024-01-10', '2024-05-20', 50, 45, 1, '23-24', 'Toán Cao Cấp', 1),
                (101, '2024-01-10', '2024-05-20', 50, 45, 1, '23-24', 'Toán Cao Cấp', 1),
                (102, '2024-01-10', '2024-05-20', 50, 45, 1, '23-24', 'Toán Cao Cấp', 1),
                (2, '2024-01-10', '2024-05-20', 50, 40, 2, '23-24', 'Vật Lý Đại Cương', 2),
                (3, '2024-01-10', '2024-05-20', 50, 48, 3, '23-24', 'Hóa Học Đại Cương', 3),
                (4, '2024-01-10', '2024-05-20', 50, 35, 1, '23-24', 'Lập Trình Cơ Bản', 4),
                (5, '2024-01-10', '2024-05-20', 50, 50, 2, '23-24', 'Cơ Sở Dữ Liệu', 5),
                (6, '2024-01-10', '2024-05-20', 50, 30, 3, '23-24', 'Lập Trình Hướng Đối Tượng', 6),
                (7, '2024-01-10', '2024-05-20', 50, 45, 1, '24-25', 'Mạng Máy Tính', 7),
                (8, '2024-01-10', '2024-05-20', 50, 40, 2, '24-25', 'Hệ Điều Hành', 8),
                (9, '2024-01-10', '2024-05-20', 50, 50, 3, '24-25', 'Trí Tuệ Nhân Tạo', 9),
                (10, '2024-01-10', '2024-05-20', 50, 45, 1, '25-26', 'Xử Lý Ảnh', 10),
                (11, '2024-01-10', '2024-05-20', 50, 30, 2, '25-26', 'Phân Tích Dữ Liệu', 11),
                (12, '2024-01-10', '2024-05-20', 50, 45, 3, '25-26', 'Kỹ Thuật Lập Trình', 12),
                (13, '2024-01-10', '2024-05-20', 50, 48, 1, '25-26', 'Cấu Trúc Dữ Liệu', 13),
                (14, '2024-01-10', '2024-05-20', 50, 40, 2, '25-26', 'Thiết Kế Web', 14),
                (15, '2024-01-10', '2024-05-20', 50, 35, 3, '25-26', 'Công Nghệ Phần Mềm', 15)
            """.trimIndent())

            db.execSQL("""
                INSERT INTO DANGKY (MSSV, MaLopHocPhan, NgayDangKi) VALUES
                (1, 1, '2023-12-01'),
                (1, 2, '2023-12-01'),
                (2, 2, '2023-12-01'),
                (1, 3, '2023-12-01'),
                (3, 3, '2023-12-01'),
                (4, 4, '2023-12-01'),
                (5, 5, '2023-12-01'),
                (6, 6, '2023-12-01'),
                (7, 7, '2023-12-01'),
                (8, 8, '2023-12-01'),
                (9, 9, '2023-12-01'),
                (10, 10, '2023-12-01'),
                (11, 11, '2023-12-01'),
                (12, 12, '2023-12-01'),
                (13, 13, '2023-12-01'),
                (14, 14, '2023-12-01'),
                (15, 15, '2023-12-01')
            """.trimIndent())

            db.execSQL("""
                INSERT INTO HOCPHANTIENQUYET (MaHocPhan, MaHocPhanTienQuyet) VALUES
                (1, 3),
                (2, 4),
                (3, 5),
                (4, 6),
                (5, 7),
                (6, 8),
                (7, 9),
                (8, 10),
                (9, 11),
                (10, 12),
                (11, 13),
                (12, 14),
                (13, 15),
                (14, 1),
                (15, 2)
            """.trimIndent())

            db.execSQL("""
                INSERT INTO HOCPHANTRUOC (MaHocPhan, MaHocPhanTruoc) VALUES
                (1, 2),
                (2, 3),
                (3, 4),
                (4, 5),
                (5, 6),
                (6, 7),
                (7, 8),
                (8, 9),
                (9, 10),
                (10, 11),
                (11, 12),
                (12, 13),
                (13, 14),
                (14, 15),
                (15, 1)
            """.trimIndent())

            db.execSQL("""
                INSERT INTO CHUONGTRINHDAOTAO (MaNganh, MaHocPhan) VALUES
                (1, 1),
                (2, 2),
                (3, 3),
                (4, 4),
                (5, 5),
                (6, 6),
                (7, 7),
                (8, 8),
                (9, 9),
                (10, 10),
                (11, 11),
                (12, 12),
                (13, 13),
                (14, 14),
                (15, 15)
            """.trimIndent())

            // Thêm dữ liệu mẫu cho bảng đăng nhập
            db.execSQL("""
                INSERT INTO DANGNHAP (MSSV, User, MatKhau) VALUES
                (1, 'abc', '123'),
                (2, 'abc123', '123')
            """.trimIndent())

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS KHOA")
        db.execSQL("DROP TABLE IF EXISTS NGANH")
        db.execSQL("DROP TABLE IF EXISTS SINHVIEN")
        db.execSQL("DROP TABLE IF EXISTS HOCPHAN")
        db.execSQL("DROP TABLE IF EXISTS LOPHOCPHAN")
        db.execSQL("DROP TABLE IF EXISTS DANGKY")
        db.execSQL("DROP TABLE IF EXISTS HOCPHANTIENQUYET")
        db.execSQL("DROP TABLE IF EXISTS HOCPHANTRUOC")
        db.execSQL("DROP TABLE IF EXISTS CHUONGTRINHDAOTAO")
        db.execSQL("DROP TABLE IF EXISTS DANGNHAP")
        onCreate(db)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM DANGNHAP WHERE User = ? AND MatKhau = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val result = cursor.count > 0
        cursor.close()
        return result
    }

    fun getThongKeHocPhan(): List<Map<String, String>> {
        val db = this.readableDatabase
        val query = """
            SELECT L.MaHocPhan, H.TenHocPhan, COUNT(D.MSSV) AS SoLuongDangKy
            FROM DANGKY D
            JOIN LOPHOCPHAN L ON D.MaLopHocPhan = L.MaLopHocPhan
            JOIN HOCPHAN H ON L.MaHocPhan = H.MaHocPhan
            GROUP BY L.MaHocPhan, H.TenHocPhan
            ORDER BY SoLuongDangKy DESC
        """
        val cursor = db.rawQuery(query, null)
        val results = mutableListOf<Map<String, String>>()
        if (cursor.moveToFirst()) {
            do {
                val maHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("MaHocPhan"))
                val tenHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("TenHocPhan"))
                val soLuongDangKy = cursor.getString(cursor.getColumnIndexOrThrow("SoLuongDangKy"))
                val item = mapOf("MaHocPhan" to maHocPhan, "TenHocPhan" to tenHocPhan, "SoLuongDangKy" to soLuongDangKy)
                results.add(item)
                Log.d("DBHelper", "HocPhan: $maHocPhan, TenHocPhan: $tenHocPhan, SoLuongDangKy: $soLuongDangKy")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return results
    }

    fun getThongKeSinhVien(): List<Map<String, String>> {
        val db = this.readableDatabase
        val query = """
            SELECT S.MSSV, S.HoTen, H.TenHocPhan
            FROM DANGKY D
            JOIN SINHVIEN S ON D.MSSV = S.MSSV
            JOIN LOPHOCPHAN L ON D.MaLopHocPhan = L.MaLopHocPhan
            JOIN HOCPHAN H ON L.MaHocPhan = H.MaHocPhan
            ORDER BY S.MSSV
        """
        val cursor = db.rawQuery(query, null)
        val results = mutableListOf<Map<String, String>>()
        if (cursor.moveToFirst()) {
            do {
                val mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV"))
                val hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HoTen"))
                val tenHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("TenHocPhan"))
                val item = mapOf("MSSV" to mssv, "HoTen" to hoTen, "TenHocPhan" to tenHocPhan)
                results.add(item)
                Log.d("DBHelper", "MSSV: $mssv, HoTen: $hoTen, TenHocPhan: $tenHocPhan")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return results
    }

    fun getMssvByUsername(username: String): String? {
        val db = this.readableDatabase
        val query = "SELECT MSSV FROM DANGNHAP WHERE User = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        var mssv: String? = null
        if (cursor.moveToFirst()) {
            mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV"))
        }
        cursor.close()
        return mssv
    }

    fun getStudentProfile(mssv: String): Map<String, String>? {
        val db = this.readableDatabase
        val query = "SELECT * FROM SINHVIEN WHERE MSSV = ?"
        val cursor = db.rawQuery(query, arrayOf(mssv))
        var studentProfile: Map<String, String>? = null
        if (cursor.moveToFirst()) {
            studentProfile = mapOf(
                "MSSV" to cursor.getString(cursor.getColumnIndexOrThrow("MSSV")),
                "HoTen" to cursor.getString(cursor.getColumnIndexOrThrow("HoTen")),
                "GioiTinh" to cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh")),
                "SDT" to cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                "EmailEdu" to cursor.getString(cursor.getColumnIndexOrThrow("EmailEdu")),
                "Lop" to cursor.getString(cursor.getColumnIndexOrThrow("Lop")),
                "NienKhoa" to cursor.getString(cursor.getColumnIndexOrThrow("NienKhoa")),
                "TrinhTrangHoc" to cursor.getString(cursor.getColumnIndexOrThrow("TrinhTrangHoc")),
            )
        }
        cursor.close()
        return studentProfile
    }
    fun dangKiHocPhan(mssv: String, maLopHocPhan: Int, ngayDangKi: String): Boolean {
        val db = this.writableDatabase
        return try {
            val query = "INSERT INTO DANGKY (MSSV, MaLopHocPhan, NgayDangKi) VALUES (?, ?, ?)"
            val stmt = db.compileStatement(query)
            stmt.bindString(1, mssv)
            stmt.bindLong(2, maLopHocPhan.toLong())
            stmt.bindString(3, ngayDangKi)
            stmt.executeInsert()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllDangKy(mssv: String): List<String> {
        val db = this.readableDatabase
        val query = """
        SELECT L.MaLopHocPhan, L.MaHocPhan, L.ThongTin, D.NgayDangKi
        FROM DANGKY D
        JOIN LOPHOCPHAN L ON D.MaLopHocPhan = L.MaLopHocPhan
        WHERE D.MSSV = ?
    """
        val cursor = db.rawQuery(query, arrayOf(mssv))
        val results = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val maLopHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("MaLopHocPhan"))
                val maHocPhan = cursor.getString(cursor.getColumnIndexOrThrow("MaHocPhan"))
                val thongTin = cursor.getString(cursor.getColumnIndexOrThrow("ThongTin"))
                val ngayDangKi = cursor.getString(cursor.getColumnIndexOrThrow("NgayDangKi"))
                val item = "     $maLopHocPhan        - $maHocPhan        - $thongTin -   $ngayDangKi"
                results.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return results
    }

    fun updateDangKy(mssv: String, originalMaLopHocPhan: Int, newMaLopHocPhan: Int, newNgayDangKi: String): Boolean {
        val db = this.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("MaLopHocPhan", newMaLopHocPhan)
                put("NgayDangKi", newNgayDangKi)
            }
            val whereClause = "MSSV = ? AND MaLopHocPhan = ?"
            val whereArgs = arrayOf(mssv, originalMaLopHocPhan.toString())
            db.update("DANGKY", values, whereClause, whereArgs) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteDangKy(mssv: String, maLopHocPhan: Int): Boolean {
        val db = this.writableDatabase
        return try {
            val whereClause = "MSSV = ? AND MaLopHocPhan = ?"
            val whereArgs = arrayOf(mssv, maLopHocPhan.toString())
            db.delete("DANGKY", whereClause, whereArgs) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getAllLopHocPhanForHocPhan(maHocPhan: String): List<Int> {
        val db = this.readableDatabase
        val query = """
        SELECT MaLopHocPhan
        FROM LOPHOCPHAN
        WHERE MaHocPhan = ?
    """
        val cursor = db.rawQuery(query, arrayOf(maHocPhan))
        val results = mutableListOf<Int>()
        if (cursor.moveToFirst()) {
            do {
                val maLopHocPhan = cursor.getInt(cursor.getColumnIndexOrThrow("MaLopHocPhan"))
                results.add(maLopHocPhan)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return results
    }





    companion object {
        private const val DATABASE_NAME = "university1.db"
        private const val DATABASE_VERSION = 14
    }
}
