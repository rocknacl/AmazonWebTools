package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import dao.SellerFeedbackDao;

public class SellerIDQueue {
	final static int qSize = 5000;
	private static Map<String, ConcurrentLinkedQueue<String>> ques = new HashMap<String, ConcurrentLinkedQueue<String>>();

	public static ConcurrentLinkedQueue<String> getQueue(String country) {

		ConcurrentLinkedQueue<String> q = ques.get(country);
		if (q == null) {
			q = new ConcurrentLinkedQueue<String>();
			ques.put(country, q);
		}
		return q;
	}

	public static List<String> getSellerIDs(String country, int limit) {
		ConcurrentLinkedQueue<String> q = SellerIDQueue.getQueue(country);
		if (q.isEmpty()) {
			q.addAll(SellerFeedbackDao.getSellerIDs(country, qSize));
		}
		List<String> ids = new ArrayList<String>();
		int count = 0;
		while (count < limit && !q.isEmpty()) {
			ids.add(q.poll());
			count++;
		}
		System.out.println(q.size());
		return ids;
	}

}
