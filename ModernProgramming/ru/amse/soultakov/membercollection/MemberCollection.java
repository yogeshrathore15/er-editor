package ru.amse.soultakov.membercollection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemberCollection extends AbstractCollection<AccessibleObject> {

	private ArrayList<AccessibleObject> members = new ArrayList<AccessibleObject>();
	
	public MemberCollection(Class clazz) {
		initElements(clazz);
	}
	
	private void initElements(Class clazz) {
		for(AccessibleObject m : clazz.getDeclaredFields()) {
			members.add(m);
		}
		for(AccessibleObject m : clazz.getDeclaredConstructors()) {
			members.add(m);
		}
		for(AccessibleObject m : clazz.getDeclaredMethods()) {
			members.add(m);
		}
	}

	@Override
	public Iterator<AccessibleObject> iterator() {
		return new Iterator<AccessibleObject>() {
			Iterator<AccessibleObject> itr = members.iterator();
			
			public boolean hasNext() {
				return itr.hasNext();
			}

			public AccessibleObject next() {
				return itr.next();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int size() {
		return members.size();
	}
	
	public static List<AccessibleObject> getAnnotatedMembers(Class clazz, 
			Class<? extends Annotation> a) {
		MemberCollection mc = new MemberCollection(clazz);
		ArrayList<AccessibleObject> annotatedMembers = new ArrayList<AccessibleObject>();
		for(AccessibleObject o : mc) {
			if (o.isAnnotationPresent(a)) {
				annotatedMembers.add(o);
			}
		}
		return annotatedMembers;
	}

}
