package uz.gita.broadcastdemo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Int,
    val name: String,
    val status: Boolean = false
)
