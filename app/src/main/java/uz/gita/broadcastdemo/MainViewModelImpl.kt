package uz.gita.broadcastdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModelImpl : MainViewModel, ViewModel() {
    private val repository = Repository.instance()
    override val liveData = MutableLiveData<List<EventEntity>>()

    init {
        viewModelScope.launch {
            repository.getList().collectLatest {
                liveData.value = it
            }
        }
    }

    override fun insert(entity: EventEntity) {
        viewModelScope.launch {
            repository.insert(entity)
        }
    }

    override fun update(entity: EventEntity) {
        viewModelScope.launch {
            repository.update(entity)
        }
    }

}