package com.ge.ev.EventAuditTrail;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.util.StringValueResolver;

/**
 * Created by 212391398 on 3/11/16.
 */
@Configuration
public class CloudEnvironmentPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer
{
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, final ConfigurablePropertyResolver propertyResolver) throws BeansException {
        propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
        propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
        propertyResolver.setValueSeparator(this.valueSeparator);

        StringValueResolver valueResolver = new StringValueResolver() {
            @Override
            public String resolveStringValue(String strVal) {

                String resolved = (ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal) : propertyResolver.resolveRequiredPlaceholders(strVal));
                String environmentVariableName = strVal.replaceAll("\\$\\{|\\}", "");
                String environmentVariableVal = System.getenv(environmentVariableName);
                resolved = resolved.trim();
                resolved = (environmentVariableVal == null) ? resolved : environmentVariableVal;
                return (resolved.equals(nullValue) ? null : resolved);
            }
        };

        doProcessProperties(beanFactory, valueResolver);
    }

}