package com.wql.boot.wqlboot.common.properties;

import com.xxl.conf.core.annotation.XxlConf;
import org.springframework.stereotype.Component;

/**
 * xxl-conf-admin中的配置参数，依赖zookeeper
 * 
 * @author wangqiulin
 * @date 2018年5月12日
 */
@Component
public class SystemParamProperty {

	@XxlConf("default.key02")
	public String paramKey02;

	

}
