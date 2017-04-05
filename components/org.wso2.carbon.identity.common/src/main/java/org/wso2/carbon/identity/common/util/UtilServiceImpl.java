package org.wso2.carbon.identity.common.util;

/*
  * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.security.SecureRandom;

/**
 * Identity Util Service.
 */

public class UtilServiceImpl implements UtilService {
    private static UtilServiceImpl instance = new UtilServiceImpl();

    public static UtilServiceImpl getInstance() {
        return instance;
    }

    /**
     * generates the one time password
     *
     * @param maxLength maximum length of a code
     * @return auto-generated pass code value
     */
    @Override
    public String generatePasscode(int maxLength) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < maxLength; i++) {
            sb.append(chars[rnd.nextInt(chars.length)]);
        }
        return sb.toString();
    }
}

