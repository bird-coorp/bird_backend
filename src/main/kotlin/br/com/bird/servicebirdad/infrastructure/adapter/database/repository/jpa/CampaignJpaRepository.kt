package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

@Repository
interface CampaignJpaRepository : JpaRepository<CampaignEntity, Long> {

    @Query(
        value = "SELECT c FROM CampaignEntity c " +
                "LEFT JOIN c.company d " +
                "WHERE c.company.id = :firmId " +
                "ORDER BY c.id DESC",
        countQuery = "SELECT COUNT(c) FROM CampaignEntity c WHERE c.company.id = :firmId"
    )
    fun findByCompanyId(@Param("firmId") firmId: Long, pageable: Pageable): Page<CampaignEntity>

    @EntityGraph(attributePaths = ["company", "totems"])
    override fun findById(id: Long): Optional<CampaignEntity>
}