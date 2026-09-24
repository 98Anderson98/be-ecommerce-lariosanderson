package com.ecommerce.repository;

import com.ecommerce.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    @Query("SELECT COUNT(d) > 0 FROM DetallePedido d WHERE d.producto.id = :productoId")
    boolean existePorProductoId(@Param("productoId") Long productoId);
}
