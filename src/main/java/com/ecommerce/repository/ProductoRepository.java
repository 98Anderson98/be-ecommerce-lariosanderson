package com.ecommerce.repository;

import com.ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaNamed(@Param("categoria") String categoria);

    List<Producto> findByMarcaNamed(@Param("marca") String marca);

    Optional<Producto> findByCodigoBarrasNamed(@Param("codigoBarras") String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max ORDER BY p.precio ASC")
    List<Producto> buscarPorRangoPrecioJPQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Producto> buscarPorNombreJPQL(@Param("keyword") String keyword);

    @Query("SELECT COUNT(p) > 0 FROM Producto p WHERE p.codigoBarras = :codigoBarras AND p.id <> :id")
    boolean existeCodigoBarrasEnOtroProducto(@Param("codigoBarras") String codigoBarras, @Param("id") Long id);
}
