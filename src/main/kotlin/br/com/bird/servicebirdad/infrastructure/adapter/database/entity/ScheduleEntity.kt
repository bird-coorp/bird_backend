package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "schedule")
data class ScheduleEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(name = "start_time", nullable = false)
    val from: LocalTime,

    @Column(name = "end_time", nullable = false)
    val to: LocalTime,

    @OneToOne
    @JoinColumn(name = "totem_id", nullable = false, unique = true)
    val totem: TotemEntity
)