package com.example.newsreader

import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), NewsItemClicked ,TextToSpeech.OnInitListener{
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mAdapter:NewsListAdapter
    public var mTTS: TextToSpeech?=null



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    } // this function will allow us to access our menu..


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.read -> {
                Log.i("selected:", "read")

                processTextToSpeech()

                true
            }
            R.id.stop -> {
                Log.i("selected:", "stop")
                mTTS!!.stop()
                true
            }
            else -> false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager= LinearLayoutManager(this)//go and see build.gradle(module..)..
// in above line we have made recyclerview of linear type ..
        val items = fetchData()
        mAdapter= NewsListAdapter(this)

        mTTS = TextToSpeech(this, this)

        recyclerView.adapter=mAdapter
        /*
* Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
* performs a swipe-to-refresh gesture.
*/
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener {
            Log.i("Refresh", "onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            updateData()


        }


        //speaking
//        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
//            if (status != TextToSpeech.ERROR){
//                //if there is no error then set language
//                mTTS.language = Locale.US
//            }
//        })

    }
    private fun updateData(){
        fetchData()
        mSwipeRefreshLayout.setRefreshing(false)
    }
    private fun fetchData() {
        val intent = intent
        val value = intent.getStringExtra("TypeOfNews")

        if (value!=null){
        val url= "https://newsapi.org/v2/top-headlines?country=in&category=$value&apiKey=c64523ec601540af80d281a657d84b39"
        val jsonObjectRequest = object:JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    val newJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()

                    for (i in 0 until newJsonArray.length()) {
                        val newJsonObeject = newJsonArray.getJSONObject(i)
                        val news = News(
                                newJsonObeject.getString("title"),
                                newJsonObeject.getString("author"),
                                newJsonObeject.getString("url"),
                                newJsonObeject.getString("urlToImage")

                        )
                        newsArray.add(news)
                    }
                    mAdapter.updateNews(newsArray)

                },
                Response.ErrorListener {

                }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }//stackoverflow :Unexpected response code 403 while fetching data from apis

        }
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)}


    }

    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }
    fun onRefresh(){

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
//            val v = Voice("en-us-x-sfg#male_2-local", Locale("en", "US"), 400, 200, true,a)
//            mTTS!!.setVoice()

                //if there is no error then set language
                val result = mTTS!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA||result== TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "language not supported")
            }
            }else{
                Log.e("TTS", "initialization failed")
        }
    }
    override fun onDestroy() {

        if(mTTS!=null){
            mTTS!!.stop()
            mTTS!!.shutdown()
        }
        super.onDestroy()
    }
    private fun processTextToSpeech(){

        var n=1;
        for(item in items) {

//            mTTS = TextToSpeech(this, this)

            var text:String = n.toString()
            text +="."+ item.title
            mTTS!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
            n++
        }

//        Log.i("newsArray:",newsArray.toString())
    }


}