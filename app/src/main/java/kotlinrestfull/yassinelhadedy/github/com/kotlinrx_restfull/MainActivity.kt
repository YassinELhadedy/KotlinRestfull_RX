package kotlinrestfull.yassinelhadedy.github.com.kotlinrx_restfull

import android.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api.RetrofitObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    var listview: ListView?=null
    var movies = mutableListOf<String>()
    var movisadapter: ArrayAdapter<String>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_main)
        listview= ListView(this)

        setContentView(listview)


        movisadapter= ArrayAdapter(this, R.layout.simple_list_item_1,movies)
        listview?.adapter=movisadapter

        val api= RetrofitObject()
        // api .loadmovies().subscribeOn(Schedulers.io())
        api.loadmoviesfull()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Movie -> movies.add("${Movie.title}-${Movie.epsoid_id} \r ${Movie.charcters.toString()}")},
                        {
                            e -> e.printStackTrace()
                        },{
                    movisadapter?.notifyDataSetChanged()
                })


    }
}
