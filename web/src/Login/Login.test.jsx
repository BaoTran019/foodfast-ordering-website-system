import { render, screen } from "@testing-library/react";
import { describe, test, expect, vi } from "vitest";
import LoginModal from "./LoginModal/LoginModal";
import { AuthContext } from "../context/AuthenticationContext";

describe("LoginModal component", () => {
  test("renders phone input when modal is open", () => {
    const mockAuth = {
      logIn: vi.fn(),
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

    expect(
      screen.getByPlaceholderText("Nhập số điện thoại")
    ).toBeInTheDocument();
  });
});
