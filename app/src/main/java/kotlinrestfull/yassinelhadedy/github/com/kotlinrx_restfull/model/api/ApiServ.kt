package k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by Elhadedy on 4/19/2017.
 */
interface ApiServ {
    @GET("films")
    fun listmovies():Observable<ResultFilm>
    @GET("people/{personId}")
    fun loadperson(@Path("personId")pesonid:String):Observable<Person>
}