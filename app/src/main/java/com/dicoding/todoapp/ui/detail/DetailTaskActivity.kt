package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.TimeConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        //11 : Show detail task and implement delete action
        val taskId = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(taskId)
        detailTaskViewModel.task.observe(this){
            findViewById<TextInputEditText>(R.id.detail_ed_title).setText(it.title)
            findViewById<TextInputEditText>(R.id.detail_ed_description).setText(it.description)
            findViewById<TextInputEditText>(R.id.detail_ed_start_time).setText(TimeConverter.convertMillisToString(it.startTimeMillis))
            findViewById<TextInputEditText>(R.id.detail_ed_end_time).setText(TimeConverter.convertMillisToString(it.endTimeMillis))
        }

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            detailTaskViewModel.deleteTask()
            startActivity(Intent(this, TaskActivity::class.java))
        }

    }
}