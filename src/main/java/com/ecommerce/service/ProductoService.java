package com.ecommerce.service;

import com.ecommerce.dto.ProductoRequestDTO;
import com.ecommerce.dto.ProductoResponseDTO;
import com.ecommerce.entity.Producto;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.DetallePedidoRepository;
import com.ecommerce.repository.ProductoCustomRepositoryImpl;
import com.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoCustomRepositoryImpl productoCustomRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {
        return toDto(productoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        return ProductoResponseDTO.fromEntity(buscarEntidad(id));
    }

    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        if (productoRepository.findByCodigoBarrasNamed(dto.getCodigoBarras()).isPresent()) {
            throw new BadRequestException("Ya existe un producto con el codigo de barras: " + dto.getCodigoBarras());
        }
        Producto producto = new Producto();
        copiarDatos(dto, producto);
        return ProductoResponseDTO.fromEntity(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = buscarEntidad(id);
        if (productoRepository.existeCodigoBarrasEnOtroProducto(dto.getCodigoBarras(), id)) {
            throw new BadRequestException("El codigo de barras " + dto.getCodigoBarras() + " ya pertenece a otro producto");
        }
        copiarDatos(dto, producto);
        return ProductoResponseDTO.fromEntity(productoRepository.save(producto));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id);
        }
        if (detallePedidoRepository.existePorProductoId(id)) {
            throw new BadRequestException("No se puede eliminar el producto " + id + " porque tiene pedidos asociados");
        }
        productoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {
        return toDto(productoRepository.findByCategoriaNamed(categoria));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorMarca(String marca) {
        return toDto(productoRepository.findByMarcaNamed(marca));
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorCodigoBarras(String codigoBarras) {
        return productoRepository.findByCodigoBarrasNamed(codigoBarras)
                .map(ProductoResponseDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con codigo de barras: " + codigoBarras));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorRangoPrecio(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) {
            throw new BadRequestException("El precio minimo no puede ser mayor que el precio maximo");
        }
        return toDto(productoRepository.buscarPorRangoPrecioJPQL(min, max));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorNombre(String keyword) {
        return toDto(productoRepository.buscarPorNombreJPQL(keyword));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarAvanzado(String keyword, Integer stockMin) {
        return toDto(productoCustomRepository.buscarPorFiltrosAvanzados(keyword, stockMin));
    }

    Producto buscarEntidad(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));
    }

    private void copiarDatos(ProductoRequestDTO dto, Producto producto) {
        producto.setNombre(dto.getNombre().trim());
        producto.setCategoria(dto.getCategoria().trim());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setMarca(dto.getMarca().trim());
        producto.setCodigoBarras(dto.getCodigoBarras());
    }

    private List<ProductoResponseDTO> toDto(List<Producto> productos) {
        return productos.stream().map(ProductoResponseDTO::fromEntity).toList();
    }
}
