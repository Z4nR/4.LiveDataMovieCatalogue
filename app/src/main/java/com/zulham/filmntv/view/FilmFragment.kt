package com.zulham.filmntv.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.filmntv.model.DataModel
import com.zulham.filmntv.adapter.FilmAdapter
import com.zulham.filmntv.viewmodel.FilmViewModel
import com.zulham.filmntv.R
import kotlinx.android.synthetic.main.fragment_film.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FilmFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var filmViewModel: FilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filmViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FilmViewModel::class.java)

        filmViewModel.setData()

        filmViewModel.getData().observe(viewLifecycleOwner, {
            recyclerV(it) }
        )
    }

    private fun recyclerV(films: ArrayList<DataModel>){
        recyclerV.apply {
            adapter = FilmAdapter(films, object : FilmAdapter.OnItemClicked {
                override fun onItemClick(position: Int) {
                    Toast.makeText(context, films[position].title+" Clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("film", films[position])
                    startActivity(intent)
                }
            })

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}