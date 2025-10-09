package com.cnpm.Service.impl;

import com.cnpm.DTO.PaymentResponse;
import com.cnpm.Entity.Order;
import com.cnpm.Entity.Payment;
import com.cnpm.Repository.OrderRepo;
import com.cnpm.Service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

import com.cnpm.Config.VNPAYConfig;

import org.springframework.stereotype.Service;
import com.cnpm.Repository.PaymentRepo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepo orderRepo;
    private final PaymentRepo paymentRepo;
    private final VNPAYConfig vnpayConfig;

    public PaymentServiceImpl(OrderRepo orderRepo, PaymentRepo paymentRepo, VNPAYConfig vnpayConfig) {
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
        this.vnpayConfig = vnpayConfig;
    }

    @Override
    public PaymentResponse createPayment(int orderId, double amount, String method, HttpServletRequest request) {

        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (!optionalOrder.isPresent()) {
            return new PaymentResponse(false, "Order not found with ID: " + orderId, null, null);
        }

        Order order = optionalOrder.get();

        // kiểm tra số tiền
        if (amount != order.getTotalPrice()) {
            return new PaymentResponse(false, "Amount does not match order total", null, null);
        }

        try {
            // 1. Tạo bản ghi Payment trong DB
            Payment payment = new Payment();
            payment.setOrderId(order.getId());
            payment.setAmount(order.getTotalPrice());
            payment.setMethod(order.getPaymentMethod());
            payment.setStatus("UNPAID");
            payment.setCreatedAt(LocalDateTime.now());
            paymentRepo.save(payment);

            // 2. Build URL VNPAY sandbox
            String paymentUrl = buildVnpayUrl(request, order, payment);

            // 3. Trả response
            return new PaymentResponse(
                    true,
                    "Payment created, redirect to VNPAY",
                    payment,
                    paymentUrl);

        } catch (Exception e) {
            return new PaymentResponse(
                    false,
                    "Error creating payment: " + e.getMessage(),
                    null,
                    null);
        }
    }

    @Override
    public Payment updateStatus(int orderId, String status) {
        Payment payment = paymentRepo.findByOrderId(orderId).orElse(null);
        if (payment != null) {
            payment.setStatus(status);
            paymentRepo.save(payment);
        }
        return payment;
    }

    @Override
    public Payment getPaymentByID(int id) {
        return paymentRepo.findById(id).orElse(null);
    }

    private String buildVnpayUrl(HttpServletRequest request, Order order, Payment payment) throws Exception {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPAYConfig.getIpAddress(request);
        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
        String orderType = "other";
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long) payment.getAmount() * 100L));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + order.getId());
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        String urlReturn =VNPAYConfig.vnp_ReturnUrl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPAYConfig.vnp_Url + "?" + queryUrl;

        System.out.println("Hash data: " + hashData.toString());
        System.out.println("Secure Hash: " + vnp_SecureHash);

        return paymentUrl;
    }
}
