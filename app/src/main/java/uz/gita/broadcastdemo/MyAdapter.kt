package uz.gita.broadcastdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.broadcastdemo.databinding.EventItemBinding

class MyAdapter : ListAdapter<EventEntity, MyAdapter.VH>(DiffCallback()) {

    private var onItemClickListener: ((EventEntity) -> Unit)? = null

    fun setOnItemClickListener(block: (EventEntity) -> Unit) {
        onItemClickListener = block
    }

    inner class VH(private val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.itemImg.setBackgroundResource(currentList[bindingAdapterPosition].image)
            binding.itemName.text = currentList[bindingAdapterPosition].name
            binding.itemStatus.isChecked = currentList[bindingAdapterPosition].status
        }

        init {
            binding.itemStatus.setOnClickListener {
                onItemClickListener?.invoke(currentList[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

}

class DiffCallback : DiffUtil.ItemCallback<EventEntity>() {
    override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.image == newItem.image &&
                oldItem.name == newItem.name && oldItem.status == newItem.status
    }

}