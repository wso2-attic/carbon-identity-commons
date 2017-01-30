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

package org.wso2.carbon.identity.common.util.config;

import org.wso2.carbon.identity.common.base.exception.IdentityException;
import org.wso2.carbon.identity.common.util.IdentityUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils related to identity.yaml.
 */
public class IdentityConfigUtils {

    private static final Object lock = new Object();
    private static volatile IdentityConfigUtils identityConfigUtils;
    private Map<String, Object> identityConfigs;

    public static final String IDENTITY_YAML_FILE = "identity.yaml";


    private IdentityConfigUtils() throws IdentityException {
        identityConfigs = (Map<String, Object>) readYamlConfigFile(Paths.get(IdentityUtils.getIdentityConfigDirPath()
                , IDENTITY_YAML_FILE));
    }

    /**
     * Instantiate a new instance and get it or get the available instance.
     *
     * @return IdentityConfigUtils instance.
     */
    public static IdentityConfigUtils getInstance() throws IdentityException {

        if (identityConfigUtils == null) {
            synchronized (lock) {
                if (identityConfigUtils == null) {
                    identityConfigUtils = new IdentityConfigUtils();
                }
            }
        }

        return identityConfigUtils;
    }

    public Object getProperty(String configKey) {
        return identityConfigs.get(configKey);
    }

    /**
     * Returns all available configurations of the identity.yaml file.
     *
     * @return Map of all identity.yaml configs.
     */
    public Map<String, Object> getIdentityYamlConfig() {
        return identityConfigs;
    }

    /**
     * Returns a config object from identity.yaml when given the config key and the config object type.
     *
     * @param configKey Configuration identifier key.
     * @param t Configuration value class type.
     * @param <T> Configuration value class type.
     * @return Configuration class type object.
     */
    public <T> T getConfiguration(String configKey, Class<T> t) {

        Object configValue = identityConfigs.get(configKey);

        Map<String, Object> configEntry = new HashMap<>();
        configEntry.put(configKey, configValue);

        Yaml yaml = new Yaml();
        String dump = yaml.dump(configEntry);

        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml.loadAs(dump, t);
    }

    /**
     * Reads a YAML file and returns the content.
     *
     * @param file Path to the YAML file.
     * @return Content of the YAML file.
     * @throws IdentityException If error occurred while reading the file.
     */
    public static Object readYamlConfigFile(Path file) throws IdentityException {

        try (InputStreamReader inputStreamReader =
                     new InputStreamReader(Files.newInputStream(file), StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml();
            return yaml.load(inputStreamReader);
        } catch (IOException e) {
            throw new IdentityException(String.format("Error in reading file %s", file.toString()), e);
        }
    }
}
