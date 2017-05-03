package k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api

import android.net.Uri
import com.google.gson.GsonBuilder
import k2rx.yassinelhadedy.github.com.kotlinretrrx.model.Characters
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
    val personMap= mutableMapOf<String,Person>()
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
       return service.listMovies()
                .flatMap { ResultFilm -> Observable.from(ResultFilm.results) }
                .flatMap { Film ->       Observable.just(Movie(Film.title,Film.episodeId,ArrayList<Characters>())) }
    }
    fun loadmoviesfull(): Observable<Movie> {
        return service.listMovies()
                .flatMap { resultFilm -> Observable.from(resultFilm.results) }
                .flatMap { film ->       Observable.zip( Observable.just(Movie(film.title,film.episodeId,ArrayList<Characters>())),
                        Observable.from(film.personUrl).flatMap{ person_url ->
                            Observable.concat(getPerson(person_url), service.loadPerson(Uri.parse(person_url).lastPathSegment).doOnNext {Person ->


                                personMap.put(person_url,Person)
                            })
                                    .first()



                        }
                                .flatMap{Person -> Observable.just(Characters(Person.name,Person.gender))}
                                .toList(),{
                    Movie , Charcters -> Movie.characters.addAll(Charcters)
                    Movie
                }
                )
                }

    }
    private fun getPerson(personUrl:String):Observable<Person>{//MUST return an observable to concant to above
        return Observable.from(personMap.keys)
                .filter { key -> key==personUrl }
                .flatMap{ key -> Observable.just(personMap[personUrl])}
    }

}
