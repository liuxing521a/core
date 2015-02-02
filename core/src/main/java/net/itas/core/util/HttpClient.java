package net.itas.core.util;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import net.itas.util.Utils.Objects;
 
/**
 * http工具
 * @author liuzhen
 * @createTime 2014年11月10日下午4:39:13
 */
public class HttpClient {
	
	/**
	 * http request read time out time
	 */
	static private final int TIMEOUT = 15*1000;

	/**
	 * http connection time out time
	 */
	static private final int CONNECTION_TIMEOUT = 15*1000;
	
	static private final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	/**
	 * http request header
	 */
	private Map<String, String> headers;
	
	/**
	 * http request params
	 */
	private Map<String, String> params;
	
	/**
	 * http request boday
	 */
	private String body;
	
	/**
	 * http request charEconding
	 */
	private Charset charSet;
	
	/**
	 * http connection
	 */
	private HttpURLConnection conn;
	
	public HttpClient() {
		charSet = DEFAULT_CHARSET;
		headers = new HashMap<String, String>(4);
		params = new HashMap<String, String>(4);
	}
	
	/***
	 * look http request char enconding
	 * @return Charset
	 */
    public Charset getCharSet() {
		return charSet;
	}

    /**
     * given http request char encoding
     * @param charSet char encoding
     */
	public void setCharSet(Charset charSet) {
		this.charSet = charSet;
	}

	/**
	 * http request body
	 * @return body for String
	 */
	public String getBody() {
		return body;
	}

	/**
	 * given value http request body
	 * @param body http request body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * http request headers
	 * @return http header info
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * given value to http headers
	 * @param headers http headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * add http request header
	 * @param key http header title
	 * @param value http header value
	 */
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
	
	/**
	 * http request params
	 * @return http url params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * given value to http params
	 * @param params http params
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	/**
	 * add http request param
	 * @param key http param key
	 * @param value http param value
	 */
	public void addParam(String key, String value) {
		params.put(key, value);
	}

	/**
     * Send a get request
     * 
     * @param url
     * @return response
     * @throws IOException
     */
    static public String get(String url) throws IOException {
        return get(url, null);
    }
 
    /**
     * Send a get request
     * 
     * @param url
     *            Url as string
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String get(String url, Map<String, String> headers) throws IOException {
        return fetch("GET", url, null, headers);
    }
 
    /**
     * Send a post request
     * 
     * @param url
     *            Url as string
     * @param body
     *            Request body as string
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String post(String url, String body, Map<String, String> headers) throws IOException {
        return fetch("POST", url, body, headers);
    }
 
    /**
     * Send a post request
     * 
     * @param url
     *            Url as string
     * @param body
     *            Request body as string
     * @return response Response as string
     * @throws IOException
     */
    static public String post(String url, String body) throws IOException {
        return post(url, body, null);
    }
 
    /**
     * Post a form with parameters
     * 
     * @param url
     *            Url as string
     * @param params
     *            map with parameters/values
     * @return response Response as string
     * @throws IOException
     */
    static public String postForm(String url, Map<String, String> params) throws IOException {
        return postForm(url, params, null);
    }
 
    /**
     * Post a form with parameters
     * 
     * @param url
     *            Url as string
     * @param params
     *            Map with parameters/values
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String postForm(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        // set content type
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put("Content-Type", "application/x-www-form-urlencoded");
 
        // parse parameters
        String body = "";
        if (params != null) {
            boolean first = true;
            for (String param : params.keySet()) {
                if (first) {
                    first = false;
                } else {
                    body += "&";
                }
                String value = params.get(param);
                body += URLEncoder.encode(param, "UTF-8") + "=";
                body += URLEncoder.encode(value, "UTF-8");
            }
        }
 
        return post(url, body, headers);
    }
 
    /**
     * Send a put request
     * 
     * @param url
     *            Url as string
     * @param body
     *            Request body as string
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String put(String url, String body, Map<String, String> headers) throws IOException {
        return fetch("PUT", url, body, headers);
    }
 
    /**
     * Send a put request
     * 
     * @param url
     *            Url as string
     * @return response Response as string
     * @throws IOException
     */
    static public String put(String url, String body) throws IOException {
        return put(url, body, null);
    }
 
