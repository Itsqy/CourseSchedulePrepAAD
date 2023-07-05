package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    lateinit var binding: ActivityAddCourseBinding
    lateinit var viewModel: AddCourseViewModel
    private lateinit var view: View

    var tvTitle: String = ""
    private lateinit var lecturer: String
    private lateinit var note: String
    private lateinit var startDate: String
    private lateinit var endDate: String
    private var day: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val addCourseFactory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, addCourseFactory)[AddCourseViewModel::class.java]




        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true)
                onBackPressed()
            else {
                val message = getString(R.string.input_empty_message)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                binding?.apply {

                    tvTitle = edtAddTitle.text?.trim().toString()
                    lecturer = edtAddLecture.text?.trim().toString()
                    note = edtAddNote.text?.trim().toString()
                    startDate = tvItemStartTime.text.trim().toString()
                    endDate = tvItemEndTime.text.trim().toString()
                    day = spinnerAddDay.selectedItemPosition


                }
                Log.d("TAG", "onOptionsItemSelected: $tvTitle")
                viewModel.insertCourse(tvTitle, day, startDate, endDate, lecturer, note)






                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }

    fun pickerStart(view: View) {
        TimePickerFragment().show(
            supportFragmentManager, " startTime"
        )
        this.view = view
    }

    fun pickerEnd(view: View) {
        TimePickerFragment().show(
            supportFragmentManager, " endTime"
        )
        this.view = view
    }


    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (view?.id) {
            R.id.ic_add_start_time -> {
                binding.tvItemStartTime.text = timeFormat.format(calendar.time)
            }

            R.id.ic_add_end_time -> {
                binding.tvItemEndTime.text = timeFormat.format(calendar.time)
            }
        }
    }

}





