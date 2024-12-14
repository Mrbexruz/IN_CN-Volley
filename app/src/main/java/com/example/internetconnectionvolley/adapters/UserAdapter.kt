package com.example.internetconnectionvolley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internetconnectionvolley.databinding.ItemRvBinding
import com.example.internetconnectionvolley.models.User

class UserAdapter(val list: List<User>) : RecyclerView.Adapter<UserAdapter.Vh>() {

    private val userList = ArrayList<User>()

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User) {
            // Matnlarni o'rnatish
            itemRvBinding.tv1.text = user.login
            itemRvBinding.tv2.text = user.id.toString()

            // Tasvirni Glide yordamida yuklash
            Glide.with(itemRvBinding.rasm.context)
                .load(user.avatar_url) // URL manzili
                .centerCrop()          // Tasvirni crop qilish
                .into(itemRvBinding.rasm) // ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    fun submitList(newList: List<User>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }
}
