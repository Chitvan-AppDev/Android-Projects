package com.example.socialmediaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile_info.*

class ProfileInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)

        val auth=Firebase.auth
        val name= auth.currentUser?.displayName.toString()
        val email=auth.currentUser?.email.toString()
        val photoUrl=auth.currentUser?.photoUrl
        Username.text = Username.text.toString()+" $name"
        Email.text=Email.text.toString()+" $email"
        Glide.with(this).load(photoUrl).circleCrop().into(UserImage)

    }
}