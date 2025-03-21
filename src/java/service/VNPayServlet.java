package service;

import utils.VNPayUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(name = "VnpayServlet", urlPatterns = {"/vnpay_payment"})
public class VNPayServlet extends HttpServlet {

    private static final String VNP_TMN_CODE = "3OP1YVAF";  // Mã Website của bạn
    private static final String VNP_HASH_SECRET = "JDGD38HENGYYDAI2XH808SA08EIJXSCV"; // Secret Key
    private static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL thanh toán của VNPAY
    private static final String RETURN_URL = "http://localhost:8080/DemoISP/vnpay_return"; // Trang xử lý sau khi thanh toán

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long amount = Long.parseLong(request.getParameter("amount")) * 100; // VNPAY tính theo đơn vị VND x100
        String orderId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Tạo request params
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNP_TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Nap tien vao tai khoan");
        vnp_Params.put("vnp_OrderType", "topup");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", RETURN_URL);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());

        // Thời gian tạo request
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(new Date()));

        // Sắp xếp tham số theo thứ tự alphabet để tạo checksum
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = vnp_Params.get(fieldName);
            if ((value != null) && (!value.isEmpty())) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, "UTF-8"));
                query.append(fieldName).append('=').append(URLEncoder.encode(value, "UTF-8"));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Tạo checksum (mã bảo mật) cho request
        String secureHash = VNPayUtils.hmacSHA512(VNP_HASH_SECRET, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        // Chuyển hướng đến trang thanh toán của VNPAY
        String paymentUrl = VNP_URL + "?" + query.toString();
        response.sendRedirect(paymentUrl);
    }
}
