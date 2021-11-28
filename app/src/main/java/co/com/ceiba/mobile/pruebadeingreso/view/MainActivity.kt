package co.com.ceiba.mobile.pruebadeingreso.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.adapter.UsersAdapter
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding
import co.com.ceiba.mobile.pruebadeingreso.db.AppDatabase
import co.com.ceiba.mobile.pruebadeingreso.db.User
import co.com.ceiba.mobile.pruebadeingreso.rest.ApiService
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.URL_BASE
import co.com.ceiba.mobile.pruebadeingreso.rest.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), UsersAdapter.OnPostClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var database: AppDatabase
    private var cantUsers: Int = 0
    private val userData = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val progressDialog = ProgressDialog(this@MainActivity)

        var listUsers: List<User>

        database = AppDatabase.getDatabase(this)

        database.users().getAll().observe(this, {
            listUsers = it

            when {
                listUsers.isEmpty() -> {
                    searchUsers()
                    binding.includedEmptyView.root.visibility = View.VISIBLE
                    progressDialog.setMessage(getString(R.string.generic_message_progress))
                    progressDialog.show()
                }
                listUsers.size == cantUsers -> {
                    binding.includedEmptyView.root.visibility = View.GONE
                    progressDialog.cancel()
                }
            }

            userData.clear()
            userData.addAll(listUsers)
            adapter.notifyDataSetChanged()
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = UsersAdapter(userData, this)
        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchResults.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchUsers() {
        try {
            val call = getRetrofit().create(ApiService::class.java)
            call.getUsers().enqueue(object : Callback<List<Users>> {
                override fun onResponse(
                    call: Call<List<Users>>,
                    response: Response<List<Users>>
                ) {
                    val users = response.body()
                    if (users != null) {
                        users.size.also { cantUsers = it }
                        for (i in users.indices) {
                            val user =
                                User(users[i].name, users[i].phone, users[i].email, users[i].id)

                            CoroutineScope(Dispatchers.IO).launch {
                                database.users().insertAll(user)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        } catch (e: Exception) {
            Log.e("ERROR", e.stackTraceToString())
        }
    }

    override fun onPostClickListenet(position: Int) {
        val intent = Intent(this@MainActivity, PostActivity::class.java)
        val infoUser: User = userData[position]

        intent.putExtra("idUser", infoUser.id)
        intent.putExtra("nameUser", infoUser.name)
        intent.putExtra("phoneUser", infoUser.phone)
        intent.putExtra("emailUser", infoUser.email)

        startActivity(intent)
        finish()
    }

}