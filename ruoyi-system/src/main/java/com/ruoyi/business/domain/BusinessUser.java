package com.ruoyi.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 用户对象 business_user
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public class BusinessUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 客户号码 */
    @Excel(name = "客户号码")
    private String customerNum;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 所属集团 */
    @Excel(name = "所属集团")
    private String membership;

    /** 产品ID */
    @Excel(name = "产品ID")
    private String productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 所属CP */
    @Excel(name = "所属CP")
    private String belongCp;

    /** 发展人账号 */
    @Excel(name = "发展人")
    private String developeNum;

    /** 发展人姓名 */
    private String developePerson;

    /** 订购时间 */
    @Excel(name = "订购时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 话单时间 */
    @Excel(name = "话单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date callTime;

    /** 产品金额 */
    @Excel(name = "产品金额")
    private BigDecimal productAssets;

    /** 返点比例 */
    @Excel(name = "返点比例")
    private String productRate;

    /** 预付 */
//    @Excel(name = "预付")
    private Double advance;

    /** 清缴 */
//    @Excel(name = "清缴")
    private Double clear;

    /** 退费*/
//    @Excel(name = "退费")
    private Double returns;

    /** 总额*/
//    @Excel(name = "总额")
    private Double total;




    /** 账单时间 */
    private String inputDate;



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
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setMembership(String membership) 
    {
        this.membership = membership;
    }

    public String getMembership() 
    {
        return membership;
    }
    public void setProductId(String productId) 
    {
        this.productId = productId;
    }

    public String getProductId() 
    {
        return productId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setBelongCp(String belongCp) 
    {
        this.belongCp = belongCp;
    }

    public String getBelongCp() 
    {
        return belongCp;
    }
    public void setDevelopeNum(String developeNum) 
    {
        this.developeNum = developeNum;
    }

    public String getDevelopeNum() 
    {
        return developeNum;
    }
    public void setDevelopePerson(String developePerson) 
    {
        this.developePerson = developePerson;
    }

    public String getDevelopePerson() 
    {
        return developePerson;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }
    public void setCallTime(Date callTime) 
    {
        this.callTime = callTime;
    }

    public Date getCallTime() 
    {
        return callTime;
    }
    public void setProductAssets(BigDecimal productAssets) 
    {
        this.productAssets = productAssets;
    }

    public BigDecimal getProductAssets() 
    {
        return productAssets;
    }
    public void setProductRate(String productRate) 
    {
        this.productRate = productRate;
    }

    public String getProductRate() 
    {
        return productRate;
    }
    public Double getAdvance() {

        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getClear() {
        return clear;
    }

    public void setClear(Double clear) {
        this.clear = clear;
    }

    public Double getReturns() {

        return returns;
    }

    public void setReturns(Double returns) {
        this.returns = returns;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerNum", getCustomerNum())
            .append("province", getProvince())
            .append("membership", getMembership())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("belongCp", getBelongCp())
            .append("developeNum", getDevelopeNum())
            .append("developePerson", getDevelopePerson())
            .append("orderTime", getOrderTime())
            .append("callTime", getCallTime())
            .append("productAssets", getProductAssets())
            .append("productRate", getProductRate())
            .append("advance", getAdvance())
            .append("clear", getClear())
            .append("returns", getReturns())
            .append("total", getTotal())
            .append("inputDate", getInputDate())
            .toString();
    }
}
