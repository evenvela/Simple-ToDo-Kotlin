package com.example.simpletodokotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val position = intent.extras?.get("position").toString()
        val item = intent.extras?.get("item").toString()

        val etEditItem = findViewById<EditText>(R.id.etEditItem)
        val btSave = findViewById<Button>(R.id.btSave)

        etEditItem.setText(item)

        btSave.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("item", etEditItem.text.toString())

            //Return to the Expense screen
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}