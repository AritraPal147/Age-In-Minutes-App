package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // Text View variable was created as null, so that it can be assigned later in the onCreate() method
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        // Finds button on basis of its id and puts it in a variable to be used

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        // Finds text view on basis of its id and puts it in a variable to be used

        // Governs what the button does on clicking it
        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()         // Gives access to calendar
        val year = myCalendar.get(Calendar.YEAR)        // Gets Year
        val month = myCalendar.get(Calendar.MONTH)      // Gets Month
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)     // Gets day of month

        // Check Documentation - DatePickerDialog
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{_, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,
                    "Year was $selectedYear, Month was ${selectedMonth+1}, " +
                            "Day was $selectedDayOfMonth", Toast.LENGTH_LONG
                ).show()

                val selectedDate = "$selectedDayOfMonth.${selectedMonth+1}.$selectedYear"
                tvSelectedDate?.text = selectedDate
                // One way of assigning text to a text view
                // tvSelectedDate?.setText = selectedDate could also be used - old fashioned way

                // Check Documentation - SimpleDateFormat
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                // Created an object of SimpleDateFormat, so that we can create a date object

                val theDate = sdf.parse(selectedDate)
                // parsed (WHAT IS PARSING??) the object into theData variable

                // Check Documentation - Date
                // .let{  } is an approach to make sure that we only execute if the variable is not empty
                // If for ex: theDate is empty, then the execution is not carried out
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    // Gives the amt of time that has passed between selected date and 1st January, 1970

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    // Amt of time passed between now and 1970

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }

            }, year, month, day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        // System.currentTimeMillis() gives the system's current time in milliseconds
        dpd.show()
    }
}