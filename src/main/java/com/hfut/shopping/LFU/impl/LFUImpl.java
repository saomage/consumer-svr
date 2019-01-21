package com.hfut.shopping.LFU.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.springframework.stereotype.Component;

import com.hfut.shopping.LFU.LFU;

@Component
public class LFUImpl<V,E>implements LFU<V, E> {
	
	static class LFUFactory{
		@SuppressWarnings("rawtypes")
		public static  final  LFUImpl instance=new LFUImpl<>();
	}
	
	@SuppressWarnings("unchecked")
	public static<V, E> LFU<V, E> getLFU() {
		return LFUFactory.instance;
	}
	
	ListEntry<E> begin;
	
	ListEntry<E> end;
	
	Map<V, Entry<E>> map;
	
	Map<Entry<E>, V> map2;
	
	Queue<V> queue;
	
	private Integer size=0;
	
	private static final Integer QUEUE_SIZE=100;

	private static final Integer CAPACITY=10;
	
	public LFUImpl() {
		begin=new ListEntry<E>(1);
		end=begin;
		map=new HashMap<>();
		map2=new HashMap<>();
		queue=new LinkedList<>();
	}

	@Override
	public synchronized void add(V v, E e) {
		if(map.get(v)!=null) {
			this.get(v);
			return;
		}
		V v2 = this.addQueue(v);
		this.reduce(v2);
		Entry<E> entry = new Entry<E>(e);
		map.put(v, entry);
		map2.put(entry, v);
		this.insert(entry);
		if(size==CAPACITY) {
			this.remove();
		}else {
			size++;
		}
	}

	@Override
	public synchronized E get(V v) {
		if(map.get(v)==null) {
			return null;
		}
		V v2 = this.addQueue(v);
		this.reduce(v2);
		this.increase(v);
		return map.get(v).getE();
	}
	
	private void increase(V v) {
		Entry<E> entry = map.get(v);
		ListEntry<E>listEntry= entry.getHead();
		if(listEntry.getBegin()==entry) {
			listEntry.setBegin(entry.getNext());
		}
		if(listEntry.getEnd()==entry) {
			listEntry.setEnd(entry.getBefore());
		}
		Optional.ofNullable(entry.getBefore()).ifPresent(u->u.setNext(entry.getNext()));
		Optional.ofNullable(entry.getNext()).ifPresent(u->u.setBefore(entry.getBefore()));
		entry.setBefore(null);
		entry.setNext(null);
		int num = listEntry.getNum();
		listEntry.sizeDown();
		if(listEntry.getBefore()==null) {
			ListEntry<E> listEntry2 = new ListEntry<E>(num+1);
			listEntry.setBefore(listEntry2);
			listEntry2.setNext(listEntry);
			begin=listEntry2;
			listEntry2.setBegin(entry);
			listEntry2.setEnd(entry);
			listEntry2.setSize(1);
			entry.setHead(listEntry2);
		}else if(listEntry.getBefore().getNum()>num+1) {
			ListEntry<E> listEntry2 = new ListEntry<E>(num+1);
			listEntry2.setNext(listEntry);
			listEntry2.setBefore(listEntry.getBefore());
			listEntry.getBefore().setNext(listEntry2);
			listEntry.setBefore(listEntry2);
			listEntry2.setBegin(entry);
			listEntry2.setEnd(entry);
			listEntry2.setSize(1);
			entry.setHead(listEntry2);
		}else {
			ListEntry<E> listEntry2=listEntry.getBefore();
			listEntry2.getEnd().setNext(entry);
			entry.setBefore(listEntry2.getEnd());
			listEntry2.setEnd(entry);
			listEntry2.sizeUp();
			entry.setHead(listEntry2);
		}
		if(listEntry.getSize()==0)
			this.deleteListEntry(listEntry);
	}
	
	private void remove(){
		Entry<E> entry = end.getBegin();
		if(end.getSize()==1) {
			this.deleteListEntry(end);
		}else {
			entry.getNext().setBefore(null);
			end.setBegin(entry.getNext());
			end.sizeDown();
		}
		V v = map2.get(entry);
		map2.remove(entry);
		map.remove(v);
	}
	
	private void insert(Entry<E> entry) {
		ListEntry<E> listEntry=end;
		if(listEntry.getNum()<1)
			listEntry=listEntry.getBefore();
		if(listEntry.getNum()==1) {
			if(listEntry.getSize()==0) {
				listEntry.setBegin(entry);
				listEntry.setEnd(entry);
				listEntry.sizeUp();
				entry.setHead(listEntry);
			}else {
				listEntry.getEnd().setNext(entry);
				entry.setBefore(listEntry.getEnd());
				listEntry.setEnd(entry);
				listEntry.sizeUp();
				entry.setHead(listEntry);
			}
		}else {
			ListEntry<E> listEntry2 = new ListEntry<E>(1);
			if(end==listEntry) {
				end=listEntry2;
			}
			listEntry2.setBefore(listEntry);
			listEntry2.setNext(listEntry.getNext());
			Optional.ofNullable(listEntry.getNext()).ifPresent(u->u.setBefore(listEntry2));
			listEntry.setNext(listEntry2);
			listEntry2.setBegin(entry);
			listEntry2.setEnd(entry);
			listEntry2.setSize(1);
			entry.setHead(listEntry2);
		}
	}

