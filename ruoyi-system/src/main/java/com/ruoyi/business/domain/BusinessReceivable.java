package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 应收账单对象 business_receivable
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public class BusinessReceivable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 客户号码 */
    @Excel(name = "客户号码")
    private String customerNum;

    /** 收账时间 */
    @Excel(name = "收账时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;


    @Excel(name = "订购时间")
    private  Date orderTime;
    /** 应收金额 */
//    @Excel(name = "应收金额")
//    private BigDecimal receivableAssets;

    /** 产品id */
    @Excel(name = "产品id")
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
    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
    }
//    public void setReceivableAssets(BigDecimal receivableAssets)
//    {
//        this.receivableAssets = receivableAssets;
//    }
//
//    public BigDecimal getReceivableAssets()
//    {
//        return receivableAssets;
//    }
    public void setProductId(String productId) 
    {
        this.productId = productId;
    }

    public String getProductId() 
    {
        return productId;
    }
    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerNum", getCustomerNum())
            .append("billDate", getBillDate())
//            .append("receivableAssets", getReceivableAssets())
            .append("productId", getProductId())
            .append("businessUser", getBusinessUser())
            .append("orderTime", getOrderTime())
            .toString();
    }
}
