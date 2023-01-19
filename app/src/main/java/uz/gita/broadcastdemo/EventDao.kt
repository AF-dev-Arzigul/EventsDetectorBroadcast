package uz.gita.broadcastdemo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(eventEntity: EventEntity)

    @Update
    suspend fun updateEvent(eventEntity: EventEntity)

    @Query("SELECT * FROM EventEntity")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM EventEntity WHERE id=:id")
    suspend fun getEventById(id: Int): EventEntity

    @Query("SELECT COUNT(*) FROM EventEntity")
    suspend fun getCount(): Int
}