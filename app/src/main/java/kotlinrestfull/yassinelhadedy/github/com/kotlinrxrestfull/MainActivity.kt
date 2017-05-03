package kotlinrestfull.yassinelhadedy.github.com.kotlinrxrestfull

import android.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api.RetrofitObject
import kotlinx.collections.immutable.immutableListOf
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {



    var movisadapter: ArrayAdapter<String>?=null

   // var movisadapter:RecycleAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_main)
        val listview: ListView
        //val listview:RecyclerView
       val movies = immutableListOf<String>()
//       val movies = mutableListOf<String>()






        listview= ListView(this)
      //  listview=RecyclerView(this)

        setContentView(listview)


        movisadapter= ArrayAdapter(this, R.layout.simple_list_item_1,movies)
        //movisadapter=RecycleAdapter(this,movies)
        listview.adapter=movisadapter

        val api= RetrofitObject()
        // api .loadmovies().subscribeOn(Schedulers.io())
        api.loadmoviesfull()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({movie -> movies.add("${movie.title}-${movie.episodeId} \r ${movie.characters.toString()}")},
                        {
                            e -> e.printStackTrace()
                        },{

                    movisadapter?.notifyDataSetChanged()
                })


    }
}
