package com.example.spotifystatistics

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class HomeActivity : AppCompatActivity() {
    private lateinit var nameOfUser: TextView
    private var token:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //spotifyDAO.setContext(this)
        val extras=intent.extras
        if(extras!=null)
        {
            //dio u kojem se uzima username
            token=extras.getString("accessToken")
            /*val scope= CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                user= spotifyDAO.getUserDetails("Bearer $token")!!
                nameOfUser.text= "Hello ${user.displayName}!"
            }*/
            nameOfUser.text= "Hello $token"
        }
    }
}