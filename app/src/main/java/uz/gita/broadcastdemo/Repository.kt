package uz.gita.broadcastdemo

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Repository {
    private val dao = AppDatabase.instance().eventDao()

    suspend fun insert(entity: EventEntity) = dao.insertEvent(entity)

    suspend fun update(entity: EventEntity) = dao.updateEvent(entity)

    fun getList() = dao.getAllEvents()

    init {
        GlobalScope.launch {
            if (dao.getCount() == 0) {
                dao.insertEvent(EventEntity(0, R.drawable.screen_on, "Screen on"))
                dao.insertEvent(EventEntity(0, R.drawable.screen_off, "Screen off"))
                dao.insertEvent(EventEntity(0, R.drawable.battery_full, "Battery full"))
                dao.insertEvent(EventEntity(0, R.drawable.battery_low, "Battery low"))
                dao.insertEvent(EventEntity(0, R.drawable.charging, "Charging"))
                dao.insertEvent(EventEntity(0, R.drawable.discharging, "Discharging"))
                dao.insertEvent(EventEntity(0, R.drawable.airplane_mode, "Airplane mode change"))
            }
        }
    }

    companion object {
        private var repo: Repository? = null
        fun init() {
            repo = Repository()
        }


        fun instance() = repo!!

    }

}