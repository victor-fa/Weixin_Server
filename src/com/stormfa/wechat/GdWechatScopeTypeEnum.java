package com.stormfa.wechat;

/**
 * ΢�Ż�ȡ�û���Ϣ����
 * @author administrator
 *
 */
public enum GdWechatScopeTypeEnum {

    SNSAPI_BASE("snsapi_base", "������Ϣ"),
    SNSAPI_USERINFO("snsapi_userinfo", "��ϸ��Ϣ");
    private String typeCode;

    private String typeDesc;

    private GdWechatScopeTypeEnum(String typeCode, String typeDesc) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
