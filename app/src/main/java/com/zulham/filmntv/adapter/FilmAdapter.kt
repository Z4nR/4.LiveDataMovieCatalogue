package com.zulham.filmntv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.filmntv.R
import com.zulham.filmntv.adapter.FilmAdapter.ViewHolder
import com.zulham.filmntv.model.DataModel
import com.zulham.filmntv.model.DataModelDetail
import kotlinx.android.synthetic.main.item.view.*

class FilmAdapter(private val listItems: ArrayList<DataModel>)
    : RecyclerView.Adapter<ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DataModel){
            with(itemView){
                Glide.with(itemView.context)
                    .load(item.img)
                    .apply(RequestOptions().override(110, 110))
                    .into(img_poster)


                tv_item_title.text = item.title
                tv_item_date.text = item.releaseDate
                tv_item_vote.text = item.vote

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item) }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int = listItems.size
}
