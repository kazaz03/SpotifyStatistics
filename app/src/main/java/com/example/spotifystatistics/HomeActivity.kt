package com.example.spotifystatistics

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.spotifystatistics.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class HomeActivity : AppCompatActivity() {
    private lateinit var nameOfUser: TextView
    private var token:String?=""
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar.toolBar)
        //when we first open that the main fragment is shown
        replaceFragment(HomeFragment())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.navigationPart.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_top -> {
                    replaceFragment(TopFragment())
                    true
                }
                R.id.nav_statistic -> {
                    replaceFragment(StatisticFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        binding.appBar.toolBar.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.search->{
                    true
                }
                R.id.settings->{
                    true
                }else->false
            }
        }

        var selectedBottomNavItem=binding.navigationPart.selectedItemId

        //spotifyDAO.setContext(this)
        //alo alo alo
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
        }
    }

    //funkcija za mijenjanje fragmenata u frame layoutu
    private fun replaceFragment(fragment: Fragment){
        var fragmentManager=supportFragmentManager;
        fragmentManager.beginTransaction().replace(R.id.fragmentPart,fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuinflater=menuInflater
        menuinflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
}