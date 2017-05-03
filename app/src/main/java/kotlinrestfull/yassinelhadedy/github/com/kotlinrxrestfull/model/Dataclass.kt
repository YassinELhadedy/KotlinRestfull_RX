package k2rx.yassinelhadedy.github.com.kotlinretrrx.model

/**
 * Created by Elhadedy on 4/19/2017.
 */
data class Movie(val title:String,val episodeId:Int,val characters:MutableList<Characters>)
data class Characters(val name :String,val gender:String){
    override fun toString():String{
        return "${name} - ${gender}"
    }
}
