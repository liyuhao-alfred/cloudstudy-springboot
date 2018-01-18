/******************************************************************
 *    
 *    Package:     com.cloudstudy.validate
 *
 *    Filename:    package-info.java
 *
 *    @author:     alfred
 *
 *    @version:    1.0.0
 *
 *    Create at:   2018年1月18日 上午11:27:55
 *
 *    - first revision 
 *
 *****************************************************************/
/**
 * 
 * 
 * @ClassName package-info 
 * @author alfred 
 * @Date 2018年1月18日 上午11:27:55 
 * @version 1.0.0  
 */
package com.cloudstudy.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidateUtil implements Serializable {
	/**
	 * @Field @serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	public static <T> List<String> validate(T t) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

		List<String> messageList = new ArrayList<String>();
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String message = "验证失败[" + constraintViolation.getPropertyPath() + "]约束是:"
					+ constraintViolation.getMessage() + ",接受的数据是:" + constraintViolation.getInvalidValue();
			messageList.add(message);
		}
		return messageList;
	}

	public static <T> boolean isQulify(T t) {
		List<String> messageList = validate(t);
		if (messageList == null || messageList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}