	private V addQueue(V v) {
		queue.add(v);
		if(queue.size()>QUEUE_SIZE) {
			return queue.poll();
		}
		return null;
	}
	
	private void reduce(V v) {
		Entry<E> entry = map.get(v);
		if(entry==null)
			return;
		ListEntry<E>listEntry= entry.getHead();
		if(listEntry.getBegin()==entry) {
			listEntry.setBegin(entry.getNext());
		}
		if(listEntry.getEnd()==entry) {
			listEntry.setEnd(entry.getBefore());
		}
		Optional.ofNullable(entry.getBefore()).ifPresent(u->u.setNext(entry.getNext()));
		Optional.ofNullable(entry.getNext()).ifPresent(u->u.setBefore(entry.getBefore()));
		entry.setBefore(null);
		entry.setNext(null);
		int num = listEntry.getNum();
		listEntry.sizeDown();
		if(listEntry.getNext()==null) {
			ListEntry<E> listEntry2 = new ListEntry<E>(num-1);
			listEntry.setNext(listEntry2);
			listEntry2.setBefore(listEntry);
			end=listEntry2;
			listEntry2.setBegin(entry);
			listEntry2.setEnd(entry);
			listEntry2.setSize(1);
			entry.setHead(listEntry2);
		}else if(listEntry.getNext().getNum()<num-1) {
			ListEntry<E> listEntry2 = new ListEntry<E>(num-1);
			listEntry2.setBefore(listEntry);
			listEntry2.setNext(listEntry.getNext());
			listEntry.setNext(listEntry2);
			listEntry2.getNext().setBefore(listEntry2);
			listEntry2.setBegin(entry);
			listEntry2.setEnd(entry);
			listEntry2.setSize(1);
			entry.setHead(listEntry2);
		}else {
			ListEntry<E> listEntry2=listEntry.getNext();
			listEntry2.getEnd().setNext(entry);
			entry.setBefore(listEntry2.getEnd());
			listEntry2.setEnd(entry);
			listEntry2.sizeUp();
			entry.setHead(listEntry2);
		}
		if(listEntry.getSize()==0)
			this.deleteListEntry(listEntry);
	}
	
	private void deleteListEntry(ListEntry<E> entry) {
		if(this.begin==entry&&this.end==entry)
			return;
		if(this.begin==entry)
			begin=entry.getNext();
		if(this.end==entry)
			end=entry.getBefore();
		Optional.ofNullable(entry.getBefore()).ifPresent(u->u.setNext(entry.getNext()));
		Optional.ofNullable(entry.getNext()).ifPresent(u->u.setBefore(entry.getBefore()));
	}

}

class Entry<E>{
	public Entry(E e) {
		this.e = e;
	}
	private E e;
	private Entry<E> before;
	private Entry<E> next;
	private ListEntry<E> head;
	public E getE() {
		return e;
	}
	public void setE(E e) {
		this.e = e;
	}
	public Entry<E> getBefore() {
		return before;
	}
	public void setBefore(Entry<E> before) {
		this.before = before;
	}
	public Entry<E> getNext() {
		return next;
	}
	public void setNext(Entry<E> next) {
		this.next = next;
	}
	public ListEntry<E> getHead() {
		return head;
	}
	public void setHead(ListEntry<E> head) {
		this.head = head;
	}
}

class ListEntry<E>{
	public ListEntry(int num) {
		this.num = num;
	}
	private final int num;
	private int size;
	private ListEntry<E> before;
	private ListEntry<E> next;
	private Entry<E> begin;
	private Entry<E> end;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ListEntry<E> getBefore() {
		return before;
	}

	public void setBefore(ListEntry<E> before) {
		this.before = before;
	}

	public ListEntry<E> getNext() {
		return next;
	}

	public void setNext(ListEntry<E> next) {
		this.next = next;
	}

	public Entry<E> getBegin() {
		return begin;
	}

	public void setBegin(Entry<E> begin) {
		this.begin = begin;
	}

	public Entry<E> getEnd() {
		return end;
	}

	public void setEnd(Entry<E> end) {
		this.end = end;
	}

	public int getNum() {
		return num;
	}

	void sizeUp() {
		size++;
	}
	
	void sizeDown() {
		size--;
	}
}