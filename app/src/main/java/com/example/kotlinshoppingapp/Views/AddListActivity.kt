package com.example.kotlinshoppingapp.Views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlinshoppingapp.Adapters.AdapterAddAct
import com.example.kotlinshoppingapp.Model.ShoppingList
import com.example.kotlinshoppingapp.RoomDb.ListDao
import com.example.kotlinshoppingapp.RoomDb.ListDatabase
import com.example.kotlinshoppingapp.databinding.ActivityAddListBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddListBinding
    private var shopList: MutableList<String> = mutableListOf()
    private lateinit var database: ListDatabase
    private lateinit var dao: ListDao
    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapterAddAct: AdapterAddAct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }
    private fun init(){
        database = Room.databaseBuilder(applicationContext,ListDatabase::class.java,"ShoppingList")
            .build()
        dao = database.listDao()

        adapterAddAct = AdapterAddAct(shopList)
        binding.itemRV.layoutManager =LinearLayoutManager(this)
        binding.itemRV.adapter = adapterAddAct
    }

    fun saveList(view: View){
        val list = ShoppingList(tittle = binding.etTittleAddAct.text.toString(),
            description = binding.etDescriptionAddAct.text.toString(),
            list = shopList)


        compositeDisposable.add(
            dao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponce)
        )

    }

    fun addItem(view:View){
        shopList.add(binding.etAddItemAddAct.text.toString())
        binding.etAddItemAddAct.setText("")
        if (Build.VERSION.SDK_INT >= 26) {
            binding.etAddItemAddAct.autofillHints
        }
        adapterAddAct.refreshData(shopList)
    }

    private fun handleResponce(){
        val intent = Intent(this@AddListActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}