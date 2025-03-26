package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ScheduleJpaRepository : JpaRepository<ScheduleEntity, Long> {

    @Query(
        """
        SELECT s 
        FROM ScheduleEntity s 
        WHERE s.totem.id = :totemId
    """
    )
    fun findScheduleByTotemId(@Param("totemId") totemId: Long): ScheduleEntity?
}