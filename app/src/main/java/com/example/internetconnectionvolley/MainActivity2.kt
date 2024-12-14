package com.example.internetconnectionvolley

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.internetconnectionvolley.adapters.UserAdapter
import com.example.internetconnectionvolley.databinding.ActivityMain2Binding
import com.example.internetconnectionvolley.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    lateinit var requestQueue: RequestQueue
    lateinit var userAdapter: UserAdapter
    val url = "https://api.github.com/users"
    private val TAG = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)

        // Make the request to the GitHub API
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    response?.let {
                        // Gson yordamida JSON arrayni User ro'yxatiga o'zgartirish
                        val type = object : TypeToken<List<User>>() {}.type
                        val list: List<User> = Gson().fromJson(it.toString(), type)

                        // Adapterni yaratish va RecyclerView-ga ulash
                        userAdapter = UserAdapter(list)
                        binding.rv.adapter = userAdapter

                        Log.d(TAG, "onResponse: $list")
                    } ?: run {
                        Log.e(TAG, "Response is null")
                    }
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e(TAG, "Error: ${error?.message}")
                }
            }
        )

        // Requestni queuing
        jsonArrayRequest.tag = "tag1"
        requestQueue.add(jsonArrayRequest)
    }
}
