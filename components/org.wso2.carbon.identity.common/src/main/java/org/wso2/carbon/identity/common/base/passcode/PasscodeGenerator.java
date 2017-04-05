package org.wso2.carbon.identity.common.base.passcode;

/**
 * Passcode generator contract
 */
public interface PasscodeGenerator {

    /**
     *
     * @param maxLength specify the length of generated passcode
     * @return generated passcode
     */
    String generatePasscode(int maxLength);
}
