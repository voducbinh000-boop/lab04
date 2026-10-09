package thjava.thbuoi2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import thjava.thbuoi2.models.Category;
import thjava.thbuoi2.models.Product;
import thjava.thbuoi2.repository.CategoryRepository;
import thjava.thbuoi2.repository.OrderDetailRepository;
import thjava.thbuoi2.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final OrderDetailRepository orderDetailRepository;

	public ProductService(ProductRepository productRepository, 
	                      CategoryRepository categoryRepository, 
	                      OrderDetailRepository orderDetailRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.orderDetailRepository = orderDetailRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public Product addProduct(Product product) {
		product.setCategory(resolveCategory(product.getCategory()));
		return productRepository.save(product);
	}

	public Product updateProduct(@NotNull Product product) {
		Product existingProduct = productRepository.findById(product.getId())
				.orElseThrow(() -> new IllegalStateException("Sản phẩm ID " + product.getId() + " không tồn tại."));
		
		existingProduct.setName(product.getName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setCategory(resolveCategory(product.getCategory()));
		
		return productRepository.save(existingProduct);
	}

	public void deleteProductById(Long id) {
		if (!productRepository.existsById(id)) {
			throw new IllegalStateException("Sản phẩm ID " + id + " không tồn tại.");
		}
		if (orderDetailRepository.existsByProductId(id)) {
			throw new IllegalStateException("Sản phẩm ID " + id + " đã nằm trong đơn hàng, không thể xóa.");
		}
		productRepository.deleteById(id);
	}

	private Category resolveCategory(Category category) {
		if (category == null || category.getId() == null) {
			return null;
		}
		return categoryRepository.findById(category.getId())
				.orElseThrow(() -> new IllegalStateException("Danh mục ID " + category.getId() + " không tồn tại."));
	}
}