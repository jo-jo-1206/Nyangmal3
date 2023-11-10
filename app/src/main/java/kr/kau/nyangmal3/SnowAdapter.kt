package kr.kau.nyangmal3

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SnowAdapter(private val snowList: List<SnowItem>) :
    RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    class SnowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val postTextView: TextView = itemView.findViewById(R.id.postTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnowViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_snow, parent, false)
        return SnowViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SnowViewHolder, position: Int) {
        val currentItem = snowList[position]

        holder.userNameTextView.text = currentItem.userName
        holder.postTextView.text = currentItem.postText

        // Format timestamp to display time elapsed
        val timestampInMillis = currentItem.timestamp
        val elapsedMillis = System.currentTimeMillis() - timestampInMillis
        val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis)
        holder.timestampTextView.text = "$hours hours ago"
    }

    override fun getItemCount(): Int {
        return snowList.size
    }
}