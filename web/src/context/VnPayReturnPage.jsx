import { useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const VnPayReturnPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const txnRef = searchParams.get("vnp_TxnRef");       // orderId
    const secureHash = searchParams.get("vnp_SecureHash");
    const responseCode = searchParams.get("vnp_ResponseCode");

    // 1️⃣ Gọi backend để verify giao dịch
    fetch(`http://localhost:8080/api/payments/verify?vnp_TxnRef=${txnRef}&vnp_SecureHash=${secureHash}&vnp_ResponseCode=${responseCode}`)
      .then(res => res.json())
      .then(result => {
        if (result.success) {
          toast.success("Thanh toán thành công!");
          // Xoá giỏ hàng hoặc cập nhật state
          navigate("/menu"); // Chuyển hướng về trang menu
        } else {
          toast.error("Thanh toán thất bại!");
          navigate("/menu");
        }
      })
      .catch(err => {
        console.error(err);
        toast.error("Lỗi xác minh thanh toán!");
        navigate("/menu");
      });
  }, []);

  return <div>Đang xử lý thanh toán...</div>;
};

export default VnPayReturnPage;
