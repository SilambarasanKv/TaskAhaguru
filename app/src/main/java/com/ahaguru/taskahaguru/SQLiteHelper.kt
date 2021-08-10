package com.ahaguru.taskahaguru

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object {

        private const val DATABASE_NAME = "STUDENTDB"
        private const val DATABASE_VERSION = 1
        const val STUDENTS = "Students"
        private const val ID = "Id"
        private const val NAME = "Name"
        private const val EMAIL = "Email"
        private const val CLASS = "Class"
        private const val LOCATION = "Location"
        private const val DATE = "Date"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTblStudent = ("CREATE TABLE " + STUDENTS + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT," + CLASS + " TEXT," + LOCATION + " TEXT," + DATE + " TEXT" + ")")
        db?.execSQL(createTblStudent)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $STUDENTS")
        onCreate(db)

    }

    fun insertStudent(student: StudentModel): Long {

        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(ID, student.Id)
        cv.put(NAME, student.Name)
        cv.put(EMAIL, student.Email)
        cv.put(CLASS, student.Class)
        cv.put(LOCATION, student.Location)
        cv.put(DATE, student.Date)

        val success = db.insert(STUDENTS, null, cv)
        db.close()
        return success

    }

    fun getAllStudents(): ArrayList<StudentModel> {

        val studentList: ArrayList<StudentModel> = ArrayList()

        val selectQuery = "SELECT * FROM $STUDENTS"


        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var Id: Int
        var Name: String
        var Email: String
        var Class: String
        var Location: String
        var Date: String

        if(cursor.moveToFirst()) {

            do {
                Id = cursor.getInt(cursor.getColumnIndex("Id"))
                Name = cursor.getString(cursor.getColumnIndex("Name"))
                Email = cursor.getString(cursor.getColumnIndex("Email"))
                Class = cursor.getString(cursor.getColumnIndex("Class"))
                Location = cursor.getString(cursor.getColumnIndex("Location"))
                Date = cursor.getString(cursor.getColumnIndex("Date"))

                val student = StudentModel(Id = Id, Name = Name, Email = Email, Class = Class, Location = Location, Date = Date)
                studentList.add(student)
            } while (cursor.moveToNext())

        }

        return studentList

    }
}