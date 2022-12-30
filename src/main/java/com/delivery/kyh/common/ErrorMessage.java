package com.delivery.kyh.common;

public enum ErrorMessage {

    CAN_NOT_CANCEL_IN_DELIVERY("배달중에는 취소할 수 없습니다."),

    CAN_NOT_CANCEL_DELIVERY_COMPLETE("배달 완료후에는 취소할 수 없습니다."),

    CAN_NOT_CHANGE_ADDRESS_IN_DELIVERY("배달중에는 주소를 변경할 수 없습니다."),

    CAN_NOT_CHANGE_ADDRESS_DELIVERY_COMPLETE("배달완료후에는 주소를 변경할 수 없습니다."),

    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
