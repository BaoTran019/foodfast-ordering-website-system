import { render, screen, fireEvent } from "@testing-library/react";
import { describe, test, expect, vi } from "vitest";
import LoginModal from "./LoginModal/LoginModal";
import { AuthContext } from "../context/AuthenticationContext";

describe("LoginModal integration test", () => {
  test("calls logIn when user submits login form", async () => {
    // 1️⃣ Arrange
    const logInMock = vi.fn().mockResolvedValue();

    const mockAuth = {
      logIn: logInMock,
      forgotPassword: vi.fn(),
      verifyOTP: vi.fn(),
      resetPassword: vi.fn(),
      register: vi.fn(),
    };

    render(
      <AuthContext.Provider value={mockAuth}>
        <LoginModal show={true} handleClose={vi.fn()} />
      </AuthContext.Provider>
    );

    // 2️⃣ Act – nhập dữ liệu
    fireEvent.change(
      screen.getByPlaceholderText("Nhập số điện thoại"),
      { target: { value: "0123456789" } }
    );

    fireEvent.change(
      screen.getByPlaceholderText("Nhập mật khẩu"),
      { target: { value: "123456" } }
    );

      // Click nút submit ĐĂNG NHẬP
    const buttons = screen.getAllByRole("button", { name: /đăng nhập/i });
    const submitButton = buttons.find(
    (btn) => btn.tagName === "BUTTON"
    );

    fireEvent.click(submitButton);


    // 3️⃣ Assert – kiểm tra integration
    expect(logInMock).toHaveBeenCalledTimes(1);
    expect(logInMock).toHaveBeenCalledWith("0123456789", "123456");
  });
});
