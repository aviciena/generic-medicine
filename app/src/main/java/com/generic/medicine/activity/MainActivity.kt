package com.generic.medicine.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generic.medicine.R
import com.generic.medicine.adapter.MedicineListAdapter
import com.generic.medicine.model.Medicine
import com.generic.medicine.model.MedicineList
import com.google.gson.Gson
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var recycleView: RecyclerView
    private var dataList: ArrayList<Medicine> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView = findViewById(R.id.medicine_recycle_view)
        recycleView.setHasFixedSize(true)

        val jsonString = resources.openRawResource(R.raw.medicine_list)
            .bufferedReader().use { it.readText() }

        val gSonFormat = Gson()
        val medicineListResp = gSonFormat.fromJson(jsonString, MedicineList::class.java)

        for (data in medicineListResp.data) {
            for (medicineList in data.list) {
                dataList.add(medicineList)
            }
        }

        showRecyclerList()

    }

    private fun showRecyclerList() {
        recycleView.layoutManager = LinearLayoutManager(this)
        val medicineListAdapter = MedicineListAdapter(this, dataList)
        recycleView.adapter = medicineListAdapter

        medicineListAdapter.setOnItemClickCallback(object: MedicineListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Medicine) {
                val openDetails = Intent(this@MainActivity, MedicineDetailsActivity::class.java)
                openDetails.putExtra(MedicineDetailsActivity.DETAILS_KEY, data as Serializable)
                startActivity(openDetails)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.about_page -> {
                startActivity(Intent(this@MainActivity, AboutPage::class.java))
            }
        }
    }
}
