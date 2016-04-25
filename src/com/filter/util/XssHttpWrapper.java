/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filter.util;
 
import com.blogspot.radialmind.html.HTMLParser;
import com.blogspot.radialmind.xss.XSSFilter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
/**
 * XSS����
 *
 * @author storezhang
 */
public class XssHttpWrapper extends HttpServletRequestWrapper {
 
    private HttpServletRequest orgRequest;
 
    public XssHttpWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
 
    /**
     * ����getParameter���������������Ͳ���ֵ����xss���ˡ�<br/>
     * �����Ҫ���ԭʼ��ֵ����ͨ��super.getParameterValues(name)����ȡ<br/>
     * getParameterNames,getParameterValues��getParameterMapҲ������Ҫ����
     */
    @Override
    public String getParameter(String name) {
//    	System.out.println("ffffffff");
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }
 
    /**
     * ����getHeader���������������Ͳ���ֵ����xss���ˡ�<br/>
     * �����Ҫ���ԭʼ��ֵ����ͨ��super.getHeaders(name)����ȡ<br/> getHeaderNames Ҳ������Ҫ����
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }
 
    /**
     * ����������xss©���İ���ַ�ֱ���滻��ȫ���ַ�
     *
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
 
        StringReader reader = new StringReader(s);
        StringWriter writer = new StringWriter();
        try {
            HTMLParser.process(reader, writer, new XSSFilter(), true);
            return writer.toString();
        } catch (NullPointerException e) {
            return s;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }
 
    /**
     * ��ȡ��ԭʼ��request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }
 
    /**
     * ��ȡ��ԭʼ��request�ľ�̬����
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpWrapper) {
            return ((XssHttpWrapper) req).getOrgRequest();
        }
        return req;
    }
}