package thjava.thbuoi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thjava.thbuoi2.models.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	// Kiểm tra sản phẩm đã nằm trong đơn hàng nào chưa (dùng trước khi xóa sản phẩm)
	boolean existsByProductId(Long productId);
}
