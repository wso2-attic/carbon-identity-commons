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

package org.wso2.carbon.identity.common.util.xml;

import org.w3c.dom.Element;
import org.wso2.carbon.identity.common.base.Constants;
import org.wso2.carbon.identity.common.base.exception.IdentityException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * XML Utils.
 */
public class XMLUtils {

    private static final int ENTITY_EXPANSION_LIMIT = 0;
    private static volatile XMLUtils instance = new XMLUtils();
    private static final Map<String, String> xmlSignatureAlgorithms;
    private static final Map<String, String> xmlDigestAlgorithms;

    static {

        Map<String, String> xmlSignatureAlgorithmMap = new LinkedHashMap<>();
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.DSA_SHA1,
                Constants.XML.SignatureAlgorithmURI.DSA_SHA1);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.ECDSA_SHA1,
                Constants.XML.SignatureAlgorithmURI.ECDSA_SHA1);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.ECDSA_SHA256,
                Constants.XML.SignatureAlgorithmURI.ECDSA_SHA256);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.ECDSA_SHA384,
                Constants.XML.SignatureAlgorithmURI.ECDSA_SHA384);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.ECDSA_SHA512,
                Constants.XML.SignatureAlgorithmURI.ECDSA_SHA512);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_MD5,
                Constants.XML.SignatureAlgorithmURI.RSA_MD5);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_RIPEMD160,
                Constants.XML.SignatureAlgorithmURI.RSA_RIPEMD160);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_SHA1,
                Constants.XML.SignatureAlgorithmURI.RSA_SHA1);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_SHA256,
                Constants.XML.SignatureAlgorithmURI.RSA_SHA256);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_SHA384,
                Constants.XML.SignatureAlgorithmURI.RSA_SHA384);
        xmlSignatureAlgorithmMap.put(
                Constants.XML.SignatureAlgorithm.RSA_SHA512,
                Constants.XML.SignatureAlgorithmURI.RSA_SHA512);
        xmlSignatureAlgorithms = Collections.unmodifiableMap(xmlSignatureAlgorithmMap);

        Map<String, String> xmlDigestAlgorithmMap = new LinkedHashMap<>();
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.MD5,
                                  Constants.XML.DigestAlgorithmURI.MD5);
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.RIPEMD160,
                                  Constants.XML.DigestAlgorithmURI.RIPEMD160);
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.SHA1,
                                  Constants.XML.DigestAlgorithmURI.SHA1);
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.SHA256,
                                  Constants.XML.DigestAlgorithmURI.SHA256);
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.SHA384,
                                  Constants.XML.DigestAlgorithmURI.SHA384);
        xmlDigestAlgorithmMap.put(Constants.XML.DigestAlgorithm.SHA512,
                                  Constants.XML.DigestAlgorithmURI.SHA512);
        xmlDigestAlgorithms = Collections.unmodifiableMap(xmlDigestAlgorithmMap);
    }

    private XMLUtils() {

    }

    public static XMLUtils getInstance() {

        return instance;
    }

    // Comment.
    public static Element getDocumentElement(String xmlString) throws IdentityException {
//
//        try {
//            DocumentBuilderFactory documentBuilderFactory = getSecuredDocumentBuilderFactory();
//            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = docBuilder.parse(new ByteArrayInputStream(xmlString.trim().getBytes(StandardCharsets
//                    .UTF_8)));
//            Element element = document.getDocumentElement();
//            return element;
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            String message = "Error in constructing element from the encoded XML string.";
//            throw IdentityException.error(message, e);
//        }

        return null;
    }

    /**
     * Create DocumentBuilderFactory with the XXE and XEE prevention measurements.
     *
     * @return DocumentBuilderFactory instance
     */
    public static DocumentBuilderFactory getSecuredDocumentBuilderFactory() {

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        dbf.setXIncludeAware(false);
//        dbf.setExpandEntityReferences(false);
//        try {
//            dbf.setFeature(Constants.SAX_FEATURE_PREFIX + Constants.EXTERNAL_GENERAL_ENTITIES_FEATURE, false);
//            dbf.setFeature(Constants.SAX_FEATURE_PREFIX + Constants.EXTERNAL_PARAMETER_ENTITIES_FEATURE, false);
//            dbf.setFeature(Constants.XERCES_FEATURE_PREFIX + Constants.LOAD_EXTERNAL_DTD_FEATURE, false);
//            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//
//        } catch (ParserConfigurationException e) {
//            logger.error("Failed to load XML Processor Feature " + Constants.EXTERNAL_GENERAL_ENTITIES_FEATURE+"
// or" +
//                    " " +
//                    Constants.EXTERNAL_PARAMETER_ENTITIES_FEATURE + " or " + Constants.LOAD_EXTERNAL_DTD_FEATURE +
//                    " or secure-processing.");
//        }
//
//        SecurityManager securityManager = new SecurityManager();
//        securityManager.setEntityExpansionLimit(ENTITY_EXPANSION_LIMIT);
//        dbf.setAttribute(Constants.XERCES_PROPERTY_PREFIX + Constants.SECURITY_MANAGER_PROPERTY, securityManager);

        return null;

    }

    /**
     * Get XML Signature Algorithm URI from friendly name.
     *
     * @param signatureAlgo signature algorithm friendly name
     * @return Signature algorithm URI
     */
    public String getXmlSignatureAlgorithmURI(String signatureAlgo) {
        return xmlSignatureAlgorithms.get(signatureAlgo);
    }

    /**
     * Get XML Digest Algorithm URI from friendly name.
     *
     * @param digestAlgo digest algorithm friendly name
     * @return Digest algorithm URI
     */
    public String getXmlDigestAlgorithmURI(String digestAlgo) {
        return xmlDigestAlgorithms.get(digestAlgo);
    }
}
