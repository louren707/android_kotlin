package co.com.ceiba.mobile.pruebadeingreso.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.adapter.PostsAdapter
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.db.User
import co.com.ceiba.mobile.pruebadeingreso.rest.ApiService
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.GET_POST_USER
import co.com.ceiba.mobile.pruebadeingreso.rest.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private var idUser: Int = 0
    private lateinit var adapter: PostsAdapter
    private val postData = mutableListOf<Posts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUser = intent.getIntExtra("idUser", 0)
        val nameUser = intent.getStringExtra("nameUser")
        val phoneUser = intent.getStringExtra("phoneUser")
        val emailUser = intent.getStringExtra("emailUser")

        binding.name.text = nameUser
        binding.phone.text = phoneUser
        binding.email.text = emailUser

        searchPostsById("${GET_POST_USER}userId=$idUser")
        initRecyclerView()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initRecyclerView() {
        adapter = PostsAdapter(postData)
        binding.recyclerViewPostsResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPostsResults.adapter = adapter
    }

    private fun searchPostsById(query: String) {
        try {
            val call = getRetrofit().create(ApiService::class.java)
            call.getPostsById(query).enqueue(object : Callback<List<Posts>> {
                override fun onResponse(
                    call: Call<List<Posts>>,
                    response: Response<List<Posts>>
                ) {
                    val posts = response.body()
                    if (posts != null) {
                        postData.clear()
                        postData.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        } catch (e: Exception) {
            Log.e("ERROR", e.stackTraceToString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PostActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}