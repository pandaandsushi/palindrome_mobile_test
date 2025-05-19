package com.example.palindrome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
class SecondActivity : AppCompatActivity() {
    private lateinit var welcomeTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var selectedUserTextView: TextView
    private lateinit var chooseUserButton: Button
    private lateinit var backButton: ImageButton

    private var userName: String = ""
    private var selectedUserName: String = ""

    companion object {
        const val REQUEST_CODE_USER_SELECTION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        nameTextView = findViewById(R.id.nameTextView)
        selectedUserTextView = findViewById(R.id.selectedUserTextView)
        chooseUserButton = findViewById(R.id.chooseUserButton)
        backButton = findViewById(R.id.backButton)

        userName = intent.getStringExtra("NAME") ?: ""
        nameTextView.text = userName
        backButton.setOnClickListener {
            finish()
        }
        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_USER_SELECTION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER_SELECTION && resultCode == RESULT_OK) {
            selectedUserName = data?.getStringExtra("SELECTED_USER_NAME") ?: ""
            selectedUserTextView.text = selectedUserName
        }
    }
}