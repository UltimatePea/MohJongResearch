package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class MohJongContainer {
	
	private ArrayList<MohJong> mohJongs;

	public int size() {
		return mohJongs.size();
	}

	public boolean isEmpty() {
		return mohJongs.isEmpty();
	}

	public boolean contains(Object o) {
		return mohJongs.contains(o);
	}

	public int indexOf(Object o) {
		return mohJongs.indexOf(o);
	}

	public MohJong get(int index) {
		return mohJongs.get(index);
	}

	public String toString() {
		return mohJongs.toString();
	}

	public MohJong set(int index, MohJong element) {
		return mohJongs.set(index, element);
	}

	public boolean add(MohJong e) {
		return mohJongs.add(e);
	}

	public void add(int index, MohJong element) {
		mohJongs.add(index, element);
	}

	public boolean equals(Object o) {
		return mohJongs.equals(o);
	}

	public MohJong remove(int index) {
		return mohJongs.remove(index);
	}

	public boolean remove(Object o) {
		return mohJongs.remove(o);
	}

	public void clear() {
		mohJongs.clear();
	}

	public Iterator<MohJong> iterator() {
		return mohJongs.iterator();
	}

	public List<MohJong> subList(int fromIndex, int toIndex) {
		return mohJongs.subList(fromIndex, toIndex);
	}

	public void forEach(Consumer<? super MohJong> action) {
		mohJongs.forEach(action);
	}

	public void sort(Comparator<? super MohJong> c) {
		mohJongs.sort(c);
	}
	
	public void shuffle(){
		Collections.shuffle(mohJongs);
	}
	
	public MohJong getFirst(){
		return get(0);
	}
	
	public MohJong getLast(){
		return get(size() - 1);
	}

	public ArrayList<MohJong> getMohJongs() {
		return mohJongs;
	}

	/**
	 * @return
	 * @see java.util.ArrayList#clone()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MohJong> clone() {
		Object o = mohJongs.clone();
		if (o instanceof ArrayList<?>) {
			return (ArrayList<MohJong>)o;
		}
		return null;
	}

	public MohJongContainer(ArrayList<MohJong> mohJongs) {
		super();
		this.mohJongs = mohJongs;
		if (this.mohJongs == null) {
			this.mohJongs = new ArrayList<MohJong>();
		}
		
	}
	

}
