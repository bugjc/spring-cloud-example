package com.bugjc.zuul.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProperty {

    /*签名私钥公钥*/
    @Value("${upc.public-key}")
    public String upcPublicKey;
    @Value("${upc.private-key}")
    public String upcPrivateKey;

}
