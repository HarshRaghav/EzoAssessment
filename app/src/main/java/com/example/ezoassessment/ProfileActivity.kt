package com.example.ezoassessment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ezoassessment.data.Constants.SelectedItem
import com.example.ezoassessment.data.models.User
import com.example.ezoassessment.databinding.ActivityMainBinding
import com.example.ezoassessment.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MyApplication).appComponent.inject(this)

        var user: User ?= null
        if(intent.hasExtra(SelectedItem)){
            user = intent.getSerializableExtra(SelectedItem) as User
        }

        if(user != null){

            val name = user.first_name + " " + user.last_name
            val id = "id: " + user.id
            binding.profileId.text = id
            binding.profileName.text = name
            binding.profileEmail.text = user.email
            Glide.with(this).load(user.avatar).into(binding.profileImage)
        }

    }
}