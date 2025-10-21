package com.arslan.signuppage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arslan.signuppage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener of submit button
        binding.submitbtn.setOnClickListener {
            validateAndSubmit()
        }

        binding.clearbtn.setOnClickListener {
            clearForm()
        }

        binding.etDateofbirth.setOnClickListener {
            showDatePicker()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showDatePicker() {
        val calender = java.util.Calendar.getInstance()
        val year = calender.get(java.util.Calendar.YEAR)
        val month = calender.get(java.util.Calendar.MONTH)
        val day = calender.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etDateofbirth.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show(
        )
    }

    private fun clearForm(){
        binding.name.setText("")
        binding.email.setText("")
        binding.password.setText("")
        binding.confirmPassword.setText("")
        binding.etDateofbirth.setText("")
        binding.city.setText("")
        binding.country.setText("")

        binding.rGender.clearCheck()
        binding.rskills.clearCheck()

        binding.python.isChecked = false
        binding.cpp.isChecked = false
        binding.javascript.isChecked = false
        binding.cbTerms.isChecked = false

        android.widget.Toast.makeText(this, "Form cleared", android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun getSelectedGenders(): String{
        return if (binding.rMale.isChecked) "Male" else "Female"
    }

    private fun getSelectedSkills(): String {
        return if (binding.rbeginner.isChecked) "Beginner" else "Advance"
    }

    private fun getSelectedLanguages(): String {
        val languageList = mutableListOf<String>()
        if (binding.python.isChecked) languageList.add("Python")
        if (binding.cpp.isChecked) languageList.add("C++")
        if (binding.javascript.isChecked) languageList.add("JavaScript")
        return if (languageList.isEmpty()) "None" else languageList.joinToString(", ")
    }

    private fun validateAndSubmit() {
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()
        val termsAccepted = binding.cbTerms.isChecked

        if (password != confirmPassword) {
            android.widget.Toast.makeText(this, "Passwords do not match", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        if (!termsAccepted){
            android.widget.Toast.makeText(this, "Please accept the terms and conditions", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        val name = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val dateOfBirth = binding.etDateofbirth.text.toString().trim()
        val city = binding.city.text.toString().trim()
        val country = binding.country.text.toString().trim()

        val gender = getSelectedGenders()
        val skills = getSelectedSkills()
        val languages = getSelectedLanguages()


        val details = """
                Welcome: $name
                Email: $email
                Born on: $dateOfBirth
                Location: $city, $country
                Gender: $gender
                Skills: $skills
                Languages: $languages
            """.trimIndent()

        android.widget.Toast.makeText(this, details, android.widget.Toast.LENGTH_LONG).show()
    }
}
