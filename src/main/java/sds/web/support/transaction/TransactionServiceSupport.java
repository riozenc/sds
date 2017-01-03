/**
 * 	Title:sds.web.support.transaction
 *		Datetime:2016年12月16日 上午11:25:47
 *		Author:czy
 */
package sds.web.support.transaction;

import org.springframework.context.annotation.Configuration;

import com.riozenc.quicktool.springmvc.transaction.registry.TransactionServiceRegistryPostProcessor;

@Configuration
public  class TransactionServiceSupport extends TransactionServiceRegistryPostProcessor {
}