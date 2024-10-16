package com.company.helper;

public class MessIdGenerator {

	public String messId(String universityName, int messNo) {
		StringBuilder messId = new StringBuilder();

		String[] words = universityName.split("[\\.\\s]");

		for (String word : words) {
			if (!word.isEmpty()) {
				messId.append(word.charAt(0));
			}
		}

		messId.append(messNo);

		return messId.toString();
	}

}
