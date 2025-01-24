package myhaja.kata.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

    @Query("""
			select a from ActivityJpaEntity a 
			where a.ownerAccountId = :ownerAccountId
			""")
    List<ActivityJpaEntity> findByOwner(
            @Param("ownerAccountId") long ownerAccountId);

	@Query("""
			select sum(a.amount) from ActivityJpaEntity a
			where a.targetAccountId = :accountId
			and a.ownerAccountId = :accountId
			""")
	Optional<BigDecimal> getDepositBalance(
			@Param("accountId") long accountId);

	@Query("""
			select sum(a.amount) from ActivityJpaEntity a
			where a.sourceAccountId = :accountId
			and a.ownerAccountId = :accountId
			""")
	Optional<BigDecimal> getWithdrawalBalance(
			@Param("accountId") long accountId);
}
