package com.example.newsreader

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
public  var titleArray=ArrayList<String>()
public var items : ArrayList<News> = ArrayList()
class NewsListAdapter(private val listner:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder> (){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)// in this layout inflater is used to convert xml file into view so that we can pass it in View Holder ...
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener {
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }//it gets calls no. of views in screen at a time  times..

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        for(x in items){
//            titleArray.add(x.title)
//        }

        val currentItem= items[position]
        holder.titleView.text=currentItem.title

        if(currentItem.author!="null"){
        holder.author.text=currentItem.author}
        else{
            holder.author.text="No Specific Author"}

        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)



    }//this fun binds the items in holder

    override fun getItemCount(): Int {
        return items.size
    }// it tells no. of items
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()

    }



}


// in kotlin if we want to extend one class to another we do it by placing : as above ....
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.title)//in this we are telling view holder that we have a different xml file for our item
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)


}// in this class we have view holder which actually defines different items in the recycler view...
interface NewsItemClicked{
fun onItemClicked(item:News)
}// this is made for onClickListner

