package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 返点比例表对象 business_rate
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public class BusinessRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 集团名称 */
    @Excel(name = "集团名称")
    private String membership;

    /** 集团比例 */
    @Excel(name = "集团比例")
    private String memberRate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMembership(String membership) 
    {
        this.membership = membership;
    }

    public String getMembership() 
    {
        return membership;
    }
    public void setMemberRate(String memberRate) 
    {
        this.memberRate = memberRate;
    }

    public String getMemberRate() 
    {
        return memberRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("membership", getMembership())
            .append("memberRate", getMemberRate())
            .toString();
    }
}
