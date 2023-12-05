package com.example.kotlinshoppingapp.Views

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.kotlinshoppingapp.Adapters.AdapterDetailsAct
import com.example.kotlinshoppingapp.Model.ShoppingList
import com.example.kotlinshoppingapp.RoomDb.ListDao
import com.example.kotlinshoppingapp.RoomDb.ListDatabase
import com.example.kotlinshoppingapp.databinding.ActivityDetailsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database: ListDatabase
    private lateinit var dao: ListDao
    private val compositeDisposable = CompositeDisposable()
    private var listFromMain : ShoppingList? = null
    private lateinit var adapterDetailsAct:AdapterDetailsAct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init(){
        database = Room.databaseBuilder(applicationContext,ListDatabase::class.java,"ShoppingList")
            .build()
        dao = database.listDao()

        val intent = intent
        listFromMain = intent.getSerializableExtra("selectedList") as? ShoppingList

        listFromMain?.let {
            binding.tvTittleDetailsAct.text = it.tittle
            binding.tvDescriptionDetailsAct.text = it.description
        }

        binding.recyclerViewDetailsAct.layoutManager = LinearLayoutManager(this)
        adapterDetailsAct = AdapterDetailsAct(listFromMain!!.list)
        binding.recyclerViewDetailsAct.adapter = adapterDetailsAct
    }
    fun allDelete(view: View){
        val alert = AlertDialog.Builder(this@DetailsActivity)

        alert.setTitle("DELETE LIST")
        alert.setMessage("Are you sure?")

        alert.setPositiveButton("Yes", object  : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                listFromMain?.let {
                    compositeDisposable.add(
                        dao.delete(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this@DetailsActivity::handleResponce)
                    )
                }
            }

        })

        alert.setNegativeButton("No", object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })
        alert.show()

    }

    fun goToHomePage(view: View){
        listFromMain?.let {
            compositeDisposable.add(
                dao.update(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponce)
            )
        }
    }

    private fun handleResponce(){
        val intent = Intent(this@DetailsActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

}