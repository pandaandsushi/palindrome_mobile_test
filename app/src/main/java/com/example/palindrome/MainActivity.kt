package com.example.palindrome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var palindromeEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        palindromeEditText = findViewById(R.id.palindromeEditText)
        checkButton = findViewById(R.id.checkButton)
        nextButton = findViewById(R.id.nextButton)

        checkButton.setOnClickListener {
            val text = palindromeEditText.text.toString().trim()
            if (text.isEmpty()) {
                palindromeEditText.error = "Please enter a text"
                return@setOnClickListener
            }

            val isPalindrome = checkPalindrome(text)
            val message = if (isPalindrome) "isPalindrome" else "not palindrome"

            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }

        nextButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            if (name.isEmpty()) {
                nameEditText.error = "Please enter your name"
                return@setOnClickListener
            }

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("NAME", name)
            startActivity(intent)
        }
    }

    private fun checkPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s+".toRegex(), "").lowercase()
        return cleanText == cleanText.reversed()
    }
}