    /**
     * Send a delete request
     * 
     * @param url
     *            Url as string
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String delete(String url, Map<String, String> headers) throws IOException {
        return fetch("DELETE", url, null, headers);
    }
 
    /**
     * Send a delete request
     * 
     * @param url
     *            Url as string
     * @return response Response as string
     * @throws IOException
     */
    static public String delete(String url) throws IOException {
        return delete(url, null);
    }
 
    /**
     * Append query parameters to given url
     * 
     * @param url
     *            Url as string
     * @param params
     *            Map with query parameters
     * @return url Url with query parameters appended
     * @throws IOException
     */
    static public String appendQueryParams(String url, Map<String, String> params) throws IOException {
        String fullUrl = new String(url);
 
        if (params != null) {
            boolean first = (fullUrl.indexOf('?') == -1);
            for (String param : params.keySet()) {
                if (first) {
                    fullUrl += '?';
                    first = false;
                } else {
                    fullUrl += '&';
                }
                String value = params.get(param);
                fullUrl += URLEncoder.encode(param, "UTF-8") + '=';
                fullUrl += URLEncoder.encode(value, "UTF-8");
            }
        }
 
        return fullUrl;
    }
 
    /**
     * Retrieve the query parameters from given url
     * 
     * @param url
     *            Url containing query parameters
     * @return params Map with query parameters
     * @throws IOException
     */
    static public Map<String, String> getQueryParams(String url) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
 
        int start = url.indexOf('?');
        while (start != -1) {
            // read parameter name
            int equals = url.indexOf('=', start);
            String param = "";
            if (equals != -1) {
                param = url.substring(start + 1, equals);
            } else {
                param = url.substring(start + 1);
            }
 
            // read parameter value
            String value = "";
            if (equals != -1) {
                start = url.indexOf('&', equals);
                if (start != -1) {
                    value = url.substring(equals + 1, start);
                } else {
                    value = url.substring(equals + 1);
                }
            }
 
            params.put(URLDecoder.decode(param, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
        }
 
        return params;
    }
 
    /**
     * Returns the url without query parameters
     * 
     * @param url
     *            Url containing query parameters
     * @return url Url without query parameters
     * @throws IOException
     */
    static public String removeQueryParams(String url) throws IOException {
        int q = url.indexOf('?');
        if (q != -1) {
            return url.substring(0, q);
        } else {
            return url;
        }
    }
 
    
    
    /**
     * Send a request
     * 
     * @param method
     *            HTTP method, for example "GET" or "POST"
     * @param url
     *            Url as string
     * @param body
     *            Request body as string
     * @param headers
     *            Optional map with headers
     * @return response Response as string
     * @throws IOException
     */
    static public String fetch(String method, String url, String body, Map<String, String> headers) throws IOException {
        // connection
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
 
        // method
        if (Objects.nonEmpty(method)) {
            conn.setRequestMethod(method);
        }
 
        // headers
        if (Objects.nonEmpty(headers)) {
            for (Entry<String, String> head : headers.entrySet()) {
                conn.addRequestProperty(head.getKey(), head.getValue());
            }
        }
 
        // body
        if (Objects.nonEmpty(body)) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("utf-8"));
            os.flush();
            os.close();
        }
        boolean isGzip = false;
        String charset = null;
        // response
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
 
