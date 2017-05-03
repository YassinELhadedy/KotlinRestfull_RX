package kotlinrestfull.yassinelhadedy.github.com.kotlinrxrestfull.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinrestfull.yassinelhadedy.github.com.kotlinrxrestfull.R
import kotlinx.collections.immutable.ImmutableList

/**
 * Created by Elhadedy on 5/3/2017.
 */

class RecycleAdapter(internal var mcontext: Context, internal var mlist: ImmutableList<String> ) : RecyclerView.Adapter<RecycleAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val moviedesign = LayoutInflater.from(mcontext).inflate(R.layout.movie_design, parent, false)
        return MovieHolder(moviedesign)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val photo=mlist[position]
        holder.movietxt.text=photo

    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movietxt: TextView

        init {
            movietxt = itemView.findViewById(R.id.tv) as TextView
        }
    }
}
