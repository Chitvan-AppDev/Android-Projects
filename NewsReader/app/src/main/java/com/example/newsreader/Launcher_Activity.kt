package com.example.newsreader

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Launcher_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    fun newsType(view: View) {
        lateinit var value:String
        if(view.tag.toString() == "general"){
            value="general"
        }
        else if(view.tag.toString() == "technology"){
            value="technology"
        }
        else if(view.tag.toString() == "sports"){
            value="sports"
        }
        else if(view.tag.toString() == "business"){
            value="business"
        }
        else if(view.tag.toString() == "entertainment"){
            value="entertainment"
        }

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(
            "TypeOfNews",value

        ) //this is used to pass extra info with the intent

        startActivity(intent)
    }
}