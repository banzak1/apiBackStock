package com.stock_api.repository;

import com.stock_api.model.Chart;
import com.stock_api.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    @Query(nativeQuery = true, value = """
                select * from grafico as gra where
                gra.id_stock = :id_stock and
                date_trunc('hour', gra.created_on) = date_trunc('hour', cast (:now as Timestamp))
            """)
    Optional<Chart> findByIdAndDate(@Param("id_stock") Long idStock, @Param("now") Timestamp agora);

    @Query(value = "select * from grafico where id_stock = ?1", nativeQuery = true)
    List<Chart> findByStock(Stocks idStock);
}
