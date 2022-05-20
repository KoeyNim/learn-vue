package com.project.vue.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	/**
	 *  Class 안에 있는 변수명을 받음.
	 */
	public static List<String> getColList(Class<?> Entity) {
		List<String> list = Arrays
								.stream(Entity.getDeclaredFields())
								.parallel()
				                .map(entity -> entity.getName().substring(0,1).toUpperCase() + entity.getName().substring(1))
				                .collect(Collectors.toList());
//		ArrayList<String> list = new ArrayList<>();
//		for (Field lists : Entity.getDeclaredFields()) {
//			String firstUpper = lists.getName().substring(0,1).toUpperCase() + lists.getName().substring(1);
//			list.add(firstUpper);
//		}
		return list;
	}
}
