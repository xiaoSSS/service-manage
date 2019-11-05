package com.baizhitong.portal.manage.vo;


/**
 * url过滤规则
 * @author wangsj
 * @Date 2016年2月23日
 *
 */
public class UrlRule {

	/**
	 * 访问路径
	 */
	String url;
	
	/**
	 * 过滤规则
	 */
	Rule rule;

	/**
	 * 是否是开放api
	 */
	Boolean flagOpenApi;


	public String getUrl() {
		return url;
	}

	public Rule getRule() {
		return rule;
	}

	public Boolean getFlagOpenApi(){
		return flagOpenApi;
	}

	public UrlRule(){
	
	}
	
	public UrlRule(String url,Rule rule){
		this.url=url;
		this.rule=rule;
		this.flagOpenApi=false;
	}

	public UrlRule(String url,Rule rule,Boolean flagOpenApi){
		this.url=url;
		this.rule=rule;
		this.flagOpenApi=flagOpenApi;
	}

	/**
	 * 规则类型，枚举类型 
	 * 以后遇到特殊情况可以扩展
	 * @author wangsj
	 * @Date 2016年2月23日
	 *
	 */
	public enum Rule{
		/**以关键字开始*/
		startwith,
		/**包括关键字*/
		contains,
		/**正则匹配*/
		regex,
	};
}
