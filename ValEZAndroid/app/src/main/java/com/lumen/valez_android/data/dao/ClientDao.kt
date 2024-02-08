package com.lumen.valez_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface ClientDao {
    @Upsert
    suspend fun insertClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("SELECT * FROM client ORDER BY clientId ASC")
    fun getClients(): Flow<List<Client>>
}*/
