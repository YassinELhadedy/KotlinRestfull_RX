package k2rx.yassinelhadedy.github.com.kotlinretrrx.model.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Elhadedy on 4/19/2017.
 */
data class ResultFilm(val results:List<Film>)
data class Film ( val title:String,
                  @SerializedName("episode_id") val episodeId:Int,
                  @SerializedName("characters") val personUrl:List<String> )
data class Person(val name :String,val gender:String)