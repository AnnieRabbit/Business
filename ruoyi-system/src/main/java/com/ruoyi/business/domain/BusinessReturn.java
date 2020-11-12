package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投诉退费对象 business_return
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public class BusinessReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 客户号码 */
    @Excel(name = "客户号码")
    private String customerNum;

    /** 投诉类型 */
    @Excel(name = "投诉类型")
    private String returnType;

    /** 退费金额 */
    @Excel(name = "退费金额")
    private BigDecimal returnAssets;

    /** 退费金额 */
    @Excel(name = "予以退费年月")
    private String returnDate;

    /** 订购时间 */
    @Excel(name = "订购时间")
    private Date orderTime;

    /** 产品ID */
    @Excel(name = "产品ID")
    private String productId;

    /** 客户订单实体类 */
    private BusinessUser businessUser;

    public BusinessUser getBusinessUser(){
        return businessUser;
    }

    public void setBusinessUser(BusinessUser businessUser){
        this.businessUser=businessUser;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerNum(String customerNum) 
    {
        this.customerNum = customerNum;
    }

    public String getCustomerNum() 
    {
        return customerNum;
    }
    public void setReturnType(String returnType) 
    {
        this.returnType = returnType;
    }

    public String getReturnType() 
    {
        return returnType;
    }
    public void setReturnAssets(BigDecimal returnAssets) 
    {
        this.returnAssets = returnAssets;
    }

    public BigDecimal getReturnAssets() 
    {
        return returnAssets;
    }
    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }
    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductId()
    {
        return productId;
    }
    public void setReturnDate(String returnDate)
    {
        this.returnDate = returnDate;
    }

    public String getReturnDate()
    {
        return returnDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerNum", getCustomerNum())
            .append("returnType", getReturnType())
            .append("returnAssets", getReturnAssets())
            .append("businessUser", getBusinessUser())
                .append("returnDate", getReturnDate())
            .append("orderTime", getOrderTime())
            .append("productId", getProductId())
            .toString();
    }
}
