/**
 * @author allen
 * @date 2014-8-2
 * @function 自定义分页标签
 */
package com.lb.utils;
import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class PageTag extends TagSupport {
	private String FORM_NAME;
	@Override
	public int doEndTag() throws JspException {
		ActionContext actionContext=ActionContext.getContext();
		PageBean pageBean = (PageBean)actionContext.getSession().get("page");
		if (pageBean==null){
		}
		else{
			Integer pageNo=pageBean.getPageNo();
			Integer pageCount=pageBean.getPageCount();
			Integer rowCount=pageBean.getRowCount();
			StringBuffer sb = new StringBuffer();
			 sb .append("	<SCRIPT type=\"text/javascript\">\n")
			 .append("			$(function(){	  \n")
			 .append("				 var groupNo=1;\n")
			 .append("				var pageNo=$(\"#pageNo\").val()*1;\n")
			 .append("				var pageCount=$(\"#pageCount\").val()*1;\n")
			 
			 .append("				$(\"#firstPage\").click(function(){ \n")
			 .append("					$(\"#pageNo\").val(\"1\");\n")
		 	 .append("		    	     document."+FORM_NAME+".submit();")
			 .append("				});\n")
			 
			  .append("				$(\"#lastPage\").click(function(){ \n")
			 .append("					$(\"#pageNo\").val(\""+pageCount+"\");\n")
		 	 .append("		    	     document."+FORM_NAME+".submit();")
			 .append("				});\n")
			 
			 .append("				$(\"#prePage\").click(function(){ \n")
			  .append("	   			var pageNo=$(\"#pageNo\").val()*1;\n")
			 .append("				var prePage=pageNo-1;\n")
			 .append("					if(prePage<1){\n")
			 .append("					alert(\"已经是第一页\");\n")
			 .append("						return;\n")
			 .append("					}\n")
			 .append("					$(\"#pageNo\").val(prePage);\n")
		 
		 	 .append("		    	     document."+FORM_NAME+".submit();")
			 .append("				});\n")
			 .append("			 $(\"#nextPage\").click(function(){\n")
			 .append("			 	var currentno=$(\"#spanno\").text();\n")
			  .append("	 		    var pageNo=$(\"#pageNo\").val()*1;\n")
			 .append("				var nextPage=pageNo+1;\n")
			 .append("					if(nextPage>pageCount){\n")
			 .append("					alert(\"已经是最后一页\");\n")
			 .append("						return false;\n")
			 .append("					}\n")
			 .append("					$(\"#pageNo\").val(nextPage);\n")
			 
			 .append("					$(\"#spanno\").html(currentno*1+1);\n")
			 .append("		    	      document."+FORM_NAME+".submit();")
			 .append("	   	});\n")
			 
			 .append("	   	$(\"#goPage\").click(function () {\n") 
			 .append("		            var reg = new RegExp(\"^[1-9]|[1-9][0-9]\\d*$\"); \n")
			 .append("		            var pageno=$(\"input:[name=goPage]\").val(); \n")
			 .append("					if(pageno==''){ alert(\"请输入数字!\");return;} \n")
			 .append("					if(!reg.test(pageno)){ alert(\"请输入大于0的整数!\");return;} \n")
			 .append("					if(pageno>pageCount){\n")
			 .append("					alert(\"一共只有\"+pageCount+\"页!\");\n")
			 .append("						return;\n")
			 .append("					}\n")
			 .append("					$(\"#pageNo\").val(pageno);\n")
			 
		 	 .append("		    	     document."+FORM_NAME+".submit();")
			 .append("		          });\n")
			 .append("			});	\n")		
			 .append("		</SCRIPT>\n")
		  /* .append("   <table> \n") 
		   .append(" 		<tr> \n") 
		   .append(" 			<!--name=\"page.pageNo\"  Action中通过page.pageNo获取此处的值 -->\n") 
		   .append(" 			<input type=\"hidden\" value=\""+pageNo+"\"  id=\"pageNo\" name=\"page.pageNo\"></input >	\n") 	 
		   .append(" 			<input type=\"hidden\" value=\""+pageCount+"\"  id=\"pageCount\" name=\"page.pageCount\"></input >\n") 	
		   .append(" 			<td id=\"current\"> 共有"+rowCount+"条记录,当前第"+pageBean.getPageNo()+"页</td>\n") 
		   .append("  			<td>\n") 
		   .append(" 				/共"+pageCount+"页\n") 
		   .append("    			</td>\n") 
		    .append("  			<td><input type=\"button\" id=\"prePage\" value=\"上一页\"/></td>\n") 
		   .append(" 			<td><input type=\"button\" id=\"xiaPage\" value=\"下一页\"/>	</td>\n") 
		   .append("   			<td>\n") 
		   .append(" 				,跳转至<input type=\"text\" style=\"width:50px\" id=\"goToPage\" name=\"goPage\">页\n") 
		   .append(" 				 <a id=\"go\" href=\"javascript:;\">GO</a>\n") 
		   .append(" 			</td> 	  \n") 
		   .append(" </tr>\n") 
		   .append("  </table>\n");*/
			  .append("   <table style=\"width:100%;margin-top:15px;\"> \n") 
		   .append(" 		<tr> \n") 
		   .append(" 			<!--name=\"page.pageNo\"  Action中通过page.pageNo获取此处的值 -->\n") 
		   .append(" 			<input type=\"hidden\" value=\""+pageNo+"\"  id=\"pageNo\" name=\"page.pageNo\"></input >	\n") 	 
		   .append(" 			<input type=\"hidden\" value=\""+pageCount+"\"  id=\"pageCount\" name=\"page.pageCount\"></input >\n") 	
		   .append(" 			<td style=\"text-align: right;\" id=\"current\"> 共有"+rowCount+"条记录,当前第"+pageBean.getPageNo()+"页\n") 
	
		   .append(" 				/共"+pageCount+"页\n") 
		  .append("  			<input type=\"button\" id=\"firstPage\" value=\"首页\"/>\n") 
		    .append("  			<input type=\"button\" id=\"prePage\" value=\"上一页\"/>\n") 
		   .append(" 			<input type=\"button\" id=\"nextPage\" value=\"下一页\"/>\n") 
		   .append(" 			<input type=\"button\" id=\"lastPage\" value=\"末页\"/>\n") 
		   .append("   			\n") 
		   .append(" 				,跳转至<input type=\"text\" style=\"width:50px;border:1px solid gray;\" id=\"goToPage\" name=\"goPage\">页\n") 
		   .append(" 				 <a id=\"goPage\" href=\"javascript:;\">GO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>\n") 
		   .append(" 			</td> 	  \n") 
		   .append(" </tr>\n") 
		   .append("  </table>\n");
	  
			try {
				JspWriter out = pageContext.getOut();
				out.println(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}
	public String getFORM_NAME() {
		return FORM_NAME;
	}
	public void setFORM_NAME(String fORMNAME) {
		FORM_NAME = fORMNAME;
	}
 
	
}
