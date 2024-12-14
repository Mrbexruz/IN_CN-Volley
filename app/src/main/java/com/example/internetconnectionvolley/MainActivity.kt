package com.example.internetconnectionvolley

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.internetconnectionvolley.adapters.UserAdapter
import com.example.internetconnectionvolley.databinding.ActivityMainBinding
import com.example.internetconnectionvolley.utils.MyNetworkHelper
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var myNetworkHelper: MyNetworkHelper
    lateinit var userAdapter: UserAdapter
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myNetworkHelper = MyNetworkHelper(this)


        if (myNetworkHelper.isNetworkConnected()){
            binding.tvInfo.text ="Connected"

            requestQueue = Volley.newRequestQueue(this)

            fetchImageLoad()

            fetchObjectLoad()

        }else
            binding.tvInfo.text ="Disconnected"
    }

    private fun fetchObjectLoad(){
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, "http://ip.jsontest.com/", null,
            object : Response.Listener<JSONObject>{
                override fun onResponse(response: JSONObject?) {
                    val massa = response?.getString("ip")
                    binding.tvInfo.text = massa

                }
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tvInfo.text = error?.message
                }
            }
            )
        requestQueue.add(jsonObjectRequest)
    }

    private fun fetchImageLoad(){
        val imageRequest = ImageRequest("https://library.sportingnews.com/styles/crop_style_16_9_desktop/s3/2023-06/messageImage_1686582573680.jpg?h=df3c6bf4&itok=N-16wSvs",
            object :Response.Listener<Bitmap>{
                override fun onResponse(response: Bitmap?) {
                    binding.imageView.setImageBitmap(response)

                }
            },0,0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
            object :Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tvInfo.text = error?.message
                }
            }
            )
        requestQueue.add(imageRequest)
    }

    private fun isNetworkConnected():Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }else{
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            return activeNetworkInfo != null && activeNetworkInfo.isConnected

        }


    }

}