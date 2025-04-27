package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.BankBalanceEntity
import jakarta.persistence.LockModeType.PESSIMISTIC_WRITE
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BankBalanceJpaRepository : JpaRepository<BankBalanceEntity, Long> {
    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT c FROM BankBalanceEntity c JOIN c.company u WHERE u.id = :companyId")
    fun findByCompanyId(companyId: Long): Optional<BankBalanceEntity>
}