            // for (int i = 0;; i++) {
            // String headerName = conn.getHeaderFieldKey(i);
            String contentEncoding = conn.getHeaderField("Content-Encoding");
            if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                isGzip = true;
            }
            String contentType = conn.getHeaderField("Content-Type");
            charset = getCharsetFromContentType(contentType);
        }
        InputStream is = conn.getInputStream();
        String response = streamToString(is, charset, isGzip);
        is.close();
 
        // handle redirects
        if (conn.getResponseCode() == 301) {
            String location = conn.getHeaderField("Location");
            return fetch(method, location, body, headers);
        }
 
        return response;
    }
 
    //
    /**
     * 字符集编码
     */
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
 
    /**
     * 检测网页编码
     * 
     * @param contentType
     * @return
     */
    static String getCharsetFromContentType(String contentType) {
        if (Objects.nonNull(contentType)) {
        	return null;
        }
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim();
            charset = charset.replace("charset=", "");
            if (charset.length() == 0)
                return null;
            try {
                if ("gb2312".equalsIgnoreCase(charset) || "gbk".equalsIgnoreCase(charset)) {
                    charset = "gb18030";
                }
                if (Charset.isSupported(charset))
                    return charset;
                charset = charset.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(charset))
                    return charset;
            } catch (IllegalCharsetNameException e) {
                // if our advanced charset matching fails.... we just take the default
                return null;
            }
        }
        return null;
    }
 
    /**
     * Read an input stream into a string
     * 
     * @param in
     * @return
     * @throws IOException
     */
    static public String streamToString(InputStream is, String charset, boolean isGzip) throws IOException {
        if (charset == null || "".equals(charset.trim())) {
            charset = "utf-8";
        }
        BufferedReader reader = null;
        if (isGzip) {
            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(is), charset));// 设置编码,否则中文乱码
        } else {
            reader = new BufferedReader(new InputStreamReader(is, charset));// 设置编码,否则中文乱码
        }
        String line = "";
        StringBuffer result = new StringBuffer();
        while (null != (line = reader.readLine())) {
            if (!"".equals(line)) {
                result.append(line).append("\r\n");
            }
        }
 
        return result.toString();
    }
    
    
    /**
     * Send a request
     * 
     * @param method  HTTP method, for example "GET" or "POST"
     * @param url Url as string
     * @return response Response as string
     * @throws IOException
     */
    String fetch(String url, String method) throws IOException {
    	String reqUrl = urlParams(url, params);

    	// connection
    	URL u = new URL(reqUrl);
    	conn = (HttpURLConnection) u.openConnection();
    	conn.setReadTimeout(TIMEOUT);
    	conn.setConnectTimeout(CONNECTION_TIMEOUT);
    	conn.setDoInput(true);
    	conn.setDoOutput(true);
    	
    	// method
		conn.setRequestMethod(method);
    	
    	// headers
		byte[] bodyBs = getHttpBody();
		setHeaders(bodyBs);
    	
    	// body
		if (Objects.nonNull(bodyBs)) {
			try (OutputStream os = conn.getOutputStream()) {
				os.write(bodyBs);
				os.flush();
			}
		}
		
    	boolean isGzip = false;
    	String charset = null;
    	// response
    	if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
    		
    		// for (int i = 0;; i++) {
    		// String headerName = conn.getHeaderFieldKey(i);
    		String contentEncoding = conn.getHeaderField("Content-Encoding");
    		if (Objects.nonNull(contentEncoding) && contentEncoding.equalsIgnoreCase("gzip")) {
    			isGzip = true;
    		}
    		
    		String contentType = conn.getHeaderField("Content-Type");
    		charset = getCharsetFromContentType(contentType);
    	}
    	
    	InputStream is = conn.getInputStream();
    	String response = streamToString(is, charset, isGzip);
    	is.close();
    	
    	// handle redirects
    	if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM) {
    		String location = conn.getHeaderField("Location");
    		return fetch(location, method);
    	}
    	
    	return response;
    }
    
    private String urlParams(String url, Map<String, String> params) throws UnsupportedEncodingException {
    	if (Objects.isEmpty(params)) {
    		return url;
    	}

    	StringBuffer paramBuf = new StringBuffer(url);
    	boolean first = (url.indexOf('?') == -1);
    	
    	for (Entry<String, String> param : params.entrySet()) {
    		if (first) {
    			paramBuf.append('?');
    			first = false;
    		} else {
    			paramBuf.append('&');
    		}
    		
    		paramBuf.append(URLEncoder.encode(param.getKey(), "UTF-8"));
    		paramBuf.append('=');
    		paramBuf.append(URLEncoder.encode(param.getValue(), "UTF-8"));
    	}
    	
    	return paramBuf.toString();
    }
 
    private void setHeaders(byte[] bodyBs) {
    	for (Entry<String, String> head : headers.entrySet()) {
    		conn.addRequestProperty(head.getKey(), head.getValue());
    	}
    	
    	if (Objects.nonNull(bodyBs)) {
    		conn.addRequestProperty("Content-Length", String.valueOf(bodyBs.length));
    	}
    }
    
    private byte[] getHttpBody(){
    	if (Objects.isNull(body)) {
    		return null;
    	}
    	
    	return body.getBytes(charSet);
    }
}
