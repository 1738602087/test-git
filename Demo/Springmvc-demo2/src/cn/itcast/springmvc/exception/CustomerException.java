package cn.itcast.springmvc.exception;

public class CustomerException extends Exception{
    private String msg;

    public CustomerException() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CustomerException(String msg) {
        this.msg = msg;
    }
}
