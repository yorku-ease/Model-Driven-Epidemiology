package epimodel.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListForwardDecorator <T> implements List<T> {
	List<T> to_decorate;
	
	public ListForwardDecorator(List<T> to_decorate) {
		this.to_decorate = to_decorate;
	}

	@Override
	public boolean add(T e) {
		return to_decorate.add(e);
	}

	@Override
	public void add(int index, T element) {
		to_decorate.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean res = true;
		for (T l : c)
			res &= add(l);
		return res;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return to_decorate.addAll(index, c);
	}

	@Override
	public void clear() {
		to_decorate.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		return to_decorate.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return to_decorate.containsAll(c);
	}

	@Override
	public T get(int index) {
		return to_decorate.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return to_decorate.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return to_decorate.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return to_decorate.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return to_decorate.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return to_decorate.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return to_decorate.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return to_decorate.remove(o);
	}

	@Override
	public T remove(int index) {
		return to_decorate.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return to_decorate.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return to_decorate.retainAll(c);
	}

	@Override
	public T set(int index, T element) {
		return to_decorate.set(index, element);
	}

	@Override
	public int size() {
		return to_decorate.size();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return to_decorate.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return to_decorate.toArray();
	}

	@Override
	public <U> U[] toArray(U[] a) {
		return to_decorate.toArray(a);
	}
}
