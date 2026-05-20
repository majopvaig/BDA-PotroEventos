/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author maria
 */
public class RefundDTO {

    private String refundID;
    private LocalDateTime refundDate;
    private Double amountRefunded;
    private String refundMethod;

    public RefundDTO() {
    }

    public RefundDTO(String refundID, LocalDateTime refundDate, Double amountRefunded, String refundMethod) {
        this.refundID = refundID;
        this.refundDate = refundDate;
        this.amountRefunded = amountRefunded;
        this.refundMethod = refundMethod;
    }

    public String getRefundID() {
        return refundID;
    }

    public void setRefundID(String refundID) {
        this.refundID = refundID;
    }

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public Double getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Double amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }
}
