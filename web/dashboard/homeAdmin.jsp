<%-- 
    Document   : home
    Created on : Feb 8, 2025, 5:59:03 PM
    Author     : TIEN DAT PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <title>Bảng Điều Khiển</title>


    </head>

    <body>
        <div id="main">
            <div class="header">
                <div class="name-project">
                    <h2>Rice storage</h2>
                </div>


              <div class="balance">
    <a href="loginURL?service=logoutUser">Log out</a>
</div>

            </div>
            <div class="sidebar">
                <div class="logo">Bảng Điều Khiển</div>
                <a href="#">Trang chủ</a>
                <a href="#">Sổ ghi nợ</a>
                <a href="#">Quản lý kho</a>
                <a href="#">Tạo hóa đơn</a>
                <a href="#">Danh sách hóa đơn</a>
                <a href="#">Liên hệ</a>                
<!--                 Thêm các mục dài để hiển thị thanh trượt 
                <a href="#">Báo cáo tài chính</a>
                <a href="#">Báo cáo hàng hóa</a>
                <a href="#">Hồ sơ người dùng</a>
                <a href="#">Cài đặt hệ thống</a>
                <a href="#">Phân quyền</a>
                <a href="#">Quản lý người dùng</a>
                <a href="#">Quản lý đơn hàng</a>
                <a href="#">Thống kê bán hàng</a>
                <a href="#">Quản lý sản phẩm</a>
                <a href="#">Lịch sử giao dịch</a>
                <a href="#">Quản lý danh mục</a>
                <a href="#">Quản lý tài khoản</a>-->
            </div>


            <div class="main-content">
                <div class="notification">
                    Thông báo: Mọi người có thể liên hệ admin tại fanpage Group 4
                </div>


                <div class="table-container">
                    <h3>Sản phẩm</h3>
                    <div class="filters">
                        <select>
                            <option value="">Trạng thái</option>
                            <option value="">A->Z</option>
                            <option value="">Z->A</option>

                        </select>
                        <input type="text" placeholder="Từ">
                        <input type="text" placeholder="Đến">
                        <button>Bỏ lọc</button>
                        <button>Thu gọn</button>
                    </div>

                    <table>
                        <thead id="table-header">
                            <tr>
                                <th>Mã sản phẩm</th>
                                <th>Tên sản phẩm</th>
                                <th>Người bán</th>
                                <th>Chủ đề</th>
                                <th>Phương thức</th>
                                <th>Công khai</th>
                                <th>Giá tiền</th>
                                <th>Ảnh</th>
                                <th>Nơi chứa sản phẩm</th>
                            </tr>
                        </thead>
                            <tbody id="table-tbody">

                                <tr class="no-rows">
                                    <!--                                <td colspan="8" style="text-align: center;">No rows found</td>
                                                                     <td >1</td>
                                                                    <td >2</td>
                                                                    <td >3</td>
                                                                    <td >4</td>
                                                                    <td >5</td>
                                                                    <td >No rows found</td> -->
                                </tr>
                            </tbody>
                    </table>
                </div>
            </div>
        </div>
        
    </div>
    </body>
    
    

    <script>
        // Lấy tbody
        const tbody = document.getElementById("table-tbody");

        // Dữ liệu mẫu (thêm 30 mục)
        const data = [
            {
                maTrungGian: "TG001",
                trangThai: "Hoàn thành",
                nguoiBan: "Nguyễn Văn A",
                chuDe: "Gạo tám thơm",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "500,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG002",
                trangThai: "Đang xử lý",
                nguoiBan: "Trần Thị B",
                chuDe: "Gạo ST25",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "700,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG003",
                trangThai: "Đã hủy",
                nguoiBan: "Lê Văn C",
                chuDe: "Gạo lứt",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "450,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG004",
                trangThai: "Hoàn thành",
                nguoiBan: "Hoàng Thị D",
                chuDe: "Gạo Jasmine",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "550,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG005",
                trangThai: "Đang xử lý",
                nguoiBan: "Nguyễn Thị E",
                chuDe: "Gạo ST24",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "800,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG006",
                trangThai: "Đã hủy",
                nguoiBan: "Lê Thị F",
                chuDe: "Gạo Lài",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "600,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG007",
                trangThai: "Hoàn thành",
                nguoiBan: "Trần Văn G",
                chuDe: "Gạo Nàng Hoa",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "650,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG008",
                trangThai: "Đang xử lý",
                nguoiBan: "Hoàng Văn H",
                chuDe: "Gạo Bắc Hương",
                phuongThuc: "Trực tiếp",
                congKhai: "Không",
                giaTien: "500,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG009",
                trangThai: "Hoàn thành",
                nguoiBan: "Nguyễn Văn I",
                chuDe: "Gạo Nếp",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "750,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG010",
                trangThai: "Đã hủy",
                nguoiBan: "Trần Văn J",
                chuDe: "Gạo Đài Loan",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "900,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG011",
                trangThai: "Hoàn thành",
                nguoiBan: "Nguyễn Thị K",
                chuDe: "Gạo Tám Xoan",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "650,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG012",
                trangThai: "Đang xử lý",
                nguoiBan: "Lê Văn L",
                chuDe: "Gạo ST10",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "900,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG013",
                trangThai: "Đã hủy",
                nguoiBan: "Trần Thị M",
                chuDe: "Gạo Khẩu Minh",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "500,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG014",
                trangThai: "Hoàn thành",
                nguoiBan: "Hoàng Thị N",
                chuDe: "Gạo Lai Mỹ",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "700,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG015",
                trangThai: "Đang xử lý",
                nguoiBan: "Nguyễn Văn O",
                chuDe: "Gạo Thơm Chợ Lớn",
                phuongThuc: "Trực tiếp",
                congKhai: "Không",
                giaTien: "800,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG016",
                trangThai: "Hoàn thành",
                nguoiBan: "Lê Văn P",
                chuDe: "Gạo Lúa Mới",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "500,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG017",
                trangThai: "Đã hủy",
                nguoiBan: "Trần Thị Q",
                chuDe: "Gạo Sen Sôi",
                phuongThuc: "Trực tiếp",
                congKhai: "Không",
                giaTien: "600,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG018",
                trangThai: "Đang xử lý",
                nguoiBan: "Hoàng Văn R",
                chuDe: "Gạo Mùa Vàng",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "850,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG019",
                trangThai: "Hoàn thành",
                nguoiBan: "Nguyễn Thị S",
                chuDe: "Gạo Tam Mai",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "950,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG020",
                trangThai: "Đã hủy",
                nguoiBan: "Lê Thị T",
                chuDe: "Gạo Nàng Hoa",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "450,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG021",
                trangThai: "Hoàn thành",
                nguoiBan: "Trần Thị U",
                chuDe: "Gạo ST10",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "600,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG022",
                trangThai: "Đang xử lý",
                nguoiBan: "Hoàng Thị V",
                chuDe: "Gạo Bảy Nở",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "750,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG023",
                trangThai: "Đã hủy",
                nguoiBan: "Nguyễn Thị W",
                chuDe: "Gạo ST25",
                phuongThuc: "Trực tiếp",
                congKhai: "Không",
                giaTien: "700,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG024",
                trangThai: "Hoàn thành",
                nguoiBan: "Lê Văn X",
                chuDe: "Gạo Mặt Trời",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "950,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG025",
                trangThai: "Đang xử lý",
                nguoiBan: "Trần Thị Y",
                chuDe: "Gạo Khẩu Minh",
                phuongThuc: "Trực tiếp",
                congKhai: "Không",
                giaTien: "600,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG026",
                trangThai: "Đã hủy",
                nguoiBan: "Hoàng Văn Z",
                chuDe: "Gạo Thái Bình",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "500,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG027",
                trangThai: "Hoàn thành",
                nguoiBan: "Nguyễn Văn AA",
                chuDe: "Gạo Nhật",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "750,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG028",
                trangThai: "Đang xử lý",
                nguoiBan: "Lê Văn AB",
                chuDe: "Gạo Lúa Nếp",
                phuongThuc: "Giao hàng",
                congKhai: "Không",
                giaTien: "850,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG029",
                trangThai: "Đã hủy",
                nguoiBan: "Trần Thị AC",
                chuDe: "Gạo Chín Đỏ",
                phuongThuc: "Trực tiếp",
                congKhai: "Có",
                giaTien: "600,000đ",
                anh: "https://via.placeholder.com/50",
            },
            {
                maTrungGian: "TG030",
                trangThai: "Hoàn thành",
                nguoiBan: "Hoàng Thị AD",
                chuDe: "Gạo Nếp Cẩm",
                phuongThuc: "Giao hàng",
                congKhai: "Có",
                giaTien: "950,000đ",
                anh: "https://via.placeholder.com/50",
            }
        ];

        // Kiểm tra nếu có dữ liệu để hiển thị
        if (data.length > 0) {
            // Xóa các hàng "No rows found" nếu có
            const noRows = document.querySelectorAll('.no-rows');
            noRows.forEach(row => row.remove());

            // Tạo các hàng dữ liệu
            data.forEach(item => {
                const row = document.createElement("tr");

                // Tạo các ô trong hàng
                row.innerHTML = `
          <td>${item.maTrungGian}</td>
          <td>${item.trangThai}</td>
          <td>${item.nguoiBan}</td>
          <td>${item.chuDe}</td>
          <td>${item.phuongThuc}</td>
          <td>${item.congKhai}</td>
          <td>${item.giaTien}</td>
          <td><img src="${item.anh}" alt="Ảnh" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;"></td>
          <td><button style="padding: 5px 10px; border: none; background-color: #d9534f; color: white; border-radius: 4px; cursor: pointer;">Hành động</button></td>
        `;

                tbody.appendChild(row);
            });
        } else {
            // Nếu không có dữ liệu, hiển thị thông báo "No rows found"
            const row = document.createElement("tr");
            row.className = "no-rows";

            const cell = document.createElement("td");
            cell.colSpan = document.querySelectorAll("thead th").length;
            cell.style.textAlign = "center";
            cell.textContent = "No rows found";

            row.appendChild(cell);
            tbody.appendChild(row);
        }
        
        
        


    </script>


</html>
