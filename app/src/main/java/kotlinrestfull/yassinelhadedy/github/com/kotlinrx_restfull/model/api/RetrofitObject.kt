package k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api

import android.net.Uri
import com.google.gson.GsonBuilder
import k2rx.yassinelhadedy.github.com.kotlinretrrx.model.Charcters
import k2rx.yassinelhadedy.github.com.kotlinretrrx.model.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by Elhadedy on 4/19/2017.
 */
class RetrofitObject {
    val service : ApiServ
    val peoplechace= mutableMapOf<String,Person>()
    init {
        val logging=HttpLoggingInterceptor()
        logging.level=HttpLoggingInterceptor.Level.BODY
        val httpclient=OkHttpClient.Builder()
        httpclient.addInterceptor (logging)
        val gson=GsonBuilder().setLenient().create()
        val retrofit=Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpclient.build())
                .build()
        service=retrofit.create<ApiServ>(ApiServ::class.java)

    }
    fun loadmovies():Observable<Movie>{
       return service.listmovies()
                .flatMap { ResultFilm -> Observable.from(ResultFilm.results) }
                .flatMap { Film ->       Observable.just(Movie(Film.title,Film.epsoid_id,ArrayList<Charcters>())) }
    }
    fun loadmoviesfull(): Observable<Movie> {
        return service.listmovies()
                .flatMap { ResultFilm -> Observable.from(ResultFilm.results) }
                .flatMap { Film ->       Observable.zip( Observable.just(Movie(Film.title,Film.epsoid_id,ArrayList<Charcters>())),
                        Observable.from(Film.person_url).flatMap{ person_url ->
                            Observable.concat(getchace(person_url), service.loadperson(Uri.parse(person_url).lastPathSegment).doOnNext {Person ->


                                peoplechace.put(person_url,Person)
                            })
                                    .first()



                        }
                                .flatMap{Person -> Observable.just(Charcters(Person.name,Person.gender))}
                                .toList(),{
                    Movie , Charcters -> Movie.charcters.addAll(Charcters)
                    Movie
                }
                )
                }

    }
    private fun getchace(pesorurl:String):Observable<Person>{
        return Observable.from(peoplechace.keys)
                .filter { key -> key==pesorurl }
                .flatMap{key -> Observable.just(peoplechace[pesorurl])}
    }

}
