package com.bugjc.zuul.core.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.fastjson.JSON;
import com.bugjc.zuul.config.GlobalProperty;
import com.bugjc.zuul.core.dto.ResultGenerator;
import com.bugjc.zuul.core.enums.ResultErrorEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * 授权认证
 * @author aoki
 */
public class AuthorizationRequestFilter extends ZuulFilter {

	public final static Logger logger = LoggerFactory.getLogger(AuthorizationRequestFilter.class);

	@Resource
	private GlobalProperty globalProperty;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String contentType = request.getHeader("Content-Type");
		if (!contentType.startsWith("application/json")){
			genResult(ctx,401,"请正确设置编码类型为application/json");
			return null;
		}

		String sign = request.getHeader("Signature");
		if (StrUtil.isEmpty(sign)){
			genResult(ctx,ResultErrorEnum.ParamError.code,"签名参数不能为空");
			return null;
		}

		String body = null;
		Sign signed = SecureUtil.sign(SignAlgorithm.SHA1withRSA,null,globalProperty.upcPublicKey);;
		boolean verify = false;

		try {
			request.setCharacterEncoding("UTF-8");
			RequestContext context = getCurrentContext();
			InputStream stream = context.getResponseDataStream();
			body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			if (StrUtil.isBlank(body)){
				genResult(ctx,ResultErrorEnum.ParamError.code,"参数不能为空");
				return null;
			}

			logger.debug("请求路径为：" + request.getRequestURI());
			logger.debug("请求参数："+body);
			verify = signed.verify(body.getBytes(CharsetUtil.CHARSET_UTF_8), Base64.decode(sign));

		}catch (Exception e) {
			try {
				assert body != null;
				verify = signed.verify(body.getBytes(CharsetUtil.CHARSET_UTF_8), hexStringToByteArray(sign));
			}catch (Exception ex){
				e.printStackTrace();
			}
		}

		if (verify){
			logger.debug("验签成功！");
			return null;
		}else {
			genResult(ctx, ResultErrorEnum.ParamError.code,"签名验证失败！");
			return null;
		}
	}

	public static byte[] hexStringToByteArray(String data) {
		int k = 0;
		byte[] results = new byte[data.length() / 2];
		for (int i = 0; i + 1 < data.length(); i += 2, k++) {
			results[k] = (byte) (Character.digit(data.charAt(i), 16) << 4);
			results[k] += (byte) (Character.digit(data.charAt(i + 1), 16));
		}
		return results;
	}


	private static void genResult(RequestContext ctx,int status,String message){
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(status);
		ctx.setResponseBody(JSON.toJSONString(ResultGenerator.genUnauthorizedResult(message)));
		ctx.getResponse().setContentType("application/json;charset=UTF-8");
	}

}
