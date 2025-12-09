import React, { useState, useContext } from "react";
import { Modal, Button } from "react-bootstrap";
import "./LoginModal.css";
import 'bootstrap-icons/font/bootstrap-icons.css';
import logo from "../../assets/logo/logo.png";
import { AuthContext } from "../../context/AuthenticationContext"
import { toast } from "react-toastify";

function LoginModal({ show, handleClose }) {

  const { logIn, forgotPassword, verifyOTP, resetPassword, register } = useContext(AuthContext)
  const [login_data, setLogin] = useState({
    phone: '',
    password: ''
  })

  const [register_info, setRegister] = useState({
    userName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    address: ''
  })

  const [resetPsw, setResetPsw] = useState({
    phone: '',
    otp: ''
  })

  const [newPsw, setNewPsw] = useState({
    new: '',
    confirm: ''
  })

  const [showPassword, setShowPassword] = useState(false);

  const [formType, setFormType] = useState("login"); // login | forgot | register

  const switchToForm = (type) => {
    setFormType(type)
  }

  const handleLogIn = async (e) => {
    e.preventDefault()
    try {
      await logIn(login_data.phone, login_data.password)
      toast.success('Đăng nhập thành công')
    } catch (err) {
      toast.warning('Đăng nhập thất bại')
    }
    handleClose();
  }

  const handleRegister = async (e) => {
    e.preventDefault()
    console.log(register_info)
    try {
      await register(register_info)
      toast.success('Đăng kí thành công')
      setFormType('login')
    } catch (err) {
      toast.warning('Đăng kí thất bại')
    }
  }

  const handleForgotPassword = async (e) => {
    e.preventDefault()
    try {
      await forgotPassword(resetPsw.phone)
      setFormType('otp')
    } catch (err) {
      toast.warning(err.toString())
    }
  }

  const handleVerifyOTP = async (e) => {
    e.preventDefault()
    try {
      await verifyOTP(resetPsw.phone, resetPsw.otp)
      setFormType('reset')
    } catch (err) {
      toast.warning('Mã OTP không chính xác')
    }
  }

  const handleResetPassWord = async (e) => {
    e.preventDefault()
    try {
      await resetPassword(resetPsw.phone, newPsw.confirm)
      toast.success('Đổi mật khẩu thành công')
      setFormType('login')
    }
    catch (err) {
      toast.warning('Không thể đổi mật khẩu')
    }

  }

  const handleCloseModal = () => {
    setFormType("login"); // reset về login khi tắt
    handleClose();
  };

  return (
    <Modal
      show={show}
      onHide={handleCloseModal}
      centered
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header closeButton className="border-0" />

      <Modal.Body>
        {/* Logo */}
        <div className="text-center mb-1">
          <img src={logo} alt="FoodFast Logo" className="login-logo" />
        </div>

        {/* Tabs cho login / register */}
        {formType !== "forgot" && (
          <div className="login-tabs mb-3 text-center">
            <span
              className={`fw-bold me-4 pb-2 ${formType === "login"
                ? "border-bottom border-3 border-primary text-primary"
                : "text-muted"
                }`}
              role="button"
              onClick={() => switchToForm("login")}
            >
              Đăng nhập
            </span>
            <span
              className={`pb-2 ${formType === "register"
                ? "border-bottom border-3 border-primary text-primary"
                : "text-muted"
                }`}
              role="button"
              onClick={() => switchToForm("register")}
            >
              Tạo tài khoản
            </span>
          </div>
        )}

        {/* Form Đăng nhập */}
        {formType === "login" && (
          <form onSubmit={(e) => handleLogIn(e)}>
            <div className="mb-3">
              <label className="form-label">Số điện thoại</label>
              <input
                type="tel"
                className="form-control rounded-3"
                placeholder="Nhập số điện thoại"
                pattern="[0-9]{10,11}"
                required
                value={login_data.phone}
                onChange={e => setLogin({ ...login_data, phone: e.target.value })}
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Mật khẩu</label>
              <input
                type={showPassword ? "text" : "password"}
                autoComplete="current-password"
                inputMode="text"
                spellCheck="false"
                className="form-control rounded-3"
                placeholder="Nhập mật khẩu"
                required
                value={login_data.password}
                onChange={e => setLogin({ ...login_data, password: e.target.value })}
              />
              <i className={`bi ${showPassword ? "bi-eye-slash" : "bi-eye"}`}
                onClick={() => setShowPassword(!showPassword)}
                style={{
                  position: "absolute",
                  top: "75%",
                  right: "8%",
                  transform: "translateY(-50%)",
                  cursor: "pointer",
                  fontSize: "1.2rem",
                  color: "#666"
                }} />
            </div>

            <div className="text-end mb-3">
              <a
                href="#"
                onClick={() => switchToForm("forgot")}
                className="text-decoration-none small text-primary"
              >
                Quên mật khẩu?
              </a>
            </div>

            <Button
              variant="warning"
              type="submit"
              className="w-100 fw-bold text-white"
              style={{ backgroundColor: "#ff6600", border: "none" }}
            >
              ĐĂNG NHẬP
            </Button>
          </form>
        )}

        {/* Form Quên mật khẩu */}
        {formType === "forgot" && (
          <form onSubmit={(e) => handleForgotPassword(e)}>
            <h5 className="text-center mb-3">Quên mật khẩu</h5>
            <p className="text-muted small text-center mb-3">
              Nhập số điện thoại để nhận mã khôi phục
            </p>

            <div className="mb-3">
              <label className="form-label">Số điện thoại</label>
              <input
                type="tel"
                className="form-control rounded-3"
                placeholder="Nhập số điện thoại"
                pattern="[0-9]{10,11}"
                required
                value={resetPsw.phone}
                onChange={e => setResetPsw({ ...resetPsw, phone: e.target.value })}
              />
            </div>

            <Button
              variant="warning"
              type="submit"
              className="w-100 fw-bold text-white mb-3"
              style={{ backgroundColor: "#ff6600", border: "none" }}
            >
              GỬI MÃ KHÔI PHỤC
            </Button>

            <div className="text-center">
              <a
                href="#"
                onClick={() => switchToForm("login")}
                className="text-decoration-none small text-primary"
              >
                ← Quay lại đăng nhập
              </a>
            </div>
          </form>
        )}

        {/* Form nhập mã OTP */}
        {formType === "otp" && (
          <form onSubmit={(e) => handleVerifyOTP(e)}>
            <h5 className="text-center mb-3">Xác minh OTP</h5>
            <p className="text-muted small text-center mb-3">
              Nhập mã OTP đã gửi đến số điện thoại {resetPsw.phone}
            </p>

            <div className="mb-3">
              <label className="form-label">Mã OTP</label>
              <input
                type="text"
                className="form-control rounded-3"
                placeholder="Nhập mã OTP"
                value={resetPsw.otp}
                onChange={e => setResetPsw({ ...resetPsw, otp: e.target.value })}
                required
              />
            </div>

            <Button type="submit" className="w-100 fw-bold text-white" style={{ background: "#ff6600", border: "none" }}>
              XÁC MINH
            </Button>
          </form>
        )}

        {/* Form đặt lại mật khẩu */}
        {formType === "reset" && (
          <form onSubmit={(e) => handleResetPassWord(e)}>
            <h5 className="text-center mb-3">Đặt lại mật khẩu</h5>

            <div className="mb-3">
              <label className="form-label">Mật khẩu mới</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập mật khẩu mới"
                value={newPsw.new}
                onChange={e => setNewPsw({ ...newPsw, new: e.target.value })}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Xác nhận mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập lại mật khẩu"
                value={newPsw.confirm}
                onChange={e => setNewPsw({ ...newPsw, confirm: e.target.value })}
                required
              />
            </div>

            <Button type="submit" className="w-100 fw-bold text-white" style={{ background: "#ff6600", border: "none" }}>
              ĐẶT LẠI MẬT KHẨU
            </Button>
          </form>
        )}

        {/* Form Đăng ký */}
        {formType === "register" && (
          <form onSubmit={(e) => handleRegister(e)}>
            <div className="mb-1">
              <label className="form-label">Họ tên</label>
              <input
                type="text"
                className="form-control rounded-3"
                placeholder="Nhập họ tên"
                required
                value={register_info.userName}
                onChange={e => setRegister({ ...register_info, userName: e.target.value })}
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-control rounded-3"
                placeholder="Nhập email"
                required
                value={register_info.email}
                onChange={e => setRegister({ ...register_info, email: e.target.value })}
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Số điện thoại</label>
              <input
                type="tel"
                className="form-control rounded-3"
                placeholder="Nhập số điện thoại"
                pattern="[0-9]{10,11}"
                required
                value={register_info.phone}
                onChange={e => setRegister({ ...register_info, phone: e.target.value })}
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Địa chỉ</label>
              <input
                type="text"
                className="form-control rounded-3"
                placeholder="Nhập địa chỉ"
                required
                value={register_info.address}
                onChange={e => setRegister({ ...register_info, address: e.target.value })}
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập mật khẩu"
                required
                value={register_info.password}
                onChange={e => setRegister({ ...register_info, password: e.target.value })}
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Xác nhận mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập lại mật khẩu"
                required
                value={register_info.confirmPassword}
                onChange={e => setRegister({ ...register_info, confirmPassword: e.target.value })}
              />
            </div>

            <Button
              variant="warning"
              type="submit"
              className="w-100 fw-bold text-white"
              style={{ backgroundColor: "#ff6600", border: "none" }}
            >
              TẠO TÀI KHOẢN
            </Button>
          </form>
        )}
      </Modal.Body>
    </Modal>
  );
}

export default LoginModal;
