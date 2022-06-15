package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindeksScoreCheckService;

@Service
public class FindeksServiceAdapter implements FindeksScoreCheckService {

	Random random = new Random();
	HashMap<String, Integer> findeksScore;

	@Override
	public int checkFindeksScore(String nationalityIdentification) {

		findeksScore = new HashMap<String, Integer>();
		int score = random.nextInt(1900) + 1;

		System.out.println(score);
		findeksScore.put(nationalityIdentification, score);
		return score;
	}
}
