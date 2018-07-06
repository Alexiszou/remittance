package com.dfans.model;

import java.math.BigDecimal;
import java.util.Date;

public class BasComInfo {
    private Long id;

    private Long comId;

    private String comName;

    private String chiSht;

    private String engName;

    private String engSht;

    private Long natrBiz;

    private Long natrEcon;

    private Date regDtFirst;

    private Date regDtChg;

    private Date foundDt;

    private BigDecimal regCptl;

    private Long currCode;

    private String lglRepr;

    private String gm;

    private String direSecr;

    private String regAddr;

    private Long ctryCode;

    private String ctryName;

    private Long regProv;

    private Long regCity;

    private String ofsAddr;

    private Long provCode;

    private String provName;

    private Long cityCode;

    private String cityName;

    private String posc;

    private String tel;

    private String fax;

    private String email;

    private String webSite;

    private String regNumBiz;

    private String regNumTax;

    private Long newComId;

    private String isExist;

    private Date canlDt;

    private Long canlRsn;

    private String canlExpl;

    private Date entTime;

    private Date updTime;

    private Date grdTime;

    private String rsId;

    private String recId;

    private Long natrBizii;

    private String svcTel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getChiSht() {
        return chiSht;
    }

    public void setChiSht(String chiSht) {
        this.chiSht = chiSht == null ? null : chiSht.trim();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getEngSht() {
        return engSht;
    }

    public void setEngSht(String engSht) {
        this.engSht = engSht == null ? null : engSht.trim();
    }

    public Long getNatrBiz() {
        return natrBiz;
    }

    public void setNatrBiz(Long natrBiz) {
        this.natrBiz = natrBiz;
    }

    public Long getNatrEcon() {
        return natrEcon;
    }

    public void setNatrEcon(Long natrEcon) {
        this.natrEcon = natrEcon;
    }

    public Date getRegDtFirst() {
        return regDtFirst;
    }

    public void setRegDtFirst(Date regDtFirst) {
        this.regDtFirst = regDtFirst;
    }

    public Date getRegDtChg() {
        return regDtChg;
    }

    public void setRegDtChg(Date regDtChg) {
        this.regDtChg = regDtChg;
    }

    public Date getFoundDt() {
        return foundDt;
    }

    public void setFoundDt(Date foundDt) {
        this.foundDt = foundDt;
    }

    public BigDecimal getRegCptl() {
        return regCptl;
    }

    public void setRegCptl(BigDecimal regCptl) {
        this.regCptl = regCptl;
    }

    public Long getCurrCode() {
        return currCode;
    }

    public void setCurrCode(Long currCode) {
        this.currCode = currCode;
    }

    public String getLglRepr() {
        return lglRepr;
    }

    public void setLglRepr(String lglRepr) {
        this.lglRepr = lglRepr == null ? null : lglRepr.trim();
    }

    public String getGm() {
        return gm;
    }

    public void setGm(String gm) {
        this.gm = gm == null ? null : gm.trim();
    }

    public String getDireSecr() {
        return direSecr;
    }

    public void setDireSecr(String direSecr) {
        this.direSecr = direSecr == null ? null : direSecr.trim();
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr == null ? null : regAddr.trim();
    }

    public Long getCtryCode() {
        return ctryCode;
    }

    public void setCtryCode(Long ctryCode) {
        this.ctryCode = ctryCode;
    }

    public String getCtryName() {
        return ctryName;
    }

    public void setCtryName(String ctryName) {
        this.ctryName = ctryName == null ? null : ctryName.trim();
    }

    public Long getRegProv() {
        return regProv;
    }

    public void setRegProv(Long regProv) {
        this.regProv = regProv;
    }

    public Long getRegCity() {
        return regCity;
    }

    public void setRegCity(Long regCity) {
        this.regCity = regCity;
    }

    public String getOfsAddr() {
        return ofsAddr;
    }

    public void setOfsAddr(String ofsAddr) {
        this.ofsAddr = ofsAddr == null ? null : ofsAddr.trim();
    }

    public Long getProvCode() {
        return provCode;
    }

    public void setProvCode(Long provCode) {
        this.provCode = provCode;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName == null ? null : provName.trim();
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getPosc() {
        return posc;
    }

    public void setPosc(String posc) {
        this.posc = posc == null ? null : posc.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
    }

    public String getRegNumBiz() {
        return regNumBiz;
    }

    public void setRegNumBiz(String regNumBiz) {
        this.regNumBiz = regNumBiz == null ? null : regNumBiz.trim();
    }

    public String getRegNumTax() {
        return regNumTax;
    }

    public void setRegNumTax(String regNumTax) {
        this.regNumTax = regNumTax == null ? null : regNumTax.trim();
    }

    public Long getNewComId() {
        return newComId;
    }

    public void setNewComId(Long newComId) {
        this.newComId = newComId;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist == null ? null : isExist.trim();
    }

    public Date getCanlDt() {
        return canlDt;
    }

    public void setCanlDt(Date canlDt) {
        this.canlDt = canlDt;
    }

    public Long getCanlRsn() {
        return canlRsn;
    }

    public void setCanlRsn(Long canlRsn) {
        this.canlRsn = canlRsn;
    }

    public String getCanlExpl() {
        return canlExpl;
    }

    public void setCanlExpl(String canlExpl) {
        this.canlExpl = canlExpl == null ? null : canlExpl.trim();
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Date getGrdTime() {
        return grdTime;
    }

    public void setGrdTime(Date grdTime) {
        this.grdTime = grdTime;
    }

    public String getRsId() {
        return rsId;
    }

    public void setRsId(String rsId) {
        this.rsId = rsId == null ? null : rsId.trim();
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId == null ? null : recId.trim();
    }

    public Long getNatrBizii() {
        return natrBizii;
    }

    public void setNatrBizii(Long natrBizii) {
        this.natrBizii = natrBizii;
    }

    public String getSvcTel() {
        return svcTel;
    }

    public void setSvcTel(String svcTel) {
        this.svcTel = svcTel == null ? null : svcTel.trim();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}