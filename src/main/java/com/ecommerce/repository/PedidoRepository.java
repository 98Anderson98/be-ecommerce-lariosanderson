package com.ecommerce.repository;

import com.ecommerce.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstadoNamed(@Param("estado") String estado);

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalles d LEFT JOIN FETCH d.producto WHERE p.id = :id")
    Optional<Pedido> buscarPorIdConDetallesJPQL(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalles d LEFT JOIN FETCH d.producto ORDER BY p.id")
    List<Pedido> listarConDetallesJPQL();

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalles d LEFT JOIN FETCH d.producto "
         + "WHERE LOWER(p.cliente) = LOWER(:cliente) ORDER BY p.id")
    List<Pedido> buscarPorClienteConDetallesJPQL(@Param("cliente") String cliente);
}
