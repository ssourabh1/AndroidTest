package com.example.testappandroid

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.RoomDB.DatabaseHelper
import com.example.testappandroid.Mvvm.NoteRepository
import com.example.testappandroid.Mvvm.NoteViewModel
import com.example.testappandroid.Mvvm.NoteViewModelFactory
import com.example.testappandroid.Notification.MyReceiver
import com.example.testappandroid.RoomDB.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var repository: NoteRepository
    private lateinit var factory: NoteViewModelFactory
    private lateinit var noteDatabase: DatabaseHelper

    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    var handler: Handler = Handler()
    lateinit var runnable: Runnable
    val delay = 10000
        //7200000
    var batteryTxt = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteDatabase = DatabaseHelper.getDB(this)!!
        repository = NoteRepository(noteDatabase)
        factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        callButton()
        callFuncRepeat()
        createNotificationChannel()

    }

    fun callButton(){
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun callFuncRepeat(){
        val currentTime: Int = Calendar.getInstance().time.hours

        if (currentTime > 7) {
            handler.postDelayed(Runnable {
                handler.postDelayed(runnable, delay.toLong())
                setAlarm()

            }.also { runnable = it }, delay.toLong())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm(){

        val cal1 = Calendar.getInstance()

        cal1.timeInMillis = System.currentTimeMillis()

        alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent)

        createDatabase()
        Toast.makeText(this,"success", Toast.LENGTH_SHORT).show()
        cal1.clear()
    }

    fun createDatabase(){
        val c = Calendar.getInstance().time

        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)
        //val databaseHelper = DatabaseHelper.getDB(this)
        this.registerReceiver(this.mBatInfoReceiver,  IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        val expense = Expense(c.hours.toString(), formattedDate, batteryTxt)
        GlobalScope.launch(Dispatchers.IO) {
            if (batteryTxt!="") {
                //databaseHelper!!.dao()!!.addTx(Expense(c.hours.toString(), formattedDate, batteryTxt))
                viewModel.insertNote(expense)
            }
        }
    }

    private val mBatInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context?, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = level * 100 / scale.toFloat()
            batteryTxt = "$batteryPct%"
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

            var mChannel =  NotificationChannel("YOUR_CHANNEL_ID",
                "YOUR CHANNEL NAME",
                NotificationManager.IMPORTANCE_DEFAULT)

            val attributes =  AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            mChannel =  NotificationChannel("TaskManagement",
                this.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            mChannel.setDescription("Task Management Operation");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(sound,attributes)


            val notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(mChannel)
        }
    }

}