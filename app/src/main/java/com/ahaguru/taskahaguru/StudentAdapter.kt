package com.ahaguru.taskahaguru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var studentList: ArrayList<StudentModel> = ArrayList()

    fun addItems(items: ArrayList<StudentModel>) {
        this.studentList = items
        notifyDataSetChanged()
    }

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var Id = view.findViewById<TextView>(R.id.tvId)
        private var Name = view.findViewById<TextView>(R.id.tvName)
        private var Email = view.findViewById<TextView>(R.id.tvEmail)
        private var Class = view.findViewById<TextView>(R.id.tvClass)
        private var Location = view.findViewById<TextView>(R.id.tvLocation)
        private var Date = view.findViewById<TextView>(R.id.tvDate)

        fun bindView(student: StudentModel) {

            Id.text = student.Id.toString()
            Name.text = student.Name
            Email.text = student.Email
            Class.text = student.Class
            Location.text = student.Location
            Date.text = student.Date


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder (

        LayoutInflater.from(parent.context).inflate(R.layout.details_students, parent, false)

    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        val student = studentList[position]
        holder.bindView(student)

    }


    override fun getItemCount(): Int {

        return studentList.size

    }

}