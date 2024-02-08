package com.lumen.valez_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface EmployeeDao {
    @Upsert
    suspend fun insertEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee ORDER BY employeeId ASC")
    fun getEmployees(): Flow<List<Employee>>
}*/
