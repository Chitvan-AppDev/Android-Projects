package com.example.socialmediaapplication

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapplication.daos.PostDao
import com.example.socialmediaapplication.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var postDao: PostDao
    private lateinit var adapter:PostAdapter
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        fab.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)


    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

//    override fun onItemClicked(postId: String) {
//        AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_btn_speak_now)
//                .setTitle("Do you want to delete the post")
//                .setMessage("Choose")
//                .setPositiveButton("Yes") { dialog, which ->
//                    Toast.makeText(this@MainActivity, "Done:)", Toast.LENGTH_SHORT).show()
//
//                }
//                .setNegativeButton("No") { dialog, which ->
//                    Toast.makeText(this@MainActivity, "Done:)", Toast.LENGTH_SHORT).show()
//
//                }
//                .show()
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.profile -> {
                Log.i("selected:", "profile")
                val profileInfoActivityIntent = Intent(this, ProfileInfoActivity::class.java)
                startActivity(profileInfoActivityIntent)
                true
            }
            R.id.signout -> {
                Log.i("selected:", "sign_out")
                auth.signOut()
                val signInActivityIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInActivityIntent)
                finish()
                true
            }

            else -> false
        }
    }
}