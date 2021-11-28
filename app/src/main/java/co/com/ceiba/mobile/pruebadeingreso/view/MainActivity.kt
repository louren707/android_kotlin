package co.com.ceiba.mobile.pruebadeingreso.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding
import co.com.ceiba.mobile.pruebadeingreso.db.AppDatabase
import co.com.ceiba.mobile.pruebadeingreso.db.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var listUsers = emptyList<User>()

        val database = AppDatabase.getDatabase(this)

        database.users().getAll().observe(this, Observer {
            listUsers = it
        })

    }

}