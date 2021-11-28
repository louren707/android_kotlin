package co.com.ceiba.mobile.pruebadeingreso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding
import co.com.ceiba.mobile.pruebadeingreso.db.User

class UsersAdapter(val users: List<User>, val listener: OnPostClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UserListItemBinding.bind(view)

        fun bind(user: User) {
            binding.name.text = user.name
            binding.phone.text = user.phone
            binding.email.text = user.email
        }

        init {
            binding.btnViewPost.setOnClickListener {
                listener.onPostClickListenet(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = users.size

    interface OnPostClickListener {
        fun onPostClickListenet(position: Int)
    }
}

