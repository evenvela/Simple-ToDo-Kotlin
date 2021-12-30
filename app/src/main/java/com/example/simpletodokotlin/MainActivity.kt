package com.example.simpletodokotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declare Variables
        val rvItems : RecyclerView
        val btAdd :Button

        //Get the handles of all controls
        rvItems = findViewById<RecyclerView>(R.id.rvItems)
        btAdd  = findViewById<Button>(R.id.btAdd)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                listOfTask.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        val onClickListener = object : TaskItemAdapter.OnClickListener{
            override fun onItemClicked(position: Int) {
                openEditScreen(position)
            }
        }

        btAdd.setOnClickListener {
            Log.i("Even", "Button clicked")
            val etItem = findViewById<EditText>(R.id.etItem)

            listOfTask.add(etItem.text.toString())

            adapter.notifyItemInserted(listOfTask.size-1)

            saveItems()

        }

        loadItems()

        adapter = TaskItemAdapter(listOfTask, onLongClickListener, onClickListener)

        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(this)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            //val data:Intent? = result.data

            val item = result.data?.getStringExtra("item").toString()
            val position = result.data?.getStringExtra("position").toString().toInt()

            listOfTask.set(position, item)
            adapter.notifyDataSetChanged()
            saveItems()
        }
    }


    fun getDataFile(): File {
        return File(filesDir, "data.txt")
    }

    //Load Items
    fun loadItems() {
        try {
            listOfTask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    //Save Items
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        } catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    fun openEditScreen(position: Int){
        intent = Intent(this, EditActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("item", listOfTask.get(position))
        resultLauncher.launch(intent)
    }
}