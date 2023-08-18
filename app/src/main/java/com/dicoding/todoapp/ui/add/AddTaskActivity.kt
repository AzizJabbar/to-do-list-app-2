package com.dicoding.todoapp.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.TimeConverter
import com.dicoding.todoapp.utils.DatePickerFragment
import com.dicoding.todoapp.utils.TimePickerFragment
import com.google.android.material.textview.MaterialTextView
import java.util.*

class AddTaskActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
//    private var dueDateMillis: Long = System.currentTimeMillis()
    private lateinit var addTaskViewModel: AddTaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)

        val factory = ViewModelFactory.getInstance(this)
        addTaskViewModel = ViewModelProvider(this, factory).get(AddTaskViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                // 12 : Create AddTaskViewModel and insert new task to database
                addTaskViewModel.insertTask(
                    Task(
                        0,
                        findViewById<EditText>(R.id.add_ed_title).text.toString(),
                        findViewById<EditText>(R.id.add_ed_description).text.toString(),
                        TimeConverter.convertStringToMillis(findViewById<MaterialTextView>(R.id.add_tv_start_time).text.toString()),
                        TimeConverter.convertStringToMillis(findViewById<MaterialTextView>(R.id.add_tv_end_time).text.toString()),
                        false
                    )
                )
                startActivity(Intent(this, TaskActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker1(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "start")
    }

    fun showTimePicker2(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "end")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        val time = String.format("%02d:%02d", hour, minute)
        if(tag.equals("start")){
            findViewById<TextView>(R.id.add_tv_start_time).text = time
        } else {
            findViewById<TextView>(R.id.add_tv_end_time).text = time
        }

//        dueDateMillis = calendar.timeInMillis
    }
}