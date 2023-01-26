package com.example.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HashMapTest {
	/*
	
You've created a new programming language, and now you've decided to add hashmap support to it. Actually you are quite disappointed that in common programming languages it's impossible to add a number to all hashmap keys, or all its values. So you've decided to take matters into your own hands and implement your own hashmap in your new language that has the following operations:

insert x y - insert an object with key x and value y.
get x - return the value of an object with key x.
addToKey x - add x to all keys in map.
addToValue y - add y to all values in map.
To test out your new hashmap, you have a list of queries in the form of two arrays: queryTypes contains the names of the methods to be called (eg: insert, get, etc), and queries contains the arguments for those methods (the x and y values).

Your task is to implement this hashmap, apply the given queries, and to find the sum of all the results for get operations.

Example

For queryType = ["insert", "insert", "addToValue", "addToKey", "get"] and query = [[1, 2], [2, 3], [2], [1], [3]], the output should be solution(queryType, query) = 5.

The hashmap looks like this after each query:

1 query: {1: 2}
2 query: {1: 2, 2: 3}
3 query: {1: 4, 2: 5}
4 query: {2: 4, 3: 5}
5 query: answer is 5
The result of the last get query for 3 is 5 in the resulting hashmap.



For queryType = ["insert", "addToValue", "get", "insert", "addToKey", "addToValue", "get"] and query = [[1, 2], [2], [1], [2, 3], [1], [-1], [3]], the output should be solution(queryType, query) = 6.

The hashmap looks like this after each query:

1 query: {1: 2}
2 query: {1: 4}
3 query: answer is 4
4 query: {1: 4, 2: 3}
5 query: {2: 4, 3: 3}
6 query: {2: 3, 3: 2}
7 query: answer is 2
The sum of the results for all the get queries is equal to 4 + 2 = 6.

Input/Output

[execution time limit] 3 seconds (java)

[input] array.string queryType

Array of query types. It is guaranteed that each queryType[i] is either "addToKey", "addToValue", "get", or "insert".

Guaranteed constraints:
1 ≤ queryType.length ≤ 105.

[input] array.array.integer query

Array of queries, where each query is represented either by two numbers for insert query or by one number for other queries. It is guaranteed that during all queries all keys and values are in the range [-109, 109].

Guaranteed constraints:
query.length = queryType.length,
1 ≤ query[i].length ≤ 2.

[output] integer64

The sum of the results for all get queries.
	 */
	@Test
	void test() {
		String[] queryType = new String[]{"insert", "insert", "addToValue", "addToKey", "get"};
		int[][] query = new int[][] {{1, 2}, {2, 3}, {2}, {1}, {3}};
		
		assertEquals(5, solutions(queryType, query));
		queryType = new String[] {"insert", "addToValue", "get", "insert", "addToKey", "addToValue", "get"};
		query = new int[][] {{1, 2},{2},{1}, {2, 3}, {1}, {-1}, {3}};
		assertEquals(6, solutions(queryType, query));
	
	}

	
	int solutions(String[] queryType, int[][] query) {
		
		MyMap<Integer, Integer> map = new MyMap<>();
		int sum=0;
		for (int i=0; i< queryType.length; i++) {
			
			sum+= mappingFunction.apply(map, queryType[i], query[i]);
			
		}
		return sum;
	}
	
	TriFunction<MyMap<Integer, Integer> , String, int[], Integer> mappingFunction = 
			(m,qt, q) -> {
				
				switch (qt){
					case "insert":
						m.add(q[0], q[1]);
						break;
					case "addToValue":
						m.addToValue(q[0]);
						break;
					case "addToKey":
						m.addToKey(q[0]);
						break;
					case "get":
						return m.get(q[0]);
				}
				return 0;
			};
	}
	
	class MyMap<K, V>{
		
		int CAPACITY = 10;
		Node[] hashNodes = new Node[CAPACITY];

		static class Node{
			int key;
			int value;
			Node next;
			Node(int key, int value) {
				this.key =key;
				this.value=value;
				next=null;
			}
		}
		
		public void add(int i, int j) {
			int hash = getHash(i);
			Node n = new Node(i,j);
			Node m = hashNodes[hash];
			if (m==null)
				hashNodes[hash] = n;
			else
				m.next=n;
		}

		public void addToKey(int i) {
			// empty all map to temp ListOfNodes
			// reinsert all
			List<Node> list = new ArrayList<>();
			for (int j=0; j< hashNodes.length; j++) {
				Node n = hashNodes[j];
				while (n!=null) {
					list.add(new Node(n.key, n.value));
					n=n.next;
				}
				hashNodes[j] = null;
			}
			for (Node n: list) {
				add(n.key +1, n.value);
			}
		}

		public void addToValue(int i) {
			for (int j=0; j< hashNodes.length; j++) {
				Node n = hashNodes[j];
				if (n!=null) {
					n.value+=i;
					Node nx = n.next;
					while (nx!=null) {
						nx.value+=i;
						nx = nx.next;
					}
				}
			}
			
		}
		
		/*
		 * Node remove(int key) { Node n = hashNodes[getHash(key)];
		 * hashNodes[getHash(key)] = null; return n; }
		 */

		public Integer get(int i) {
			Node m = hashNodes[getHash(i)];
			Node nxt = m;
			while (nxt!=null) {
				if (nxt.key == i) {
					return nxt.value;
				}
				nxt=nxt.next;
			}
			return 0;
		}
		
		
		int getHash(int key){
			return key % CAPACITY;
		}
		
	}
	
	@FunctionalInterface
	interface TriFunction<A,B,C,R> {

	    R apply(A a, B b, C c);
}
