package com.lumen.valez_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface TicketDao {
    @Upsert
    suspend fun insertTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)

    @Query("SELECT * FROM ticket ORDER BY ticketId ASC")
    fun getTickets(): Flow<List<Ticket>>
}*/
