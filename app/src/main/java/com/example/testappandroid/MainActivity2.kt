package com.example.testappandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RoomDB.DatabaseHelper
import com.example.testappandroid.Adapter.NotificationRecyclerAdapter
import com.example.testappandroid.Mvvm.NoteRepository
import com.example.testappandroid.Mvvm.NoteViewModel
import com.example.testappandroid.Mvvm.NoteViewModelFactory
import com.example.testappandroid.RoomDB.Expense


class MainActivity2 : AppCompatActivity() {

    var arrayList: ArrayList<Expense?>? = null

    private lateinit var viewModel: NoteViewModel
    private lateinit var repository: NoteRepository
    private lateinit var factory: NoteViewModelFactory
    private lateinit var noteDatabase: DatabaseHelper
    lateinit var nestedSv: ScrollView
    lateinit var loadingPB: ProgressBar
    var page = 1
    var limit = 10
    var isloading = true
    lateinit var adapter:NotificationRecyclerAdapter
    lateinit var recyclerview: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerview = findViewById(R.id.recycler)
        nestedSv = findViewById(R.id.nestedSv)
        loadingPB = findViewById(R.id.progressbar)
        noteDatabase = DatabaseHelper.getDB(this)!!
        repository = NoteRepository(noteDatabase)
        factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        layoutManager = LinearLayoutManager(this)

        recyclerview.layoutManager = layoutManager

        arrayList = viewModel.getAllNotes() as ArrayList<Expense?>

        getPage()

        callRecycler()

    }

    fun getList(){
        arrayList = viewModel.getAllNotes() as ArrayList<Expense?>
    }

    fun callRecycler(){

        recyclerview.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0){
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    if (!isloading){
                        if ((visibleItemCount + pastVisibleItem) >= total)
                        {
                            page++
                            getPage()
                        }
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    fun getPage(){
        val newarraylist: ArrayList<Expense?>? = null
        isloading = true
        loadingPB.visibility = View.VISIBLE
        val start = (page-1) * limit
        val end = (page) * limit

        for (i in 0..arrayList!!.size) {
            if (i in start..end) {
                newarraylist?.add(arrayList!![i])
            }
        }


        /*for (i in start..end)
        {
            val time  = arrayList!![i]?.time
            val date  = arrayList!![i]?.date
            val battery  = arrayList!![i]?.battery



        }*/

        Handler().postDelayed({
            if (::adapter.isInitialized)
            {
                adapter.notifyDataSetChanged()
            }
            else{

                adapter = NotificationRecyclerAdapter(arrayList!!)

                recyclerview.adapter = adapter
            }
            isloading = false
            loadingPB.visibility = View.GONE
        },5000)
    }

}