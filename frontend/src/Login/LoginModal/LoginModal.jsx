import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import "./LoginModal.css";
import logo from "../../assets/logo/logo.png";

function LoginModal({ show, handleClose }) {
  const [formType, setFormType] = useState("login"); // login | forgot | register

  const switchToForgot = () => setFormType("forgot");
  const switchToLogin = () => setFormType("login");
  const switchToRegister = () => setFormType("register");

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
        <div className="text-center mb-4">
          <img src={logo} alt="FoodFast Logo" className="login-logo" />
        </div>

        {/* Tabs cho login / register */}
        {formType !== "forgot" && (
          <div className="login-tabs mb-4 text-center">
            <span
              className={`fw-bold me-4 pb-2 ${
                formType === "login"
                  ? "border-bottom border-3 border-primary text-primary"
                  : "text-muted"
              }`}
              role="button"
              onClick={switchToLogin}
            >
              Đăng nhập
            </span>
            <span
              className={`pb-2 ${
                formType === "register"
                  ? "border-bottom border-3 border-primary text-primary"
                  : "text-muted"
              }`}
              role="button"
              onClick={switchToRegister}
            >
              Tạo tài khoản
            </span>
          </div>
        )}

        {/* Form Đăng nhập */}
        {formType === "login" && (
          <form>
            <div className="mb-3">
              <label className="form-label">Số điện thoại</label>
              <input
                type="tel"
                className="form-control rounded-3"
                placeholder="Nhập số điện thoại"
                pattern="[0-9]{10,11}"
                required
              />
            </div>

            <div className="mb-1">
              <label className="form-label">Mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập mật khẩu"
                required
              />
            </div>

            <div className="text-end mb-3">
              <a
                href="#"
                onClick={switchToForgot}
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
          <form>
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
                onClick={switchToLogin}
                className="text-decoration-none small text-primary"
              >
                ← Quay lại đăng nhập
              </a>
            </div>
          </form>
        )}

        {/* Form Đăng ký */}
        {formType === "register" && (
          <form>
            <div className="row mb-3">
              <div className="col">
                <label className="form-label">Họ</label>
                <input
                  type="text"
                  className="form-control rounded-3"
                  placeholder="Nhập họ"
                  required
                />
              </div>
              <div className="col">
                <label className="form-label">Tên</label>
                <input
                  type="text"
                  className="form-control rounded-3"
                  placeholder="Nhập tên"
                  required
                />
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-control rounded-3"
                placeholder="Nhập email"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Số điện thoại</label>
              <input
                type="tel"
                className="form-control rounded-3"
                placeholder="Nhập số điện thoại"
                pattern="[0-9]{10,11}"
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">Mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập mật khẩu"
                required
              />
            </div>

            <div className="mb-4">
              <label className="form-label">Xác nhận mật khẩu</label>
              <input
                type="password"
                className="form-control rounded-3"
                placeholder="Nhập lại mật khẩu"
                required
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
