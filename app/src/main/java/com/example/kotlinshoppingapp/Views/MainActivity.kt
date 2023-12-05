package com.example.kotlinshoppingapp.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlinshoppingapp.Adapters.AdapterMainAct
import com.example.kotlinshoppingapp.Model.ShoppingList
import com.example.kotlinshoppingapp.RoomDb.ListDao
import com.example.kotlinshoppingapp.RoomDb.ListDatabase
import com.example.kotlinshoppingapp.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var calendar: Calendar
    private lateinit var adapterMainAct:AdapterMainAct
    private lateinit var database: ListDatabase
    private lateinit var dao: ListDao
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.btnAddMainAct.setOnClickListener {
            val intent = Intent(this@MainActivity,AddListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init(){
        calendar = Calendar.getInstance()
        binding.dateMainAct.text = getBugununTarihi()

        database = Room.databaseBuilder(applicationContext,ListDatabase::class.java,"ShoppingList")
            .build()
        dao = database.listDao()

        compositeDisposable.add(
            dao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponce)
        )

        compositeDisposable.add(
            dao.getRowCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponceForEmptyDb)
        )

    }
    private fun handleResponceForEmptyDb(count:Int){
        if(count == 0){
            binding.emptyStateTextView.visibility = View.VISIBLE
        } else{
            binding.emptyStateTextView.visibility = View.GONE
        }
    }
    private fun handleResponce(list :MutableList<ShoppingList>){

        binding.recyclerViewMainAct.layoutManager = LinearLayoutManager(this@MainActivity)
        adapterMainAct = AdapterMainAct(list)
        binding.recyclerViewMainAct.adapter = adapterMainAct
    }

    private fun getBugununTarihi(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

}