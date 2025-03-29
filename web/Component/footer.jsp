<%-- 
    Document   : footer
    Created on : 25 thg 2, 2025, 21:29:58
    Author     : nguyenanh
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Footer</title>
        <style>
            .footer-container {
                /*                position: fixed;*/
                bottom: 0;
                left: 0;
                width: auto; /* Giảm chiều rộng theo sidebar */
                max-width: 1200px; /* Giới hạn chiều rộng */
                background-color: #333;
                color: white;
                margin-left: -9px;
                padding: 17px;
                font-size: 14px;
            }
            .footer-container a {
                color: white;
                text-decoration: none;
                transition: 0.3s;
            }
            .footer-container a:hover {
                text-decoration: underline;
            }
        </style>
        <!--        <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        let footer = document.querySelector(".footer-container"); // Lấy phần tử footer
                        if (footer) {
                            let footerHeight = footer.offsetHeight; // Lấy chiều cao thực tế
                            document.body.style.paddingBottom = footerHeight + "px"; // Đẩy nội dung lên
                        }
                    });
        
                </script>-->
    </head>
    <body>
        <div class="footer-container">
            <a href="https://github.com/HaPhiHung-HE186793/Sp25_ISP392_IS1806_G4-" target="_blank">
                Powered by: Group 4 ISP392 ©2025
            </a>
        </div>
    </body>
</html>

