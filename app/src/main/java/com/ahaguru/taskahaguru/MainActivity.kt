package com.ahaguru.taskahaguru

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_popup.*
import kotlinx.android.synthetic.main.details_popup.view.*
import kotlinx.android.synthetic.main.details_students.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var floatingActionButton: FloatingActionButton
//    private lateinit var customAlertDialogView : View

    /*private lateinit var etName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etClass : EditText
    private lateinit var etLocation : EditText
    private lateinit var tvDatePop: TextView

    private lateinit var btnDate: Button*/

    private lateinit var sqliteHelper: SQLiteHelper

    private lateinit var btnFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null

    private var student: StudentModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnFab = findViewById(R.id.btnFab)
        recyclerView = findViewById(R.id.recyclerView)
        sqliteHelper = SQLiteHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter


        //      materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)



        btnFab.setOnClickListener(View.OnClickListener {

            val dialog = AlertDialog.Builder(this)

            val dialogView = LayoutInflater.from(this)
                .inflate(R.layout.details_popup, null, false)

            var etName = dialogView.findViewById<EditText>(R.id.etName)
            var etEmail = dialogView.findViewById<EditText>(R.id.etEmail)
            var etClass = dialogView.findViewById<EditText>(R.id.etClass)
            var etLocation = dialogView.findViewById<EditText>(R.id.etLocation)
            var tvDatePop = dialogView.findViewById<TextView>(R.id.tvDatePop)


            var btnDate = dialogView.findViewById<Button>(R.id.btnDate)
            var btnAdd = dialogView.findViewById<Button>(R.id.btnAdd)
            var btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)



            dialog.setView(dialogView)
                .setTitle("Details")
                .setMessage("Enter students details")
            val customDialog = dialog.create()
            customDialog.show()


            btnDate.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {

                    val calendar: Calendar = Calendar.getInstance()
                    val YEAR: Int = calendar.get(Calendar.YEAR)
                    val MONTH: Int = calendar.get(Calendar.MONTH)
                    val DATE: Int = calendar.get(Calendar.DATE)

                    val datePickerDialog = DatePickerDialog(
                        this@MainActivity,
                        { datePicker, year, month, date ->
                            val calendar1: Calendar = Calendar.getInstance()
                            calendar1.set(Calendar.YEAR, year)
                            calendar1.set(Calendar.MONTH, month)
                            calendar1.set(Calendar.DATE, date)
                            val dateText: String =
                                DateFormat.format("dd/MM/yyyy", calendar1).toString()
                            tvDatePop.setText(dateText)
                        }, YEAR, MONTH, DATE
                    )

                    datePickerDialog.show()
                    tvDatePop.visibility = View.VISIBLE

                }
            })

            btnAdd.setOnClickListener {

                val Name = etName.text.toString()
                val Email = etEmail.text.toString()
                val Class = etClass.text.toString()
                val Location = etLocation.text.toString()
                val Date = tvDatePop.text.toString()

                if (Name.isEmpty() || Email.isEmpty() || Class.isEmpty() || Location.isEmpty() || Date.isEmpty()) {
                    Toast.makeText(this, "Please enter the required field", Toast.LENGTH_SHORT).show()
                } else {

                    val student = StudentModel(Name = Name, Email = Email, Class = Class, Location = Location, Date = Date)
                    val status = sqliteHelper.insertStudent(student)

                if (status > -1) {

                        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()




                        etName.setText("")
                        etEmail.setText("")
                        etClass.setText("")
                        etLocation.setText("")
                        tvDatePop.setText("")
                        etName.requestFocus()

                    customDialog.dismiss()

                    // To get List

                    val studentList = sqliteHelper.getAllStudents()
                    Log.e("pppp", "${studentList.size}")

                    //To display data in Recycler View

                    adapter?.addItems(studentList)

                    }

                else {
                        Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
                    }


                }


            }

            dialogView.btnCancel.setOnClickListener {
                customDialog.dismiss()
            }
        })

    }


}