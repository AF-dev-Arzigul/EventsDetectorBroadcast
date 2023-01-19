package uz.gita.broadcastdemo

import androidx.lifecycle.LiveData

interface MainViewModel {
    val liveData: LiveData<List<EventEntity>>

    fun insert(entity: EventEntity)
    fun update(entity: EventEntity)

}