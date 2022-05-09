package com.example.actiatest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actiatest.R
import com.example.actiatest.databinding.ActivityNavHostBinding
import com.example.actiatest.model.database.Movie
import com.example.actiatest.ui.common.MovieSearchAdapter

class NavHostActivity : AppCompatActivity() {



    lateinit var binding: ActivityNavHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

    }

}
