package com.example.spotifystatistics

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spotifystatistics.databinding.LoginScreenBinding
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContainerLogIn)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.logInButton.setOnClickListener {
            //when we click on the button it opens login on spoify in web browser
            startSpotifyLogIn()
        }
        binding.installSpotifyButton.setOnClickListener {
            redirectToPlaystore()
        }
    }
    private fun startSpotifyLogIn(){
        // AuthorizationRequest to spotify
        val builder = AuthorizationRequest.Builder(BuildConfig.CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(arrayOf("streaming"))
        builder.setShowDialog(true)
        val request = builder.build()
        // opening spotify login in web browser
        if(isSpotifyInstalledOnDevice()){
            AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
        }else{
            AuthorizationClient.openLoginInBrowser(this, request)
        }
    }

    private fun redirectToPlaystore(){
        //intent za prebacivanje na playstor
        val branchLink = Uri.encode("https://spotify.link/content_linking?~campaign=" + this.packageName)
        val appPackageName = "com.spotify.music"
        val referrer = "_branch_link=$branchLink"

        try {
            val uri = Uri.parse("market://details")
                .buildUpon()
                .appendQueryParameter("id", appPackageName)
                .appendQueryParameter("referrer", referrer)
                .build()
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (ignored: ActivityNotFoundException) {
            val uri = Uri.parse("https://play.google.com/store/apps/details")
                .buildUpon()
                .appendQueryParameter("id", appPackageName)
                .appendQueryParameter("referrer", referrer)
                .build()
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }


    private fun isSpotifyInstalledOnDevice():Boolean{
        val pm = packageManager
        try {
            pm.getPackageInfo("com.spotify.music", 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Handle the result from the Spotify client login
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    // Handle successful response (access token)
                    val accessToken = response.accessToken
                    Log.d("token", accessToken)
                    val intent=Intent(this,HomeActivity::class.java)
                    intent.putExtra("accessToken",accessToken)
                    startActivity(intent)
                }
                AuthorizationResponse.Type.ERROR -> {
                    // Handle error response
                    Log.e("SpotifyAuth", "Authorization failed: ${response.error} }")
                }
                else -> {
                    // Handle other cases (canceled or unexpected)
                    Log.d("error", "Authorization was canceled or failed")

                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri:Uri?= intent?.data;
        if(uri!=null){
            val response=AuthorizationResponse.fromUri(uri)
            if (response.type==AuthorizationResponse.Type.TOKEN){
                val accessToken=response.accessToken
                Log.d("token",accessToken)
                //now we can open home page
                val intent=Intent(this,HomeActivity::class.java)
                intent.putExtra("accessToken",accessToken)
                startActivity(intent)
            }else if(response.type==AuthorizationResponse.Type.ERROR){

            }else{

            }
        }
    }
    companion object {
        private const val REQUEST_CODE=1337
        private const val REDIRECT_URI="statistics://callback"
    }
}

