package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignProjection
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

@Repository
interface CampaignJpaRepository : JpaRepository<CampaignEntity, Long> {

    @Modifying
    @Query("UPDATE CampaignEntity c SET c.status = :status WHERE c.id = :id")
    fun updateStatusById(@Param("id") id: Long, @Param("status") status: Status): Int

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

    @Query(
        """
        SELECT c
        FROM CampaignEntity c
        JOIN c.totems t
        WHERE t.id = :totemId 
          AND c.status = 'ACTIVE'
          AND CURRENT_DATE BETWEEN c.startDate AND c.endDate
    """
    )
    fun findActiveCampaignsByTotemId(@Param("totemId") totemId: Long): List<CampaignProjection>

    @Query("SELECT COUNT(c) FROM CampaignEntity c WHERE c.status = 'ACTIVE'")
    fun countActiveCampaigns(): Long

    @Query("SELECT COUNT(c) FROM CampaignEntity c WHERE c.status = 'ACTIVE' AND c.company.id = :companyId")
    fun countActiveCampaignsByCompanyId(@Param("companyId") id: Long): Long

    fun findByStatus(status: Status): List<CampaignEntity>
}