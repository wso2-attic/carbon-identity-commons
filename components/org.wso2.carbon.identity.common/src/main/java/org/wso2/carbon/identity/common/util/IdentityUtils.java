/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.common.base.Constants;
import org.wso2.carbon.identity.common.base.exception.IdentityRuntimeException;
import org.wso2.carbon.identity.common.internal.IdentityCommonDataHolder;
import org.wso2.carbon.identity.common.internal.cache.CacheConfig;
import org.wso2.carbon.identity.common.internal.cache.CacheConfigKey;
import org.wso2.carbon.identity.common.internal.config.ConfigParser;
import org.wso2.carbon.identity.common.internal.cookie.CookieConfig;
import org.wso2.carbon.identity.common.internal.cookie.CookieConfigKey;
import org.wso2.carbon.identity.common.internal.handler.HandlerConfig;
import org.wso2.carbon.identity.common.internal.handler.HandlerConfigKey;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Identity utils.
 */
public class IdentityUtils {

    public static final ThreadLocal<Map<String, Object>> MAP_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap();
        }
    };
    private static Logger logger = LoggerFactory.getLogger(IdentityUtils.class);
    private static volatile IdentityUtils instance = null;

    private IdentityUtils() {
        ConfigParser.getInstance();
    }

    public static IdentityUtils getInstance() {
        if (instance == null) {
            synchronized (IdentityUtils.class) {
                if (instance == null) {
                    instance = new IdentityUtils();
                }
            }
        }
        return instance;
    }

    public static String generateHmacSHA1(String secretKey, String baseString) throws SignatureException {
//        try {
//            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), Constants.HMAC_SHA1);
//            Mac mac = Mac.getInstance(Constants.HMAC_SHA1);
//            mac.init(key);
//            byte[] rawHmac = mac.doFinal(baseString.getBytes());
//            return Base64.encode(rawHmac);
//        } catch (Exception e) {
//            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
//        }
        return null;
    }

    /**
     * Generates a random number using two UUIDs and HMAC-SHA1.
     * @return : random
     * @throws IdentityRuntimeException : IdentityRuntimeException
     */
    public static String generateRandomNumber() throws IdentityRuntimeException {
        try {
            String secretKey = UUID.randomUUID().toString();
            String baseString = UUID.randomUUID().toString();

            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] rawHmac = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
            String random;
            random = new String(Base64.getEncoder().encode(rawHmac), StandardCharsets.UTF_8);
            return random;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IdentityRuntimeException("Error occurred while generating random number.", e);
        }
    }

    public static int generateRandomInteger() throws IdentityRuntimeException {

        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            int number = prng.nextInt();
            while (number < 0) {
                number = prng.nextInt();
            }
            return number;
        } catch (NoSuchAlgorithmException e) {
            throw new IdentityRuntimeException("Error occurred while generating random integer.", e);
        }

    }

    public static String getIdentityConfigDirPath() {
        return String.valueOf(Paths.get(getCarbonHomeDirectory(), "conf", "identity"));
    }

    public static String getCarbonHomeDirectory() {
        return String.valueOf(Paths.get(System.getProperty(Constants.CARBON_HOME)));
    }

    public static String getServicePath() {
        // may not be needed in C5 because each service can be hosted on a unique service context
        return null;
    }

    /**
     * Get the server synchronization tolerance value in seconds.
     *
     * @return clock skew in seconds
     */
    public static int getClockSkewInSeconds() {

        return 0;
    }

    /**
     * Validates an URI.
     *
     * @param uriString URI String
     * @return <code>true</code> if valid URI, <code>false</code> otherwise
     */
    public static boolean validateURI(String uriString) {

        if (uriString != null) {
            try {
                new URL(uriString);
            } catch (MalformedURLException e) {
                logger.debug("Error while parsing the URL: " + uriString + ".", e);
                return false;
            }
        } else {
            String errorMsg = "Invalid URL: \'NULL\'";
            logger.debug(errorMsg);
            return false;
        }
        return true;
    }

    /**
     * @param array : Array of input
     * @return : ExclusiveOR
     */
    public static boolean exclusiveOR(boolean[] array) {
        boolean foundTrue = false;
        for (boolean temp : array) {
            if (temp) {
                if (foundTrue) {
                    return false;
                } else {
                    foundTrue = true;
                }
            }
        }
        return foundTrue;
    }

    public static String calculateHmacSha1(String key, String value) throws SignatureException {
        String result = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            result = new String(Base64.getEncoder().encode(rawHmac), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to create the HMAC Signature", e);
            }
            throw new SignatureException("Failed to calculate HMAC : " + e.getMessage());
        }

        return result;
    }

    // May not be needed going forward
    public static int getTenantId(String tenantDomain) throws IdentityRuntimeException {
        return 0;
    }

    // May not be needed going forward
    public static String getTenantDomain(int tenantId) throws IdentityRuntimeException {
        return null;
    }

    public Map<HandlerConfigKey, HandlerConfig> getHandlerConfig() {
        return IdentityCommonDataHolder.getInstance().getHandlerConfig();
    }

    public Map<CacheConfigKey, CacheConfig> getCacheConfig() {
        return IdentityCommonDataHolder.getInstance().getCacheConfig();
    }

    public Map<CookieConfigKey, CookieConfig> getCookieConfig() {
        return IdentityCommonDataHolder.getInstance().getCookieConfig();
    }


    /**
     * generates random string
     *
     * @return a random string
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }


    // User store case sensitivity check method must come from RealmService

    // Session cleanup period, session cleanup timeout, operation cleanup period and operation cleanup timeout must
    // be handled by authentication component

    // extracting/appending userstore domain from/to a username/groupname, reading primary userstore domain, etc. must
    // come from RealmService
}
