package ru.amse.soultakov.softwaredesign.binarytree;

import java.util.Collection;

public interface BinaryTree<T extends Comparable<? super T>> {
		
	boolean addAll(Collection<T> collection);
	
	boolean add(T element);
	
	boolean contains(T element);
	
	T getMinimum();
	
	T getMaximum();
	
	int size();
	
	boolean isEmpty();
}
