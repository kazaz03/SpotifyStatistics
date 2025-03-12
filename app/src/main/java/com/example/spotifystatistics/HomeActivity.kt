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
    private val fragmentStack = mutableMapOf<Int, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBar.toolBar)
        //when we first open that the main fragment is shown

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        replaceFragment(R.id.nav_home)

        binding.navigationPart.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(item.itemId)
                    true
                }
                R.id.nav_top -> {
                    replaceFragment(item.itemId)
                    true
                }
                R.id.nav_statistic -> {
                    replaceFragment(item.itemId)
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(item.itemId)
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
    }

    //funkcija za mijenjanje fragmenata u frame layoutu
    private fun replaceFragment(bottomNavId: Int){
        var fragmentManager=supportFragmentManager
        var existingFragment=fragmentManager.findFragmentByTag("fragment_$bottomNavId")
        var Token=token?: ""

        //if there is an existing fragment then just show it, if not make new instance
        if(existingFragment!=null){
            fragmentManager.beginTransaction().replace(R.id.fragmentPart,existingFragment,"fragment_$bottomNavId")
                .setReorderingAllowed(true).commit()
        }else{
            val newFragment= when (bottomNavId) {
                R.id.nav_home->HomeFragment.newInstance(Token)
                R.id.nav_top->TopFragment.newInstance(Token)
                R.id.nav_statistic->StatisticFragment.newInstance(Token)
                R.id.nav_profile->ProfileFragment.newInstance(Token)
                else->throw IllegalArgumentException("Uknown fragment ID")
            }
            fragmentManager.beginTransaction().replace(R.id.fragmentPart,newFragment,"fragment_$bottomNavId")
                .setReorderingAllowed(true).addToBackStack("fragment_$bottomNavId").commit()
        }
    }
    //fragmentPart is container in which we put fragments

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuinflater=menuInflater
        menuinflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
}