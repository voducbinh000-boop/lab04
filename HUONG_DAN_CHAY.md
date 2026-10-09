# Hướng dẫn chạy (Eclipse 2026-06 + Laragon)

1. Mở Laragon -> **Start All** (MySQL cổng 3306, user `root`, mật khẩu trống).
2. Eclipse: **File > Import > Existing Maven Projects** -> chọn thư mục `thbuoi2` -> Finish.
   Chờ Maven tải thư viện (cần Internet lần đầu).
3. Chuột phải project > **Maven > Update Project** (Alt+F5) > OK.
4. Chuột phải `Thbuoi2Application.java` > **Run As > Java Application** (hoặc Spring Boot App).
5. Mở trình duyệt:
   - http://localhost:9999/categories
   - http://localhost:9999/products

Database `webbanhang` và các bảng được **tự tạo**. File `database/webbanhang.sql` chỉ là tùy chọn (có dữ liệu mẫu).
Nếu MySQL có mật khẩu: sửa `spring.datasource.password` trong `application.properties`.
Lombok: nếu Eclipse báo lỗi getter/setter, cài Lombok (chạy lombok.jar -> chọn Eclipse) rồi restart.
